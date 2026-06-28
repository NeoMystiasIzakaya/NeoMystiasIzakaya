/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.NonNull;

public record RequestExtractItemToPlayerHandMessage(ItemResource resource) implements CustomPacketPayload {

    public static final Type<RequestExtractItemToPlayerHandMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("request_extract_item_to_player_hand"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestExtractItemToPlayerHandMessage> STREAM_CODEC = StreamCodec.composite(
            ItemResource.STREAM_CODEC,
            RequestExtractItemToPlayerHandMessage::resource,
            RequestExtractItemToPlayerHandMessage::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
