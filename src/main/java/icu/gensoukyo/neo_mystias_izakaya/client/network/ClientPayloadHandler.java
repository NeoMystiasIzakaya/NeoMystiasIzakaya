/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.common.network.*;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonBalanceUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
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

    public static void handleStoreMapSyncMessage(NMIStoreMapSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> ClientNMIDataAccessor.INSTANCE.setStoreMap(message.map()));
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
        context.enqueueWork(() -> NMICommonBalanceUtil.addTransactionEntry(context.player(), message.transactionEntry()));
    }

    public static void handleBalanceTransactionSyncFullMessage(NMIBalanceTransactionSyncFullMessage message, IPayloadContext context) {
        context.enqueueWork(() -> NMICommonBalanceUtil.setTransaction(context.player(), message.transaction()));
    }

    public static void handleDiningTableSaleMessage(DiningTableSaleMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            NMIClientUtil.showToast(message.saleAmount());
        });
    }

    public static void handleKitchenwareTimeSyncMessage(KitchenwareTimeSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null && level.isLoaded(message.pos())) {
                var blockEntity = level.getBlockEntity(message.pos());
                if (blockEntity instanceof AbstractKitchenwareBE target) {
                    target.setCookingTime(message.cookTime());
                }
            }
        });
    }
}
