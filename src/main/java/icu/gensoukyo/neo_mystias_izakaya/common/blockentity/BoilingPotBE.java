package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BoilingPotBE extends AbstractCookerBE {
    public BoilingPotBE(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.BOILING_POT.get(), worldPosition, blockState);
    }
}
