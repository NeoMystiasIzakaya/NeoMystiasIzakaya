/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.dal.ServerNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import lombok.Getter;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

public class NMIStoreReloadListener extends SimplePreparableReloadListener<NMIStoreMap> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final HolderLookup.Provider registries;
    @Getter
    private NMIStoreMap storeMap = NMIStoreMap.EMPTY;

    public NMIStoreReloadListener(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    protected NMIStoreMap prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        long buildStartTime = System.currentTimeMillis();

        NMIStoreMap storeMap = NMIStoreMap.create(resourceManager);

        long buildEndTime = System.currentTimeMillis();
        LOGGER.info("Finished building NMI store map in {} ms", buildEndTime - buildStartTime);
        return storeMap;
    }

    @Override
    protected void apply(NMIStoreMap map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.storeMap = map;
        LOGGER.info("Loaded {} NMI stores", storeMap.getStores().size());
        if (FMLEnvironment.getDist().isDedicatedServer()) {
            ServerNMIDataAccessor.INSTANCE.setStoreMap(storeMap);
            if (ServerLifecycleHooks.getCurrentServer() != null) {
                ServerPayloadSender.sendStoreMapSyncMessage(storeMap);
            }
        }else {
            ServerNMIDataAccessor.INSTANCE.setStoreMap(storeMap);
            ClientNMIDataAccessor.INSTANCE.setStoreMap(storeMap);
        }
    }
}
