/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class NMIBlockTags {
    public static final TagKey<Block> KITCHENWARE_BLOCK = of("kitchenware");
    public static final TagKey<Block> BOILING_POT = of("boiling_pot");
    public static final TagKey<Block> CUTTING_BOARD = of("cutting_board");
    public static final TagKey<Block> FRYING_PAN = of("frying_pan");
    public static final TagKey<Block> GRILL = of("grill");
    public static final TagKey<Block> STEAMER = of("steamer");

    public static TagKey<Block> of(String name) {
        return of(NeoMystiasIzakaya.id(name));
    }

    public static TagKey<Block> of(Identifier id) {
        return TagKey.create(Registries.BLOCK, id);
    }
}
