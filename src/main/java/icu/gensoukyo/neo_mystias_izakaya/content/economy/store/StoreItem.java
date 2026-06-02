/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;

public record StoreItem(Identifier id, int price, ItemStackTemplate stack) {
    public static final Codec<StoreItem> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Identifier.CODEC.fieldOf("id").forGetter(StoreItem::id),
            Codec.INT.fieldOf("price").forGetter(StoreItem::price),
            ItemStackTemplate.CODEC.fieldOf("stack").forGetter(StoreItem::stack)
    ).apply(ins, StoreItem::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, StoreItem> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            StoreItem::id,
            ByteBufCodecs.VAR_INT,
            StoreItem::price,
            ItemStackTemplate.STREAM_CODEC,
            StoreItem::stack,
            StoreItem::new
    );
}
