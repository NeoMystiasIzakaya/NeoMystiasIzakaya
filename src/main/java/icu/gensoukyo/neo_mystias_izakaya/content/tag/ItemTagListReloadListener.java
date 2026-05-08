package icu.gensoukyo.neo_mystias_izakaya.content.tag;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.ModifyItemTagJsonsEvent;
import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.dal.ServerNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import lombok.Getter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import org.slf4j.Logger;

import java.util.*;

public class ItemTagListReloadListener extends SimplePreparableReloadListener<TagItemListMap> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter POSITIVE_TAG_LISTER = FileToIdConverter.json(NeoMystiasIzakaya.path("item_positive_tags"));
    private static final FileToIdConverter NEGATIVE_TAG_LISTER = FileToIdConverter.json(NeoMystiasIzakaya.path("item_negative_tags"));
    private final HolderLookup.Provider registries;
    @Getter
    private TagItemListMap tagItemListMap = TagItemListMap.EMPTY;

    public ItemTagListReloadListener(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    protected TagItemListMap prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        SortedMap<Identifier, TagItemList> pItemTagTreeMap = new TreeMap<>();
        SortedMap<Identifier, TagItemList> nItemTagTreeMap = new TreeMap<>();
        var conditionalOps = new ConditionalOps<>(this.registries.createSerializationContext(JsonOps.INSTANCE), getContext());
        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, POSITIVE_TAG_LISTER, conditionalOps, TagItemList.CODEC, pItemTagTreeMap, recipeJsons -> {
                    var event = new ModifyItemTagJsonsEvent(conditionalOps, recipeJsons, true);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<TagItemListHolder> pRecipeHolders = new ArrayList<>(pItemTagTreeMap.size());
        pItemTagTreeMap.forEach((id, tag) -> {
            TagItemListHolder holder = new TagItemListHolder(id, tag);
            pRecipeHolders.add(holder);
        });

        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, NEGATIVE_TAG_LISTER, conditionalOps, TagItemList.CODEC, nItemTagTreeMap, recipeJsons -> {
                    var event = new ModifyItemTagJsonsEvent(conditionalOps, recipeJsons, false);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<TagItemListHolder> nRecipeHolders = new ArrayList<>(nItemTagTreeMap.size());
        nItemTagTreeMap.forEach((id, tag) -> {
            TagItemListHolder holder = new TagItemListHolder(id, tag);
            nRecipeHolders.add(holder);
        });


        return TagItemListMap.create(pRecipeHolders,nRecipeHolders);
    }

    @Override
    protected void apply(TagItemListMap tagItemListMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.tagItemListMap = tagItemListMap;
        if (FMLEnvironment.getDist().isDedicatedServer()) {
            ServerNMIDataAccessor.INSTANCE.setTagItemListMap(tagItemListMap);
            ServerPayloadSender.sendTagItemListMapSyncMessage(tagItemListMap);
        }else {
            ServerNMIDataAccessor.INSTANCE.setTagItemListMap(tagItemListMap);
            ClientNMIDataAccessor.INSTANCE.setTagItemListMap(tagItemListMap);
        }
        LOGGER.info("Loaded {} item positive tags with {} items", tagItemListMap.getPositiveTags().size(),tagItemListMap.getPositiveItemMap().size());
        LOGGER.info("Loaded {} item negative tags with {} items", tagItemListMap.getNegativeTags().size(),tagItemListMap.getNegativeItemMap().size());
    }
}
