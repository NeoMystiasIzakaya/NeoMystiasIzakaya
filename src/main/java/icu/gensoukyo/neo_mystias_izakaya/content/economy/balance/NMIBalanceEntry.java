/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.balance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.transfer.resource.Resource;

@EqualsAndHashCode
@Getter
public class NMIBalanceEntry implements Resource {

    private final Identifier item;
    @Setter
    private long count;

    public long changeCount(long delta) {
        this.count += delta;
        return delta;
    }

    public static final MapCodec<NMIBalanceEntry> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(NMIBalanceEntry::getItem),
                    Codec.LONG.fieldOf("count").forGetter(NMIBalanceEntry::getCount)
            ).apply(instance, NMIBalanceEntry::new)
    );
    public static final Codec<NMIBalanceEntry> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(NMIBalanceEntry::getItem),
                    Codec.LONG.fieldOf("count").forGetter(NMIBalanceEntry::getCount)
            ).apply(instance, NMIBalanceEntry::new)
    );
    public static final StreamCodec<ByteBuf, NMIBalanceEntry> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, NMIBalanceEntry::getItem,
            ByteBufCodecs.LONG, NMIBalanceEntry::getCount,
            NMIBalanceEntry::new
    );

    public NMIBalanceEntry(Identifier item) {
        this.item = item;
        this.count = 1;
    }

    public NMIBalanceEntry(Identifier item, long count) {
        this.item = item;
        this.count = count;
    }

    public boolean is(Identifier item) {
        return this.item.equals(item);
    }

    @Override
    public boolean isEmpty() {
        return this.count <= 0 || this.item.equals(NMIBalanceUnits.EMPTY);
    }

    public NMIBalanceEntry copyWithCount(int newCount) {
        return new NMIBalanceEntry(this.item, newCount);
    }

    public static final NMIBalanceEntry EMPTY = new NMIBalanceEntry(NMIBalanceUnits.EMPTY, 0);

    public NMIBalanceEntry copy() {
        return new NMIBalanceEntry(this.item, this.count);
    }
}