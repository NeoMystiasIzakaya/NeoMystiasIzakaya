/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.ModifyNMICustomerJsonsEvent;
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

public class CustomerReloadListener extends SimplePreparableReloadListener<CustomerMap> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter COMMON_CUSTOMER = FileToIdConverter.json(NeoMystiasIzakaya.path("common_customer"));
    private static final FileToIdConverter RARE_CUSTOMER = FileToIdConverter.json(NeoMystiasIzakaya.path("rare_customer"));
    private final HolderLookup.Provider registries;
    @Getter
    private CustomerMap customerMap = CustomerMap.EMPTY;

    public CustomerReloadListener(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    protected CustomerMap prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        long loadStartTime = System.currentTimeMillis();

        HashMap<Identifier, CommonCustomer> commonCustomerHashMap = new HashMap<>();
        HashMap<Identifier, RareCustomer> rareCustomerHashMap = new HashMap<>();
        var conditionalOps = new ConditionalOps<>(this.registries.createSerializationContext(JsonOps.INSTANCE), getContext());
        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, COMMON_CUSTOMER, conditionalOps, CommonCustomer.CODEC, commonCustomerHashMap, recipeJsons -> {
                    var event = new ModifyNMICustomerJsonsEvent(conditionalOps, recipeJsons, true);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<CommonCustomerHolder> commonCustomerHolders = new ArrayList<>(commonCustomerHashMap.size());
        commonCustomerHashMap.forEach((id, customer) -> {
            CommonCustomerHolder holder = new CommonCustomerHolder(id, customer);
            commonCustomerHolders.add(holder);
        });

        SimpleJsonResourceReloadListener.scanDirectoryWithModifier(
                resourceManager, RARE_CUSTOMER, conditionalOps, RareCustomer.CODEC, rareCustomerHashMap, recipeJsons -> {
                    var event = new ModifyNMICustomerJsonsEvent(conditionalOps, recipeJsons, false);
                    NeoForge.EVENT_BUS.post(event);
                }
        );
        List<RareCustomerHolder> rareCustomerHolders = new ArrayList<>(rareCustomerHashMap.size());
        rareCustomerHashMap.forEach((id, customer) -> {
            RareCustomerHolder holder = new RareCustomerHolder(id, customer);
            rareCustomerHolders.add(holder);
        });

        long loadEndTime = System.currentTimeMillis();
        LOGGER.info("Finished loading NMI customers in {} ms", loadEndTime - loadStartTime);
        long buildStartTime = System.currentTimeMillis();
        CustomerMap map = CustomerMap.create(commonCustomerHolders, rareCustomerHolders);
        long buildEndTime = System.currentTimeMillis();
        LOGGER.info("Finished building NMI customers in {} ms", buildEndTime - buildStartTime);
        return map;
    }

    @Override
    protected void apply(CustomerMap customerMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.customerMap = customerMap;
        LOGGER.info("Loaded {} common customers and {} rare customers", customerMap.getCommonCustomers().size(), customerMap.getRareCustomers().size());
        if (FMLEnvironment.getDist().isDedicatedServer()) {
            ServerNMIDataAccessor.INSTANCE.setCustomerMap(customerMap);
            ServerPayloadSender.sendCustomerDataSyncMessage(customerMap);
        }else {
            ServerNMIDataAccessor.INSTANCE.setCustomerMap(customerMap);
            ClientNMIDataAccessor.INSTANCE.setCustomerMap(customerMap);
        }
    }
}
