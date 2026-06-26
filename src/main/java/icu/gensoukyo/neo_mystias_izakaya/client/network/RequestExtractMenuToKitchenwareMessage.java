/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record RequestExtractMenuToKitchenwareMessage(int menuId, BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<RequestExtractMenuToKitchenwareMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("request_extract_menu_to_kitchenware"));

    public static final StreamCodec<ByteBuf, RequestExtractMenuToKitchenwareMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            RequestExtractMenuToKitchenwareMessage::menuId,
            BlockPos.STREAM_CODEC,
            RequestExtractMenuToKitchenwareMessage::blockPos,
            RequestExtractMenuToKitchenwareMessage::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
