/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NMIBlockVanillaTags {
    public static final TagKey<Block> KITCHENWARE_BLOCK = ofBlock("kitchenware");
    public static final TagKey<Block> BOILING_POT = ofBlock("boiling_pot");
    public static final TagKey<Block> CUTTING_BOARD = ofBlock("cutting_board");
    public static final TagKey<Block> FRYING_PAN = ofBlock("frying_pan");
    public static final TagKey<Block> GRILL = ofBlock("grill");
    public static final TagKey<Block> STEAMER = ofBlock("steamer");



    public static TagKey<Block> ofBlock(String name) {
        return ofBlock(NeoMystiasIzakaya.id(name));
    }

    public static TagKey<Block> ofBlock(Identifier id) {
        return TagKey.create(Registries.BLOCK, id);
    }

    public static TagKey<Item> ofItem(String name) {
        return ofItem(NeoMystiasIzakaya.id(name));
    }

    private static TagKey<Item> ofItem(Identifier id) {
        return TagKey.create(Registries.ITEM, id);
    }
}
