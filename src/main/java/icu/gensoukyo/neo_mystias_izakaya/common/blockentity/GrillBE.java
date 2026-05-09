package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class GrillBE extends AbstractKitchenwareBE {
    public GrillBE(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.GRILL.get(), worldPosition, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }
}
