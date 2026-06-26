/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.NonNull;

public record RequestExtractItemToKitchenwareMessage(ItemResource resource, BlockPos blockPos) implements CustomPacketPayload {

    public static final Type<RequestExtractItemToKitchenwareMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("request_extract_item_to_kitchenware"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestExtractItemToKitchenwareMessage> STREAM_CODEC = StreamCodec.composite(
            ItemResource.STREAM_CODEC,
            RequestExtractItemToKitchenwareMessage::resource,
            BlockPos.STREAM_CODEC,
            RequestExtractItemToKitchenwareMessage::blockPos,
            RequestExtractItemToKitchenwareMessage::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
