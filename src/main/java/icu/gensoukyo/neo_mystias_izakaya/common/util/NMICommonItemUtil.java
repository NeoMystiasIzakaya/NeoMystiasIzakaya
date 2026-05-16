/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Optional;

public  final class NMICommonItemUtil {

    public static Identifier get(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static Identifier get(DeferredItem<? extends Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    public static Optional<Holder.Reference<Item>> get(Identifier id) {
        return BuiltInRegistries.ITEM.get(id);
    }

    public static Item mustGet(Identifier id) {
        return get(id).orElseThrow(() -> new IllegalArgumentException("No item found for item: " + id)).value();
    }
}
