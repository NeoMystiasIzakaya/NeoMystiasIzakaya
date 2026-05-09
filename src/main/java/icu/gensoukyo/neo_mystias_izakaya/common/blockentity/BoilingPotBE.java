/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class BoilingPotBE extends AbstractKitchenwareBE {
    public BoilingPotBE(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.BOILING_POT.get(), worldPosition, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }
}
