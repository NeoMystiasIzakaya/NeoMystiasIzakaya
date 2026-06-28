/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.transfer.item.ItemResource;

public record IncubatorItemResourceConsumedMessage(ItemResource itemResource)implements CustomPacketPayload  {

    public static final Type<IncubatorItemResourceConsumedMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("incubator_item_resource_consumed"));

    public static final StreamCodec<RegistryFriendlyByteBuf, IncubatorItemResourceConsumedMessage> STREAM_CODEC = StreamCodec.composite(
            ItemResource.STREAM_CODEC,
            IncubatorItemResourceConsumedMessage::itemResource,
            IncubatorItemResourceConsumedMessage::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
