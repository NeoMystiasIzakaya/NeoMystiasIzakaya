/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/**
 * 居酒屋订单。
 * <ul>
 *   <li>普客 ({@code isRare=false})：{@code cuisine} + {@code beverage} 指定具体物品</li>
 *   <li>稀客 ({@code isRare=true})：{@code beverage} + {@code requestedTag} 标签偏好</li>
 * </ul>
 */
public record IzakayaOrder(Identifier cuisine, Identifier beverage, Identifier requestedTag, Identifier rareCustomer, boolean isRare) {

    public static final Identifier EMPTY_ORDER = NeoMystiasIzakaya.id("empty_order");
    public static final Identifier EMPTY_RARE_CUSTOMER = NeoMystiasIzakaya.id("empty_rare_customer");

    public static final IzakayaOrder EMPTY = new IzakayaOrder(EMPTY_ORDER, EMPTY_ORDER, EMPTY_ORDER, EMPTY_RARE_CUSTOMER, false);

    public static final Codec<IzakayaOrder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("cuisine").forGetter(IzakayaOrder::cuisine),
                    Identifier.CODEC.fieldOf("beverage").forGetter(IzakayaOrder::beverage),
                    Identifier.CODEC.fieldOf("requestedTag").forGetter(IzakayaOrder::requestedTag),
                    Identifier.CODEC.fieldOf("rareCustomer").forGetter(IzakayaOrder::rareCustomer),
                    Codec.BOOL.optionalFieldOf("isRare", false).forGetter(IzakayaOrder::isRare)
            ).apply(instance, IzakayaOrder::new)
    );

    public static final StreamCodec<ByteBuf, IzakayaOrder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, IzakayaOrder::cuisine,
            Identifier.STREAM_CODEC, IzakayaOrder::beverage,
            Identifier.STREAM_CODEC, IzakayaOrder::requestedTag,
            Identifier.STREAM_CODEC, IzakayaOrder::rareCustomer,
            net.minecraft.network.codec.ByteBufCodecs.BOOL, IzakayaOrder::isRare,
            IzakayaOrder::new
    );
}
