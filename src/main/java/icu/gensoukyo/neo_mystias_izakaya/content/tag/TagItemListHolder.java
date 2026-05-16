/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record TagItemListHolder(Identifier key, TagItemList tag) {


    public static final Codec<TagItemListHolder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(TagItemListHolder::key),
                    TagItemList.CODEC.fieldOf("tag").forGetter(TagItemListHolder::tag)
            ).apply(instance, TagItemListHolder::new)
    );

    public static final StreamCodec<ByteBuf, TagItemListHolder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, TagItemListHolder::key,
            TagItemList.STREAM_CODEC, TagItemListHolder::tag,
            TagItemListHolder::new
    );

    @Override
    public String toString() {
        return "TagItemListHolder{" +
                "item=" + key +
                ", tag=" + tag +
                '}';
    }
}
