/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonBalanceUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.base.NMIEconomyMap;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.NMIStoreMap;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction.NMIBalanceTransaction;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction.NMIBalanceTransactionEntry;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class ServerPayloadSender {

    public static void sendTagItemListMapSyncMessage(TagItemListMap map) {
        PacketDistributor.sendToAllPlayers(new TagItemListMapSyncMessage(map));
    }
    public static void sendTagItemListMapSyncMessage(ServerPlayer player,TagItemListMap map) {
        PacketDistributor.sendToPlayer(player,new TagItemListMapSyncMessage(map));
    }

    public static void sendRecipeMapSyncMessage(NMIRecipeMap map) {
        PacketDistributor.sendToAllPlayers(new NMIRecipeMapSyncMessage(map));
    }
    public static void sendRecipeMapSyncMessage(ServerPlayer player, NMIRecipeMap map) {
        PacketDistributor.sendToPlayer(player, new NMIRecipeMapSyncMessage(map));
    }

    public static void sendCustomerDataSyncMessage(CustomerMap message) {
        PacketDistributor.sendToAllPlayers(new NMICustomerMapSyncMessage(message));
    }
    public static void sendCustomerDataSyncMessage(ServerPlayer player, CustomerMap message) {
        PacketDistributor.sendToPlayer(player, new NMICustomerMapSyncMessage(message));
    }

    public static void sendEconomyMapSyncMessage(NMIEconomyMap map) {
        PacketDistributor.sendToAllPlayers(new NMIEconomyMapSyncMessage(map));
    }
    public static void sendEconomyMapSyncMessage(ServerPlayer player, NMIEconomyMap map) {
        PacketDistributor.sendToPlayer(player, new NMIEconomyMapSyncMessage(map));
    }

    public static void sendStoreMapSyncMessage(NMIStoreMap map) {
        PacketDistributor.sendToAllPlayers(new NMIStoreMapSyncMessage(map));
    }
    public static void sendStoreMapSyncMessage(ServerPlayer player, NMIStoreMap map) {
        PacketDistributor.sendToPlayer(player, new NMIStoreMapSyncMessage(map));
    }

    public static void sendIzakayaMenuSyncMessage(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new IzakayaOrderSyncFullMessage(NMICommonIzakayaUtil.getOrder(player)));
    }

    public static void sendIzakayaMenuSyncMessage(ServerPlayer player, IzakayaOrderList list) {
        PacketDistributor.sendToPlayer(player,new IzakayaOrderSyncFullMessage(list));
    }

    public static void sendIzakayaMenuUpdateMessage(ServerPlayer player, short id , IzakayaOrder order) {
        PacketDistributor.sendToPlayer(player,new IzakayaOrderUpdateMessage(id,order));
    }

    public static void sendTransactionSyncMessage(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new NMIBalanceTransactionSyncFullMessage(NMICommonBalanceUtil.getTransaction(player)));
    }

    public static void sendTransactionSyncMessage(ServerPlayer player, NMIBalanceTransaction transaction) {
        PacketDistributor.sendToPlayer(player, new NMIBalanceTransactionSyncFullMessage(transaction));
    }

    public static void sendTransactionUpdateMessage(ServerPlayer player, NMIBalanceTransactionEntry entry) {
        PacketDistributor.sendToPlayer(player, new NMIBalanceTransactionUpdateMessage(entry));
    }

    public static void sendKitchenwareSyncMessage(BlockPos blockPos, int cookTime) {
        PacketDistributor.sendToAllPlayers(new KitchenwareTimeSyncMessage(blockPos, cookTime));
    }

    public static void sendDiningTableSaleMessage(ServerPlayer player, int saleAmount) {
        PacketDistributor.sendToPlayer(player, new DiningTableSaleMessage(saleAmount));
    }
}
