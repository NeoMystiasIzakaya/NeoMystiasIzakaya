package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
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
}
