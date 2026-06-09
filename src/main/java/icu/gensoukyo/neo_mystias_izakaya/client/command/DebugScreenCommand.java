/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.command;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.StoreScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class DebugScreenCommand {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal(NeoMystiasIzakaya.MODID)
                .then(Commands.literal("debug")
                        .then(Commands.literal("screen")
                                .then(Commands.literal("store").executes(
                                                context -> {
                                                    Minecraft.getInstance().setScreen(new StoreScreen());
                                                    return 0;
                                                }
                                        )
                                ))));
    }
}
