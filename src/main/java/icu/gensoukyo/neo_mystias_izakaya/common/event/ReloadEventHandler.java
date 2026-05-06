package icu.gensoukyo.neo_mystias_izakaya.common.event;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID,value = Dist.DEDICATED_SERVER)
public class ReloadEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onAddServerReloadListenersEvent(AddServerReloadListenersEvent event){
        event.addListener(NeoMystiasIzakaya.id("item_tag"),new TagItemListReloadListener(event.getRegistryAccess()));
    }
}
