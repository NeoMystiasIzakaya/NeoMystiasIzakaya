/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

public record StoreItem(Identifier id, int price,double discount, ItemStackTemplate stack) {
    public static final Codec<StoreItem> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Identifier.CODEC.fieldOf("id").forGetter(StoreItem::id),
            Codec.INT.fieldOf("price").forGetter(StoreItem::price),
            Codec.DOUBLE.fieldOf("discount").forGetter(StoreItem::discount),
            ItemStackTemplate.CODEC.fieldOf("stack").forGetter(StoreItem::stack)
    ).apply(ins, StoreItem::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StoreItem> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            StoreItem::id,
            ByteBufCodecs.VAR_INT,
            StoreItem::price,
            ByteBufCodecs.DOUBLE,
            StoreItem::discount,
            ItemStackTemplate.STREAM_CODEC,
            StoreItem::stack,
            StoreItem::new
    );

    public CartItem asCartItem(int count) {
        return new CartItem(id, count);
    }
}
