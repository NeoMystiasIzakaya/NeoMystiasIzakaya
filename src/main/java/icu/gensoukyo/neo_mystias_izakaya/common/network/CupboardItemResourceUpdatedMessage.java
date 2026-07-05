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
import net.neoforged.neoforge.transfer.item.ItemResource;

public record CupboardItemResourceUpdatedMessage(ItemResourceWithCount itemResource)implements CustomPacketPayload  {

    public static final Type<CupboardItemResourceUpdatedMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("cupboard_item_resource_updated"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CupboardItemResourceUpdatedMessage> STREAM_CODEC = StreamCodec.composite(
            ItemResourceWithCount.STREAM_CODEC,
            CupboardItemResourceUpdatedMessage::itemResource,
            CupboardItemResourceUpdatedMessage::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
