/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction.NMIBalanceTransaction;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record NMIBalanceTransactionSyncFullMessage(NMIBalanceTransaction transaction) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<NMIBalanceTransactionSyncFullMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("balance_transaction_sync"));

    public static final StreamCodec<ByteBuf, NMIBalanceTransactionSyncFullMessage> STREAM_CODEC = StreamCodec.composite(
            NMIBalanceTransaction.STREAM_CODEC, NMIBalanceTransactionSyncFullMessage::transaction,
            NMIBalanceTransactionSyncFullMessage::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
