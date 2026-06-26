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

public record CupBoardItemResourceFullSyncMessage(List<ItemResourceWithCount> itemResourceList)implements CustomPacketPayload  {

    public static final StreamCodec<RegistryFriendlyByteBuf, CupBoardItemResourceFullSyncMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, ItemResourceWithCount.STREAM_CODEC),
            CupBoardItemResourceFullSyncMessage::itemResourceList,
            CupBoardItemResourceFullSyncMessage::new
    );

    public static final CustomPacketPayload.Type<CupBoardItemResourceFullSyncMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("cupboard_item_resource_full_sync"));


    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
