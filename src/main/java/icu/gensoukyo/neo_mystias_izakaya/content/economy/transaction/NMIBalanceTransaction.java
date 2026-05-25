/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.transaction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public class NMIBalanceTransaction {

    private static final int MAX_ENTRIES = 100; // Arbitrary limit to prevent abuse

    @Getter
    private final List<NMIBalanceTransactionEntry> entries;

    public NMIBalanceTransaction(List<NMIBalanceTransactionEntry> entries) {
        this.entries = entries;
    }

    public NMIBalanceTransaction addEntries(NMIBalanceTransactionEntry... add){
        List<NMIBalanceTransactionEntry> arrayList = new ArrayList<>(entries);
        int overflow = arrayList.size() + add.length - MAX_ENTRIES;
        for (int i = 0; i < overflow; i++) {
            arrayList.removeFirst(); // Remove the oldest entries to make room for the new ones
        }
        arrayList.addAll(List.of(add));
        return new NMIBalanceTransaction(arrayList);
    }

    public NMIBalanceTransaction addEntry(NMIBalanceTransactionEntry add){
        List<NMIBalanceTransactionEntry> arrayList = new ArrayList<>(entries);
        if (arrayList.size() < MAX_ENTRIES) {
            arrayList.add(add);
        }else {
            arrayList.removeFirst(); // Remove the oldest entry to make room for the new one
            arrayList.add(add);
        }
        return new NMIBalanceTransaction(arrayList);
    }


    public static final Codec<NMIBalanceTransaction> CODEC = Codec.list(NMIBalanceTransactionEntry.CODEC).xmap(NMIBalanceTransaction::new, NMIBalanceTransaction::getEntries);

    public static final MapCodec<NMIBalanceTransaction> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NMIBalanceTransactionEntry.CODEC.listOf().fieldOf("entries").forGetter(NMIBalanceTransaction::getEntries)
            ).apply(instance, NMIBalanceTransaction::new)
    );

    public static final StreamCodec<ByteBuf, NMIBalanceTransaction> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, NMIBalanceTransactionEntry>list().apply(NMIBalanceTransactionEntry.STREAM_CODEC), NMIBalanceTransaction::getEntries,
            NMIBalanceTransaction::new
    );

    public static final NMIBalanceTransaction EMPTY = new NMIBalanceTransaction(List.of());
}
