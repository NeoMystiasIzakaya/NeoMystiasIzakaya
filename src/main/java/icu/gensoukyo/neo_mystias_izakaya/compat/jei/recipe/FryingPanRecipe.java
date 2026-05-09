/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jei.recipe;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class FryingPanRecipe extends AbstractNMIRecipe {
    public static final IRecipeType<NMIRecipe> FRYING_PAN = IRecipeType.create(NeoMystiasIzakaya.MODID, "frying_pan", NMIRecipe.class);

    public FryingPanRecipe(IGuiHelper guiHelper) {
        super(guiHelper, guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, NMIMainItems.FRYING_PAN.toStack()));
    }

    @Override
    public IRecipeType<NMIRecipe> getRecipeType() {
        return FRYING_PAN;
    }
}
