/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineListWidget;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineListWidget.DisplayEntry;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.ImageStateButton;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.KitchenwareButton;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.TagButton;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientEconomyUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.Customer;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMIBeveragesTags;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMICuisinesTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import lombok.Setter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen.*;

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
    protected static final Button.CreateNarration DEFAULT_NARRATION = Supplier::get;
    final List<NMIRecipeHolder> allRecipes = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipes();
    /**
     * 从 NMIBeveragesItems 获取酒水物品列表
     */
    final List<ItemStack> allBeverages = NMIBeveragesItems.ITEM_LIST.stream()
            .map(item -> new ItemStack(item.get()))
            .toList();
    final List<CustomerHolder> allCommonCustomers = ClientNMIDataAccessor.INSTANCE.getCustomerMap().getAllCustomers()
            .stream().filter(c -> c instanceof icu.gensoukyo.neo_mystias_izakaya.content.customer.CommonCustomerHolder).toList();
    final List<CustomerHolder> allRareCustomers = ClientNMIDataAccessor.INSTANCE.getCustomerMap().getAllCustomers()
            .stream().filter(c -> c instanceof icu.gensoukyo.neo_mystias_izakaya.content.customer.RareCustomerHolder).toList();
    private final KitchenwareMenu.KitchenwareType[] kitchenwareTypes = KitchenwareMenu.KitchenwareType.values();
    private final ScreenMode[] ScreenModes = ScreenMode.values();
    private final int imageWidth;
    private final int imageHeight;
    Identifier BACKGROUND = id("textures/gui/recipe_bg.png");
    Identifier SIDE = id("textures/gui/recipe_side.png");
    // === 模式状态 ===
    ScreenMode selectedScreenMode = ScreenMode.RECIPE;
    // === 食谱/酒水模式过滤状态 ===
    ArrayList<Identifier> foodTagSelected = new ArrayList<>();
    boolean isAllSelected = true;
    ArrayList<KitchenwareMenu.KitchenwareType> selectedKitchenwareTypes = new ArrayList<>();
    boolean isAllKitchenwareSelected = true;
    // === 酒水模式过滤状态 ===
    ArrayList<Identifier> beverageTagSelected = new ArrayList<>();
    boolean isAllBeverageSelected = true;
    // === 顾客模式过滤状态 ===
    boolean isAllCustomerSelected = true;
    // === 按钮列表 ===
    TagButton allSelectTagButton;
    List<TagButton> tagButtons = new ArrayList<>();
    KitchenwareButton allSelectKitchenwareButton;
    List<KitchenwareButton> kitchenwareButtons = new ArrayList<>();
    List<ImageStateButton> screenModeButtons = new ArrayList<>();
    // === 当前展示的数据 ===
    List<Object> displayItems = new ArrayList<>();
    @Setter
    DisplayEntry selected;
    EditBox search;
    String lastFilterText = "";
    CuisineListWidget cuisineListWidget;
    boolean needsRefresh = false;

    public RecipeScreen() {
        super(Component.literal("Recipe Screen"));
        this.imageWidth = 256;
        this.imageHeight = 219;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = (this.width - this.imageWidth) / 2 - 40;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0.0F, 0.0F, 256, 256, 256, 256);
        graphics.blit(RenderPipelines.GUI_TEXTURED, SIDE, i + 256, j, 0.0F, 0.0F, 256, 256, 256, 256);

        if (this.selected != null) {
            if (selected.isRecipe()) {
                renderCuisineInfo(graphics, font, selected.getRecipe(), i + 254, j);
                renderCuisineIngredient(graphics, font, selected.getRecipe(), i, j);
            } else if (selected.isCustomer()) {
                renderCustomerInfo(graphics, font, selected.getCustomer(), i + 254, j);
            } else if (selected.isItem()) {
                renderBeverageInfo(graphics, font, selected.getItemStack(), i + 254, j);
            }
        }

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    protected void init() {
        int i = (this.width - this.imageWidth) / 2 - 40;
        int j = (this.height - this.imageHeight) / 2;

        tagButtons.clear();
        kitchenwareButtons.clear();
        screenModeButtons.clear();

        this.search = new EditBox(getFont(), i + 12, j + 9, 100, 15, Component.translatable("fml.menu.mods.search"));
        this.cuisineListWidget = new CuisineListWidget(this, minecraft, 100, 120, j + 87, getFont().lineHeight * 2);
        this.cuisineListWidget.setX(i + 262);
        addRenderableWidget(this.search);
        addRenderableWidget(this.cuisineListWidget);

        // 根据当前模式构建过滤器按钮
        rebuildFilterButtons(i, j);

        // 构建模式切换按钮
        buildScreenModeButtons(i, j);

        // 初始化数据
        reloadData();
        cuisineListWidget.refreshList();
    }

    /**
     * 根据当前模式重建过滤器按钮
     */
    private void rebuildFilterButtons(int i, int j) {
        // 清除旧按钮（从渲染列表中移除）
        if (allSelectTagButton != null) removeWidget(allSelectTagButton);
        for (TagButton btn : tagButtons) removeWidget(btn);
        if (allSelectKitchenwareButton != null) removeWidget(allSelectKitchenwareButton);
        for (KitchenwareButton btn : kitchenwareButtons) removeWidget(btn);
        tagButtons.clear();
        kitchenwareButtons.clear();
        allSelectTagButton = null;
        allSelectKitchenwareButton = null;

        switch (selectedScreenMode) {
            case RECIPE -> {
                buildRecipeFilterButtons(i, j);
                updateRecipeTagButtons();
                updateKitchenwareButtons();
            }
            case BEVERAGES -> {
                buildBeverageFilterButtons(i, j);
                updateBeverageTagButtons();
            }
            case RARE_CUSTOMER, COMMON_CUSTOMER -> {
                // 顾客模式暂无标签过滤，后续可扩展
            }
        }
    }

    /**
     * 构建食谱模式的过滤按钮：菜系标签 + 厨具
     */
    private void buildRecipeFilterButtons(int i, int j) {
        allSelectTagButton = new TagButton(
                i + TAG_OFFSET_X, j + TAG_OFFSET_Y,
                TAG_ITEM_WIDTH, TAG_ITEM_HEIGHT,
                Component.literal("全选"),
                button -> {
                    if (!isAllSelected) {
                        isAllSelected = true;
                        foodTagSelected.clear();
                        updateRecipeTagButtons();
                    }
                    needsRefresh = true;
                },
                DEFAULT_NARRATION
        );
        allSelectTagButton.setSelected(true);
        addRenderableWidget(allSelectTagButton);

        List<Identifier> allTags = NMICuisinesTags.ALL;
        for (int k = 0; k < allTags.size(); k++) {
            Identifier tag = allTags.get(k);
            int adjustedIndex = k + 1;
            int x = i + adjustedIndex % TAG_COLS_PER_ROW * TAG_COL_WIDTH + TAG_OFFSET_X;
            int y = j + adjustedIndex / TAG_COLS_PER_ROW * TAG_ROW_HEIGHT + TAG_OFFSET_Y;
            TagButton tb = new TagButton(x, y, TAG_ITEM_WIDTH, TAG_ITEM_HEIGHT,
                    Component.translatable(tag.toLanguageKey("tag")),
                    button -> handleRecipeTagClick(tag),
                    DEFAULT_NARRATION);
            tagButtons.add(tb);
            addRenderableWidget(tb);
        }

        allSelectKitchenwareButton = new KitchenwareButton(
                i + KITCHENWARE_OFFSET_X, j + KITCHENWARE_OFFSET_Y,
                KITCHENWARE_ITEM_WIDTH, KITCHENWARE_ITEM_HEIGHT,
                Component.literal("全选"),
                net.minecraft.world.item.ItemStack.EMPTY,
                button -> {
                    if (!isAllKitchenwareSelected) {
                        isAllKitchenwareSelected = true;
                        selectedKitchenwareTypes.clear();
                        updateKitchenwareButtons();
                    }
                    needsRefresh = true;
                },
                DEFAULT_NARRATION
        );
        allSelectKitchenwareButton.setSelected(true);
        addRenderableWidget(allSelectKitchenwareButton);

        for (int k = 0; k < kitchenwareTypes.length; k++) {
            KitchenwareMenu.KitchenwareType type = kitchenwareTypes[k];
            int adjustedIndex = k + 1;
            int x = i + KITCHENWARE_OFFSET_X;
            int y = j + KITCHENWARE_OFFSET_Y + adjustedIndex * KITCHENWARE_ROW_HEIGHT;
            KitchenwareButton kb = new KitchenwareButton(x, y, KITCHENWARE_ITEM_WIDTH, KITCHENWARE_ITEM_HEIGHT,
                    Component.translatable(type.KITCHENWARE_TAG.toLanguageKey("tag")),
                    type.KITCHENWARE_ITEM.getDefaultInstance(),
                    button -> handleKitchenwareClick(type),
                    DEFAULT_NARRATION);
            kitchenwareButtons.add(kb);
            addRenderableWidget(kb);
        }
    }

    /**
     * 构建酒水模式的过滤按钮：酒水特性标签
     */
    private void buildBeverageFilterButtons(int i, int j) {
        allSelectTagButton = new TagButton(
                i + TAG_OFFSET_X, j + TAG_OFFSET_Y,
                TAG_ITEM_WIDTH, TAG_ITEM_HEIGHT,
                Component.literal("全选"),
                button -> {
                    if (!isAllBeverageSelected) {
                        isAllBeverageSelected = true;
                        beverageTagSelected.clear();
                        updateBeverageTagButtons();
                    }
                    needsRefresh = true;
                },
                DEFAULT_NARRATION
        );
        allSelectTagButton.setSelected(true);
        addRenderableWidget(allSelectTagButton);

        List<Identifier> allTags = NMIBeveragesTags.ALL;
        for (int k = 0; k < allTags.size(); k++) {
            Identifier tag = allTags.get(k);
            int adjustedIndex = k + 1;
            int x = i + adjustedIndex % TAG_COLS_PER_ROW * TAG_COL_WIDTH + TAG_OFFSET_X;
            int y = j + adjustedIndex / TAG_COLS_PER_ROW * TAG_ROW_HEIGHT + TAG_OFFSET_Y;
            TagButton tb = new TagButton(x, y, TAG_ITEM_WIDTH, TAG_ITEM_HEIGHT,
                    Component.translatable(tag.toLanguageKey("tag")),
                    button -> handleBeverageTagClick(tag),
                    DEFAULT_NARRATION);
            tagButtons.add(tb);
            addRenderableWidget(tb);
        }
    }

    /**
     * 构建模式切换按钮
     */
    private void buildScreenModeButtons(int i, int j) {
        for (int k = 0; k < ScreenModes.length; k++) {
            int x = i - 40;
            int y = j + k * 20;
            ScreenMode mode = ScreenModes[k];
            ImageStateButton btn = new ImageStateButton(
                    x, y, 40, 16,
                    Component.translatable("gui.neo_mystias_izakaya." + mode.name().toLowerCase()),
                    button -> switchMode(mode),
                    DEFAULT_NARRATION);
            screenModeButtons.add(btn);
            addRenderableWidget(btn);
        }
        updateScreenModeButtons();
    }

    // === 标签点击处理 ===

    private void handleRecipeTagClick(Identifier tag) {
        if (isAllSelected) {
            isAllSelected = false;
        }
        if (foodTagSelected.contains(tag)) {
            foodTagSelected.remove(tag);
            if (foodTagSelected.isEmpty()) {
                isAllSelected = true;
            }
        } else {
            foodTagSelected.add(tag);
        }
        updateRecipeTagButtons();
        needsRefresh = true;
    }

    private void handleBeverageTagClick(Identifier tag) {
        if (isAllBeverageSelected) {
            isAllBeverageSelected = false;
        }
        if (beverageTagSelected.contains(tag)) {
            beverageTagSelected.remove(tag);
            if (beverageTagSelected.isEmpty()) {
                isAllBeverageSelected = true;
            }
        } else {
            beverageTagSelected.add(tag);
        }
        updateBeverageTagButtons();
        needsRefresh = true;
    }

    private void handleKitchenwareClick(KitchenwareMenu.KitchenwareType type) {
        if (isAllKitchenwareSelected) {
            isAllKitchenwareSelected = false;
        }
        if (selectedKitchenwareTypes.contains(type)) {
            selectedKitchenwareTypes.remove(type);
            if (selectedKitchenwareTypes.isEmpty()) {
                isAllKitchenwareSelected = true;
            }
        } else {
            selectedKitchenwareTypes.add(type);
        }
        updateKitchenwareButtons();
        needsRefresh = true;
    }

    // === 按钮状态更新 ===

    private void updateRecipeTagButtons() {
        if (allSelectTagButton != null) allSelectTagButton.setSelected(isAllSelected);
        List<Identifier> allTags = NMICuisinesTags.ALL;
        for (int idx = 0; idx < tagButtons.size() && idx < allTags.size(); idx++) {
            tagButtons.get(idx).setSelected(foodTagSelected.contains(allTags.get(idx)));
        }
    }

    private void updateBeverageTagButtons() {
        if (allSelectTagButton != null) allSelectTagButton.setSelected(isAllBeverageSelected);
        List<Identifier> allTags = NMIBeveragesTags.ALL;
        for (int idx = 0; idx < tagButtons.size() && idx < allTags.size(); idx++) {
            tagButtons.get(idx).setSelected(beverageTagSelected.contains(allTags.get(idx)));
        }
    }

    private void updateKitchenwareButtons() {
        if (allSelectKitchenwareButton != null) allSelectKitchenwareButton.setSelected(isAllKitchenwareSelected);
        for (int idx = 0; idx < kitchenwareButtons.size(); idx++) {
            kitchenwareButtons.get(idx).setSelected(selectedKitchenwareTypes.contains(kitchenwareTypes[idx]));
        }
    }

    private void updateScreenModeButtons() {
        for (int idx = 0; idx < screenModeButtons.size(); idx++) {
            screenModeButtons.get(idx).setSelected(ScreenModes[idx] == selectedScreenMode);
        }
    }

    // === 模式切换 ===

    private void switchMode(ScreenMode mode) {
        if (this.selectedScreenMode == mode) return;
        this.selectedScreenMode = mode;
        this.selected = null;
        this.search.setValue("");
        this.lastFilterText = "";

        int i = (this.width - this.imageWidth) / 2 - 40;
        int j = (this.height - this.imageHeight) / 2;
        rebuildFilterButtons(i, j);
        updateScreenModeButtons();
        reloadData();
        cuisineListWidget.refreshList();
    }

    // === 数据加载 ===

    private void reloadData() {
        displayItems.clear();
        switch (selectedScreenMode) {
            case RECIPE -> displayItems.addAll(allRecipes);
            case BEVERAGES -> displayItems.addAll(allBeverages);
            case COMMON_CUSTOMER -> displayItems.addAll(allCommonCustomers);
            case RARE_CUSTOMER -> displayItems.addAll(allRareCustomers);
        }
    }

    // === buildImageList: 将被过滤后的列表构建到 CuisineListWidget ===

    public void buildImageList(java.util.function.Consumer<DisplayEntry> addEntry) {
        for (Object item : displayItems) {
            if (item instanceof NMIRecipeHolder recipe) {
                addEntry.accept(new DisplayEntry(recipe, this, selectedScreenMode));
            } else if (item instanceof CustomerHolder customer) {
                addEntry.accept(new DisplayEntry(customer, this, selectedScreenMode));
            } else if (item instanceof ItemStack stack) {
                addEntry.accept(new DisplayEntry(stack, this, selectedScreenMode));
            }
        }
    }

    // === tick: 搜索和过滤 ===

    @Override
    public void tick() {
        String value = search.getValue();
        if (!lastFilterText.equals(value) || needsRefresh) {
            // 重新加载全量数据并过滤
            reloadData();
            applyFilters(value);
            this.lastFilterText = value;
            this.needsRefresh = false;
            this.cuisineListWidget.refreshList();
            this.cuisineListWidget.setScrollAmount(0);
        }
    }

    private void applyFilters(String searchText) {
        displayItems = switch (selectedScreenMode) {
            case RECIPE -> filterRecipes(allRecipes, searchText);
            case BEVERAGES -> filterBeverages(allBeverages, searchText);
            case COMMON_CUSTOMER -> filterCustomers(allCommonCustomers, searchText);
            case RARE_CUSTOMER -> filterCustomers(allRareCustomers, searchText);
        };
    }

    /**
     * 食谱模式过滤：按名称 + 菜系标签 + 厨具
     */
    private List<Object> filterRecipes(List<NMIRecipeHolder> source, String searchText) {
        return source.stream().filter(recipeHolder -> {
            NMIRecipe recipe = recipeHolder.recipe();
            MutableComponent translatable = Component.translatable(recipe.output().item().value().getDescriptionId());
            String name = getTranslatedString(translatable.getVisualOrderText()).toString();
            if (!name.contains(searchText)) return false;

            List<Identifier> identifiers = ClientNMIDataAccessor.INSTANCE.getTagItemListMap()
                    .getItemToTagMap().get(recipeHolder.key()).positiveTags();
            boolean containTag = isAllSelected || new HashSet<>(identifiers).containsAll(foodTagSelected);
            boolean containKitchenware = isAllKitchenwareSelected || selectedKitchenwareTypes.stream()
                    .anyMatch(type -> type.KITCHENWARE_TYPE.equals(recipe.kitchenware()));

            return containTag && containKitchenware;
        }).collect(java.util.stream.Collectors.toList());
    }

    /**
     * 酒水模式过滤：按名称 + 酒水标签（从 tagItemListMap 查找标签）
     */
    private List<Object> filterBeverages(List<ItemStack> source, String searchText) {
        var tagMap = ClientNMIDataAccessor.INSTANCE.getTagItemListMap().getItemToTagMap();
        List<Identifier> beverageTags = NMIBeveragesTags.ALL;

        return source.stream().filter(stack -> {
            String name = getTranslatedString(
                    Component.translatable(stack.getItem().getDescriptionId()).getVisualOrderText()
            ).toString();
            if (!name.contains(searchText)) return false;

            // 酒水标签过滤
            if (isAllBeverageSelected) return true;
            var itemTagList = tagMap.get(BuiltInRegistries.ITEM.getKey(stack.getItem()));
            if (itemTagList == null) return false;
            return new HashSet<>(itemTagList.positiveTags()).containsAll(beverageTagSelected);
        }).collect(java.util.stream.Collectors.toList());
    }

    /**
     * 顾客模式过滤：按名称
     */
    private List<Object> filterCustomers(List<CustomerHolder> source, String searchText) {
        return source.stream().filter(customerHolder -> {
            Identifier key = customerHolder.key();
            MutableComponent translatable = Component.translatable("customer.neo_mystias_izakaya." + key.getPath());
            String name = getTranslatedString(translatable.getVisualOrderText()).toString();
            return name.contains(searchText);
        }).collect(java.util.stream.Collectors.toList());
    }

    private void renderCuisineIngredient(GuiGraphicsExtractor graphics, Font font, NMIRecipeHolder recipe, int i, int j) {
        List<ItemStack> list = recipe.recipe().input().stream()
                .map(ingredient -> {
                    HolderSet<Item> values = ingredient.getValues();
                    if (values.size() == 0) return null;
                    return values.get(0).value().getDefaultInstance();
                })
                .filter(Objects::nonNull)
                .toList();
        for (int k = 0; k < list.size(); k++) {
            ItemStack stack = list.get(k);
            int x = i + 15 + k * 28;
            int y = j + 175;
            graphics.fill(x - 2, y - 2, x + 18, y + 18, 0xFFD0A680);
            graphics.item(stack, x, y);
        }

    }

    // === 顾客详情渲染 ===

    private void renderCustomerInfo(GuiGraphicsExtractor guiGraphics, Font font, CustomerHolder customerHolder, int i, int j) {
        Customer customer = customerHolder.customer();
        Identifier key = customerHolder.key();

        guiGraphics.text(font, Component.translatable("customer.neo_mystias_izakaya." + key.getPath()),
                i + 15, j + 10, 0xFF000000, false);

        // 预算
        guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.budget")
                        .append(": " + customer.budget().min() + "~" + customer.budget().max()),
                i + 15, j + 22, 0xFF000000, false);

        // 喜好
        if (!customer.likes().isEmpty()) {
            guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.likes")
                            .append(": " + customer.likes().size() + " 种"),
                    i + 15, j + 34, 0xFF000000, false);
        }

        // 厌恶
        if (!customer.dislikes().isEmpty()) {
            guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.dislikes")
                            .append(": " + customer.dislikes().size() + " 种"),
                    i + 15, j + 46, 0xFF000000, false);
        }

        // 酒水偏好
        if (!customer.beverage().isEmpty()) {
            guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.beverage_pref")
                            .append(": " + customer.beverage().size() + " 种"),
                    i + 15, j + 58, 0xFF000000, false);
        }
    }

    // === 酒水详情渲染 ===

    private void renderBeverageInfo(GuiGraphicsExtractor guiGraphics, Font font, ItemStack stack, int i, int j) {
        Integer itemStackPriceBase = NMIClientEconomyUtil.getItemStackPriceBase(stack);
        int price = itemStackPriceBase != null ? itemStackPriceBase : 0;

        guiGraphics.text(font, Component.translatable(stack.getItem().getDescriptionId()),
                i + 15, j + 10, 0xFF000000, false);
        guiGraphics.item(stack, i + 15, j + 20);
        guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.price")
                        .append(": " + price + " ").append(NMICommonComponentUtil.unitEn()),
                i + 50, j + 22, 0xFF000000, false);

        // 描述
        FormattedCharSequence description = Component.translatable(stack.getItem().getDescriptionId() + ".desc")
                .getVisualOrderText();
        StringBuilder builder = getTranslatedString(description);
        String substring = builder.subSequence(0, Math.min(10, builder.length())).toString();
        guiGraphics.text(font, Component.literal(substring + "..."), i + 15, j + 36, 0xFF000000, false);

        // 渲染酒水标签
        var tagMap = ClientNMIDataAccessor.INSTANCE.getTagItemListMap().getItemToTagMap();
        Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        ItemTagList itemTagList = tagMap.get(itemId);
        if (itemTagList != null) {
            renderTags(guiGraphics, font, i, j + 7, itemTagList);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    public enum ScreenMode {
        RECIPE, BEVERAGES, RARE_CUSTOMER, COMMON_CUSTOMER
    }
}
