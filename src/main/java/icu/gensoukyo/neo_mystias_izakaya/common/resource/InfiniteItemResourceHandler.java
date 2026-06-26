/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.resource;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.List;
import java.util.stream.Stream;

public class InfiniteItemResourceHandler extends InfiniteResourceHandler<ItemResource> {
    protected InfiniteItemResourceHandler(List<ItemResource> resourceList) {
        super(resourceList);
    }
    public static InfiniteItemResourceHandler withItemResource(List<ItemResource> resourceList) {
        return new InfiniteItemResourceHandler(resourceList);
    }
    public static InfiniteItemResourceHandler withItemResource(ItemResource... resourceList) {
        return new InfiniteItemResourceHandler(List.of(resourceList));
    }
    public static InfiniteItemResourceHandler withItem(List<Item> itemList) {
        return new InfiniteItemResourceHandler(itemList.stream().map(ItemResource::of).toList());
    }
    public static InfiniteItemResourceHandler withItem(Item... itemList) {
        return new InfiniteItemResourceHandler(Stream.of(itemList).map(ItemResource::of).toList());
    }
    public static InfiniteItemResourceHandler withDeferredItem(List<DeferredItem<Item>> deferredItemList) {
        return new InfiniteItemResourceHandler(deferredItemList.stream().map(DeferredItem::get).map(ItemResource::of).toList());
    }
}
