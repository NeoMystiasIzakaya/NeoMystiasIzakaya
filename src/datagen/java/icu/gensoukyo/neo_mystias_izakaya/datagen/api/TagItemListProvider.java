package icu.gensoukyo.neo_mystias_izakaya.datagen.api;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemList;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class TagItemListProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final Map<Identifier, TagItemList> tagData = new TreeMap<>();

    public TagItemListProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }

    protected abstract void addTags();

    protected void addTag(String key, List<Identifier> items) {
        this.tagData.put(Identifier.fromNamespaceAndPath(modid, key), new TagItemList(items));
    }

    protected void addTag(Identifier key, List<Identifier> items) {
        this.tagData.put(key, new TagItemList(items));
    }

    protected void addTag(TagItemListHolder holder) {
        this.tagData.put(holder.key(), holder.tag());
    }

    protected void addTag(Identifier key, TagItemList tag) {
        this.tagData.put(key, tag);
    }

    protected void addTag(String key, TagItemList tag) {
        this.tagData.put(Identifier.fromNamespaceAndPath(modid, key), tag);
    }

    protected List<Identifier> fromItems(Item... items) {
        return List.of(items).stream().map(BuiltInRegistries.ITEM::getKey).toList();
    }

    protected List<Identifier> fromItems(DeferredItem<Item>... items) {
        return List.of(items).stream().map(DeferredHolder::get).map(BuiltInRegistries.ITEM::getKey).toList();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addTags();
        PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("item_tags"));
        return DataProvider.saveAll(cachedOutput, TagItemList.CODEC, provider, tagData);
    }

    @Override
    public String getName() {
        return "neo_mystias_izakaya:item_tags:" + modid;
    }
}
