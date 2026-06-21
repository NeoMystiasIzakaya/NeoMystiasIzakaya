/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class ClientPayloadSender {
    public static void sendKitchenwareCookMessage(Identifier cuisine, BlockPos blockPos) {
        ClientPacketDistributor.sendToServer(new NMIKitchenwareCookMessage(cuisine, blockPos));
    }

    public static void sendIzakayaMenuSyncMessage(IzakayaMenu menu) {
        ClientPacketDistributor.sendToServer(new NMIIzakayaMenuSyncMessage(menu));
    }

    public static void sendOpenDishServingMessage() {
        ClientPacketDistributor.sendToServer(new OpenDishServingMessage());
    }

    public static void sendStorePurchaseMessage(Cart cart,Identifier store) {
        ClientPacketDistributor.sendToServer(new StorePurchaseMessage(cart,store));
    }

    public static void sendRecordRecipeMessage(Identifier recipeKey) {
        ClientPacketDistributor.sendToServer(new RecordRecipeMessage(recipeKey));
    }

    public static void sendToggleCanteenOpen(BlockPos controllerPos) {
        ClientPacketDistributor.sendToServer(new ToggleCanteenOpenMessage(controllerPos));
    }
}