/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.event;

import icu.gensoukyo.neo_mystias_izakaya.common.dal.ServerNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer){
            ServerPayloadSender.sendTagItemListMapSyncMessage(serverPlayer, ServerNMIDataAccessor.INSTANCE.getTagItemListMap());
            ServerPayloadSender.sendRecipeMapSyncMessage(serverPlayer, ServerNMIDataAccessor.INSTANCE.getRecipeMap());
            ServerPayloadSender.sendCustomerDataSyncMessage(serverPlayer, ServerNMIDataAccessor.INSTANCE.getCustomerMap());
            ServerPayloadSender.sendEconomyMapSyncMessage(serverPlayer, ServerNMIDataAccessor.INSTANCE.getEconomyMap());
            ServerPayloadSender.sendStoreMapSyncMessage(serverPlayer, ServerNMIDataAccessor.INSTANCE.getStoreMap());
        }
    }
}
