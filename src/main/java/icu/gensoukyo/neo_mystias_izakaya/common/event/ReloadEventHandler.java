/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.event;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerReloadListener;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.NMIEconomyReloadListener;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeReloadListener;
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
        event.addListener(NeoMystiasIzakaya.id("item_tag"), new ItemTagListReloadListener(event.getRegistryAccess()));
        event.addListener(NeoMystiasIzakaya.id("recipe"),new NMIRecipeReloadListener(event.getRegistryAccess()));
        event.addListener(NeoMystiasIzakaya.id("economy"),new NMIEconomyReloadListener(event.getRegistryAccess()));
        event.addListener(NeoMystiasIzakaya.id("customer"),new CustomerReloadListener(event.getRegistryAccess()));
    }
}
