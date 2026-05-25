package icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalanceUnits;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record NMIBalanceTransactionEntry(Identifier reason, Identifier unit, long amount, String from, String to) {
    public static final Codec<NMIBalanceTransactionEntry> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("reason").forGetter(NMIBalanceTransactionEntry::reason),
                    Identifier.CODEC.fieldOf("unit").forGetter(NMIBalanceTransactionEntry::unit),
                    Codec.LONG.fieldOf("amount").forGetter(NMIBalanceTransactionEntry::amount),
                    Codec.STRING.fieldOf("from").forGetter(NMIBalanceTransactionEntry::from),
                    Codec.STRING.fieldOf("to").forGetter(NMIBalanceTransactionEntry::to)
            ).apply(instance, NMIBalanceTransactionEntry::new)
    );

    public static final StreamCodec<ByteBuf, NMIBalanceTransactionEntry> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, NMIBalanceTransactionEntry::reason,
            Identifier.STREAM_CODEC, NMIBalanceTransactionEntry::unit,
            ByteBufCodecs.LONG, NMIBalanceTransactionEntry::amount,
            ByteBufCodecs.STRING_UTF8, NMIBalanceTransactionEntry::from,
            ByteBufCodecs.STRING_UTF8, NMIBalanceTransactionEntry::to,
            NMIBalanceTransactionEntry::new
    );

    public static final NMIBalanceTransactionEntry EMPTY =
            new NMIBalanceTransactionEntry(NMIBalanceTransactionReasons.UNDEFINED,
                    NMIBalanceUnits.EMPTY, 0,
                    "undefined", "undefined");
}
