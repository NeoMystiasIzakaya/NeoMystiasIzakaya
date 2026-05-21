/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineListWidget;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.KitchenwareButton;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.TagButton;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMICuisinesTags;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    // 按钮列表
    TagButton allSelectTagButton;
    List<TagButton> tagButtons = new ArrayList<>();
    KitchenwareButton allSelectKitchenwareButton;
    List<KitchenwareButton> kitchenwareButtons = new ArrayList<>();
    List<NMIRecipeHolder> cookedMealItems = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipes();
    final List<NMIRecipeHolder> unsortedCookedMealItems = cookedMealItems;
    @Setter
    CuisineListWidget.CuisineEntry selected;
    EditBox search;
    String lastFilterText = "";
    CuisineListWidget cuisineListWidget;
    boolean tagChanged = false;
    protected static final Button.CreateNarration DEFAULT_NARRATION = Supplier::get;

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

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    protected void init() {
        int i = (this.width - this.imageWidth) / 2 - 60;
        int j = (this.height - this.imageHeight) / 2;

        tagButtons.clear();
        kitchenwareButtons.clear();

        this.search = new EditBox(getFont(), i + 12, j + 9, 100, 15, Component.translatable("fml.menu.mods.search"));
        this.cuisineListWidget = new CuisineListWidget(this, minecraft, 100, 120, j + 87, getFont().lineHeight * 2);
        this.cuisineListWidget.setX(i + 262);
        this.cuisineListWidget.refreshList();
        addRenderableWidget(this.search);
        addRenderableWidget(this.cuisineListWidget);

        // 创建标签全选按钮
        allSelectTagButton = new TagButton(
            i + TAG_OFFSET_X,
            j + TAG_OFFSET_Y,
            TAG_ITEM_WIDTH,
            TAG_ITEM_HEIGHT,
            Component.literal("全选"),
            button -> {
                if (!isAllSelected) {
                    isAllSelected = true;
                    foodTagSelected.clear();
                    updateTagButtons();
                }
                tagChanged = true;
            },
                DEFAULT_NARRATION
        );
        allSelectTagButton.setSelected(true);
        addRenderableWidget(allSelectTagButton);

        // 创建标签按钮
        List<Identifier> allTags = NMICuisinesTags.ALL;
        for (int k = 0; k < allTags.size(); k++) {
            Identifier tag = allTags.get(k);
            int adjustedIndex = k + 1;
            int x = i + adjustedIndex % TAG_COLS_PER_ROW * TAG_COL_WIDTH + TAG_OFFSET_X;
            int y = j + adjustedIndex / TAG_COLS_PER_ROW * TAG_ROW_HEIGHT + TAG_OFFSET_Y;

            TagButton tagButton = new TagButton(
                x, y, TAG_ITEM_WIDTH, TAG_ITEM_HEIGHT,
                Component.translatable(tag.toLanguageKey("tag")),
                button -> handleTagClick(tag),
                    DEFAULT_NARRATION
            );
            tagButtons.add(tagButton);
            addRenderableWidget(tagButton);
        }

        // 创建厨具全选按钮
        allSelectKitchenwareButton = new KitchenwareButton(
            i + KITCHENWARE_OFFSET_X,
            j + KITCHENWARE_OFFSET_Y,
            KITCHENWARE_ITEM_WIDTH,
            KITCHENWARE_ITEM_HEIGHT,
            Component.literal("全选"),
            ItemStack.EMPTY,
            button -> {
                if (!isAllKitchenwareSelected) {
                    isAllKitchenwareSelected = true;
                    selectedKitchenwareTypes.clear();
                    updateKitchenwareButtons();
                }
                tagChanged = true;
            },
                DEFAULT_NARRATION
        );
        allSelectKitchenwareButton.setSelected(true);
        addRenderableWidget(allSelectKitchenwareButton);

        // 创建厨具按钮
        for (int k = 0; k < kitchenwareTypes.length; k++) {
            KitchenwareMenu.KitchenwareType type = kitchenwareTypes[k];
            int adjustedIndex = k + 1;
            int x = i + KITCHENWARE_OFFSET_X;
            int y = j + KITCHENWARE_OFFSET_Y + adjustedIndex * KITCHENWARE_ROW_HEIGHT;

            KitchenwareButton kitchenwareButton = new KitchenwareButton(
                x, y, KITCHENWARE_ITEM_WIDTH, KITCHENWARE_ITEM_HEIGHT,
                Component.translatable(type.KITCHENWARE_TAG.toLanguageKey("tag")),
                type.KITCHENWARE_ITEM.getDefaultInstance(),
                button -> handleKitchenwareClick(type),
                    DEFAULT_NARRATION
            );
            kitchenwareButtons.add(kitchenwareButton);
            addRenderableWidget(kitchenwareButton);
        }

        // 同步按钮状态到数据
        updateTagButtons();
        updateKitchenwareButtons();
    }

    private void handleTagClick(Identifier tag) {
        // 如果全选格子被选中，先取消全选格子
        if (isAllSelected) {
            isAllSelected = false;
        }

        // 切换普通格子的选中状态
        if (foodTagSelected.contains(tag)) {
            foodTagSelected.remove(tag);
            // 如果取消后没有其他格子被选中，则自动选中全选格子
            if (foodTagSelected.isEmpty()) {
                isAllSelected = true;
            }
        } else {
            foodTagSelected.add(tag);
        }
        updateTagButtons();
        tagChanged = true;
    }

    private void handleKitchenwareClick(KitchenwareMenu.KitchenwareType type) {
        // 如果全选格子被选中，先取消全选格子
        if (isAllKitchenwareSelected) {
            isAllKitchenwareSelected = false;
        }

        // 切换普通格子的选中状态
        if (selectedKitchenwareTypes.contains(type)) {
            selectedKitchenwareTypes.remove(type);
            // 如果取消后没有其他格子被选中，则自动选中全选格子
            if (selectedKitchenwareTypes.isEmpty()) {
                isAllKitchenwareSelected = true;
            }
        } else {
            selectedKitchenwareTypes.add(type);
        }
        updateKitchenwareButtons();
        tagChanged = true;
    }

    private void updateTagButtons() {
        allSelectTagButton.setSelected(isAllSelected);
        List<Identifier> allTags = NMICuisinesTags.ALL;
        for (int i = 0; i < tagButtons.size(); i++) {
            tagButtons.get(i).setSelected(foodTagSelected.contains(allTags.get(i)));
        }
    }

    private void updateKitchenwareButtons() {
        allSelectKitchenwareButton.setSelected(isAllKitchenwareSelected);
        for (int i = 0; i < kitchenwareButtons.size(); i++) {
            kitchenwareButtons.get(i).setSelected(selectedKitchenwareTypes.contains(kitchenwareTypes[i]));
        }
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

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
