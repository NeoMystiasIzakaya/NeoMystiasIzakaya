/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record IzakayaOrderUpdateMessage(short id, IzakayaOrder order) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<IzakayaOrderUpdateMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("izakaya_order_update"));

    public static final StreamCodec<RegistryFriendlyByteBuf, IzakayaOrderUpdateMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.SHORT, IzakayaOrderUpdateMessage::id,
            IzakayaOrder.STREAM_CODEC, IzakayaOrderUpdateMessage::order,
            IzakayaOrderUpdateMessage::new
    );

    @Override
    public CustomPacketPayload.Type<IzakayaOrderUpdateMessage> type() {
        return TYPE;
    }
}
