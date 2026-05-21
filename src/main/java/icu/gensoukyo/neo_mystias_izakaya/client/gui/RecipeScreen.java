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
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen.getTranslatedString;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen.renderCuisineInfo;

public class RecipeScreen extends Screen {
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
    // 厨具列表布局常量
    public static final int KITCHENWARE_OFFSET_X = 188;
    public static final int KITCHENWARE_OFFSET_Y = 18;
    public static final int KITCHENWARE_ITEM_WIDTH = 52;
    public static final int KITCHENWARE_ITEM_HEIGHT = 20;
    public static final int KITCHENWARE_ROW_HEIGHT = 24;
    final KitchenwareMenu.KitchenwareType[] kitchenwareTypes = KitchenwareMenu.KitchenwareType.values();
    private final int imageWidth;
    private final int imageHeight;
    Identifier BACKGROUND = id("textures/gui/recipe_bg.png");
    Identifier SIDE = id("textures/gui/recipe_side.png");
    ArrayList<Identifier> foodTagSelected = new ArrayList<>();
    boolean isAllSelected = true; // 全选状态，默认为true
    ArrayList<KitchenwareMenu.KitchenwareType> selectedKitchenwareTypes = new ArrayList<>();
    boolean isAllKitchenwareSelected = true; // 厨具全选状态，默认为true
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

