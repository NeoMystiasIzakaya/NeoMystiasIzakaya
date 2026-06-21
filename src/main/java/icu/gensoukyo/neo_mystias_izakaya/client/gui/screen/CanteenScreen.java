/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.screen;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientEconomyUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CanteenScreen extends Screen {

    // === 布局常量 ===
    public static final int IMAGE_WIDTH = 338;
    public static final int IMAGE_HEIGHT = 224;
    public static final int LEFT_PANEL_WIDTH = 218;
    public static final int GRID_COLS = 5;
    public static final int GRID_ITEM_SIZE = 24;
    public static final int GRID_PADDING = 4;
    public static final int GRID_OFFSET_X = 8;
    public static final int GRID_OFFSET_Y = 24;
    public static final int GRID_VISIBLE_ROWS = 4;
    public static final int GRID_CELL_W = GRID_ITEM_SIZE + GRID_PADDING;
    public static final int GRID_CELL_H = GRID_ITEM_SIZE + GRID_PADDING;
    public static final int RIGHT_PANEL_X = LEFT_PANEL_WIDTH + 2;
    public static final int RIGHT_PANEL_W = IMAGE_WIDTH - RIGHT_PANEL_X;

    // === 颜色 ===
    public static final int BG_LEFT = 0xFFFBEECB;
    public static final int BG_RIGHT = 0xFF5C3A1E;
    public static final int BG_SHELF = 0xFF4A2E15;
    public static final int TEXT_DARK = 0xFF593B1F;
    public static final int TEXT_GOLD = 0xFFD0A680;
    public static final int GRID_HOVER = 0x44FFD700;
    public static final int GRID_SELECTED = 0x66FFFFFF;
    public static final int DOT_RED = 0xFFFF0000;
    public static final int DOT_GREEN = 0xFF00CC00;
    public static final int PRICE_RED = 0xFFAA0000;
    public static final int DESC_GRAY = 0xFF666666;

    protected static final Button.CreateNarration DEFAULT_NARRATION = Supplier::get;

    // === 数据源 ===
    final List<NMIRecipeHolder> allRecipes = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipes();
    final List<ItemStack> allBeverages = NMIBeveragesItems.ITEM_LIST.stream()
            .map(item -> new ItemStack(item.get()))
            .toList();

    // === 模式状态 ===
    boolean showDishes = true;
    boolean showTags = false;

    // === 菜单 ===
    IzakayaMenu currentMenu = IzakayaMenu.EMPTY;

    // === 滚动 ===
    int scrollOffset = 0;

    // === 悬浮详情 ===
    int hoveredIndex = -1;
    Object hoveredData = null;

    // === 控件 ===
    Button toggleDishBeverageBtn;
    Button goToOpenBtn;

    @Nullable
    BlockPos controllerPos;

    int i, j;

    public CanteenScreen() {
        super(Component.literal("Canteen"));
    }

    public CanteenScreen(BlockPos controllerPos) {
        this();
        this.controllerPos = controllerPos;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        i = (this.width - IMAGE_WIDTH) / 2;
        j = (this.height - IMAGE_HEIGHT) / 2;

        // 左侧面板背景
        graphics.fill(i, j, i + LEFT_PANEL_WIDTH, j + IMAGE_HEIGHT, BG_LEFT);

        // 右侧面板背景
        graphics.fill(i + RIGHT_PANEL_X, j, i + IMAGE_WIDTH, j + IMAGE_HEIGHT, BG_RIGHT);

        renderGrid(graphics, mouseX, mouseY);
        renderDetail(graphics);
        renderShelf(graphics);
        renderBeverageSection(graphics);
        renderKitchenwareSection(graphics);
        renderStatusDots(graphics);

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    protected void init() {
        i = (this.width - IMAGE_WIDTH) / 2;
        j = (this.height - IMAGE_HEIGHT) / 2;

        if (minecraft.player != null) {
            currentMenu = NMICommonIzakayaUtil.getMenu(minecraft.player);
        }

        // 前往开店按钮
        goToOpenBtn = Button.builder(
                Component.translatable("gui.neo_mystias_izakaya.go_open"),
                _ -> {
                    if (controllerPos != null) {
                        ClientPayloadSender.sendIzakayaMenuSyncMessage(currentMenu);
                        ClientPayloadSender.sendToggleCanteenOpen(controllerPos);
                    }
                }
        ).bounds(i + 4, j + 2, 60, 16).build();
        addRenderableWidget(goToOpenBtn);

        // 切换菜品/饮料按钮
        toggleDishBeverageBtn = Button.builder(
                Component.translatable("gui.neo_mystias_izakaya.show_beverages"),
                _ -> {
                    showDishes = !showDishes;
                    hoveredIndex = -1;
                    hoveredData = null;
                    scrollOffset = 0;
                    updateToggleText();
                }
        ).bounds(i + 68, j + 2, 80, 16).build();
        addRenderableWidget(toggleDishBeverageBtn);

        // Tag 切换按钮
        addRenderableWidget(Button.builder(
                Component.literal("T"),
                _ -> showTags = !showTags
        ).bounds(i + LEFT_PANEL_WIDTH - 18, j + IMAGE_HEIGHT - 30, 14, 14).build());
    }

    // ========== 左侧网格 ==========

    private void renderGrid(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        List<?> items = showDishes ? allRecipes : allBeverages;
        int totalRows = (items.size() + GRID_COLS - 1) / GRID_COLS;
        int maxScroll = Math.max(0, totalRows - GRID_VISIBLE_ROWS);
        scrollOffset = Math.clamp(scrollOffset, 0, maxScroll);

        int gy = j + GRID_OFFSET_Y;
        hoveredIndex = -1;
        hoveredData = null;

        for (int row = 0; row < GRID_VISIBLE_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int idx = (row + scrollOffset) * GRID_COLS + col;
                if (idx >= items.size()) break;

                int x = i + GRID_OFFSET_X + col * GRID_CELL_W;
                int y = gy + row * GRID_CELL_H;

                boolean hovered = mouseX >= x && mouseX < x + GRID_ITEM_SIZE
                        && mouseY >= y && mouseY < y + GRID_ITEM_SIZE;
                if (hovered) {
                    graphics.fill(x, y, x + GRID_ITEM_SIZE, y + GRID_ITEM_SIZE, GRID_HOVER);
                    hoveredIndex = idx;
                    hoveredData = items.get(idx);
                }

                Object obj = items.get(idx);
                if (obj instanceof NMIRecipeHolder recipe) {
                    Item item = recipe.recipe().output().item().value();
                    graphics.item(item.getDefaultInstance(), x + 4, y + 4);
                } else if (obj instanceof ItemStack stack) {
                    graphics.item(stack, x + 4, y + 4);
                }
            }
        }
    }

    // ========== 左侧详情 ==========

    private void renderDetail(GuiGraphicsExtractor graphics) {
        int dy = j + GRID_OFFSET_Y + GRID_VISIBLE_ROWS * GRID_CELL_H + 4;
        int dh = j + IMAGE_HEIGHT - dy - 2;

        graphics.fill(i, dy - 2, i + LEFT_PANEL_WIDTH, dy - 1, TEXT_DARK);

        switch (hoveredData) {
            case null -> graphics.text(font, Component.translatable("gui.neo_mystias_izakaya.select_hint"),
                    i + 8, dy + 4, TEXT_DARK, false);
            case NMIRecipeHolder(
                    Identifier key, icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe recipe1
            ) -> {
                Item item = recipe1.output().item().value();

                graphics.text(font,
                        Component.translatable(item.getDescriptionId()).withStyle(ChatFormatting.BOLD),
                        i + 8, dy + 4, TEXT_DARK, false);

                Integer price = NMIClientEconomyUtil.getItemPrice(item);
                graphics.text(font,
                        Component.translatable("gui.neo_mystias_izakaya.price")
                                .append(": " + (price != null ? price : 0) + " ")
                                .append(NMICommonComponentUtil.unitEn()),
                        i + 8, dy + 16, PRICE_RED, false);

                if (showTags) {
                    var tagMap = ClientNMIDataAccessor.INSTANCE.getTagItemListMap().getItemToTagMap();
                    var tags = tagMap.get(key);
                    if (tags != null) {
                        KitchenwareScreen.renderTags(graphics, font, i, dy + 28, tags);
                    }
                } else {
                    MutableComponent desc = Component.translatable(item.getDescriptionId() + ".desc");
                    drawWrappedText(graphics, font, desc, i + 8, dy + 28, LEFT_PANEL_WIDTH - 16, DESC_GRAY);
                }

                // 原料图标
                List<ItemStack> inputs = recipe1.input().stream()
                        .map(ingredient -> {
                            var v = ingredient.getValues();
                            return v.size() > 0 ? v.get(0).value().getDefaultInstance() : null;
                        })
                        .filter(java.util.Objects::nonNull)
                        .toList();
                int ix = i + 8;
                int iy = dy + dh - 22;
                for (int k = 0; k < inputs.size() && k < 5; k++) {
                    graphics.fill(ix + k * 20, iy, ix + k * 20 + 18, iy + 18, 0xAA593B1F);
                    graphics.item(inputs.get(k), ix + k * 20 + 1, iy + 1);
                }
            }
            case ItemStack stack -> {
                graphics.text(font,
                        Component.translatable(stack.getItem().getDescriptionId()),
                        i + 8, dy + 4, TEXT_DARK, false);
                Integer beveragePrice = NMIClientEconomyUtil.getItemStackPriceBase(stack);
                graphics.text(font,
                        Component.translatable("gui.neo_mystias_izakaya.price")
                                .append(": " + (beveragePrice != null ? beveragePrice : 0) + " ")
                                .append(NMICommonComponentUtil.unitEn()),
                        i + 8, dy + 16, PRICE_RED, false);
                MutableComponent desc = Component.translatable(stack.getItem().getDescriptionId() + ".desc");
                drawWrappedText(graphics, font, desc, i + 8, dy + 28, LEFT_PANEL_WIDTH - 16, DESC_GRAY);
            }
            default -> {
            }
        }

    }

    // ========== 右侧货架 ==========

    private void renderShelf(GuiGraphicsExtractor graphics) {
        int sx = i + RIGHT_PANEL_X + 4;
        int sy = j + 8;
        int shelfH = 140;

        graphics.fill(sx - 2, sy - 2, sx + RIGHT_PANEL_W - 6, sy + shelfH, BG_SHELF);
        graphics.text(font, Component.translatable("gui.neo_mystias_izakaya.shelf"),
                sx + 4, sy, TEXT_GOLD, false);

        List<Identifier> cuisines = currentMenu.cuisines();
        for (int k = 0; k < 8; k++) {
            int sx2 = sx + 2 + (k % 4) * 28;
            int sy2 = sy + 14 + (k / 4) * 30;

            graphics.fill(sx2, sy2, sx2 + 24, sy2 + 24, 0xAA000000);

            if (k < cuisines.size()) {
                var holder = ClientNMIDataAccessor.INSTANCE.getRecipeMap()
                        .getRecipeMap().get(cuisines.get(k));
                if (holder != null) {
                    Item item = holder.recipe().output().item().value();
                    graphics.item(item.getDefaultInstance(), sx2 + 4, sy2 + 4);
                }
            }
        }
    }

    // ========== 右侧饮品 ==========

    private void renderBeverageSection(GuiGraphicsExtractor graphics) {
        int bx = i + RIGHT_PANEL_X + 4;
        int by = j + 108;

        graphics.fill(bx - 2, by, bx + RIGHT_PANEL_W - 6, by + 80, BG_SHELF);
        graphics.text(font, Component.translatable("gui.neo_mystias_izakaya.beverages"),
                bx + 4, by + 2, TEXT_GOLD, false);

        List<Identifier> beverages = currentMenu.beverages();
        for (int k = 0; k < 8; k++) {
            int slotX = bx + 2 + (k % 4) * 28;
            int slotY = by + 16 + (k / 4) * 30;
            graphics.fill(slotX, slotY, slotX + 24, slotY + 24, 0xAA000000);
            if (k < beverages.size()) {
                ItemStack stack = net.minecraft.core.registries.BuiltInRegistries.ITEM
                        .getOptional(beverages.get(k))
                        .map(ItemStack::new)
                        .orElse(ItemStack.EMPTY);
                if (!stack.isEmpty()) {
                    graphics.item(stack, slotX + 4, slotY + 4);
                }
            }
        }
    }

    // ========== 右侧厨具 ==========

    private void renderKitchenwareSection(GuiGraphicsExtractor graphics) {
        int kx = i + RIGHT_PANEL_X + 4;
        int ky = j + 196;

        graphics.text(font, Component.translatable("gui.neo_mystias_izakaya.kitchenware"),
                kx, ky, TEXT_GOLD, false);
    }

    // ========== 状态指示点 ==========

    private void renderStatusDots(GuiGraphicsExtractor graphics) {
        int dx = i + LEFT_PANEL_WIDTH - 30;
        int dy = j + 5;

        graphics.fill(dx, dy, dx + 6, dy + 6, !currentMenu.cuisines().isEmpty() ? DOT_GREEN : DOT_RED);
        graphics.fill(dx, dy + 8, dx + 6, dy + 14, !currentMenu.beverages().isEmpty() ? DOT_GREEN : DOT_RED);
        graphics.fill(dx, dy + 16, dx + 6, dy + 22, DOT_GREEN);
    }

    // ========== 交互 ==========

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();

        // 左右键：移除货架/饮品
        if (event.button() == 0 || event.button() == 1) {
            int rightX = i + RIGHT_PANEL_X + 4;
            int shelfY = j + 8;
            // 货架区点击
            if (mouseX >= rightX && mouseX < rightX + RIGHT_PANEL_W - 6
                    && mouseY >= shelfY + 14 && mouseY < shelfY + 14 + 60) {
                int column = (int) ((mouseX - rightX) / 28);
                int row = (int) ((mouseY - (shelfY + 14)) / 30);
                int slotIndex = row * 4 + column;
                List<Identifier> cuisines = new ArrayList<>(currentMenu.cuisines());
                if (slotIndex >= 0 && slotIndex < cuisines.size()) {
                    cuisines.remove(slotIndex);
                    currentMenu = new IzakayaMenu(cuisines, currentMenu.beverages());
                    return true;
                }
            }
            // 饮品区点击
            int beverageY = j + 108;
            if (mouseX >= rightX + 2 && mouseX < rightX + 2 + 4 * 28
                    && mouseY >= beverageY + 16 && mouseY < beverageY + 16 + 60) {
                int column = (int) ((mouseX - rightX) / 28);
                int row = (int) ((mouseY - (beverageY + 16)) / 30);
                int slotIndex = row * 4 + column;
                List<Identifier> beverages = new ArrayList<>(currentMenu.beverages());
                if (slotIndex >= 0 && slotIndex < beverages.size()) {
                    beverages.remove(slotIndex);
                    currentMenu = new IzakayaMenu(currentMenu.cuisines(), beverages);
                    return true;
                }
            }
        }

        // 左键：加入菜单
        if (event.button() == 0 && hoveredData != null) {
            if (showDishes && hoveredData instanceof NMIRecipeHolder recipe) {
                Identifier key = recipe.key();
                List<Identifier> cuisines = new ArrayList<>(currentMenu.cuisines());
                if (cuisines.size() >= 8) return true;
                if (!cuisines.contains(key)) {
                    cuisines.add(key);
                    currentMenu = new IzakayaMenu(cuisines, currentMenu.beverages());
                }
                return true;
            } else if (!showDishes && hoveredData instanceof ItemStack stack) {
                Identifier key = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem());
                List<Identifier> beverages = new ArrayList<>(currentMenu.beverages());
                if (beverages.size() >= 8) return true;
                if (!beverages.contains(key)) {
                    beverages.add(key);
                    currentMenu = new IzakayaMenu(currentMenu.cuisines(), beverages);
                }
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX >= i && mouseX < i + LEFT_PANEL_WIDTH) {
            List<?> items = showDishes ? allRecipes : allBeverages;
            int maxScroll = Math.max(0, (items.size() + GRID_COLS - 1) / GRID_COLS - GRID_VISIBLE_ROWS);
            scrollOffset = (int) Math.clamp(scrollOffset - scrollY, 0, maxScroll);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    // ========== 辅助 ==========

    private void updateToggleText() {
        toggleDishBeverageBtn.setMessage(Component.translatable(
                showDishes ? "gui.neo_mystias_izakaya.show_beverages" : "gui.neo_mystias_izakaya.show_dishes"));
    }

    private static void drawWrappedText(GuiGraphicsExtractor graphics, Font font,
            Component text, int x, int y, int maxWidth, int color) {
        var lines = font.split(text, maxWidth);
        if (lines.size() <= 3) {
            int cy = y;
            for (var line : lines) {
                graphics.text(font, line, x, cy, color, false);
                cy += font.lineHeight;
            }
        } else {
            int dotsWidth = font.width("...");
            String truncated = font.plainSubstrByWidth(text.getString(), maxWidth * 3 - dotsWidth) + "...";
            var truncatedLines = font.split(Component.literal(truncated), maxWidth);
            int cy = y;
            for (var line : truncatedLines) {
                graphics.text(font, line, x, cy, color, false);
                cy += font.lineHeight;
                if (cy >= y + font.lineHeight * 3) break;
            }
        }
    }
}
