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

public record RequestCupboardInfoMessage() implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf, RequestCupboardInfoMessage> STREAM_CODEC = StreamCodec.unit(new RequestCupboardInfoMessage());

    public static final CustomPacketPayload.Type<RequestCupboardInfoMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("request_cupboard_info"));

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
