package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.common.network.NMIKitchenwareCookMessage;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class ClientPayloadSender {
    public static void sendKitchenwareCookMessage(NMIKitchenwareCookMessage message) {
        ClientPacketDistributor.sendToServer(message);
    }
}