package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;

public record ItemTagList(List<Identifier> tags) {
    
    public static final ItemTagList EMPTY = new ItemTagList(List.of());

    public static final Codec<ItemTagList> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("tags").forGetter(ItemTagList::tags)
            ).apply(instance, ItemTagList::new)
    );

    public static final StreamCodec<ByteBuf, ItemTagList> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), ItemTagList::tags,
            ItemTagList::new
    );
    
    @Override
    public String toString() {
        return "ItemTagList{" +
                "tags=" + tags +
                '}';
    }
}
