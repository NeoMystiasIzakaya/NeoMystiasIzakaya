/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jei;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.DishServingScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.compat.jei.gui.DishServingContainerHandler;
import icu.gensoukyo.neo_mystias_izakaya.compat.jei.gui.KitchenwareContainerHandler;
import icu.gensoukyo.neo_mystias_izakaya.compat.jei.recipe.*;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;

import java.util.List;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

@JeiPlugin
public class MystiasJEIPlugin implements IModPlugin {
    private static final Identifier IDENTIFIER = id("jei");

    NMIRecipeJeiCategory.CuttingBoard cuttingBoard;
    NMIRecipeJeiCategory.BoilingPot boilingPot;
    NMIRecipeJeiCategory.FryingPan fryingPan;
    NMIRecipeJeiCategory.Steamer steamer;
    NMIRecipeJeiCategory.Grill grill;

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        registration.addSimpleRecipeManagerPlugin(boilingPot.getRecipeType(),MystiasJEIRecipeManagerPlugin.of(NMIKitchenware.BOILING_POT.get()));
        registration.addSimpleRecipeManagerPlugin(grill.getRecipeType(),MystiasJEIRecipeManagerPlugin.of(NMIKitchenware.GRILL.get()));
        registration.addSimpleRecipeManagerPlugin(fryingPan.getRecipeType(),MystiasJEIRecipeManagerPlugin.of(NMIKitchenware.FRYING_PAN.get()));
        registration.addSimpleRecipeManagerPlugin(steamer.getRecipeType(),MystiasJEIRecipeManagerPlugin.of(NMIKitchenware.STEAMER.get()));
        registration.addSimpleRecipeManagerPlugin(cuttingBoard.getRecipeType(),MystiasJEIRecipeManagerPlugin.of(NMIKitchenware.CUTTING_BOARD.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var gui = registration.getJeiHelpers().getGuiHelper();
        cuttingBoard = new NMIRecipeJeiCategory.CuttingBoard(gui);
        boilingPot = new NMIRecipeJeiCategory.BoilingPot(gui);
        fryingPan = new NMIRecipeJeiCategory.FryingPan(gui);
        steamer = new NMIRecipeJeiCategory.Steamer(gui);
        grill = new NMIRecipeJeiCategory.Grill(gui);
        registration.addRecipeCategories(cuttingBoard);
        registration.addRecipeCategories(boilingPot);
        registration.addRecipeCategories(fryingPan);
        registration.addRecipeCategories(steamer);
        registration.addRecipeCategories(grill);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(boilingPot.getRecipeType(), NMIMainItems.BOILING_POT.toStack());
        registration.addCraftingStation(grill.getRecipeType(), NMIMainItems.GRILL.toStack());
        registration.addCraftingStation(fryingPan.getRecipeType(), NMIMainItems.FRYING_PAN.toStack());
        registration.addCraftingStation(steamer.getRecipeType(), NMIMainItems.STEAMER.toStack());
        registration.addCraftingStation(cuttingBoard.getRecipeType(), NMIMainItems.CUTTING_BOARD.toStack());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(KitchenwareScreen.class, new KitchenwareContainerHandler());
        registration.addGuiContainerHandler(DishServingScreen.class, new DishServingContainerHandler());
    }

    @Override
    public Identifier getPluginUid() {
        return IDENTIFIER;
    }
}
