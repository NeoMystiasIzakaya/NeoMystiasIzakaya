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

public record CupboardItemResourceConsumedMessage(ItemResource itemResource)implements CustomPacketPayload  {

    public static final Type<CupboardItemResourceConsumedMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("cupboard_item_resource_consumed"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CupboardItemResourceConsumedMessage> STREAM_CODEC = StreamCodec.composite(
            ItemResource.STREAM_CODEC,
            CupboardItemResourceConsumedMessage::itemResource,
            CupboardItemResourceConsumedMessage::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
