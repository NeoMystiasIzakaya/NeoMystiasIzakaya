/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.resource;

import icu.gensoukyo.neo_mystias_izakaya.common.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResourceHandlerUtil {

    public static List<ItemResourceWithCount> extractCuisinesItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.CUISINES);
    }

    public static List<ItemResourceWithCount> extractBeveragesItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.BEVERAGES);
    }

    public static List<ItemResourceWithCount> extractIngredientItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.INGREDIENT);
    }

    public static List<ItemResourceWithCount> extractItemResourceListByTag(ResourceHandler<ItemResource> resourceHandler, TagKey<Item> tagKey) {
        Map<ItemResource, Long> itemResourceLongHashMap = new HashMap<>();
        for (int i = 0; i < resourceHandler.size(); i++) {
            Long count = itemResourceLongHashMap.getOrDefault(resourceHandler.getResource(i), 0L);
            itemResourceLongHashMap.put(resourceHandler.getResource(i), count == Long.MAX_VALUE ? count : count + resourceHandler.getAmountAsLong(i));
        }
        return itemResourceLongHashMap.keySet().stream().filter(i -> i.is(tagKey)).map(itemResource -> new ItemResourceWithCount(itemResource, itemResourceLongHashMap.get(itemResource))).toList();
    }

    public static int extractItemToPlayerHand(ServerPlayer serverPlayer, ItemResource itemResource, ResourceHandler<ItemResource> resourceHandler) {
         return extractItemToPlayerHand(serverPlayer, itemResource, serverPlayer.isShiftKeyDown() ? 64 : 1, resourceHandler);
    }

    public static int extractItemToPlayerHand(ServerPlayer serverPlayer, ItemResource itemResource, int amount, ResourceHandler<ItemResource> resourceHandler) {
        AbstractContainerMenu menu = serverPlayer.containerMenu;
        try (Transaction transaction = Transaction.openRoot()) {
            int limit = Math.min(menu.getCarried().getMaxStackSize() - menu.getCarried().count(), amount);
            int extracted = resourceHandler.extract(itemResource, limit, transaction);
            if (menu.getCarried().isEmpty()) {
                menu.setCarried(itemResource.toStack(extracted));
            }
            if (itemResource.equals(ItemResource.of(menu.getCarried()))) {
                menu.getCarried().setCount(menu.getCarried().count() + extracted);
            }
            transaction.commit();
            menu.broadcastChanges();
            return -extracted;
        }
    }


    public static ItemResourceWithCount insertItemFromPlayerHand(ServerPlayer serverPlayer, ResourceHandler<ItemResource> resourceHandler) {
        AbstractContainerMenu menu = serverPlayer.containerMenu;
        try (Transaction transaction = Transaction.openRoot()) {
            ItemResource itemResource = ItemResource.of(menu.getCarried());
            int inserted = resourceHandler.insert(itemResource, menu.getCarried().count(), transaction);
            menu.getCarried().shrink(inserted);
            transaction.commit();
            menu.broadcastChanges();
            return ItemResourceWithCount.of(itemResource,inserted);
        }
    }
}
