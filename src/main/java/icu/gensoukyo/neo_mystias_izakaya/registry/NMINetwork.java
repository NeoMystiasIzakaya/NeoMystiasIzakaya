/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.network.*;
import icu.gensoukyo.neo_mystias_izakaya.common.network.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
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

        registrar.playToClient(
                CupBoardItemResourceFullSyncMessage.TYPE,
                CupBoardItemResourceFullSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleCupBoardItemResourceFullSyncMessage
        );

        registrar.playToClient(
                CupBoardItemResourceConsumedMessage.TYPE,
                CupBoardItemResourceConsumedMessage.STREAM_CODEC,
                ClientPayloadHandler::handleCupBoardItemResourceUpdateMessage
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

        registrar.playToServer(
                StorePurchaseMessage.TYPE,
                StorePurchaseMessage.STREAM_CODEC,
                ServerPayloadHandler::handleStorePurchaseMessage
        );

        registrar.playToServer(
                RecordRecipeMessage.TYPE,
                RecordRecipeMessage.STREAM_CODEC,
                ServerPayloadHandler::handleRecordRecipeMessage
        );

        registrar.playToServer(
                ToggleCanteenOpenMessage.TYPE,
                ToggleCanteenOpenMessage.STREAM_CODEC,
                ServerPayloadHandler::handleToggleCanteenOpen
        );

        registrar.playToServer(
                RequestCupboardInfoMessage.TYPE,
                RequestCupboardInfoMessage.STREAM_CODEC,
                ServerPayloadHandler::handleRequestCupboardInfoMessage
        );

        registrar.playToServer(
                RequestExtractMenuToKitchenwareMessage.TYPE,
                RequestExtractMenuToKitchenwareMessage.STREAM_CODEC,
                ServerPayloadHandler::handleRequestExtractMenuToKitchenwareMessage
        );

        registrar.playToServer(
                RequestExtractItemToKitchenwareMessage.TYPE,
                RequestExtractItemToKitchenwareMessage.STREAM_CODEC,
                ServerPayloadHandler::handleRequestExtractItemToKitchenwareMessage
        );
    }
}
