/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record IncubatorItemResourceUpdatedMessage(ItemResourceWithCount itemResource)implements CustomPacketPayload  {

    public static final Type<IncubatorItemResourceUpdatedMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("incubator_item_resource_updated"));

    public static final StreamCodec<RegistryFriendlyByteBuf, IncubatorItemResourceUpdatedMessage> STREAM_CODEC = StreamCodec.composite(
            ItemResourceWithCount.STREAM_CODEC,
            IncubatorItemResourceUpdatedMessage::itemResource,
            IncubatorItemResourceUpdatedMessage::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
