/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;
import java.util.Map;

@Getter
public class IzakayaOrderList {
    private final List<IzakayaOrderHolder> orders;
    private final Map<Short, IzakayaOrderHolder> orderMap;

    public IzakayaOrderList(List<IzakayaOrderHolder> orders) {
        this.orders = orders;
        this.orderMap = orders.stream().collect(java.util.stream.Collectors.toMap(IzakayaOrderHolder::getId, o -> o));
    }

    public static final IzakayaOrderList EMPTY = new IzakayaOrderList(List.of());

    public static final Codec<IzakayaOrderList> CODEC = Codec.list(IzakayaOrderHolder.CODEC).xmap(IzakayaOrderList::new, IzakayaOrderList::getOrders);

    public static final StreamCodec<ByteBuf, IzakayaOrderList> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, IzakayaOrderHolder>list().apply(IzakayaOrderHolder.STREAM_CODEC), IzakayaOrderList::getOrders,
            IzakayaOrderList::new
    );

    public IzakayaOrderList copy() {
        return new IzakayaOrderList(orders.stream().map(IzakayaOrderHolder::copy).toList());
    }
}
