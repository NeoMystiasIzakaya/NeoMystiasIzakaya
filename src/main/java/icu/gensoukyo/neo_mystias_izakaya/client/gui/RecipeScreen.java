/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineListWidget;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen.getTranslatedString;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen.renderCuisineInfo;

public class RecipeScreen extends Screen {
    private final int imageWidth;
    private final int imageHeight;
    public static final int POSITIVE_IN_COLOR = 0xFFFBEECB;
    public static final int POSITIVE_OUT_COLOR = 0xFF593B1F;
    public static final int TEXT_COLOR = 0xFFD0A680;

    // 标签列表布局常量
    public static final int TAG_OFFSET_X = 13;
    public static final int TAG_OFFSET_Y = 28;
    public static final int TAG_COL_WIDTH = 42;
    public static final int TAG_ROW_HEIGHT = 12;
    public static final int TAG_ITEM_WIDTH = 38;
    public static final int TAG_ITEM_HEIGHT = 10;
    public static final int TAG_COLS_PER_ROW = 4;

    Identifier BACKGROUND = id("textures/gui/recipe_bg.png");
    Identifier SIDE = id("textures/gui/recipe_side.png");
    ArrayList<Identifier> foodTagSelected = new ArrayList<>();
    boolean isAllSelected = true; // 全选状态，默认为true
    List<NMIRecipeHolder> cookedMealItems = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipes();
    final List<NMIRecipeHolder> unsortedCookedMealItems = cookedMealItems;
    @Setter
    CuisineListWidget.CuisineEntry selected;
    EditBox search;
    String lastFilterText = "";
    CuisineListWidget cuisineListWidget;
    boolean tagChanged = false;

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
        graphics.blit(RenderPipelines.GUI_TEXTURED, SIDE, i + 256, j, 0.0F, 0.0F, 256, 256, 256, 256);

        if (this.selected != null) {
            NMIRecipeHolder cuisine = selected.getCuisine();
            renderCuisineInfo(graphics, font, cuisine, i + 254, j);
        }

        List<Identifier> all = NMICuisinesTags.ALL;

        // 渲染全选格子（索引-1表示全选）
        int allSelectX = i + TAG_OFFSET_X;
        int allSelectY = j + TAG_OFFSET_Y;
        int allSelectBgColor = isAllSelected ? POSITIVE_IN_COLOR : POSITIVE_OUT_COLOR;
        int allSelectTextColor = isAllSelected ? POSITIVE_OUT_COLOR : POSITIVE_IN_COLOR;
        graphics.fill(allSelectX, allSelectY, allSelectX + TAG_ITEM_WIDTH, allSelectY + TAG_ITEM_HEIGHT, allSelectBgColor);
        Component allSelectText = Component.literal("全选");
        FormattedCharSequence allSelectToRender = allSelectText.getVisualOrderText();
        graphics.text(font, allSelectToRender, allSelectX + 19 - font.width(allSelectToRender) / 2, allSelectY + 1, allSelectTextColor, false);

