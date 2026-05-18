package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record IzakayaOrder(Identifier cuisine, Identifier beverage, Identifier requestedTag, boolean isRareCustomer) {

    public static final Identifier EMPTY_ORDER = NeoMystiasIzakaya.id("empty_order");

    public static final IzakayaOrder EMPTY = new IzakayaOrder( EMPTY_ORDER, EMPTY_ORDER, EMPTY_ORDER, false);

    public static final Codec<IzakayaOrder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("cuisine").forGetter(IzakayaOrder::cuisine),
                    Identifier.CODEC.fieldOf("beverage").forGetter(IzakayaOrder::beverage),
                    Identifier.CODEC.fieldOf("requestedTag").forGetter(IzakayaOrder::requestedTag),
                    Codec.BOOL.fieldOf("isRareCustomer").forGetter(IzakayaOrder::isRareCustomer)
            ).apply(instance, IzakayaOrder::new)
    );

    public static final StreamCodec<ByteBuf, IzakayaOrder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, IzakayaOrder::cuisine,
            Identifier.STREAM_CODEC, IzakayaOrder::beverage,
            Identifier.STREAM_CODEC, IzakayaOrder::requestedTag,
            ByteBufCodecs.BOOL, IzakayaOrder::isRareCustomer,
            IzakayaOrder::new
    );

    public IzakayaOrder copy(){
        return new IzakayaOrder(this.cuisine, this.beverage, this.requestedTag, this.isRareCustomer);
    }
}
