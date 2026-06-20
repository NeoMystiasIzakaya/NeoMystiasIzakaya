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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenwareBlockEntity>> BOILING_POT = BLOCK_ENTITY_TYPES.register("boiling_pot", () -> new BlockEntityType<>(KitchenwareBlockEntity.BoilingPot::new, NMIBlocks.BOILING_POT.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenwareBlockEntity>> GRILL = BLOCK_ENTITY_TYPES.register("grill", () -> new BlockEntityType<>(KitchenwareBlockEntity.Grill::new, NMIBlocks.GRILL.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenwareBlockEntity>> FRYING_PAN = BLOCK_ENTITY_TYPES.register("frying_pan", () -> new BlockEntityType<>(KitchenwareBlockEntity.FryingPan::new, NMIBlocks.FRYING_PAN.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenwareBlockEntity>> STEAMER = BLOCK_ENTITY_TYPES.register("steamer", () -> new BlockEntityType<>(KitchenwareBlockEntity.Steamer::new, NMIBlocks.STEAMER.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenwareBlockEntity>> CUTTING_BOARD = BLOCK_ENTITY_TYPES.register("cutting_board", () -> new BlockEntityType<>(KitchenwareBlockEntity.CuttingBoard::new, NMIBlocks.CUTTING_BOARD.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CanteenControllerBlockEntity>> COUNTER = BLOCK_ENTITY_TYPES.register("counter", () -> new BlockEntityType<>(CanteenControllerBlockEntity::new, NMIBlocks.CANTEEN.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiningTableBlockEntity>> DINING_TABLE = BLOCK_ENTITY_TYPES.register("dining_table", () -> new BlockEntityType<>(DiningTableBlockEntity::new, NMIBlocks.DINING_TABLE.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CupboardBlockEntity>> CUPBOARD = BLOCK_ENTITY_TYPES.register("cupboard", () -> new BlockEntityType<>(CupboardBlockEntity::new, NMIBlocks.CUPBOARD.get()));
}
