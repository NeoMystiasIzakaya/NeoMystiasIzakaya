/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record NMICustomerMapSyncMessage(CustomerMap map) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<NMICustomerMapSyncMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("recipe_map_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NMICustomerMapSyncMessage> STREAM_CODEC = StreamCodec.composite(
            CustomerMap.STREAM_CODEC, NMICustomerMapSyncMessage::map,
            NMICustomerMapSyncMessage::new
    );

    @Override
    public Type<NMICustomerMapSyncMessage> type() {
        return TYPE;
    }
}
