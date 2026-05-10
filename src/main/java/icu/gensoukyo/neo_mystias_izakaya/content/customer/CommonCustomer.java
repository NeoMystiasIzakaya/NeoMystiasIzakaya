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

public record CommonCustomer(CustomerBudget budget,
                             List<Identifier> locations,
                             List<Identifier> likes,
                             List<Identifier> dislikes,
                             List<Identifier> beverage) implements NMICustomer {


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
                    CustomerBudget.CODEC.fieldOf("budget").forGetter(CommonCustomer::budget),
                    Identifier.CODEC.listOf().fieldOf("locations").forGetter(CommonCustomer::locations),
                    Identifier.CODEC.listOf().fieldOf("likes").forGetter(CommonCustomer::likes),
                    Identifier.CODEC.listOf().fieldOf("dislikes").forGetter(CommonCustomer::dislikes),
                    Identifier.CODEC.listOf().fieldOf("beverage").forGetter(CommonCustomer::beverage)
            ).apply(instance, CommonCustomer::new)
    );

    public static final StreamCodec<ByteBuf, CommonCustomer> STREAM_CODEC = StreamCodec.composite(
            CustomerBudget.STREAM_CODEC, CommonCustomer::budget,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::locations,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::likes,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::dislikes,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), CommonCustomer::beverage,
            CommonCustomer::new
    );

    public static final CommonCustomer EMPTY = new CommonCustomer(new CustomerBudget(0, 0), List.of(), List.of(), List.of(), List.of());
}
