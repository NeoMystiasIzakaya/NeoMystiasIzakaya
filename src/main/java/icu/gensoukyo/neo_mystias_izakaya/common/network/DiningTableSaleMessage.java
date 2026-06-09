/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record DiningTableSaleMessage(int saleAmount) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DiningTableSaleMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("dining_table_sale"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiningTableSaleMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, DiningTableSaleMessage::saleAmount,
            DiningTableSaleMessage::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
