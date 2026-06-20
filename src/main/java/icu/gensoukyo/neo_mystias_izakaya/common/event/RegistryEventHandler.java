/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class RegistryEventHandler {

    @SubscribeEvent
    public static void onNewRegistryEvent(NewRegistryEvent event){
        NMIKitchenware.REGISTRY = event.create(new RegistryBuilder<>(NMIKitchenware.KITCHENWARE.getRegistryKey()));
    }
}
