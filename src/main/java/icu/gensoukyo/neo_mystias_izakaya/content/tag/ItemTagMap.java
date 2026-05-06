package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import lombok.Getter;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemTagMap {
    @Getter
    private final List<ItemTagHolder> tags;
    @Getter
    private final Map<Identifier,ItemTagHolder> tagMap;

    private ItemTagMap(List<ItemTagHolder> tags) {
        this.tags = tags;
        this.tagMap = tags.stream().collect(Collectors.toMap(ItemTagHolder::key, t -> t));
    }

    public static ItemTagMap create(List<ItemTagHolder> tags) {
        return new ItemTagMap(tags);
    }

    public ItemTagHolder get(Identifier id) {
        return tagMap.get(id);
    }

    public static final ItemTagMap EMPTY = create(List.of());

}
