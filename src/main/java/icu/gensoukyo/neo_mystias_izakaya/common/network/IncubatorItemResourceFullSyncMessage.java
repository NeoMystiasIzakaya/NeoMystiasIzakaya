/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public record IncubatorItemResourceFullSyncMessage(List<ItemResourceWithCount> itemResourceList)implements CustomPacketPayload  {

    public static final StreamCodec<RegistryFriendlyByteBuf, IncubatorItemResourceFullSyncMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, ItemResourceWithCount.STREAM_CODEC),
            IncubatorItemResourceFullSyncMessage::itemResourceList,
            IncubatorItemResourceFullSyncMessage::new
    );

    public static final Type<IncubatorItemResourceFullSyncMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("incubator_item_resource_full_sync"));


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
