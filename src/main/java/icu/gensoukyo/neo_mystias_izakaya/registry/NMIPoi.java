package icu.gensoukyo.neo_mystias_izakaya.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIPoi {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, NeoMystiasIzakaya.MODID);

    public static final DeferredHolder<PoiType, PoiType> DINING_TABLE = POI_TYPES.register("dinging_table", NMIPoi::getSeats);

    public static PoiType getSeats() {
        return new PoiType(ImmutableList.of(NMIBlocks.DINING_TABLE.get())
                .stream().flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
                .collect(ImmutableSet.toImmutableSet()), 0, 1);
    }
}
