/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jei;

import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.compat.jei.recipe.*;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;

import java.util.List;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

@JeiPlugin
public class MystiasJEIPlugin implements IModPlugin {
    private static final Identifier IDENTIFIER = id("jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var gui = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BoilingPotRecipe(gui));
        registration.addRecipeCategories(new GrillRecipe(gui));
        registration.addRecipeCategories(new FryingPanRecipe(gui));
        registration.addRecipeCategories(new SteamerRecipe(gui));
        registration.addRecipeCategories(new CuttingBoardRecipe(gui));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<NMIRecipe> recipes;
        recipes = NMICommonRecipeUtil.unWarp(NMIServerRecipeUtil.getRecipesByKitchenware(NMIVanillaTags.BOILING_POT));
        registration.addRecipes(BoilingPotRecipe.BOILING_POT, recipes);

        recipes = NMICommonRecipeUtil.unWarp(NMIServerRecipeUtil.getRecipesByKitchenware(NMIVanillaTags.GRILL));
        registration.addRecipes(GrillRecipe.GRILL, recipes);

        recipes = NMICommonRecipeUtil.unWarp(NMIServerRecipeUtil.getRecipesByKitchenware(NMIVanillaTags.FRYING_PAN));
        registration.addRecipes(FryingPanRecipe.FRYING_PAN, recipes);

        recipes = NMICommonRecipeUtil.unWarp(NMIServerRecipeUtil.getRecipesByKitchenware(NMIVanillaTags.STEAMER));
        registration.addRecipes(SteamerRecipe.STEAMER, recipes);

        recipes = NMICommonRecipeUtil.unWarp(NMIServerRecipeUtil.getRecipesByKitchenware(NMIVanillaTags.CUTTING_BOARD));
        registration.addRecipes(CuttingBoardRecipe.CUTTING_BOARD, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(BoilingPotRecipe.BOILING_POT, NMIMainItems.BOILING_POT.toStack());
        registration.addCraftingStation(GrillRecipe.GRILL, NMIMainItems.GRILL.toStack());
        registration.addCraftingStation(FryingPanRecipe.FRYING_PAN, NMIMainItems.FRYING_PAN.toStack());
        registration.addCraftingStation(SteamerRecipe.STEAMER, NMIMainItems.STEAMER.toStack());
        registration.addCraftingStation(CuttingBoardRecipe.CUTTING_BOARD, NMIMainItems.CUTTING_BOARD.toStack());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {

    }

    @Override
    public Identifier getPluginUid() {
        return IDENTIFIER;
    }
}
