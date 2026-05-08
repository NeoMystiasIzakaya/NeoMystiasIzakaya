package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.BoilingPotBE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMIBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NeoMystiasIzakaya.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BoilingPotBE>> BOILING_POT = BLOCK_ENTITY_TYPES.register("boiling_pot", () -> new BlockEntityType<>(BoilingPotBE::new, NMIBlocks.BOILING_POT.get()));
}
