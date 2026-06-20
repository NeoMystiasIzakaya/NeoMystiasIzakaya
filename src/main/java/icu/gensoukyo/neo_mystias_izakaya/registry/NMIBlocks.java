/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoMystiasIzakaya.MODID);
    public static final DeferredBlock<Block> BOILING_POT = BLOCKS.register("boiling_pot", KitchenwareBlock.BoilingPot::new);
    public static final DeferredBlock<Block> GRILL = BLOCKS.register("grill", KitchenwareBlock.Grill::new);
    public static final DeferredBlock<Block> FRYING_PAN = BLOCKS.register("frying_pan", KitchenwareBlock.FryingPan::new);
    public static final DeferredBlock<Block> STEAMER = BLOCKS.register("steamer", KitchenwareBlock.Steamer::new);
    public static final DeferredBlock<Block> CUTTING_BOARD = BLOCKS.register("cutting_board", KitchenwareBlock.CuttingBoard::new);
    public static final DeferredBlock<Block> CANTEEN = BLOCKS.registerBlock("canteen", CanteenControllerBlock::new);
    public static final DeferredBlock<Block> DINING_TABLE = BLOCKS.register("dining_table", () -> new DiningTableBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> STORE = BLOCKS.registerBlock("store", StoreBlock::new);
}
