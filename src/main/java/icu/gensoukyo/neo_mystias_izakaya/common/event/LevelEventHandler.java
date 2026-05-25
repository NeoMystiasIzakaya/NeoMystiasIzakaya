package icu.gensoukyo.neo_mystias_izakaya.common.event;


import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class LevelEventHandler {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPayloadSender.sendIzakayaMenuSyncMessage(serverPlayer);
            ServerPayloadSender.sendTransactionSyncMessage(serverPlayer);
        }
    }
}
