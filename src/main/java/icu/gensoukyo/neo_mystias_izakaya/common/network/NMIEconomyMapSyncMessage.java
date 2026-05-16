/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.NMIEconomyMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record NMIEconomyMapSyncMessage(NMIEconomyMap map) implements CustomPacketPayload {

    public static final Type<NMIEconomyMapSyncMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("economy_map_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIEconomyMapSyncMessage> STREAM_CODEC = StreamCodec.composite(
            NMIEconomyMap.STREAM_CODEC, NMIEconomyMapSyncMessage::map,
            NMIEconomyMapSyncMessage::new
    );

    @Override
    public Type<NMIEconomyMapSyncMessage> type() {
        return TYPE;
    }
}
