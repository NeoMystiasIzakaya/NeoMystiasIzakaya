package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class SteamerBE extends AbstractKitchenwareBE {
    public SteamerBE(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.STEAMER.get(), worldPosition, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }
}
