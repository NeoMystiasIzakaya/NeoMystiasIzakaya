package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadHandler;
import icu.gensoukyo.neo_mystias_izakaya.common.network.TagItemListMapSyncMessage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class NMINetwork {
    @SubscribeEvent // on the mod event bus
    public static void register(RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                TagItemListMapSyncMessage.TYPE,
                TagItemListMapSyncMessage.STREAM_CODEC,
                ClientPayloadHandler::handleTagItemListMapSyncMessage
        );
    }
}
