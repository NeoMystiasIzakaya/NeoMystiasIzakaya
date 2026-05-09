/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record NMIRecipeMapSyncMessage(NMIRecipeMap map) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<NMIRecipeMapSyncMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("recipe_map_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIRecipeMapSyncMessage> STREAM_CODEC = StreamCodec.composite(
            NMIRecipeMap.STREAM_CODEC, NMIRecipeMapSyncMessage::map,
            NMIRecipeMapSyncMessage::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
