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
import net.minecraft.resources.Identifier;

public record CartItem(Identifier id, int count) {

    public static final StreamCodec<ByteBuf, CartItem> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            CartItem::id,
            ByteBufCodecs.VAR_INT,
            CartItem::count,
            CartItem::new
    );
    public static final Codec<CartItem> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Identifier.CODEC.fieldOf("id").forGetter(CartItem::id),
            Codec.INT.fieldOf("count").forGetter(CartItem::count)
    ).apply(ins, CartItem::new));
}
