package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.ModifyItemTagJsonsEvent;
import lombok.Getter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.waypoints.WaypointStyleAsset;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import org.slf4j.Logger;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ItemTagReloadListener extends SimplePreparableReloadListener<ItemTagMap> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter TAG_LISTER = FileToIdConverter.json(NeoMystiasIzakaya.path("item_tags"));
    private final HolderLookup.Provider registries;
    @Getter
    private ItemTagMap itemTagMap = ItemTagMap.EMPTY;

    public ItemTagReloadListener(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    protected ItemTagMap prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        SortedMap<Identifier, ItemTag> itemTagTreeMap = new TreeMap<>();
        var conditionalOps = new ConditionalOps<>(this.registries.createSerializationContext(JsonOps.INSTANCE), getContext());
        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, TAG_LISTER, conditionalOps, ItemTag.CODEC, itemTagTreeMap, recipeJsons -> {
                    var event = new ModifyItemTagJsonsEvent(conditionalOps, recipeJsons);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<ItemTagHolder> recipeHolders = new ArrayList<>(itemTagTreeMap.size());
        itemTagTreeMap.forEach((id, tag) -> {
            ItemTagHolder holder = new ItemTagHolder(id, tag);
            recipeHolders.add(holder);
        });
        return ItemTagMap.create(recipeHolders);
    }

    @Override
    protected void apply(ItemTagMap itemTagMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.itemTagMap = itemTagMap;
        LOGGER.info("Loaded {} item tags", itemTagMap.getTags().size());
    }
}
