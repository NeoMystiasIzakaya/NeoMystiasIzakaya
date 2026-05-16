/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.network.NMICustomerMapSyncMessage;
import icu.gensoukyo.neo_mystias_izakaya.common.network.NMIEconomyMapSyncMessage;
import icu.gensoukyo.neo_mystias_izakaya.common.network.NMIRecipeMapSyncMessage;
import icu.gensoukyo.neo_mystias_izakaya.common.network.TagItemListMapSyncMessage;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleTagItemListMapSyncMessage(TagItemListMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(()-> ClientNMIDataAccessor.INSTANCE.setTagItemListMap(message.map()));
    }

    public static void handleRecipeMapSyncMessage(NMIRecipeMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(()-> ClientNMIDataAccessor.INSTANCE.setRecipeMap(message.map()));
    }

    public static void handleCustomerMapSyncMessage(NMICustomerMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(()-> ClientNMIDataAccessor.INSTANCE.setCustomerMap(message.map()));
    }

    public static void handleEconomyMapSyncMessage(NMIEconomyMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(()-> ClientNMIDataAccessor.INSTANCE.setEconomyMap(message.map()));
    }
}
