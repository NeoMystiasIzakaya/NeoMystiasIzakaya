/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.NMIStoreMap;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record NMIStoreMapSyncMessage(NMIStoreMap map) implements CustomPacketPayload {

    public static final Type<NMIStoreMapSyncMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("store_map_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIStoreMapSyncMessage> STREAM_CODEC = StreamCodec.composite(
            NMIStoreMap.STREAM_CODEC,
            NMIStoreMapSyncMessage::map,
            NMIStoreMapSyncMessage::new
    );

    @Override
    public Type<NMIStoreMapSyncMessage> type() {
        return TYPE;
    }
}
