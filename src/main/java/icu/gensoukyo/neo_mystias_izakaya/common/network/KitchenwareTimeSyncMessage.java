/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record KitchenwareTimeSyncMessage(BlockPos pos, int cookTime) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<KitchenwareTimeSyncMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("kitchenware_time_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, KitchenwareTimeSyncMessage> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, KitchenwareTimeSyncMessage::pos,
            ByteBufCodecs.VAR_INT, KitchenwareTimeSyncMessage::cookTime,
            KitchenwareTimeSyncMessage::new
    );
    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
