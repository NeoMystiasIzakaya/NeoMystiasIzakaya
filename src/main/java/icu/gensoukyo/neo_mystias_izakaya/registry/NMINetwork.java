/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadHandler;
import icu.gensoukyo.neo_mystias_izakaya.client.network.NMIIzakayaMenuSyncMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.NMIKitchenwareCookMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.OpenDishServingMessage;
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
        registerServerBound(registrar);
        registerClientBound(registrar);
    }

    private static void registerServerBound(PayloadRegistrar registrar) {

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

        registrar.playToClient(
                NMIEconomyMapSyncMessage.TYPE,
                NMIEconomyMapSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleEconomyMapSyncMessage
        );

        registrar.playToClient(
                NMIStoreMapSyncMessage.TYPE,
                NMIStoreMapSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleStoreMapSyncMessage
        );

        registrar.playToClient(
                IzakayaOrderSyncFullMessage.TYPE,
                IzakayaOrderSyncFullMessage.STREAM_CODEC,
                ClientPayloadHandler::handleIzakayaOrderSyncFullMessage
        );

        registrar.playToClient(
                IzakayaOrderUpdateMessage.TYPE,
                IzakayaOrderUpdateMessage.STREAM_CODEC,
                ClientPayloadHandler::handleIzakayaOrderUpdateMessage
        );

        registrar.playToClient(
                NMIBalanceTransactionSyncFullMessage.TYPE,
                NMIBalanceTransactionSyncFullMessage.STREAM_CODEC,
                ClientPayloadHandler::handleBalanceTransactionSyncFullMessage
        );

        registrar.playToClient(
                NMIBalanceTransactionUpdateMessage.TYPE,
                NMIBalanceTransactionUpdateMessage.STREAM_CODEC,
                ClientPayloadHandler::handleBalanceTransactionUpdateMessage
        );

        registrar.playToClient(
                KitchenwareTimeSyncMessage.TYPE,
                KitchenwareTimeSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleKitchenwareTimeSyncMessage
        );

        registrar.playToClient(
                DiningTableSaleMessage.TYPE,
                DiningTableSaleMessage.STREAM_CODEC,
                ClientPayloadHandler::handleDiningTableSaleMessage
        );
    }

    private static void registerClientBound(PayloadRegistrar registrar) {

        registrar.playToServer(
                NMIKitchenwareCookMessage.TYPE,
                NMIKitchenwareCookMessage.STREAM_CODEC,
                ServerPayloadHandler::handleKitchenwareCookMessage
        );

        registrar.playToServer(
                NMIIzakayaMenuSyncMessage.TYPE,
                NMIIzakayaMenuSyncMessage.STREAM_CODEC,
                ServerPayloadHandler::handleIzakayaMenuSyncMessage
        );

        registrar.playToServer(
                OpenDishServingMessage.TYPE,
                OpenDishServingMessage.STREAM_CODEC,
                ServerPayloadHandler::handleOpenDishServingMessage
        );
    }
}
