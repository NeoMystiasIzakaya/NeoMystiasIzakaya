/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.ModifyNMIEconomyJsonEvent;
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

public class NMIEconomyReloadListener extends SimplePreparableReloadListener<NMIEconomyMap> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter ECONOMY = FileToIdConverter.json(NeoMystiasIzakaya.path("economy"));
    private final HolderLookup.Provider registries;
    @Getter
    private NMIEconomyMap economyMap = NMIEconomyMap.EMPTY;

    public NMIEconomyReloadListener(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    protected NMIEconomyMap prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        long loadStartTime = System.currentTimeMillis();

        HashMap<Identifier, NMIEconomyList> economyListHashMap = new HashMap<>();
        var conditionalOps = new ConditionalOps<>(this.registries.createSerializationContext(JsonOps.INSTANCE), getContext());
        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, ECONOMY, conditionalOps, NMIEconomyList.CODEC, economyListHashMap, recipeJsons -> {
                    var event = new ModifyNMIEconomyJsonEvent(conditionalOps, recipeJsons);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<NMIEconomyListHolder> recipeHolders = new ArrayList<>(economyListHashMap.size());
        economyListHashMap.forEach((id, recipe) -> {
            NMIEconomyListHolder holder = new NMIEconomyListHolder(id, recipe);
            recipeHolders.add(holder);
        });
        long loadEndTime = System.currentTimeMillis();
        LOGGER.info("Finished loading NMI economy in {} ms", loadEndTime - loadStartTime);
        long buildStartTime = System.currentTimeMillis();
        NMIEconomyMap recipeMap = NMIEconomyMap.create(recipeHolders);
        long buildEndTime = System.currentTimeMillis();
        LOGGER.info("Finished building NMI economy map in {} ms", buildEndTime - buildStartTime);
        return recipeMap;
    }

    @Override
    protected void apply(NMIEconomyMap map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.economyMap = map;
        LOGGER.info("Loaded {} NMI economy prices", economyMap.getItemPriceMap().size());
        if (FMLEnvironment.getDist().isDedicatedServer()) {
            ServerNMIDataAccessor.INSTANCE.setEconomyMap(economyMap);
            ServerPayloadSender.sendEconomyMapSyncMessage(economyMap);
        }else {
            ServerNMIDataAccessor.INSTANCE.setEconomyMap(economyMap);
            ClientNMIDataAccessor.INSTANCE.setEconomyMap(economyMap);
        }
    }
}
