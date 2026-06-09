/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record StorePurchaseMessage(Cart cart, Identifier store) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<StorePurchaseMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("store_purchase"));

    public static final StreamCodec<RegistryFriendlyByteBuf, StorePurchaseMessage> STREAM_CODEC = StreamCodec.composite(
            Cart.STREAM_CODEC, StorePurchaseMessage::cart,
            Identifier.STREAM_CODEC, StorePurchaseMessage::store,
            StorePurchaseMessage::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
