/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;

public record CommonCustomer(List<Identifier> locations,
                             List<Identifier> likes,
                             List<Identifier> dislikes,
                             List<Identifier> beverage,
                             List<Identifier> chats) implements Customer {


    @Override
    public CustomerBudget budget() {
        return CustomerBudget.MAX;
    }

    @Override
    public List<Identifier> tagRequests() {
        return List.of();
    }

    @Override
    public List<Identifier> spellCards() {
        return List.of();
    }

    public static final Codec<CommonCustomer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("locations").forGetter(CommonCustomer::locations),
                    Identifier.CODEC.listOf().fieldOf("likes").forGetter(CommonCustomer::likes),
                    Identifier.CODEC.listOf().fieldOf("dislikes").forGetter(CommonCustomer::dislikes),
                    Identifier.CODEC.listOf().fieldOf("beverage").forGetter(CommonCustomer::beverage),
                    Identifier.CODEC.listOf().fieldOf("chats").forGetter(CommonCustomer::chats)
            ).apply(instance, CommonCustomer::new)
    );

    public static final StreamCodec<ByteBuf, CommonCustomer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::locations,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::likes,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::dislikes,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::beverage,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::chats,
            CommonCustomer::new
    );

    public static final CommonCustomer EMPTY = new CommonCustomer(List.of(), List.of(), List.of(), List.of(), List.of());
}
