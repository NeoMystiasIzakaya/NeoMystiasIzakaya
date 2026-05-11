/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.datagen.api;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CommonCustomer;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerBudget;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.RareCustomer;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class CustomerProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final Map<Identifier, CommonCustomer> commonCustomerMap = new HashMap<>();
    private final Map<Identifier, RareCustomer> rareCustomerMap = new HashMap<>();

    protected CustomerProvider(PackOutput output, String modid) {
        this.modid = modid;
        this.output = output;
    }

    protected abstract void addCustomers();

    protected void addCommonCustomer(Identifier id, CommonCustomer customer) {
        commonCustomerMap.put(id, customer);
    }

    protected void addCommonCustomer(String id, CommonCustomer customer) {
        commonCustomerMap.put(Identifier.fromNamespaceAndPath(modid, id), customer);
    }

    protected void addRareCustomer(Identifier id, RareCustomer customer) {
        rareCustomerMap.put(id, customer);
    }

    protected void addRareCustomer(String id, RareCustomer customer) {
        rareCustomerMap.put(Identifier.fromNamespaceAndPath(modid, id), customer);
    }


    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addCustomers();
        PackOutput.PathProvider commonCustomer = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("common_customer"));
        PackOutput.PathProvider rareCustomer = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("rare_customer"));
        return CompletableFuture.allOf(DataProvider.saveAll(cachedOutput, CommonCustomer.CODEC, commonCustomer, commonCustomerMap),
                DataProvider.saveAll(cachedOutput, RareCustomer.CODEC, rareCustomer, rareCustomerMap));
    }

    @Override
    public @NonNull String getName() {
        return "neo_mystias_izakaya:customer:" + modid;
    }

    protected Builder builder(Identifier key) {
        return new Builder(commonCustomerMap, rareCustomerMap, key);
    }

    protected Builder builder(String key) {
        return builder(Identifier.fromNamespaceAndPath(modid, key));
    }

    protected static class Builder {
        private final Map<Identifier, CommonCustomer> commonCustomerMap;
        private final Map<Identifier, RareCustomer> rareCustomerMap;
        private final Identifier key;

        private CustomerBudget budget;
        private List<Identifier> locations = List.of();
        private List<Identifier> likes = List.of();
        private List<Identifier> dislikes = List.of();
        private List<Identifier> beverage = List.of();
        private List<Identifier> spellCards = List.of();
        private List<Identifier> chats = List.of();
        private boolean isCommon;

        protected Builder(Map<Identifier, CommonCustomer> commonCustomerMap, Map<Identifier, RareCustomer> rareCustomerMap, Identifier key) {
            this.commonCustomerMap = commonCustomerMap;
            this.rareCustomerMap = rareCustomerMap;
            this.key = key;
        }

        public Builder budget(int min, int max) {
            this.budget = new CustomerBudget(min, max);
            return this;
        }

        public Builder locations(Identifier... locations) {
            this.locations = List.of(locations);
            return this;
        }

        public Builder likes(Identifier... likes) {
            this.likes = List.of(likes);
            return this;
        }

        public Builder dislikes(Identifier... dislikes) {
            this.dislikes = List.of(dislikes);
            return this;
        }

        public Builder beverage(Identifier... beverage) {
            this.beverage = List.of(beverage);
            return this;
        }

        public Builder spellCards(Identifier... spellCards) {
            this.spellCards = List.of(spellCards);
            return this;
        }

        public Builder chats(Identifier... chats) {
            this.chats = List.of(chats);
            return this;
        }

        public Builder common() {
            this.isCommon = true;
            return this;
        }

        public Builder rare() {
            this.isCommon = false;
            return this;
        }

        public void build() {
            if (this.isCommon || (spellCards.isEmpty() && budget == null)) {
                commonCustomerMap.put(key, new CommonCustomer(locations, likes, dislikes, beverage, chats));
            } else {
                rareCustomerMap.put(key, new RareCustomer(budget, locations, likes, dislikes, beverage, spellCards, chats));
            }
        }


    }
}
