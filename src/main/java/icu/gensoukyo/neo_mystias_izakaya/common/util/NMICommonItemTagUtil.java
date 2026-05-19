/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import net.minecraft.resources.Identifier;

import java.util.Map;

public class NMICommonItemTagUtil {

    public static boolean isBeveragesTag(Identifier tag) {
        return tag.getPath().startsWith("beverages/");
    }

    public static boolean isCuisinesTag(Identifier tag) {
        return tag.getPath().startsWith("cuisines/");
    }

    public static Map<Identifier, TagItemListHolder> getBeveragesTagMap(Map<Identifier, TagItemListHolder> original) {
        return original.entrySet().stream().filter(entry -> isBeveragesTag(entry.getKey())).collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Identifier, TagItemListHolder> getCuisinesTagMap(Map<Identifier, TagItemListHolder> original) {
        return original.entrySet().stream().filter(entry -> isCuisinesTag(entry.getKey())).collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
