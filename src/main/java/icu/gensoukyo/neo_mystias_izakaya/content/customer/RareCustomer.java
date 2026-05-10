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

public record RareCustomer(CustomerBudget budget,
                           List<Identifier> locations,
                           List<Identifier> likes,
                           List<Identifier> dislikes,
                           List<Identifier> beverage,
                           List<Identifier> tagRequests,
                           List<Identifier> spellCards) implements Customer {

    public static final Codec<RareCustomer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CustomerBudget.CODEC.fieldOf("budget").forGetter(RareCustomer::budget),
                    Identifier.CODEC.listOf().fieldOf("locations").forGetter(RareCustomer::locations),
                    Identifier.CODEC.listOf().fieldOf("likes").forGetter(RareCustomer::likes),
                    Identifier.CODEC.listOf().fieldOf("dislikes").forGetter(RareCustomer::dislikes),
                    Identifier.CODEC.listOf().fieldOf("beverage").forGetter(RareCustomer::beverage),
                    Identifier.CODEC.listOf().fieldOf("tagRequests").forGetter(RareCustomer::tagRequests),
                    Identifier.CODEC.listOf().fieldOf("spellCards").forGetter(RareCustomer::spellCards)
            ).apply(instance, RareCustomer::new)
    );

    public static final StreamCodec<ByteBuf, RareCustomer> STREAM_CODEC = StreamCodec.composite(
            CustomerBudget.STREAM_CODEC, RareCustomer::budget,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), RareCustomer::locations,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), RareCustomer::likes,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), RareCustomer::dislikes,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), RareCustomer::beverage,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), RareCustomer::tagRequests,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), RareCustomer::spellCards,
            RareCustomer::new
    );

    public static final RareCustomer EMPTY = new RareCustomer(new CustomerBudget(0, 0), List.of(), List.of(), List.of(), List.of(), List.of(), List.of());

    public CommonCustomer asCommonCustomer(){
        return new CommonCustomer(budget, locations, likes, dislikes, beverage);
    }
}
