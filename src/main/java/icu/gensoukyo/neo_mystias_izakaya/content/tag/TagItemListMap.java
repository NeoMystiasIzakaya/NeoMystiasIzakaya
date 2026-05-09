package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class TagItemListMap {
    private final List<TagItemListHolder> positiveTags;
    private final List<TagItemListHolder> negativeTags;
    private final Map<Identifier, TagItemListHolder> positiveTagToItemMap;
    private final Map<Identifier, TagItemListHolder> negativeTagToItemMap;
    private final Map<Identifier, ItemTagList> itemToTagMap;
    private final Map<Identifier, List<Identifier>> itemToPositiveTagMap;
    private final Map<Identifier, List<Identifier>> itemToNegativeTagMap;

    public static final Codec<TagItemListMap> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagItemListHolder.CODEC.listOf().fieldOf("positiveTags").forGetter(TagItemListMap::getPositiveTags),
                    TagItemListHolder.CODEC.listOf().fieldOf("negativeTags").forGetter(TagItemListMap::getNegativeTags)
            ).apply(instance, TagItemListMap::new)
    );

    public static final StreamCodec<ByteBuf, TagItemListMap> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, TagItemListHolder>list().apply(TagItemListHolder.STREAM_CODEC), TagItemListMap::getPositiveTags,
            ByteBufCodecs.<ByteBuf, TagItemListHolder>list().apply(TagItemListHolder.STREAM_CODEC), TagItemListMap::getNegativeTags,
            TagItemListMap::new
    );

    private TagItemListMap(List<TagItemListHolder> positiveTags, List<TagItemListHolder> negativeTags) {
        this.positiveTags = positiveTags;
        this.negativeTags = negativeTags;
        this.positiveTagToItemMap = positiveTags.stream().collect(Collectors.toMap(TagItemListHolder::key, t -> t));
        this.negativeTagToItemMap = negativeTags.stream().collect(Collectors.toMap(TagItemListHolder::key, t -> t));
        this.itemToPositiveTagMap = buildSingleItemMap(positiveTags);
        this.itemToNegativeTagMap = buildSingleItemMap(negativeTags);
        this.itemToTagMap = buildItemMap(itemToPositiveTagMap, itemToNegativeTagMap);
    }

    private static Map<Identifier, ItemTagList> buildItemMap(Map<Identifier, List<Identifier>> positiveItemMap,Map<Identifier, List<Identifier>> negativeItemMap ) {
        Map<Identifier, ItemTagList> itemMap = new HashMap<>();
        positiveItemMap.keySet().forEach(
                itemId -> {
                    List<Identifier> positiveTags = positiveItemMap.getOrDefault(itemId, List.of());
                    List<Identifier> negativeTags = negativeItemMap.getOrDefault(itemId, List.of());
                    itemMap.put(itemId, new ItemTagList(positiveTags, negativeTags));
                }
        );
        return itemMap;
    }

    private static Map<Identifier, List<Identifier>> buildSingleItemMap(List<TagItemListHolder> tags) {
        Map<Identifier, List<Identifier>> itemMap = new HashMap<>();
        tags.forEach(holder -> holder.tag().items().forEach(item -> addTagToItemMap(itemMap, item, holder.key())));
        return itemMap;
    }

    private static void addTagToItemMap(Map<Identifier, List<Identifier>> itemMap, Identifier itemId, Identifier tagId) {
        List<Identifier> list = itemMap.getOrDefault(itemId, new ArrayList<>());
        list.add(tagId);
        itemMap.put(itemId, list);
    }

    public static TagItemListMap create(List<TagItemListHolder> positiveTags, List<TagItemListHolder> negativeTags) {
        return new TagItemListMap(positiveTags, negativeTags);
    }

    public TagItemListHolder get(Identifier id) {
        return positiveTagToItemMap.get(id);
    }

    public ItemTagList getTagsForItem(Identifier itemId) {
        return itemToTagMap.getOrDefault(itemId, ItemTagList.EMPTY);
    }

    public static final TagItemListMap EMPTY = create(List.of(), List.of());

}
