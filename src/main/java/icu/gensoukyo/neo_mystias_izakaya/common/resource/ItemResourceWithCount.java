/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.resource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.transfer.item.ItemResource;

public record ItemResourceWithCount(ItemResource itemResource,long count) {
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemResourceWithCount> STREAM_CODEC = StreamCodec.composite(
            ItemResource.STREAM_CODEC,
            ItemResourceWithCount::itemResource,
            ByteBufCodecs.VAR_LONG,
            ItemResourceWithCount::count,
            ItemResourceWithCount::new
    );
    public static final Codec<ItemResourceWithCount> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            ItemResource.CODEC.fieldOf("itemResource").forGetter(ItemResourceWithCount::itemResource),
            Codec.LONG.fieldOf("count").forGetter(ItemResourceWithCount::count)
    ).apply(ins, ItemResourceWithCount::new));
}
