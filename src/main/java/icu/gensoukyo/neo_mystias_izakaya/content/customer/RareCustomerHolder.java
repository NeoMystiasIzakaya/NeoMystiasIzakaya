/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record RareCustomerHolder(Identifier key, RareCustomer customer) implements CustomerHolder {

    public static final Codec<RareCustomerHolder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter(RareCustomerHolder::key),
                    RareCustomer.CODEC.fieldOf("customer").forGetter(RareCustomerHolder::customer)
            ).apply(instance, RareCustomerHolder::new)
    );

    public static final StreamCodec<ByteBuf,RareCustomerHolder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, RareCustomerHolder::key,
            RareCustomer.STREAM_CODEC, RareCustomerHolder::customer,
            RareCustomerHolder::new
    );

    public static final RareCustomerHolder EMPTY = new RareCustomerHolder(NeoMystiasIzakaya.id("empty"), RareCustomer.EMPTY);
}
