package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record ItemTagList(List<Identifier> positiveTags, List<Identifier> negativeTags) {

    public static final ItemTagList EMPTY = new ItemTagList(List.of(), List.of());

    public static final Codec<ItemTagList> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("positiveTags").forGetter(ItemTagList::positiveTags),
                    Identifier.CODEC.listOf().fieldOf("negativeTags").forGetter(ItemTagList::negativeTags)
            ).apply(instance, ItemTagList::new)
    );

    public static final StreamCodec<ByteBuf, ItemTagList> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), ItemTagList::positiveTags,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), ItemTagList::negativeTags,
            ItemTagList::new
    );

    @Override
    public String toString() {
        return "ItemTagList{" +
                "positiveTags=" + positiveTags +
                ", negativeTags=" + negativeTags +
                '}';
    }

    public ItemTagList copy() {
        return new ItemTagList(new ArrayList<>(positiveTags), new ArrayList<>(negativeTags));
    }

    public ItemTagList sort(){
        List<Identifier> sortedPositive = new ArrayList<>(positiveTags);
        List<Identifier> sortedNegative = new ArrayList<>(negativeTags);
        sortedPositive.sort(Identifier::compareTo);
        sortedNegative.sort(Identifier::compareTo);
        return new ItemTagList(sortedPositive, sortedNegative);
    }
}
