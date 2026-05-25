package icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class NMIBalanceTransaction {

    @Getter
    private final List<NMIBalanceTransactionEntry> entries;

    public NMIBalanceTransaction(List<NMIBalanceTransactionEntry> entries) {
        this.entries = entries;
    }

    public static final Codec<NMIBalanceTransaction> CODEC = Codec.list(NMIBalanceTransactionEntry.CODEC).xmap(NMIBalanceTransaction::new, NMIBalanceTransaction::getEntries);

    public static final StreamCodec<ByteBuf, NMIBalanceTransaction> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, NMIBalanceTransactionEntry>list().apply(NMIBalanceTransactionEntry.STREAM_CODEC), NMIBalanceTransaction::getEntries,
            NMIBalanceTransaction::new
    );
}
