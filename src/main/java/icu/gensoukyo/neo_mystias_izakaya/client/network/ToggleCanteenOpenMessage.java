/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record ToggleCanteenOpenMessage(BlockPos controllerPos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ToggleCanteenOpenMessage> TYPE =
            new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("toggle_canteen_open"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleCanteenOpenMessage> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ToggleCanteenOpenMessage::controllerPos,
                    ToggleCanteenOpenMessage::new
            );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
