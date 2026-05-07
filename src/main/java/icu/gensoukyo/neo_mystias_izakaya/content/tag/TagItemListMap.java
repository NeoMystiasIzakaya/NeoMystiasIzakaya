package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TagItemListMap {
    @Getter
    private final List<TagItemListHolder> tags;
    @Getter
    private final Map<Identifier, TagItemListHolder> tagMap;
    @Getter
    private final Map<Identifier, List<Identifier>> itemMap;

    public static final Codec<TagItemListMap> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagItemListHolder.CODEC.listOf().fieldOf("tags").forGetter(TagItemListMap::getTags)
            ).apply(instance, TagItemListMap::new)
    );

    public static final StreamCodec<ByteBuf, TagItemListMap> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, TagItemListHolder>list().apply(TagItemListHolder.STREAM_CODEC), TagItemListMap::getTags,
            TagItemListMap::new
    );

    private TagItemListMap(List<TagItemListHolder> tags) {
        this.tags = tags;
        this.tagMap = tags.stream().collect(Collectors.toMap(TagItemListHolder::key, t -> t));
        this.itemMap = buildItemMap(tags);
    }

    private static Map<Identifier, List<Identifier>> buildItemMap(List<TagItemListHolder> tags) {
        Map<Identifier, List<Identifier>> itemMap = new TreeMap<>();
        tags.forEach(holder -> holder.tag().items().forEach(item -> addTagToItemMap(itemMap, item, holder.key())));
        return itemMap;
    }

    private static void addTagToItemMap(Map<Identifier, List<Identifier>> itemMap, Identifier itemId, Identifier tagId) {
        List<Identifier> list = itemMap.getOrDefault(itemId, new ArrayList<>());
        list.add(tagId);
        itemMap.put(itemId, list);
    }

    public static TagItemListMap create(List<TagItemListHolder> tags) {
        return new TagItemListMap(tags);
    }

    public TagItemListHolder get(Identifier id) {
        return tagMap.get(id);
    }

    public static final TagItemListMap EMPTY = create(List.of());

}
