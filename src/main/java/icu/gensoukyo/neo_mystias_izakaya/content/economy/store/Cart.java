/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record Cart(List<CartItem> items) {
    public static final Codec<Cart> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            CartItem.CODEC.listOf().fieldOf("items").forGetter(Cart::items)
    ).apply(ins, Cart::new));

    public static final StreamCodec<ByteBuf, Cart> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, CartItem>list().apply(CartItem.STREAM_CODEC),
            Cart::items,
            Cart::new
    );
}
