package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction.NMIBalanceTransactionEntry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record NMIBalanceTransactionUpdateMessage(NMIBalanceTransactionEntry transactionEntry) implements CustomPacketPayload {

    public static final Type<NMIBalanceTransactionUpdateMessage> TYPE = new Type<>(NeoMystiasIzakaya.id("balance_transaction_update"));
  
    public static final StreamCodec<ByteBuf, NMIBalanceTransactionUpdateMessage> STREAM_CODEC = StreamCodec.composite(
            NMIBalanceTransactionEntry.STREAM_CODEC, NMIBalanceTransactionUpdateMessage::transactionEntry,
            NMIBalanceTransactionUpdateMessage::new
    );
  
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
