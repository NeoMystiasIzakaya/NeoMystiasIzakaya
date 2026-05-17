package icu.gensoukyo.neo_mystias_izakaya.content.economy.balance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class NMIBalance {

    private final List<NMIBalanceEntry> entries;
    private final Map<Identifier, Integer> balanceMap;

    public NMIBalance(List<NMIBalanceEntry> entries) {
        this.entries = entries;
        this.balanceMap = entries.stream().collect(java.util.stream.Collectors.toMap(NMIBalanceEntry::item, NMIBalanceEntry::count));
        this.entryMap = entries.stream().collect(java.util.stream.Collectors.toMap(NMIBalanceEntry::item, entry -> entry));
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


    public record NMIBalanceEntry(Identifier item, int count) {
        public static final MapCodec<NMIBalanceEntry> MAP_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Identifier.CODEC.fieldOf("item").forGetter(NMIBalanceEntry::item),
                        Codec.INT.fieldOf("count").forGetter(NMIBalanceEntry::count)
                ).apply(instance, NMIBalanceEntry::new)
        );
        public static final Codec<NMIBalanceEntry> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Identifier.CODEC.fieldOf("item").forGetter(NMIBalanceEntry::item),
                        Codec.INT.fieldOf("count").forGetter(NMIBalanceEntry::count)
                ).apply(instance, NMIBalanceEntry::new)
        );
        public static final StreamCodec<ByteBuf, NMIBalanceEntry> STREAM_CODEC = StreamCodec.composite(
                Identifier.STREAM_CODEC, NMIBalanceEntry::item,
                ByteBufCodecs.INT, NMIBalanceEntry::count,
                NMIBalanceEntry::new
        );
    }

    public static final NMIBalance EMPTY = new NMIBalance(List.of());

    public NMIBalance copy() {
        return new NMIBalance(new ArrayList<>(this.entries));
    }

    public NMIBalance merge(NMIBalance other) {
        Set<Identifier> allItems = new java.util.HashSet<>(this.balanceMap.keySet());
        allItems.addAll(other.balanceMap.keySet());
        List<NMIBalanceEntry> mergedEntries = new ArrayList<>();
        for (Identifier item : allItems) {
            int count1 = this.balanceMap.getOrDefault(item, 0);
            int count2 = other.balanceMap.getOrDefault(item, 0);
            mergedEntries.add(new NMIBalanceEntry(item, count1 + count2));
        }
        return new NMIBalance(mergedEntries);
    }

    public boolean canAfford(NMIBalance cost) {
        for (NMIBalanceEntry entry : cost.entries) {
            int available = this.balanceMap.getOrDefault(entry.item(), 0);
            if (available < entry.count()) {
                return false;
            }
        }
        return true;
    }

    public NMIBalance subtract(NMIBalance cost) {
        List<NMIBalanceEntry> newEntries = new ArrayList<>();
        for (NMIBalanceEntry entry : this.entries) {
            int costCount = cost.balanceMap.getOrDefault(entry.item(), 0);
            int newCount = entry.count() - costCount;
            if (newCount > 0) {
                newEntries.add(new NMIBalanceEntry(entry.item(), newCount));
            }
        }
        return new NMIBalance(newEntries);
    }

    public NMIBalance setCount(Identifier item, int count) {
        List<NMIBalanceEntry> newEntries = new ArrayList<>();
        boolean found = false;
        for (NMIBalanceEntry entry : this.entries) {
            if (entry.item().equals(item)) {
                if (count > 0) {
                    newEntries.add(new NMIBalanceEntry(item, count));
                }
                found = true;
            } else {
                newEntries.add(entry);
            }
        }
        if (!found && count > 0) {
            newEntries.add(new NMIBalanceEntry(item, count));
        }
        return new NMIBalance(newEntries);
    }
}
