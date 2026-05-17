package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.render.KitchenwareRenderer;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class MenuScreenEvent {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(NMIMenus.KITCHENWARE_MENU.get(), KitchenwareScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NMIBlockEntities.BOILING_POT.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.GRILL.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.FRYING_PAN.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.STEAMER.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.CUTTING_BOARD.get(), KitchenwareRenderer::new);
    }
}
