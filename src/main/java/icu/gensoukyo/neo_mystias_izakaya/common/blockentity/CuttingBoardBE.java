/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareMenu;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class CuttingBoardBE extends AbstractKitchenwareBE {
    public CuttingBoardBE(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.CUTTING_BOARD.get(), worldPosition, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Cutting Board");
    }

    @Override
    public KitchenwareMenu.KitchenwareType getKitchenwareType() {
        return KitchenwareMenu.KitchenwareType.CUTTING_BOARD;
    }
}
