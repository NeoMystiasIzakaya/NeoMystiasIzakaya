/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.base.NMIEconomyMap;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class ServerPayloadSender {

    public static void sendTagItemListMapSyncMessage(TagItemListMap map) {
        PacketDistributor.sendToAllPlayers(new TagItemListMapSyncMessage(map));
    }

    public static void sendRecipeMapSyncMessage(NMIRecipeMap map) {
        PacketDistributor.sendToAllPlayers(new NMIRecipeMapSyncMessage(map));
    }

    public static void sendCustomerDataSyncMessage(CustomerMap message) {
        PacketDistributor.sendToAllPlayers(new NMICustomerMapSyncMessage(message));
    }

    public static void sendEconomyMapSyncMessage(NMIEconomyMap map) {
        PacketDistributor.sendToAllPlayers(new NMIEconomyMapSyncMessage(map));
    }

    public static void sendIzakayaMenuSyncMessage(ServerPlayer player, IzakayaOrderList list) {
        PacketDistributor.sendToPlayer(player,new IzakayaOrderSyncFullMessage(list));
    }

    public static void sendIzakayaMenuUpdateMessage(ServerPlayer player, short id , IzakayaOrder order) {
        PacketDistributor.sendToPlayer(player,new IzakayaOrderUpdateMessage(id,order));
    }
}
