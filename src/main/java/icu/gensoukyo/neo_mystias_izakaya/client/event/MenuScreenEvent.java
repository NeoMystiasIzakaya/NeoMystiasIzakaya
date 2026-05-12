package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class MenuScreenEvent {
    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(NMIMenus.KITCHENWARE_MENU.get(), KitchenwareScreen::new);
    }
}
