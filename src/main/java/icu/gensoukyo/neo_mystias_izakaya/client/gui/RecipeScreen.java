/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineListWidget;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMICuisinesTags;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen.renderCuisineInfo;

public class RecipeScreen extends Screen {
    private final int imageWidth;
    private final int imageHeight;
    public static final int positiveInColor = new Color(230, 180, 166).getRGB();
    public static final int positiveOutColor = new Color(157, 84, 55).getRGB();

    // 标签列表布局常量
    public static final int TAG_OFFSET_X = 13;
    public static final int TAG_OFFSET_Y = 28;
    public static final int TAG_COL_WIDTH = 42;
    public static final int TAG_ROW_HEIGHT = 12;
    public static final int TAG_ITEM_WIDTH = 38;
    public static final int TAG_ITEM_HEIGHT = 10;
    public static final int TAG_COLS_PER_ROW = 4;

    Identifier BACKGROUND = id("textures/gui/recipe_bg.png");
    ArrayList<Identifier> foodTagSelected = new ArrayList<>();
    List<NMIRecipeHolder> cookedMealItems = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipes();
    final List<NMIRecipeHolder> unsortedCookedMealItems = cookedMealItems;
    @Setter
    CuisineListWidget.CuisineEntry selected;
    EditBox search;
    String lastFilterText = "";
    CuisineListWidget cuisineListWidget;

    public RecipeScreen() {
        super(Component.literal("Recipe Screen"));
        this.imageWidth = 256;
        this.imageHeight = 219;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0.0F, 0.0F, 256, 256, 256, 256);

        if (this.selected != null) {
            NMIRecipeHolder cuisine = selected.getCuisine();
            renderCuisineInfo(graphics, font, cuisine, i + 246, j);
        }

        List<Identifier> all = NMICuisinesTags.ALL;
        for (int k = 0; k < all.size(); k++) {
            Identifier foodTagEnum = all.get(k);
            int stringX = i + k % TAG_COLS_PER_ROW * TAG_COL_WIDTH + TAG_OFFSET_X;
            int stringY = j + k / TAG_COLS_PER_ROW * TAG_ROW_HEIGHT + TAG_OFFSET_Y;

            graphics.fill(stringX, stringY, stringX + TAG_ITEM_WIDTH, stringY + TAG_ITEM_HEIGHT, positiveInColor);
            graphics.text(font, Component.translatable(foodTagEnum.toLanguageKey("tag")), stringX, stringY, 0xFFFFFFFF);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;
        int index = getIndexFromPosition((int) event.x(), (int) event.y(), i, j);
        if (index >= 0 && index < NMICuisinesTags.ALL.size()) {
            foodTagSelected.add(NMICuisinesTags.ALL.get(index));
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    protected void init() {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;
        this.search = new EditBox(getFont(), i + 12, j + 9, 100, 15, Component.translatable("fml.menu.mods.search"));
        this.cuisineListWidget = new CuisineListWidget(this, minecraft, 100, 120, j + 87, getFont().lineHeight * 2);
        this.cuisineListWidget.setX(i + 256);
        this.cuisineListWidget.refreshList();
        addRenderableWidget(this.search);
        addRenderableWidget(this.cuisineListWidget);
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildImageList(Consumer<T> modListViewConsumer, Function<NMIRecipeHolder, T> newEntry) {
        cookedMealItems.forEach(cookedMealItem -> modListViewConsumer.accept(newEntry.apply(cookedMealItem)));
    }

    @Override
    public void tick() {

    }

    /**
     * 从鼠标坐标反向获取标签列表的索引
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param baseX 基准X坐标（即i的值）
     * @param baseY 基准Y坐标（即j的值）
     * @return 对应的索引，如果坐标不在有效范围内则返回-1
     */
    private static int getIndexFromPosition(int mouseX, int mouseY, int baseX, int baseY) {
        // 计算相对坐标
        int relativeX = mouseX - baseX - TAG_OFFSET_X;
        int relativeY = mouseY - baseY - TAG_OFFSET_Y;

        // 计算列和行
        int col = relativeX / TAG_COL_WIDTH;
        int row = relativeY / TAG_ROW_HEIGHT;

        // 检查是否在有效范围内
        if (col < 0 || col >= TAG_COLS_PER_ROW || row < 0) {
            return -1;
        }

        // 检查是否在具体的矩形区域内
        int itemX = col * TAG_COL_WIDTH;
        int itemY = row * TAG_ROW_HEIGHT;
        if (relativeX < itemX || relativeX >= itemX + TAG_ITEM_WIDTH ||
            relativeY < itemY || relativeY >= itemY + TAG_ITEM_HEIGHT) {
            return -1;
        }

        return row * TAG_COLS_PER_ROW + col;
    }
}
