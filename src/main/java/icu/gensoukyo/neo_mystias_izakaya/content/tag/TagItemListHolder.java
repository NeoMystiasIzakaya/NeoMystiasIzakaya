package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

public record TagItemListHolder(Identifier key, TagItemList tag) {


    public static final Codec<TagItemListHolder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("key").forGetter(TagItemListHolder::key),
                    TagItemList.CODEC.fieldOf("tag").forGetter(TagItemListHolder::tag)
            ).apply(instance, TagItemListHolder::new)
    );

}
