/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import net.minecraft.resources.Identifier;

public class NMICommonTagUtil {

    public static boolean isBeveragesTag(Identifier tag){
        return tag.getPath().startsWith("beverages/");
    }

    public static boolean isFoodsTag(Identifier tag){
        return tag.getPath().startsWith("cuisines/");
    }
}
