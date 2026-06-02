/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import icu.gensoukyo.neo_mystias_izakaya.api.event.common.ServerStoreEvent;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerEconomyUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import lombok.Getter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.common.NeoForge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class NMIStoreMap {

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIStoreMap> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, Store.STREAM_CODEC),
            o -> o.stores,
            NMIStoreMap::new
    );

    private final List<Store> stores;
    private final Map<Identifier,Store> storeMap;

    private NMIStoreMap(List<Store> stores) {
        this.stores = stores;
        this.storeMap = stores.stream().collect(java.util.stream.Collectors.toMap(Store::getId, s -> s));
    }

    public static final NMIStoreMap EMPTY = new NMIStoreMap(List.of());

    // 必须在服务器启动后调用
    public static NMIStoreMap create(ResourceManager resourceManager){
        List<StoreItem> ingredients = NMIIngredientItems.ITEMS.getEntries().stream()
                .map(e -> new StoreItem(e.getId(), NMIServerEconomyUtil.getPriceWithDefault(e.getId(), 0), new ItemStackTemplate(e))).toList();
        List<StoreItem> beverages = NMIBeveragesItems.ITEMS.getEntries().stream()
                .map(e -> new StoreItem(e.getId(), NMIServerEconomyUtil.getPriceWithDefault(e.getId(), 0), new ItemStackTemplate(e))).toList();
        Store ingredientStore = new Store(Store.INGREDIENTS, ingredients);
        Store beverageStore = new Store(Store.BEVERAGES, beverages);
        List<StoreItem> all = new ArrayList<>();
        all.addAll(ingredients);
        all.addAll(beverages);
        Store allStore = new Store(Store.ALL, all);

        List<Store> stores = new ArrayList<>();
        stores.add(ingredientStore);
        stores.add(beverageStore);
        stores.add(allStore);

        ServerStoreEvent.Init post = NeoForge.EVENT_BUS.post(new ServerStoreEvent.Init(stores));

        return new NMIStoreMap(post.getStores());
    }
}
