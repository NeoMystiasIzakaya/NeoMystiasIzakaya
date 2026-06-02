/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.event;

import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;

@EventBusSubscriber
public class ServerEventHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    @Getter
    private static MinecraftServer server;

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event){
        server = event.getServer();

        LOGGER.info("Ciallo～(∠・ω< )⌒★ ");
    }
}
