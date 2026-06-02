/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.overlay;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public class CanteenOverlay implements GuiLayer {

    /**
     * 每个物品矩形宽度
     */
    private static final int ITEM_RECT_WIDTH = 80;
    /**
     * 每个物品矩形高度
     */
    private static final int ITEM_RECT_HEIGHT = 24;
    /**
     * 物品矩形之间的间距
     */
    private static final int ITEM_RECT_SPACING = 4;
    /**
     * 矩形填充颜色 (半透明灰)
     */
    private static final int FILL_COLOR = 0x40FFFFFF;
    /**
     * 矩形边框颜色 (白色)
     */
    private static final int BORDER_COLOR = 0xFFFFFFFF;

    private static final Identifier CONFIRM_SPRITE = Identifier.withDefaultNamespace("container/beacon/confirm");
    private static final Identifier CANCEL_SPRITE = Identifier.withDefaultNamespace("container/beacon/cancel");
    public static final int DARK_RED = 0xFFAA0000;
    public static final int DARK_GREEN = 0xFF00FF00;

    @Override
    public void render(@NonNull GuiGraphicsExtractor guiGraphics, @NonNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        if (player == null || level == null) {
            return;
        }

        ItemStack itemBySlot = player.getItemBySlot(EquipmentSlot.HEAD);

        // 获取屏幕尺寸
        var window = Minecraft.getInstance().getWindow();
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        int centerY = screenHeight / 2;

        // 厨房用具列表
        List<BlockPos> kitchenware = itemBySlot.get(NMIDataComponentTypes.BOUND_KITCHENWARE);
        // 餐桌列表
        List<BlockPos> diningTables = itemBySlot.get(NMIDataComponentTypes.BOUND_DINING_TABLES);

        // --- 左侧：厨房用具矩形组 ---
        if (kitchenware != null && !kitchenware.isEmpty()) {
            drawKitchenwareOverlay(guiGraphics, kitchenware, centerY, level);
        }

        // --- 右侧：餐桌矩形组 ---
        if (diningTables != null && !diningTables.isEmpty()) {
            drawDiningTableOverlay(guiGraphics, diningTables, screenWidth, centerY, level);
        }
    }

    /**
     * 在左侧绘制厨房用具叠加矩形组（以垂直中线为对称轴上下居中）
     */
    private void drawKitchenwareOverlay(GuiGraphicsExtractor guiGraphics, List<BlockPos> kitchenwareList, int centerY, ClientLevel level) {
        int count = kitchenwareList.size();
        int totalHeight = count * ITEM_RECT_HEIGHT + (count - 1) * ITEM_RECT_SPACING;
        int startY = centerY - totalHeight / 2;

        for (int i = 0; i < count; i++) {
            int y0 = startY + i * (ITEM_RECT_HEIGHT + ITEM_RECT_SPACING);
            int y1 = y0 + ITEM_RECT_HEIGHT;
            drawItemRect(guiGraphics, 0, y0, ITEM_RECT_WIDTH, y1);

            BlockPos blockPos = kitchenwareList.get(i);
            if (level.isLoaded(blockPos) && level.getBlockEntity(blockPos) instanceof AbstractKitchenwareBE kitchenware) {
                ItemStack defaultInstance = kitchenware.getKitchenwareType().KITCHENWARE_ITEM.getDefaultInstance();
                guiGraphics.item(defaultInstance, 1, y0 + 1);
                if (!kitchenware.getResultItem().isEmpty()) {
                    guiGraphics.item(kitchenware.getResultItem(), 20, y0 + 3);
                    guiGraphics.text(Minecraft.getInstance().font, "完成", 40, y0 + 7, 0xFF000000, false);
                }

                if (!kitchenware.getTargetItem().isEmpty()) {
                    guiGraphics.item(kitchenware.getTargetItem(), 20, y0 + 3);
                    float cookingProgress = kitchenware.getCookingProgress();
                    float width = ITEM_RECT_WIDTH - 44;
                    guiGraphics.fill(40, y0 + 4, (int) (40 + width * cookingProgress), y1 - 4, 0xFFFFFFFF);
                }
            }
        }
    }

    /**
     * 在右侧绘制餐桌叠加矩形组（以垂直中线为对称轴上下居中）
     * <ul>
     *   <li>空闲桌：灰色"空闲"文字</li>
     *   <li>有客 + 订单未完成：显示需求菜品/Tag</li>
     *   <li>有客 + 订单已完成：显示桌上实际物品</li>
     * </ul>
     */
    private void drawDiningTableOverlay(GuiGraphicsExtractor guiGraphics, List<BlockPos> diningTableList, int screenWidth, int centerY, ClientLevel level) {
        var font = Minecraft.getInstance().font;
        int count = diningTableList.size();
        int totalHeight = count * ITEM_RECT_HEIGHT + (count - 1) * ITEM_RECT_SPACING;
        int startY = centerY - totalHeight / 2;
        int x0 = screenWidth - ITEM_RECT_WIDTH;

        for (int i = 0; i < count; i++) {
            int y0 = startY + i * (ITEM_RECT_HEIGHT + ITEM_RECT_SPACING);
            int y1 = y0 + ITEM_RECT_HEIGHT;
            drawItemRect(guiGraphics, x0, y0, screenWidth, y1);

            BlockPos blockPos = diningTableList.get(i);
            if (level.isLoaded(blockPos) && level.getBlockEntity(blockPos) instanceof DiningTableBlockEntity diningTable) {
                if (diningTable.isOccupied()) {
                    renderOccupiedTable(guiGraphics, font, diningTable, x0, y0);
                } else {
                    renderIdleTable(guiGraphics, font, x0, y0);
                }
            }
        }
    }

    /**
     * 渲染空闲餐桌
     */
    private void renderIdleTable(GuiGraphicsExtractor guiGraphics, Font font, int x0, int y0) {
        guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.idle"), x0 + 4, y0 + 7, 0xFF000000, false);
    }

    /**
     * 渲染有客餐桌：订单未完成显示需求，已完成显示物品
     */
    private void renderOccupiedTable(GuiGraphicsExtractor guiGraphics, Font font,
                                     DiningTableBlockEntity diningTable, int x0, int y0) {
        IzakayaOrder order = diningTable.getCurrentOrder();
        Identifier customerId = diningTable.getCustomerId();

        // 订单未完成：显示需求
        if (order.isRare()) {
            // 稀客：两个都是 Tag，显示 Tag 名称
            Component cuisineTag = Component.translatable(order.cuisine().toLanguageKey("tag"));
            Component beverageTag = Component.translatable(order.beverage().toLanguageKey("tag"));

            int cuisineColor = NMICommonItemTagUtil.get(diningTable.getCuisine()).positiveTags().contains(order.cuisine()) ? DARK_GREEN : DARK_RED;
            int beverageColor = NMICommonItemTagUtil.get(diningTable.getBeverage()).positiveTags().contains(order.beverage()) ? DARK_GREEN : DARK_RED;

            guiGraphics.text(font, cuisineTag, x0 + 4, y0 + 3, cuisineColor, false);
            guiGraphics.text(font, beverageTag, x0 + 4, y0 + 13, beverageColor, false);
        } else {
            // 普客：两个都是物品，显示需求菜品 + 饮品图标
            ClientNMIDataAccessor instance = ClientNMIDataAccessor.INSTANCE;
            ItemStack cuisineItem = instance.getRecipeMap().getRecipeMap().get(order.cuisine()).recipe().output().item().value().getDefaultInstance();
            Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(order.beverage());
            ItemStack beverageItem = ItemStack.EMPTY;
            if (itemReference.isPresent()) {
                beverageItem = itemReference.get().value().getDefaultInstance();
            }
            guiGraphics.item(cuisineItem, x0 + 2, y0 + 3);
            guiGraphics.item(beverageItem, x0 + 20, y0 + 3);

            if (!diningTable.getCuisine().isEmpty()) {
                if (diningTable.getCuisine().is(cuisineItem.getItem())) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIRM_SPRITE, x0 + 2, y0 + 2, 18, 18);
                } else {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CANCEL_SPRITE, x0 + 2, y0 + 2, 18, 18);
                }
            }

            if (!diningTable.getBeverage().isEmpty()) {
                if (diningTable.getBeverage().is(beverageItem.getItem())) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIRM_SPRITE, x0 + 20, y0 + 2, 18, 18);
                } else {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CANCEL_SPRITE, x0 + 20, y0 + 2, 18, 18);
                }
            }
        }

        // 客人名
        Component customerName = Component.translatable("customer.neo_mystias_izakaya." + customerId.getPath());
        int width = font.width(customerName);
        if (width > 40) {
            NMIClientUtil.renderScaledText(guiGraphics, font, customerName, x0 + 38, y0 + 7, 0xFF000000, false, (float) 40 / width);
        } else {
            guiGraphics.text(font, customerName, x0 + 38, y0 + 7, 0xFF000000, false);
        }
    }

    /**
     * 绘制单个物品矩形（填充 + 边框）
     */
    private void drawItemRect(GuiGraphicsExtractor guiGraphics, int x0, int y0, int x1, int y1) {
        // 半透明填充
        guiGraphics.fill(x0 + 1, y0 + 1, x1 - 1, y1 - 1, FILL_COLOR);
        // 上边框
        guiGraphics.fill(x0, y0, x1, y0 + 1, BORDER_COLOR);
        // 下边框
        guiGraphics.fill(x0, y1 - 1, x1, y1, BORDER_COLOR);
        // 左边框
        guiGraphics.fill(x0, y0, x0 + 1, y1, BORDER_COLOR);
        // 右边框
        guiGraphics.fill(x1 - 1, y0, x1, y1, BORDER_COLOR);
    }
}
