/*
 * Copyright 2026 Kaguya154
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.kaguya.client.event;

import icu.gensoukyo.kaguya.Kaguya;
import icu.gensoukyo.kaguya.client.pipeline.KaguyaPipelines;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

@EventBusSubscriber(modid = Kaguya.MODID)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onRegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event){
        event.registerPipeline(KaguyaPipelines.POSITION_COLOR);
        event.registerPipeline(KaguyaPipelines.POSITION_TEX);
        event.registerPipeline(KaguyaPipelines.POSITION_TEX_COLOR);
    }
}
