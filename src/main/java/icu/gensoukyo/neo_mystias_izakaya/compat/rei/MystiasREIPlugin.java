/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.rei;


import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.DishServingScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.compat.rei.gui.DishServingExclusionZonesProvider;
import icu.gensoukyo.neo_mystias_izakaya.compat.rei.gui.KitchenwareExclusionZonesProvider;
import icu.gensoukyo.neo_mystias_izakaya.compat.rei.recipe.NMIRecipeDisplayGenerator;
import icu.gensoukyo.neo_mystias_izakaya.compat.rei.recipe.NMIRecipeReiCategory;
import icu.gensoukyo.neo_mystias_izakaya.compat.rei.recipe.NMIRecipeReiDisplay;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import me.shedaniel.rei.api.client.config.addon.ConfigAddonRegistry;
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.entry.renderer.EntryRendererRegistry;
import me.shedaniel.rei.api.client.favorites.FavoriteEntryType;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.CollapsibleEntryRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.search.method.InputMethodRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class MystiasREIPlugin implements REIClientPlugin {

    NMIRecipeReiCategory.CuttingBoard cuttingBoard;
    NMIRecipeReiCategory.BoilingPot boilingPot;
    NMIRecipeReiCategory.FryingPan fryingPan;
    NMIRecipeReiCategory.Steamer steamer;
    NMIRecipeReiCategory.Grill grill;

    private void init() {
        if(cuttingBoard != null)return;
        cuttingBoard = new NMIRecipeReiCategory.CuttingBoard();
        boilingPot = new NMIRecipeReiCategory.BoilingPot();
        fryingPan = new NMIRecipeReiCategory.FryingPan();
        steamer = new NMIRecipeReiCategory.Steamer();
        grill = new NMIRecipeReiCategory.Grill();
    }

    @Override
    public void registerEntryRenderers(EntryRendererRegistry registry) {

    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        init();

        registry.add(cuttingBoard);
        registry.add(boilingPot);
        registry.add(fryingPan);
        registry.add(steamer);
        registry.add(grill);

        registry.addWorkstations(cuttingBoard.getCategoryIdentifier(), EntryStacks.of(cuttingBoard.getKitchenware().kitchenwareBlock()));
        registry.addWorkstations(boilingPot.getCategoryIdentifier(), EntryStacks.of(boilingPot.getKitchenware().kitchenwareBlock()));
        registry.addWorkstations(fryingPan.getCategoryIdentifier(), EntryStacks.of(fryingPan.getKitchenware().kitchenwareBlock()));
        registry.addWorkstations(steamer.getCategoryIdentifier(), EntryStacks.of(steamer.getKitchenware().kitchenwareBlock()));
        registry.addWorkstations(grill.getCategoryIdentifier(), EntryStacks.of(grill.getKitchenware().kitchenwareBlock()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        init();
        registry.registerDisplayGenerator(cuttingBoard.getCategoryIdentifier(),new NMIRecipeDisplayGenerator.CuttingBoard());
        registry.registerDisplayGenerator(boilingPot.getCategoryIdentifier(),new NMIRecipeDisplayGenerator.BoilingPot());
        registry.registerDisplayGenerator(fryingPan.getCategoryIdentifier(),new NMIRecipeDisplayGenerator.FryingPan());
        registry.registerDisplayGenerator(steamer.getCategoryIdentifier(),new NMIRecipeDisplayGenerator.Steamer());
        registry.registerDisplayGenerator(grill.getCategoryIdentifier(),new NMIRecipeDisplayGenerator.Grill());
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        zones.register(KitchenwareScreen.class, new KitchenwareExclusionZonesProvider());
        zones.register(DishServingScreen.class, new DishServingExclusionZonesProvider());
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
    }

    @Override
    public void registerBasicEntryFiltering(BasicFilteringRule<?> rule) {
    }

    @Override
    public void registerCollapsibleEntries(CollapsibleEntryRegistry registry) {
    }

    @Override
    public void registerFavorites(FavoriteEntryType.Registry registry) {
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
    }

    @Override
    public void registerConfigAddons(ConfigAddonRegistry registry) {
    }

    @Override
    public void registerInputMethods(InputMethodRegistry registry) {
    }
}
