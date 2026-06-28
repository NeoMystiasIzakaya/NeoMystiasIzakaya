/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record RequestCupboardBeveragesInfoMessage() implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf, RequestCupboardBeveragesInfoMessage> STREAM_CODEC = StreamCodec.unit(new RequestCupboardBeveragesInfoMessage());

    public static final Type<RequestCupboardBeveragesInfoMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("request_cupboard_beverages_info"));

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
