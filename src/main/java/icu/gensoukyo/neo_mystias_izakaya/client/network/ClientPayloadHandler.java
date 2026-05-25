/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.network.*;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleTagItemListMapSyncMessage(TagItemListMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> ClientNMIDataAccessor.INSTANCE.setTagItemListMap(message.map()));
    }

    public static void handleRecipeMapSyncMessage(NMIRecipeMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> ClientNMIDataAccessor.INSTANCE.setRecipeMap(message.map()));
    }

    public static void handleCustomerMapSyncMessage(NMICustomerMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> ClientNMIDataAccessor.INSTANCE.setCustomerMap(message.map()));
    }

    public static void handleEconomyMapSyncMessage(NMIEconomyMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> ClientNMIDataAccessor.INSTANCE.setEconomyMap(message.map()));
    }

    public static void handleIzakayaOrderSyncFullMessage(IzakayaOrderSyncFullMessage message, IPayloadContext context) {
        context.enqueueWork(() -> NMICommonIzakayaUtil.setOrder(context.player(), message.list()));
    }

    public static void handleIzakayaOrderUpdateMessage(IzakayaOrderUpdateMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            IzakayaOrderList data = NMICommonIzakayaUtil.getOrder(context.player());
            data.getOrderMap().get(message.id()).setOrder(message.order());
            NMICommonIzakayaUtil.setOrder(context.player(), data);
        });
    }

    public static void handleBalanceTransactionUpdateMessage(NMIBalanceTransactionUpdateMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {

        });
    }

    public static void handleBalanceTransactionSyncFullMessage(NMIBalanceTransactionSyncFullMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {

        });
    }
}