        // 渲染其他标签格子（索引从0开始，在渲染时+1）
        for (int k = 0; k < all.size(); k++) {
            Identifier foodTagEnum = all.get(k);
            // 计算位置时+1，因为索引0被全选格子占用了
            int adjustedIndex = k + 1;
            int stringX = i + adjustedIndex % TAG_COLS_PER_ROW * TAG_COL_WIDTH + TAG_OFFSET_X;
            int stringY = j + adjustedIndex / TAG_COLS_PER_ROW * TAG_ROW_HEIGHT + TAG_OFFSET_Y;

            // 根据选中状态使用不同颜色
            int bgColor = foodTagSelected.contains(foodTagEnum) ? POSITIVE_IN_COLOR : POSITIVE_OUT_COLOR;
            int textColor = foodTagSelected.contains(foodTagEnum) ? POSITIVE_OUT_COLOR : POSITIVE_IN_COLOR;
            graphics.fill(stringX, stringY, stringX + TAG_ITEM_WIDTH, stringY + TAG_ITEM_HEIGHT, bgColor);
            MutableComponent text = Component.translatable(foodTagEnum.toLanguageKey("tag"));
            FormattedCharSequence toRender = text.getVisualOrderText();
            graphics.text(font, toRender, stringX + 19 - font.width(toRender) / 2, stringY + 1, textColor, false);
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;
        int index = getIndexFromPosition((int) event.x(), (int) event.y(), i, j);

        // 点击全选格子
        if (index == -1) {
            if (!isAllSelected) {
                // 如果全选格子未被选中，则选中它并清空其他所有格子
                isAllSelected = true;
                foodTagSelected.clear();
                tagChanged = true;
            }
            // 如果全选格子已被选中，则不做任何操作（保持选中状态）
        }
        // 点击普通标签格子
        else if (index >= 0 && index < NMICuisinesTags.ALL.size()) {
            Identifier clickedTag = NMICuisinesTags.ALL.get(index);

            // 如果全选格子被选中，先取消全选格子
            if (isAllSelected) {
                isAllSelected = false;
                tagChanged = true;
            }

            // 切换普通格子的选中状态
            if (foodTagSelected.contains(clickedTag)) {
                foodTagSelected.remove(clickedTag);
                // 如果取消后没有其他格子被选中，则自动选中全选格子
                if (foodTagSelected.isEmpty()) {
                    isAllSelected = true;
                }
                tagChanged = true;
            } else {
                foodTagSelected.add(clickedTag);
                tagChanged = true;
            }
        }

        // 如果标签选择发生变化，则刷新列表
        if (tagChanged) {
            cuisineListWidget.refreshList();
            cuisineListWidget.setScrollAmount(0);
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    protected void init() {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;
        this.search = new EditBox(getFont(), i + 12, j + 9, 100, 15, Component.translatable("fml.menu.mods.search"));
        this.cuisineListWidget = new CuisineListWidget(this, minecraft, 100, 120, j + 87, getFont().lineHeight * 2);
        this.cuisineListWidget.setX(i + 262);
        this.cuisineListWidget.refreshList();
        addRenderableWidget(this.search);
        addRenderableWidget(this.cuisineListWidget);
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildImageList(Consumer<T> modListViewConsumer, Function<NMIRecipeHolder, T> newEntry) {
        cookedMealItems.forEach(cookedMealItem -> modListViewConsumer.accept(newEntry.apply(cookedMealItem)));
    }

    @Override
    public void tick() {
        String value = search.getValue();
        if (!lastFilterText.equals(value) || tagChanged) {
            this.cookedMealItems = unsortedCookedMealItems.stream().filter(recipeHolder -> {
                NMIRecipe recipe = recipeHolder.recipe();
                MutableComponent translatable = Component.translatable(recipe.output().item().value().getDescriptionId());
                String string = getTranslatedString(translatable.getVisualOrderText()).toString();
                boolean containName = string.contains(value);

                List<Identifier> identifiers = ClientNMIDataAccessor.INSTANCE.getTagItemListMap().getItemToTagMap().get(recipeHolder.key()).positiveTags();

                // 标签过滤：如果是全选状态则直接通过，否则检查是否包含已选中的标签
                boolean containTag = isAllSelected || identifiers.stream().anyMatch(foodTagSelected::contains);

                return containName && containTag;
            }).toList();
            this.lastFilterText = value;
            this.tagChanged = false;
            this.cuisineListWidget.refreshList();
            this.cuisineListWidget.setScrollAmount(0);
        }
    }

    /**
     * 从鼠标坐标反向获取标签列表的索引
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param baseX 基准X坐标（即i的值）
     * @param baseY 基准Y坐标（即j的值）
     * @return 对应的索引，-1表示全选格子，0-N表示普通标签格子，-2表示不在有效范围内
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
            return -2;
        }

        // 检查是否在具体的矩形区域内
        int itemX = col * TAG_COL_WIDTH;
        int itemY = row * TAG_ROW_HEIGHT;
        if (relativeX < itemX || relativeX >= itemX + TAG_ITEM_WIDTH ||
            relativeY < itemY || relativeY >= itemY + TAG_ITEM_HEIGHT) {
            return -2;
        }

        int index = row * TAG_COLS_PER_ROW + col;
        // 索引0表示第一个格子，即全选格子，返回-1
        if (index == 0) {
            return -1;
        }
        // 其他格子返回实际索引-1（因为全选格子占用了位置0）
        return index - 1;
    }
}
