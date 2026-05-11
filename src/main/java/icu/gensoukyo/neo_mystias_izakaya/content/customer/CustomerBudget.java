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

public record CustomerBudget(int min, int max) {

    public static final Codec<CustomerBudget> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("min").forGetter(CustomerBudget::min),
                    Codec.INT.fieldOf("max").forGetter(CustomerBudget::max)
            ).apply(instance, CustomerBudget::new)
    );

    public static final StreamCodec<ByteBuf, CustomerBudget> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CustomerBudget::min,
            ByteBufCodecs.INT, CustomerBudget::max,
            CustomerBudget::new
    );

    public static final CustomerBudget MAX = new CustomerBudget(Integer.MAX_VALUE, Integer.MAX_VALUE);
}
