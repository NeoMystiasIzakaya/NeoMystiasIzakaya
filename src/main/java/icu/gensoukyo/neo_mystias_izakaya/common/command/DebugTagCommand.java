/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.command;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class DebugTagCommand {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal(NeoMystiasIzakaya.MODID)
                .then(Commands.literal("debug")
                        .then(Commands.literal("tag")
                                .then(Commands.literal("getItemList").then(Commands.argument("tag", IdentifierArgument.id()).executes(
                                        context -> {
                                            Identifier tag = IdentifierArgument.getId(context, "tag");
                                            TagItemListHolder holder = NMIDataAccessor.server().getTagItemListMap().get(tag);
                                            MutableComponent component = Component.literal( "items of " + tag + ":");
                                            holder.tag().items().forEach(item -> component.append("\n- " + item));
                                            component.append("\n total: " + holder.tag().items().size());
                                            context.getSource().sendSuccess(() -> component, false);
                                            return 0;
                                        }
                                )))
                                .then(Commands.literal("getTagList").then(Commands.argument("item", ItemArgument.item(event.getBuildContext())).executes(
                                        context -> {
                                            Item item = ItemArgument.getItem(context, "item").item().value();
                                            // todo 从component拿tag
                                            return 0;
                                        }
                                )))
                                .then(Commands.literal("getTagListRaw").then(Commands.argument("item", ItemArgument.item(event.getBuildContext())).executes(
                                                context -> {
                                                    Item item = ItemArgument.getItem(context, "item").item().value();
                                                    Identifier key = BuiltInRegistries.ITEM.getKey(item);
                                                    List<Identifier> identifiers = NMIDataAccessor.server().getTagItemListMap().getItemToPositiveTagMap().getOrDefault(key,new ArrayList<>());
                                                    MutableComponent component = Component.literal("positiveTags of " + key + ":");
                                                    identifiers.forEach(id -> component.append("\n- " + id));
                                                    component.append("\n total: " + identifiers.size());
                                                    context.getSource().sendSuccess(() -> component, false);
                                                    return 0;
                                                }
                                        ))
                                ))));
    }
}
