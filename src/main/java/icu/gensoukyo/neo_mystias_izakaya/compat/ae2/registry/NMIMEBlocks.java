/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry;

import appeng.block.AEBaseEntityBlock;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.block.MECupboardBlock;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.block.MEIncubatorBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMEBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoMystiasIzakaya.MODID);

    public static final DeferredBlock<AEBaseEntityBlock<?>> ME_CUPBOARD = BLOCKS.registerBlock("me_cupboard", MECupboardBlock::new);
    public static final DeferredBlock<AEBaseEntityBlock<?>> ME_INCUBATOR = BLOCKS.registerBlock("me_incubator", MEIncubatorBlock::new);
}
