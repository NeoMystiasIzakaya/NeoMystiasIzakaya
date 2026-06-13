/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.DishServingScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.model.MystiasHatModel;
import icu.gensoukyo.neo_mystias_izakaya.client.overlay.CanteenOverlay;
import icu.gensoukyo.neo_mystias_izakaya.client.render.DiningTableRenderer;
import icu.gensoukyo.neo_mystias_izakaya.client.render.KitchenwareRenderer;
import icu.gensoukyo.neo_mystias_izakaya.registry.ModelLayersRegistry;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.CROSSHAIR;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientRegister {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(NMIMenus.KITCHENWARE_MENU.get(), KitchenwareScreen::new);
        event.register(NMIMenus.DISH_SERVING_MENU.get(), DishServingScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NMIBlockEntities.BOILING_POT.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.GRILL.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.FRYING_PAN.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.STEAMER.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.CUTTING_BOARD.get(), KitchenwareRenderer::new);
        event.registerBlockEntityRenderer(NMIBlockEntities.DINING_TABLE.get(), DiningTableRenderer::new);
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(CROSSHAIR, NeoMystiasIzakaya.id("canteen_overlay"), new CanteenOverlay());
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayersRegistry.MYSTIAS_HAT,() -> LayerDefinition.create(MystiasHatModel.setup(),64,64));
    }
}
