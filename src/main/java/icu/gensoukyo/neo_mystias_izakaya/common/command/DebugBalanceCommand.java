/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonBalanceUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class DebugBalanceCommand {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal(NeoMystiasIzakaya.MODID)
                .then(Commands.literal("debug")
                        .then(Commands.literal("balance")
                                .then(Commands.literal("set").then(Commands.argument("count", IntegerArgumentType.integer()).executes(
                                        context -> {
                                            int count = IntegerArgumentType.getInteger(context, "count");
                                            ServerPlayer player = context.getSource().getPlayer();

                                            NMICommonBalanceUtil.setEn(player,count,false);

                                            return 0;
                                        }
                                ))))));
    }
}
