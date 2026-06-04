package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class NMIMemoryTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, NeoMystiasIzakaya.MODID);
    public static Supplier<MemoryModuleType<PositionTracker>> TARGET_POS = MEMORY_MODULE_TYPES.register("dinging_table_pos", () -> new MemoryModuleType<>(Optional.empty()));
}
