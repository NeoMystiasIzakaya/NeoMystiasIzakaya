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

public record CommonCustomerHolder(Identifier key, CommonCustomer customer) implements NMICustomerHolder {

    public static final Codec<CommonCustomerHolder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter(CommonCustomerHolder::key),
                    CommonCustomer.CODEC.fieldOf("customer").forGetter(CommonCustomerHolder::customer)
            ).apply(instance, CommonCustomerHolder::new)
    );

    public static final StreamCodec<ByteBuf,CommonCustomerHolder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, CommonCustomerHolder::key,
            CommonCustomer.STREAM_CODEC, CommonCustomerHolder::customer,
            CommonCustomerHolder::new
    );

    public static final CommonCustomerHolder EMPTY = new CommonCustomerHolder(NeoMystiasIzakaya.id("empty"), CommonCustomer.EMPTY);
}
