package icu.gensoukyo.neo_mystias_izakaya.common.event;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagListReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class ReloadEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();


    @SubscribeEvent
    public static void onAddServerReloadListenersEvent(AddServerReloadListenersEvent event){
        ItemTagListReloadListener tagItemListReloadListener = new ItemTagListReloadListener(event.getRegistryAccess());
        event.addListener(NeoMystiasIzakaya.id("item_tag"),tagItemListReloadListener);
    }
}
