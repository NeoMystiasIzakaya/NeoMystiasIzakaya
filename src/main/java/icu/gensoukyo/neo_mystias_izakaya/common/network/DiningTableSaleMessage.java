/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.consts.CustomerEvaluation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

/**
 * @param saleAmount  修正后的收入金额
 * @param evaluation  评价等级
 * @param textKey     客户端翻译键（稀客为 evaluation 键，普客为随机 chat 键）
 */
public record DiningTableSaleMessage(int saleAmount, CustomerEvaluation evaluation, String textKey) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DiningTableSaleMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("dining_table_sale"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiningTableSaleMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, DiningTableSaleMessage::saleAmount,
            CustomerEvaluation.STREAM_CODEC, DiningTableSaleMessage::evaluation,
            ByteBufCodecs.STRING_UTF8, DiningTableSaleMessage::textKey,
            DiningTableSaleMessage::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
