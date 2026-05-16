package icu.gensoukyo.neo_mystias_izakaya.content.economy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record NMIEconomyListHolder(Identifier key, NMIEconomyList economy) {

    public static final Codec<NMIEconomyListHolder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("key").forGetter(NMIEconomyListHolder::key),
                    NMIEconomyList.CODEC.fieldOf("economy").forGetter(NMIEconomyListHolder::economy)
            ).apply(instance, NMIEconomyListHolder::new)
    );

    public static final StreamCodec<ByteBuf, NMIEconomyListHolder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, NMIEconomyListHolder::key,
            NMIEconomyList.STREAM_CODEC, NMIEconomyListHolder::economy,
            NMIEconomyListHolder::new
    );
}
