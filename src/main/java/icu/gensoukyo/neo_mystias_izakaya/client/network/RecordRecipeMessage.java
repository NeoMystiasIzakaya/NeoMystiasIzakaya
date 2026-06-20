/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record RecordRecipeMessage(Identifier recipeKey) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RecordRecipeMessage> TYPE =
            new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("record_recipe"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RecordRecipeMessage> STREAM_CODEC =
            StreamCodec.composite(
                    Identifier.STREAM_CODEC, RecordRecipeMessage::recipeKey,
                    RecordRecipeMessage::new
            );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
