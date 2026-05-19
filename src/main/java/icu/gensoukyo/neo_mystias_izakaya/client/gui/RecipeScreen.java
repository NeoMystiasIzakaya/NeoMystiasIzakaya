/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineListWidget;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class RecipeScreen extends Screen {
    private final int imageWidth;
    private final int imageHeight;
    Identifier BACKGROUND = id("textures/gui/recipe_bg.png");
//    List<FoodTagEnum> foodTagEnums = List.of(FoodTagEnum.values());
//    ArrayList<FoodTagEnum> foodTagSelected = new ArrayList<>();
    List<NMIRecipeHolder> cookedMealItems = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipes();
    final List<NMIRecipeHolder> unsortedCookedMealItems = cookedMealItems;
    @Setter
    CuisineListWidget.CuisineEntry selected;
    EditBox search;
    String lastFilterText = "";
    CuisineListWidget cuisineListWidget;
    public RecipeScreen() {
        super(Component.literal("Recipe Screen"));
        this.imageWidth = 230;
        this.imageHeight = 219;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0.0F, 0.0F, 256, 256, 256, 256);
        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    protected void init() {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.search = new EditBox(getFont(), i + 12, j + 9, 50, 15, Component.translatable("fml.menu.mods.search"));
        this.cuisineListWidget = new CuisineListWidget(this, minecraft, 100, 120, j + 87, getFont().lineHeight * 2);
        this.cuisineListWidget.setX(i + 128);
        this.cuisineListWidget.refreshList();
        addRenderableWidget(this.search);
        addRenderableWidget(this.cuisineListWidget);
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildImageList(Consumer<T> modListViewConsumer, Function<NMIRecipeHolder, T> newEntry) {
        cookedMealItems.forEach(cookedMealItem -> modListViewConsumer.accept(newEntry.apply(cookedMealItem)));
    }
}
