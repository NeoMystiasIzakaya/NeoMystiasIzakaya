package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import lombok.Getter;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagItemListMap {
    @Getter
    private final List<TagItemListHolder> tags;
    @Getter
    private final Map<Identifier, TagItemListHolder> tagMap;

    private TagItemListMap(List<TagItemListHolder> tags) {
        this.tags = tags;
        this.tagMap = tags.stream().collect(Collectors.toMap(TagItemListHolder::key, t -> t));
    }

    public static TagItemListMap create(List<TagItemListHolder> tags) {
        return new TagItemListMap(tags);
    }

    public TagItemListHolder get(Identifier id) {
        return tagMap.get(id);
    }

    public static final TagItemListMap EMPTY = create(List.of());

}
