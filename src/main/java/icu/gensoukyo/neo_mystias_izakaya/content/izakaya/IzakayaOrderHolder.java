package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

@Getter
public class IzakayaOrderHolder {

    private final short id;
    private IzakayaOrder order;

    public IzakayaOrderHolder(short id, IzakayaOrder order) {
        this.id = id;
        this.order = order;
    }

    public static final Codec<IzakayaOrderHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.SHORT.fieldOf("id").forGetter(IzakayaOrderHolder::getId),
            IzakayaOrder.CODEC.fieldOf("order").forGetter(IzakayaOrderHolder::getOrder)
    ).apply(instance, IzakayaOrderHolder::new));

    public static final StreamCodec<ByteBuf, IzakayaOrderHolder> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.SHORT, IzakayaOrderHolder::getId,
            IzakayaOrder.STREAM_CODEC, IzakayaOrderHolder::getOrder,
            IzakayaOrderHolder::new
    );

    public IzakayaOrderHolder copy() {
        return new IzakayaOrderHolder(id, order.copy());
    }
}
