/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class NMIServerRecipeUtil {

    public static List<NMIRecipeHolder> getAvailableRecipes( List<ItemStack> input) {
        Set<Identifier> recipesIds = new HashSet<>();
        for (ItemStack stack : input) {
            List<Identifier> identifiers = NMIDataAccessor.server().getRecipeMap().getInputItemToRecipeMap().get(NMICommonItemStackUtil.get(stack));
            if (identifiers != null) {
                recipesIds.addAll(identifiers);
            }
        }
        return getRecipes(new ArrayList<>(recipesIds));
    }

    public static List<NMIRecipeHolder> getRecipesByOutput(ItemStack output) {
        List<Identifier> recipesIds = NMIDataAccessor.server().getRecipeMap().getOutputItemToRecipeMap().get(NMICommonItemStackUtil.get(output));
        return getRecipes(recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipesByKitchenware(TagKey<Block> kitchenware) {
        List<Identifier> recipesIds = NMIDataAccessor.server().getRecipeMap().getKitchenwareToRecipeMap().get(kitchenware);
        return getRecipes(recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipes(List<Identifier> recipesIds) {
        List<NMIRecipeHolder> recipes = new ArrayList<>(recipesIds.size());
        recipesIds.forEach(id -> {
            NMIRecipeHolder recipe = NMIDataAccessor.server().getRecipeMap().getRecipeMap().get(id);
            if (recipe != null) {
                recipes.add(recipe);
            }
        });
        return recipes;
    }

    public static NMIRecipeHolder getRecipe(Identifier id) {
        return NMIDataAccessor.server().getRecipeMap().getRecipeMap().get(id);
    }

}
