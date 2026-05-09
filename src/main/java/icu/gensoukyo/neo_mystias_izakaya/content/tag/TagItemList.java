/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;

public record TagItemList(List<Identifier> items) {

    public static final TagItemList EMPTY = new TagItemList(List.of());

    public static final Codec<TagItemList> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("items").forGetter(TagItemList::items)
            ).apply(instance, TagItemList::new)
    );

    public static final StreamCodec<ByteBuf, TagItemList> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), TagItemList::items,
            TagItemList::new
    );

    @Override
    public String toString() {
        return "TagItemList{" +
                "items=" + items +
                '}';
    }
}
