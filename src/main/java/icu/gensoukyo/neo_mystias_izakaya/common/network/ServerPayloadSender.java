package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import net.neoforged.neoforge.network.PacketDistributor;

public class ServerPayloadSender {

    public static void sendTagItemListMapSyncMessage(TagItemListMap map){
        PacketDistributor.sendToAllPlayers(new TagItemListMapSyncMessage(map));
    }
}
