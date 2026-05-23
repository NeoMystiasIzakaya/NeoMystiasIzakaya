/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class ClientPayloadSender {
    public static void sendKitchenwareCookMessage(NMIKitchenwareCookMessage message) {
        ClientPacketDistributor.sendToServer(message);
    }

    public static void sendIzakayaMenuSyncMessage(IzakayaMenu menu) {
        ClientPacketDistributor.sendToServer(new NMIIzakayaMenuSyncMessage(menu));
    }
}