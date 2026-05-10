/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.recipe;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.ModifyNMIRecipeEvent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NMIRecipeReloadListener extends SimplePreparableReloadListener<NMIRecipeMap> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter NMI_RECIPE = FileToIdConverter.json(NeoMystiasIzakaya.path("recipe"));
    private final HolderLookup.Provider registries;
    @Getter
    private NMIRecipeMap nmiRecipeMap = NMIRecipeMap.EMPTY;

    public NMIRecipeReloadListener(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    protected NMIRecipeMap prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        long loadStartTime = System.currentTimeMillis();

        HashMap<Identifier, NMIRecipe> recipeHolderTreeMap = new HashMap<>();
        var conditionalOps = new ConditionalOps<>(this.registries.createSerializationContext(JsonOps.INSTANCE), getContext());
        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, NMI_RECIPE, conditionalOps, NMIRecipe.CODEC, recipeHolderTreeMap, recipeJsons -> {
                    var event = new ModifyNMIRecipeEvent(conditionalOps, recipeJsons);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<NMIRecipeHolder> recipeHolders = new ArrayList<>(recipeHolderTreeMap.size());
        recipeHolderTreeMap.forEach((id, recipe) -> {
            NMIRecipeHolder holder = new NMIRecipeHolder(id, recipe);
            recipeHolders.add(holder);
        });
        long loadEndTime = System.currentTimeMillis();
        LOGGER.info("Finished loading NMI recipes in {} ms", loadEndTime - loadStartTime);
        long buildStartTime = System.currentTimeMillis();
        NMIRecipeMap recipeMap = NMIRecipeMap.create(recipeHolders);
        long buildEndTime = System.currentTimeMillis();
        LOGGER.info("Finished building NMI recipe map in {} ms", buildEndTime - buildStartTime);
        return recipeMap;
    }

    @Override
    protected void apply(NMIRecipeMap map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.nmiRecipeMap = map;
        LOGGER.info("Loaded {} NMI recipes", nmiRecipeMap.getRecipes().size());
        if (FMLEnvironment.getDist().isDedicatedServer()) {
            ServerNMIDataAccessor.INSTANCE.setRecipeMap(nmiRecipeMap);
            ServerPayloadSender.sendRecipeMapSyncMessage(nmiRecipeMap);
        }else {
            ServerNMIDataAccessor.INSTANCE.setRecipeMap(nmiRecipeMap);
            ClientNMIDataAccessor.INSTANCE.setRecipeMap(nmiRecipeMap);
        }
    }
}
