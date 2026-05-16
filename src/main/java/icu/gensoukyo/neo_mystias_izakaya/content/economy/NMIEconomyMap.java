package icu.gensoukyo.neo_mystias_izakaya.content.economy;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class NMIEconomyMap {

    private final List<NMIEconomyListHolder> economyLists;
    private final Map<Identifier,NMIEconomyListHolder> economyListMap;
    private final Map<Identifier,Integer> itemPriceMap;

    public static final NMIEconomyMap EMPTY = new NMIEconomyMap(List.of());

    public static final Codec<NMIEconomyMap> CODEC = NMIEconomyListHolder.CODEC.listOf().xmap(NMIEconomyMap::new, NMIEconomyMap::getEconomyLists);

    public static final StreamCodec<ByteBuf, NMIEconomyMap> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, NMIEconomyListHolder>list().apply(NMIEconomyListHolder.STREAM_CODEC), NMIEconomyMap::getEconomyLists, NMIEconomyMap::new
    );


    private NMIEconomyMap(List<NMIEconomyListHolder> economyLists) {
        this.economyLists = economyLists;
        this.economyListMap = economyLists.stream().collect(Collectors.toMap(NMIEconomyListHolder::key, e -> e));
        this.itemPriceMap = buildItemPriceMap(economyLists);
    }

    public static NMIEconomyMap create(List<NMIEconomyListHolder> economyLists) {
        return new NMIEconomyMap(economyLists);
    }

    private static Map<Identifier, Integer> buildItemPriceMap(List<NMIEconomyListHolder> economyLists) {
        return economyLists.stream()
                .flatMap(holder -> holder.economy().economies().stream())
                .collect(Collectors.toMap(NMIEconomy::item, NMIEconomy::price));
    }
}