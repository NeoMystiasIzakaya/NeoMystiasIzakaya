/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class NMIMemoryTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, NeoMystiasIzakaya.MODID);
    public static Supplier<MemoryModuleType<BlockPos>> TARGET_POS = MEMORY_MODULE_TYPES.register("dinging_table_pos", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));
    public static Supplier<MemoryModuleType<BlockPos>> CONTROLLER_POS = MEMORY_MODULE_TYPES.register("canteen_controller_pos", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));
}
