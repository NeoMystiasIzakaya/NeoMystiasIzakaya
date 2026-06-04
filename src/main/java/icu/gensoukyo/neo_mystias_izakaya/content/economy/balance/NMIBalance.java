/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.balance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class NMIBalance implements ResourceHandler<NMIBalanceEntry> {

    private final List<NMIBalanceEntry> entries;
    private final ArrayList<EntrySnapshotJournal> snapshotJournals;

    public NMIBalance(List<NMIBalanceEntry> entries) {
        this.entries = new ArrayList<>();
        this.entries.addAll(entries);
        this.entries.add(NMIBalanceEntry.EMPTY);
        this.snapshotJournals = new ArrayList<>();
        for (int i = 0; i < this.entries.size(); i++) {
            this.snapshotJournals.add(new EntrySnapshotJournal(i));
        }
    }

    public static final MapCodec<NMIBalance> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NMIBalanceEntry.CODEC.listOf().fieldOf("entries").forGetter(NMIBalance::getEntries)
            ).apply(instance, NMIBalance::new)
    );

    public static final Codec<NMIBalance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    NMIBalanceEntry.CODEC.listOf().fieldOf("entries").forGetter(NMIBalance::getEntries)
            ).apply(instance, NMIBalance::new)
    );

    public static final StreamCodec<ByteBuf, NMIBalance> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, NMIBalanceEntry>list().apply(NMIBalanceEntry.STREAM_CODEC), NMIBalance::getEntries,
            NMIBalance::new
    );

    public static final NMIBalance EMPTY = new NMIBalance(List.of());

    public NMIBalance copy() {
        return new NMIBalance(new ArrayList<>(this.entries));
    }

    @Override
    public int size() {
        return getEntries().size(); // Always have one extra slot for new resources
    }

    @Override
    public NMIBalanceEntry getResource(int index) {
        Objects.checkIndex(index, this.size());
        return getEntries().get(index);
    }

    @Override
    public long getAmountAsLong(int index) {
        Objects.checkIndex(index, this.size());
        return getEntries().get(index).getCount();
    }

    @Override
    public long getCapacityAsLong(int index, NMIBalanceEntry resource) {
        if (getResource(index).isEmpty()) {
            return Long.MAX_VALUE;
        }
        return getEntries().get(index).getItem() == resource.getItem() ? Long.MAX_VALUE : 0;
    }

    @Override
    public boolean isValid(int index, NMIBalanceEntry resource) {
        if (getResource(index).isEmpty()) {
            return true;
        }
        return getResource(index).getItem() == resource.getItem();
    }

    @Override
    public int insert(int index, NMIBalanceEntry resource, int amount, TransactionContext transaction) {
        Objects.checkIndex(index, this.size());
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        if (resource.isEmpty()) {
            return 0;
        }

        if (isValid(index, resource)) {
            if (getResource(index).isEmpty()) {
                snapshotJournals.get(index).updateSnapshots(transaction);
                getEntries().set(index, resource.copyWithCount(amount));
                return amount;
            }
            if (amount > 0) {
                snapshotJournals.get(index).updateSnapshots(transaction);
            }
            return (int) getResource(index).changeCount(amount);
        }
        return 0;
    }

    @Override
    public int extract(int index, NMIBalanceEntry resource, int amount, TransactionContext transaction) {
        Objects.checkIndex(index, this.size());
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);

        if (resource.isEmpty()) {
            return 0;
        }

        if (isValid(index, resource)) {
            long extracted = Math.min(amount, getResource(index).getCount());
            if (extracted > 0) {
                snapshotJournals.get(index).updateSnapshots(transaction);
            }
            return (int) -getResource(index).changeCount(-extracted);
        }
        return 0;
    }

    public class EntrySnapshotJournal extends SnapshotJournal<NMIBalanceEntry> {

        private final int index;

        private EntrySnapshotJournal(int index) {
            this.index = index;
        }

        @Override
        protected NMIBalanceEntry createSnapshot() {
            return getResource(index).copy();
        }

        @Override
        protected void revertToSnapshot(NMIBalanceEntry snapshot) {
            getEntries().set(index, snapshot);
        }
    }
}
