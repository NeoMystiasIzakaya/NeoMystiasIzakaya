package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Items;

import java.util.List;

public record ItemTag(List<String> items) {

    public static final ItemTag EMPTY = new ItemTag(List.of());

    public static final Codec<ItemTag> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.listOf().fieldOf("items").forGetter(ItemTag::items)
            ).apply(instance, ItemTag::new)
    );

    public static final StreamCodec<ByteBuf,ItemTag> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf,String>list().apply(ByteBufCodecs.STRING_UTF8), ItemTag::items,
            ItemTag::new
    );
}
