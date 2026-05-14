/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleKitchenwareCookMessage(NMIKitchenwareCookMessage message, IPayloadContext context) {
        context.enqueueWork(()->{
            BlockEntity blockEntity = context.player().level().getBlockEntity(message.blockPos());
            if (blockEntity instanceof AbstractKitchenwareBE kitchenware) {
                kitchenware.getItems().clear();
                kitchenware.setTargetItem(message.cuisine());
            }
        });
    }
}
