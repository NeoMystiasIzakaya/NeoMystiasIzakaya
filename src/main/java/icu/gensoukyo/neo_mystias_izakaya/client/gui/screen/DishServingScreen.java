/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.screen;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CupboardHud;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.IncubatorHud;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.Optional;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class DishServingScreen extends AbstractContainerScreen<DishServingMenu> {
    private static final Identifier BACKGROUND = id("textures/gui/dish_serve.png");

    // --- 渲染常量（与 CanteenOverlay 保持一致） ---
    private static final int DARK_RED = 0xFFAA0000;
    private static final int DARK_GREEN = 0xFF00FF00;
    private static final Identifier CONFIRM_SPRITE = Identifier.withDefaultNamespace("container/beacon/confirm");
    private static final Identifier CANCEL_SPRITE = Identifier.withDefaultNamespace("container/beacon/cancel");

    private CupboardHud cupBoardHud;
    private IncubatorHud incubatorHud;

    public DishServingScreen(DishServingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 256, 219);
    }

    @Override
    protected void init() {
        super.init();

        int x1 = getLeftPos() + getImageWidth() + 4;
        int width = Math.min(130, getMinecraft().getWindow().getGuiScaledWidth() - x1 - 10);
        int x2 = Math.max(getLeftPos() - 130, 10);
        cupBoardHud = new CupboardHud(x2, getTopPos(), getLeftPos() - x2 - 4, getImageHeight(), this::onCupboardHudItemResourceClick);
        incubatorHud = new IncubatorHud(x1, getTopPos(), width, getImageHeight(), this::onIncubatorHudItemResourceClick);
        addRenderableWidget(cupBoardHud);
        addRenderableWidget(incubatorHud);
        ClientPayloadSender.sendRequestCupboardBeveragesInfoMessage();
        ClientPayloadSender.sendRequestIncubatorCuisinesInfoMessage();
    }

    private void onCupboardHudItemResourceClick(ItemResourceWithCount resource) {
        if (menu.getCarried().isEmpty() || resource.itemResource().equals(ItemResource.of(menu.getCarried()))) {
            ClientPayloadSender.sendRequestCupboardExtractItemToPlayerHandMessage(resource.itemResource());
        } else {
            ClientPayloadSender.sendRequestCupboardInsertItemFromPlayerHandMessage();
        }
    }

    private void onIncubatorHudItemResourceClick(ItemResourceWithCount resource) {
        if (menu.getCarried().isEmpty() || resource.itemResource().equals(ItemResource.of(menu.getCarried()))) {
            ClientPayloadSender.sendRequestIncubatorExtractItemToPlayerHandMessage(resource.itemResource());
        } else {
            ClientPayloadSender.sendRequestIncubatorInsertItemFromPlayerHandMessage();
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        // 在槽位上方绘制确认/取消精灵图
        drawConfirmCancelSprites(graphics);
    }

    @Override
    protected void extractTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractTooltip(graphics, mouseX, mouseY);
        // 为订单需求物品渲染 tooltip
        drawOrderRequirementTooltips(graphics, mouseX, mouseY);
    }

    private void getTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, ItemStack item) {
        graphics.setTooltipForNextFrame(this.font, this.getTooltipFromContainerItem(item), item.getTooltipImage(), item, mouseX, mouseY, item.get(DataComponents.TOOLTIP_STYLE));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0.0F, 0.0F, 256, 256, 256, 256);

        // 绘制餐桌详细信息
        drawDiningTableDetails(graphics, leftPos, topPos);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    }

    // === 餐桌详细绘制（从 CanteenOverlay 迁移） ===

    /**
     * 绘制所有餐桌的详细信息：矩形背景 + 订单状态
     */
    private void drawDiningTableDetails(GuiGraphicsExtractor graphics, int leftPos, int topPos) {
        var font = Minecraft.getInstance().font;
        DishServingMenu menu = this.menu;
        int tableCount = menu.getTableCount();
        boolean lastRowSingle = menu.isLastRowSingle();

        for (int i = 0; i < tableCount; i++) {
            // 获取该餐桌的槽位对（每 2 个槽位 = 1 个餐桌）
            Slot cuisineSlot = this.menu.slots.get(i * 2);
            if (!(cuisineSlot.container instanceof DiningTableBlockEntity diningTable)) {
                continue;
            }

            int cellX = DishServingMenu.getCellX(i, lastRowSingle, tableCount);
            int cellY = DishServingMenu.getCellY(i);

            // 在 GUI 坐标系中绘制
            int x0 = leftPos + cellX;
            int y0 = topPos + cellY;
            int x1 = x0 + DishServingMenu.CELL_WIDTH;
            int y1 = y0 + 24; // 单元格高度 24

            graphics.fill(x0, y1, x1, y1 + 1, RecipeScreen.POSITIVE_OUT_COLOR);

            // 绘制槽位区域边框（每个 18×18 槽位独立画框）
            int slotTop = y0 + 2;
            int cuisineLeft = x0 + DishServingMenu.SLOT_X_OFFSET - 1;
            int beverageLeft = cuisineLeft + DishServingMenu.SLOT_SPACING;
            drawSlotAreaBorder(graphics, cuisineLeft, slotTop, cuisineLeft + 18, slotTop + 18);
            drawSlotAreaBorder(graphics, beverageLeft, slotTop, beverageLeft + 18, slotTop + 18);

            if (diningTable.isOccupied()) {
                renderOccupiedTable(graphics, font, diningTable, x0, y0);
            } else {
                renderIdleTable(graphics, font, x0, y0);
            }
        }
    }

    /**
     * 在槽位上方绘制确认/取消精灵图
     */
    private void drawConfirmCancelSprites(GuiGraphicsExtractor graphics) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        DishServingMenu menu = this.menu;
        int tableCount = menu.getTableCount();
        boolean lastRowSingle = menu.isLastRowSingle();

        for (int i = 0; i < tableCount; i++) {
            Slot cuisineSlot = this.menu.slots.get(i * 2);
            if (!(cuisineSlot.container instanceof DiningTableBlockEntity diningTable)) {
                continue;
            }

            int cellX = DishServingMenu.getCellX(i, lastRowSingle, tableCount);
            int cellY = DishServingMenu.getCellY(i);

            IzakayaOrder order = diningTable.getCurrentOrder();

            if (IzakayaOrder.isEmpty(order)) {
                continue; // 无订单不显示确认/取消图标
            }

            // 仅普客绘制确认/取消图标（稀客显示 Tag，无物品图标）
            if (!order.isRare()) {
                int slotX = leftPos + cellX + 44;
                int slotY = topPos + cellY + 3;

                // 菜品确认/取消
                ClientNMIDataAccessor instance = ClientNMIDataAccessor.INSTANCE;
                ItemStack cuisineItem = instance.getRecipeMap().getRecipeMap().get(order.cuisine()).recipe().output().item().value().getDefaultInstance();
                if (!diningTable.getCuisine().isEmpty()) {
                    if (diningTable.getCuisine().is(cuisineItem.getItem())) {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIRM_SPRITE, slotX, slotY, 18, 18);
                    } else {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CANCEL_SPRITE, slotX, slotY, 18, 18);
                    }
                }

                // 饮品确认/取消
                int beverageSlotX = slotX + DishServingMenu.SLOT_SPACING;
                Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(order.beverage());
                ItemStack beverageItem = itemReference.map(ref -> ref.value().getDefaultInstance()).orElse(ItemStack.EMPTY);
                if (!diningTable.getBeverage().isEmpty() && !beverageItem.isEmpty()) {
                    if (diningTable.getBeverage().is(beverageItem.getItem())) {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIRM_SPRITE, beverageSlotX, slotY, 18, 18);
                    } else {
                        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, CANCEL_SPRITE, beverageSlotX, slotY, 18, 18);
                    }
                }
            }
        }
    }

    /**
     * 为右侧订单需求物品渲染 tooltip
     */
    private void drawOrderRequirementTooltips(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        DishServingMenu menu = this.menu;
        int tableCount = menu.getTableCount();
        boolean lastRowSingle = menu.isLastRowSingle();

        for (int i = 0; i < tableCount; i++) {
            Slot cuisineSlot = this.menu.slots.get(i * 2);
            if (!(cuisineSlot.container instanceof DiningTableBlockEntity diningTable)) {
                continue;
            }
            if (!diningTable.isOccupied()) {
                continue;
            }

            IzakayaOrder order = diningTable.getCurrentOrder();
            if (order.isRare()) {
                continue; // 稀客显示 Tag 文字，无物品图标
            }

            int cellX = DishServingMenu.getCellX(i, lastRowSingle, tableCount);
            int cellY = DishServingMenu.getCellY(i);

            // 菜品需求物品位置 (16x16)
            int cuisineX = leftPos + cellX + 44;
            int cuisineY = topPos + cellY + 3;
            if (mouseX >= cuisineX && mouseX < cuisineX + 16 && mouseY >= cuisineY && mouseY < cuisineY + 16) {
                ClientNMIDataAccessor instance = ClientNMIDataAccessor.INSTANCE;
                ItemStack cuisineItem = instance.getRecipeMap().getRecipeMap().get(order.cuisine()).recipe().output().item().value().getDefaultInstance();
                getTooltip(graphics, mouseX, mouseY, cuisineItem);
                return;
            }

            // 饮品需求物品位置 (16x16)
            int beverageX = leftPos + cellX + 62;
            int beverageY = topPos + cellY + 3;
            if (mouseX >= beverageX && mouseX < beverageX + 16 && mouseY >= beverageY && mouseY < beverageY + 16) {
                Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(order.beverage());
                ItemStack beverageItem = itemReference.map(ref -> ref.value().getDefaultInstance()).orElse(ItemStack.EMPTY);
                if (!beverageItem.isEmpty()) {
                    getTooltip(graphics, mouseX, mouseY, beverageItem);
                }
                return;
            }
        }
    }

    /**
     * 渲染空闲餐桌
     */
    private void renderIdleTable(GuiGraphicsExtractor graphics, Font font, int x0, int y0) {
        graphics.text(font, Component.translatable("gui.neo_mystias_izakaya.idle"), x0 + 80, y0 + 7, 0xFF000000, false);
    }

    /**
     * 渲染有客餐桌：订单未完成显示需求，已完成显示物品
     */
    private void renderOccupiedTable(GuiGraphicsExtractor graphics, Font font,
                                     DiningTableBlockEntity diningTable, int x0, int y0) {
        IzakayaOrder order = diningTable.getCurrentOrder();
        Identifier customerId = diningTable.getCustomerId();

        // 客人名（右侧，与订单需求分离避免重叠）
        Component customerName = Component.translatable("customer.neo_mystias_izakaya." + customerId.getPath());
        int nameWidth = font.width(customerName);
        int nameX = x0 + 78;
        int nameY = y0 + 7;
        int nameMaxWidth = 40;
        if (nameWidth > nameMaxWidth) {
            NMIClientUtil.renderScaledText(graphics, font, customerName, nameX, nameY, 0xFF000000, false, (float) nameMaxWidth / nameWidth);
        } else {
            graphics.text(font, customerName, nameX, nameY, 0xFF000000, false);
        }

        // 订单未完成：显示需求
        if (order.isRare()) {
            // 稀客：两个都是 Tag，在客人名下方显示 Tag 名称
            Component cuisineTag = Component.translatable(order.cuisine().toLanguageKey("tag"));
            Component beverageTag = Component.translatable(order.beverage().toLanguageKey("tag"));

            int cuisineColor = NMIClientItemTagUtil.get(diningTable.getCuisine()).positiveTags().contains(order.cuisine()) ? DARK_GREEN : DARK_RED;
            int beverageColor = NMIClientItemTagUtil.get(diningTable.getBeverage()).positiveTags().contains(order.beverage()) ? DARK_GREEN : DARK_RED;

            graphics.text(font, cuisineTag, x0 + 44, y0 + 3, cuisineColor, false);
            graphics.text(font, beverageTag, x0 + 44, y0 + 13, beverageColor, false);
        } else {
            // 普客：显示需求菜品 + 饮品图标
            ClientNMIDataAccessor instance = ClientNMIDataAccessor.INSTANCE;
            ItemStack cuisineItem = instance.getRecipeMap().getRecipeMap().get(order.cuisine()).recipe().output().item().value().getDefaultInstance();
            Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(order.beverage());
            ItemStack beverageItem = ItemStack.EMPTY;
            if (itemReference.isPresent()) {
                beverageItem = itemReference.get().value().getDefaultInstance();
            }
            graphics.item(cuisineItem, x0 + 44, y0 + 3);
            graphics.item(beverageItem, x0 + 62, y0 + 3);
        }
    }

    /**
     * 绘制槽位区域边框（包裹菜品槽 + 饮品槽两个 18×18 槽位）
     */
    private void drawSlotAreaBorder(GuiGraphicsExtractor graphics, int left, int top, int right, int bottom) {
        // 浅色半透明填充
        graphics.fill(left + 1, top + 1, right - 1, bottom - 1, 0x20FFFFFF);
        // 上边框
        graphics.fill(left, top, right, top + 1, 0xFFAAAAAA);
        // 下边框
        graphics.fill(left, bottom - 1, right, bottom, 0xFFAAAAAA);
        // 左边框
        graphics.fill(left, top, left + 1, bottom, 0xFFAAAAAA);
        // 右边框
        graphics.fill(right - 1, top, right, bottom, 0xFFAAAAAA);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        boolean b = super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        if (cupBoardHud.isMouseOver(mouseX, mouseY)) {
            return cupBoardHud.mouseScrolled(mouseX, mouseY, scrollX, scrollY) || b;
        }
        if (incubatorHud.isMouseOver(mouseX, mouseY)) {
            return incubatorHud.mouseScrolled(mouseX, mouseY, scrollX, scrollY) || b;
        }
        return b;
    }

    public boolean isPauseScreen() {
        return true;
    }
}
