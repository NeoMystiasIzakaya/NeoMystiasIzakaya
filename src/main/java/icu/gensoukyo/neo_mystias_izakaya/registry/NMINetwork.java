/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadHandler;
import icu.gensoukyo.neo_mystias_izakaya.common.network.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class NMINetwork {
    @SubscribeEvent // on the mod event bus
    public static void register(RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                TagItemListMapSyncMessage.TYPE,
                TagItemListMapSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleTagItemListMapSyncMessage
        );

        registrar.playToClient(
                NMIRecipeMapSyncMessage.TYPE,
                NMIRecipeMapSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleRecipeMapSyncMessage
        );

        registrar.playToClient(
                NMICustomerMapSyncMessage.TYPE,
                NMICustomerMapSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleCustomerMapSyncMessage
        );

        registrar.playToServer(
                NMIKitchenwareCookMessage.TYPE,
                NMIKitchenwareCookMessage.STREAM_CODEC,
                ServerPayloadHandler::handleKitchenwareCookMessage
        );
    }
}