    /**
     * 从鼠标坐标反向获取标签列表的索引
     *
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param baseX  基准X坐标（即i的值）
     * @param baseY  基准Y坐标（即j的值）
     * @return 对应的索引，-1表示全选格子，0-N表示普通标签格子，-2表示不在有效范围内
     */
    private static int getTagIndex(int mouseX, int mouseY, int baseX, int baseY) {
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

    /**
     * 从鼠标坐标反向获取厨具列表的索引
     *
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param baseX  基准X坐标（即i的值）
     * @param baseY  基准Y坐标（即j的值）
     * @return 对应的索引，-1表示全选格子，0-N表示普通厨具格子，-2表示不在有效范围内
     */
    private static int getKitchenwareIndex(int mouseX, int mouseY, int baseX, int baseY) {
        // 计算相对坐标
        int relativeX = mouseX - baseX - KITCHENWARE_OFFSET_X;
        int relativeY = mouseY - baseY - KITCHENWARE_OFFSET_Y;

        // 检查X坐标是否在有效范围内
        if (relativeX < 0 || relativeX >= KITCHENWARE_ITEM_WIDTH) {
            return -2;
        }

        // 计算行索引
        int row = relativeY / KITCHENWARE_ROW_HEIGHT;

        // 检查行是否在有效范围内（包含全选格子）
        int totalRows = KitchenwareMenu.KitchenwareType.values().length + 1; // +1 for all select
        if (row < 0 || row >= totalRows) {
            return -2;
        }

        // 检查是否在具体的矩形区域内
        int itemY = row * KITCHENWARE_ROW_HEIGHT;
        if (relativeY < itemY || relativeY >= itemY + KITCHENWARE_ITEM_HEIGHT) {
            return -2;
        }

        // 行0表示全选格子，返回-1
        if (row == 0) {
            return -1;
        }
        // 其他行返回实际索引-1（因为全选格子占用了位置0）
        return row - 1;
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

        renderTags(graphics, i, j);
        renderKitchenware(graphics, i, j);

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    private void renderKitchenware(GuiGraphicsExtractor graphics, int i, int j) {
        // 渲染全选格子（索引-1表示全选）
        int allSelectX = i + KITCHENWARE_OFFSET_X;
        int allSelectY = j + KITCHENWARE_OFFSET_Y;
        int allSelectBgColor = isAllKitchenwareSelected ? POSITIVE_IN_COLOR : POSITIVE_OUT_COLOR;
        int allSelectTextColor = isAllKitchenwareSelected ? POSITIVE_OUT_COLOR : POSITIVE_IN_COLOR;
        graphics.fill(allSelectX, allSelectY, allSelectX + KITCHENWARE_ITEM_WIDTH, allSelectY + KITCHENWARE_ITEM_HEIGHT, allSelectBgColor);
        Component allSelectText = Component.literal("全选");
        graphics.text(font, allSelectText, allSelectX + 22, allSelectY + 6, allSelectTextColor, false);

        // 渲染其他厨具格子（索引从0开始，在渲染时+1）
        for (int k = 0; k < kitchenwareTypes.length; k++) {
            KitchenwareMenu.KitchenwareType type = kitchenwareTypes[k];
            // 计算位置时+1，因为索引0被全选格子占用了
            int adjustedIndex = k + 1;
            int x = i + KITCHENWARE_OFFSET_X;
            int y = j + KITCHENWARE_OFFSET_Y + adjustedIndex * KITCHENWARE_ROW_HEIGHT;

            // 根据选中状态使用不同颜色
            int bgColor = selectedKitchenwareTypes.contains(type) ? POSITIVE_IN_COLOR : POSITIVE_OUT_COLOR;
            int textColor = selectedKitchenwareTypes.contains(type) ? POSITIVE_OUT_COLOR : POSITIVE_IN_COLOR;
            graphics.fill(x, y, x + KITCHENWARE_ITEM_WIDTH, y + KITCHENWARE_ITEM_HEIGHT, bgColor);
            ItemStack defaultInstance = type.KITCHENWARE_ITEM.getDefaultInstance();
            graphics.item(defaultInstance, x + 2, y);
            graphics.text(font, Component.translatable(type.KITCHENWARE_TAG.toLanguageKey("tag")), x + 22, y + 6, textColor, false);
        }
    }

    private void renderTags(GuiGraphicsExtractor graphics, int i, int j) {
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
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;
        int index = getTagIndex((int) event.x(), (int) event.y(), i, j);
        int kitchenwareIndex = getKitchenwareIndex((int) event.x(), (int) event.y(), i, j);

        // 处理标签点击
        // 点击全选格子
        if (index == -1) {
            if (!isAllSelected) {
                // 如果全选格子未被选中，则选中它并清空其他所有格子
                isAllSelected = true;
                foodTagSelected.clear();
            }
            // 如果全选格子已被选中，则不做任何操作（保持选中状态）
            tagChanged = true;
        }
        // 点击普通标签格子
        else if (index >= 0 && index < NMICuisinesTags.ALL.size()) {
            Identifier clickedTag = NMICuisinesTags.ALL.get(index);

            // 如果全选格子被选中，先取消全选格子
            if (isAllSelected) {
                isAllSelected = false;
            }

            // 切换普通格子的选中状态
            if (foodTagSelected.contains(clickedTag)) {
                foodTagSelected.remove(clickedTag);
                // 如果取消后没有其他格子被选中，则自动选中全选格子
                if (foodTagSelected.isEmpty()) {
                    isAllSelected = true;
                }
            } else {
                foodTagSelected.add(clickedTag);
            }
            tagChanged = true;
        }

        // 处理厨具点击
        // 点击厨具全选格子
        if (kitchenwareIndex == -1) {
            if (!isAllKitchenwareSelected) {
                // 如果全选格子未被选中，则选中它并清空其他所有格子
                isAllKitchenwareSelected = true;
                selectedKitchenwareTypes.clear();
            }
            // 如果全选格子已被选中，则不做任何操作（保持选中状态）
            tagChanged = true;
        }
        // 点击普通厨具格子
        else if (kitchenwareIndex >= 0 && kitchenwareIndex < kitchenwareTypes.length) {
            KitchenwareMenu.KitchenwareType clickedType = kitchenwareTypes[kitchenwareIndex];

            // 如果全选格子被选中，先取消全选格子
            if (isAllKitchenwareSelected) {
                isAllKitchenwareSelected = false;
            }

            // 切换普通格子的选中状态
            if (selectedKitchenwareTypes.contains(clickedType)) {
                selectedKitchenwareTypes.remove(clickedType);
                // 如果取消后没有其他格子被选中，则自动选中全选格子
                if (selectedKitchenwareTypes.isEmpty()) {
                    isAllKitchenwareSelected = true;
                }
            } else {
                selectedKitchenwareTypes.add(clickedType);
            }
            tagChanged = true;
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
                boolean containTag = isAllSelected || new HashSet<>(identifiers).containsAll(foodTagSelected);

                // 厨具过滤：如果是全选状态则直接通过，否则检查是否匹配已选中的厨具
                boolean containKitchenware = isAllKitchenwareSelected || selectedKitchenwareTypes.stream()
                        .anyMatch(type -> type.KITCHENWARE_TYPE.equals(recipe.kitchenware()));

                return containName && containTag && containKitchenware;
            }).toList();
            this.lastFilterText = value;
            this.tagChanged = false;
            this.cuisineListWidget.refreshList();
            this.cuisineListWidget.setScrollAmount(0);
        }
    }
}
