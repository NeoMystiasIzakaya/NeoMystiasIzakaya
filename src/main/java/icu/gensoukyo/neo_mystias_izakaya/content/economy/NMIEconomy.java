package icu.gensoukyo.neo_mystias_izakaya.content.economy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record NMIEconomy(Identifier item, int price) {

    public static final Codec<NMIEconomy> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(NMIEconomy::item),
                    Codec.INT.fieldOf("price").forGetter(NMIEconomy::price)
            ).apply(instance, NMIEconomy::new)
    );

    public static final StreamCodec<ByteBuf, NMIEconomy> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, NMIEconomy::item,
            ByteBufCodecs.INT, NMIEconomy::price,
            NMIEconomy::new
    );

    public static final NMIEconomy EMPTY = new NMIEconomy(Identifier.parse("minecraft:air"), 0);
}
