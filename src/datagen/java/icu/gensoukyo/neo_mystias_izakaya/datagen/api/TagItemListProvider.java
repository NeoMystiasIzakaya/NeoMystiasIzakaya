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
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public abstract class TagItemListProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final Map<Identifier, TagItemList> pTagData = new TreeMap<>();
    private final Map<Identifier, TagItemList> nTagData = new TreeMap<>();

    public TagItemListProvider(PackOutput output, String modid) {
        this.output = output;
        this.modid = modid;
    }

    protected abstract void addTags();

    protected void addPTag(String key, List<Identifier> items) {
        this.pTagData.put(Identifier.fromNamespaceAndPath(modid, key), new TagItemList(items));
    }

    protected void addPTag(Identifier key, List<Identifier> items) {
        this.pTagData.put(key, new TagItemList(items));
    }

    protected void addPTag(TagItemListHolder holder) {
        this.pTagData.put(holder.key(), holder.tag());
    }

    protected void addPTag(Identifier key, TagItemList tag) {
        this.pTagData.put(key, tag);
    }

    protected void addPTag(String key, TagItemList tag) {
        this.pTagData.put(Identifier.fromNamespaceAndPath(modid, key), tag);
    }

    protected void addNTag(String key, List<Identifier> items) {
        this.nTagData.put(Identifier.fromNamespaceAndPath(modid, key), new TagItemList(items));
    }

    protected void addNTag(Identifier key, List<Identifier> items) {
        this.nTagData.put(key, new TagItemList(items));
    }

    protected void addNTag(TagItemListHolder holder) {
        this.nTagData.put(holder.key(), holder.tag());
    }

    protected void addNTag(Identifier key, TagItemList tag) {
        this.nTagData.put(key, tag);
    }

    protected List<Identifier> fromItems(Item... items) {
        return Stream.of(items).map(BuiltInRegistries.ITEM::getKey).toList();
    }

    protected List<Identifier> fromItems(DeferredItem<? extends Item>... items) {
        return Stream.of(items).map(DeferredHolder::get).map(BuiltInRegistries.ITEM::getKey).toList();
    }

    protected Builder pTag(String key) {
        return new Builder(this.pTagData, Identifier.fromNamespaceAndPath(modid, key));
    }

    protected Builder pTag(Identifier key) {
        return new Builder(this.pTagData, key);
    }

    protected Builder nTag(String key) {
        return new Builder(this.nTagData, Identifier.fromNamespaceAndPath(modid, key));
    }

    protected Builder nTag(Identifier key) {
        return new Builder(this.nTagData, key);
    }


    protected static class Builder {
        final Map<Identifier, TagItemList> tagData;
        final Identifier key;
        List<Identifier> tmp = new ArrayList<>();

        protected Builder(Map<Identifier, TagItemList> tagData, Identifier key) {
            this.tagData = tagData;
            this.key = key;
        }

        public Builder add(Item... items) {
            this.tmp.addAll(Stream.of(items).map(BuiltInRegistries.ITEM::getKey).toList());
            this.tagData.put(key, new TagItemList(tmp));
            return this;
        }

        @SafeVarargs
        public final Builder add(DeferredItem<? extends Item>... items) {
            this.tmp.addAll(Stream.of(items).map(DeferredHolder::get).map(BuiltInRegistries.ITEM::getKey).toList());
            this.tagData.put(key, new TagItemList(tmp));
            return this;
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addTags();
        PackOutput.PathProvider pProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("item_positive_tags"));
        PackOutput.PathProvider nProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("item_negative_tags"));
        return CompletableFuture.allOf(DataProvider.saveAll(cachedOutput, TagItemList.CODEC, pProvider, pTagData),
                DataProvider.saveAll(cachedOutput, TagItemList.CODEC, nProvider, nTagData));
    }

    @Override
    public @NonNull String getName() {
        return "neo_mystias_izakaya:item_tags:" + modid;
    }
}
