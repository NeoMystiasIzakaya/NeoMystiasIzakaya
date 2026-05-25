package icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record NMIBalanceTransactionEntry(Identifier reason,Identifier unit, long amount,Identifier from,Identifier to) {
    public static final Codec<NMIBalanceTransactionEntry> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("reason").forGetter(NMIBalanceTransactionEntry::reason),
                    Identifier.CODEC.fieldOf("unit").forGetter(NMIBalanceTransactionEntry::unit),
                    Codec.LONG.fieldOf("amount").forGetter(NMIBalanceTransactionEntry::amount),
                    Identifier.CODEC.fieldOf("from").forGetter(NMIBalanceTransactionEntry::from),
                    Identifier.CODEC.fieldOf("to").forGetter(NMIBalanceTransactionEntry::to)
            ).apply(instance, NMIBalanceTransactionEntry::new)
    );

    public static final StreamCodec<ByteBuf, NMIBalanceTransactionEntry> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, NMIBalanceTransactionEntry::reason,
            Identifier.STREAM_CODEC, NMIBalanceTransactionEntry::unit,
            ByteBufCodecs.LONG, NMIBalanceTransactionEntry::amount,
            Identifier.STREAM_CODEC, NMIBalanceTransactionEntry::from,
            Identifier.STREAM_CODEC, NMIBalanceTransactionEntry::to,
            NMIBalanceTransactionEntry::new
    );
}
