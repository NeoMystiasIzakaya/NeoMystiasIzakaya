/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NeoMystiasIzakaya.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BoilingPotBE>> BOILING_POT = BLOCK_ENTITY_TYPES.register("boiling_pot", () -> new BlockEntityType<>(BoilingPotBE::new, NMIBlocks.BOILING_POT.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrillBE>> GRILL = BLOCK_ENTITY_TYPES.register("grill", () -> new BlockEntityType<>(GrillBE::new, NMIBlocks.GRILL.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FryingPanBE>> FRYING_PAN = BLOCK_ENTITY_TYPES.register("frying_pan", () -> new BlockEntityType<>(FryingPanBE::new, NMIBlocks.FRYING_PAN.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SteamerBE>> STEAMER = BLOCK_ENTITY_TYPES.register("steamer", () -> new BlockEntityType<>(SteamerBE::new, NMIBlocks.STEAMER.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CuttingBoardBE>> CUTTING_BOARD = BLOCK_ENTITY_TYPES.register("cutting_board", () -> new BlockEntityType<>(CuttingBoardBE::new, NMIBlocks.CUTTING_BOARD.get()));
}
