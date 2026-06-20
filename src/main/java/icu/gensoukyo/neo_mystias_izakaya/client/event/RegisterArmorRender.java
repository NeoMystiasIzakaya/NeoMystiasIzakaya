/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.render.NMIArmorRender;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID, value = Dist.CLIENT)
public class RegisterArmorRender {
    @SubscribeEvent
    public static void registerRender(RegisterClientExtensionsEvent event) {
        event.registerItem(new NMIArmorRender(), NMIMainItems.MYSTIAS_HAT.get());
    }
}
