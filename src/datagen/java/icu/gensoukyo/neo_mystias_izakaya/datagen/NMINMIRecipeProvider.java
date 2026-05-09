/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.datagen.api.NMIRecipeProvider;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class NMINMIRecipeProvider extends NMIRecipeProvider {
    protected NMINMIRecipeProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void addRecipes() {
        this.addBoilingPotRecipes();
        this.addCuttingBoardRecipes();
        this.addFryingPanRecipes();
        this.addGrillRecipes();
        this.addSteamerRecipes();
    }

    private void addBoilingPotRecipes() {
        this.builder(NMICuisinesItems.SEA_URCHIN_SASHIMI)
                .input(Items.KELP)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(6)
                .build();
        this.builder(NMICuisinesItems.MISO_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();
        this.builder(NMICuisinesItems.POWER_SOUP)
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

    }

    private void addCuttingBoardRecipes() {

    }

    private void addFryingPanRecipes() {

    }

    private void addGrillRecipes() {

    }

    private void addSteamerRecipes() {

    }
}
