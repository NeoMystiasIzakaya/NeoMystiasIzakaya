/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class FryingPanBE extends AbstractKitchenwareBE {
    public FryingPanBE(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.FRYING_PAN.get(), worldPosition, blockState);
    }

    @Override
    protected @NonNull Component getDefaultName() {
        return Component.literal("Frying Pan");
    }
}
