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

public record RequestCupboardInsertItemFromPlayerHandMessage() implements CustomPacketPayload {

    public static final Type<RequestCupboardInsertItemFromPlayerHandMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("request_cupboard_insert_item_from_player_hand"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestCupboardInsertItemFromPlayerHandMessage> STREAM_CODEC = StreamCodec.unit(new RequestCupboardInsertItemFromPlayerHandMessage());

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
