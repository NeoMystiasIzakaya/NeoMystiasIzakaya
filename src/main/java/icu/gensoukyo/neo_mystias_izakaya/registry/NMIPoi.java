package icu.gensoukyo.neo_mystias_izakaya.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public class NMIPoi {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, NeoMystiasIzakaya.MODID);

    public static final DeferredHolder<PoiType, PoiType> DINING_TABLE = POI_TYPES.register("dinging_table", NMIPoi::getSeats);

    private static final Set<BlockState> SEATS = ImmutableList.of(NMIBlocks.DINING_TABLE.get())
            .stream().flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .collect(ImmutableSet.toImmutableSet());

    public static PoiType getSeats() {
        return new PoiType(SEATS, 0, 1);
    }
}
