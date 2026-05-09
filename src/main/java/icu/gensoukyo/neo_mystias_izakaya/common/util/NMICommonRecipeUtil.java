/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;

import java.util.List;

public final class NMICommonRecipeUtil {

    public static List<NMIRecipe> unWarp(List<NMIRecipeHolder> holders) {
        return holders.stream().map(NMIRecipeHolder::recipe).toList();
    }
}
