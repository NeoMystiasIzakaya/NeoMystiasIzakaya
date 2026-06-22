/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CuisineGridWidget extends AbstractScrollArea {

    public static final int COLS = 5;
    public static final int ITEM_SIZE = 24;
    public static final int PADDING = 4;
    public static final int CELL_W = ITEM_SIZE + PADDING;
    public static final int CELL_H = ITEM_SIZE + PADDING;

    private static final int HOVER_COLOR = 0x44FFD700;

    private final List<NMIRecipeHolder> dishes = new ArrayList<>();
    private final List<ItemStack> beverages = new ArrayList<>();
    private boolean showingDishes = true;

    private int hoveredIndex = -1;
    @Setter
    private Consumer<NMIRecipeHolder> onDishClick;
    @Setter
    private Consumer<ItemStack> onBeverageClick;

    private boolean hoveringDish;

    public CuisineGridWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty(), defaultSettings(2));
    }

    public void setDishes(List<NMIRecipeHolder> dishes) {
        this.dishes.clear();
        this.dishes.addAll(dishes);
        onItemsChanged();
    }

    public void setBeverages(List<ItemStack> beverages) {
        this.beverages.clear();
        this.beverages.addAll(beverages);
        onItemsChanged();
    }

    public void setShowingDishes(boolean showingDishes) {
        this.showingDishes = showingDishes;
        onItemsChanged();
    }

    public @Nullable NMIRecipeHolder getHoveredDish() {
        return hoveringDish && hoveredIndex >= 0 && hoveredIndex < dishes.size()
                ? dishes.get(hoveredIndex) : null;
    }

    public @Nullable ItemStack getHoveredBeverage() {
        return !hoveringDish && hoveredIndex >= 0 && hoveredIndex < beverages.size()
                ? beverages.get(hoveredIndex) : null;
    }

    private void onItemsChanged() {
        this.hoveredIndex = -1;
        this.setScrollAmount(0);
    }

    private int itemCount() {
        return showingDishes ? dishes.size() : beverages.size();
    }

    @Override
    protected int contentHeight() {
        return totalRows() * CELL_H;
    }

    @Override
    protected double scrollRate() {
        return 16.0;
    }

    private int visibleRows() {
        return this.height / CELL_H;
    }

    private int totalRows() {
        return (itemCount() + COLS - 1) / COLS;
    }

    // ===== 渲染 =====

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int startRow = (int) (this.scrollAmount() / CELL_H);
        int visibleRows = visibleRows();

        hoveredIndex = -1;
        for (int row = 0; row < visibleRows; row++) {
            for (int col = 0; col < COLS; col++) {
                int idx = (startRow + row) * COLS + col;
                if (idx >= itemCount()) break;

                int x = this.getX() + col * CELL_W;
                int y = this.getY() + row * CELL_H;

                boolean hovered = mouseX >= x && mouseX < x + ITEM_SIZE
                        && mouseY >= y && mouseY < y + ITEM_SIZE;
                if (hovered) {
                    graphics.fill(x, y, x + ITEM_SIZE, y + ITEM_SIZE, HOVER_COLOR);
                    hoveredIndex = idx;
                    hoveringDish = showingDishes;
                }

                if (showingDishes) {
                    Item item = dishes.get(idx).recipe().output().item().value();
                    graphics.item(item.getDefaultInstance(), x + 4, y + 4);
                } else {
                    graphics.item(beverages.get(idx), x + 4, y + 4);
                }
            }
        }

        this.extractScrollbar(graphics, mouseX, mouseY);
    }

    // ===== 交互 =====

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (this.updateScrolling(event)) return true;

        if (event.button() == 0 && hoveredIndex >= 0) {
            if (showingDishes && onDishClick != null && hoveredIndex < dishes.size()) {
                onDishClick.accept(dishes.get(hoveredIndex));
                return true;
            }
            if (!showingDishes && onBeverageClick != null && hoveredIndex < beverages.size()) {
                onBeverageClick.accept(beverages.get(hoveredIndex));
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) {
        this.defaultButtonNarrationText(narration);
    }
}
