/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.client.network.NMIIzakayaMenuSyncMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.NMIKitchenwareCookMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.OpenDishServingMessage;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.IzakayaCookingUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleKitchenwareCookMessage(NMIKitchenwareCookMessage message, IPayloadContext context) {
        context.enqueueWork(() -> IzakayaCookingUtil.processCooking(context.player(),message.cuisine(),message.blockPos()));
    }

    public static void handleIzakayaMenuSyncMessage(NMIIzakayaMenuSyncMessage message, IPayloadContext context) {
        context.enqueueWork(()-> NMICommonIzakayaUtil.setMenu(context.player(), message.izakayaMenu()));
    }

    public static void handleOpenDishServingMessage(OpenDishServingMessage message, IPayloadContext context) {
        context.enqueueWork(()-> context.player().openMenu(new SimpleMenuProvider(
                (containerId, inventory, player) -> new DishServingMenu(containerId, inventory),
                Component.literal("Dish Serving")
        )));
    }
}
