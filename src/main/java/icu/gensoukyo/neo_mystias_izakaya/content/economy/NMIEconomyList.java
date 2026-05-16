package icu.gensoukyo.neo_mystias_izakaya.content.economy;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record NMIEconomyList(List<NMIEconomy> economies) {

    public static final Codec<NMIEconomyList> CODEC = Codec.list(NMIEconomy.CODEC).xmap(NMIEconomyList::new, NMIEconomyList::economies);

    public static final StreamCodec<ByteBuf, NMIEconomyList> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, NMIEconomy>list().apply(NMIEconomy.STREAM_CODEC), NMIEconomyList::economies,
            NMIEconomyList::new
    );

    public static final NMIEconomyList EMPTY = new NMIEconomyList(List.of());
}
