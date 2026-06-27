/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import icu.gensoukyo.neo_mystias_izakaya.api.common.ICupboard;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.transfer.CombinedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.PlayerInventoryWrapper;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.*;

public final class CupBoardUtil {

    public static ResourceHandler<ItemResource> getItemResourceHandler(ServerPlayer serverPlayer) {
        ItemStack hat = serverPlayer.getItemBySlot(EquipmentSlot.HEAD);
        if (!hat.is(NMIMainItems.MYSTIAS_HAT)) return new ItemStacksResourceHandler(0);
        CanteenConfigData data = hat.get(NMIDataComponentTypes.CANTEEN_CONFIG);
        if (data == null) return new ItemStacksResourceHandler(0);
        ServerLevel serverLevel = serverPlayer.level();
        List<BlockPos> blockPosList = data.cupboardList();
        List<ResourceHandler<ItemResource>> resourceHandlerList = new ArrayList<>();
        for (BlockPos blockPos : blockPosList) {
            if (serverLevel.isLoaded(blockPos) && serverLevel.getBlockEntity(blockPos) instanceof ICupboard entity) {
                resourceHandlerList.add(entity.getItemHandler());
            }
        }
        return new CombinedResourceHandler<>(resourceHandlerList);
    }

    public static List<ItemResourceWithCount> extractBeveragesItemResourceList(ServerPlayer serverPlayer) {
        return extractIngredientItemResourceList(getItemResourceHandler(serverPlayer));
    }

    public static List<ItemResourceWithCount> extractBeveragesItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.BEVERAGES);
    }

    public static List<ItemResourceWithCount> extractIngredientItemResourceList(ServerPlayer serverPlayer) {
        return extractIngredientItemResourceList(getItemResourceHandler(serverPlayer));
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
        return itemResourceLongHashMap.keySet().stream().filter(i->i.is(tagKey)).map(itemResource -> new ItemResourceWithCount(itemResource, itemResourceLongHashMap.get(itemResource))).toList();
    }

    public static void extractMenuIngredientToKitchenware(ServerPlayer serverPlayer, int menuId, BlockPos kitchenwarePos) {
        ServerLevel serverLevel = serverPlayer.level();
        IzakayaMenu menu = NMICommonIzakayaUtil.getMenu(serverPlayer);
        Identifier cuisineId = menu.cuisines().get(menuId);
        NMIRecipeHolder holder = NMIDataAccessor.server().getRecipeMap().getRecipeMap().get(cuisineId);
        if (holder == null) {
            return;
        }
        List<Ingredient> input = holder.recipe().input();

        if (!serverLevel.isLoaded(kitchenwarePos) || !(serverLevel.getBlockEntity(kitchenwarePos) instanceof KitchenwareBlockEntity kitchenwareBlockEntity)) {
            return;
        }


        PlayerInventoryWrapper playerResourceHandler = PlayerInventoryWrapper.of(serverPlayer);
        ResourceHandler<ItemResource> kitchenwareResourceHandler = kitchenwareBlockEntity.getItemHandler();
        ResourceHandler<ItemResource> combinedResourceHandler = new CombinedResourceHandler<>(getItemResourceHandler(serverPlayer),
                playerResourceHandler,
                kitchenwareResourceHandler);

        List<ItemResource> extractedList = new ArrayList<>(5);
        try (Transaction transaction = Transaction.openRoot()) {
            for (int i = 0; i < input.size(); i++) {
                HolderSet<Item> values = input.get(i).getValues();
                for (Holder<Item> value : values) {
                    if (combinedResourceHandler.extract(ItemResource.of(value), 1, transaction) == 1) {
                        extractedList.add(ItemResource.of(value));
                        break;
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                boolean equals = i < extractedList.size() && ItemResource.of(kitchenwareBlockEntity.getItems().get(i)).equals(extractedList.get(i));
                if (!equals && !kitchenwareBlockEntity.getItems().get(i).isEmpty()) {
                    ItemResource resource = kitchenwareResourceHandler.getResource(i);
                    int extracted = kitchenwareResourceHandler.extract(i, resource, kitchenwareResourceHandler.getAmountAsInt(i), transaction);
                    playerResourceHandler.insert(resource, extracted, transaction);
                }
                if (!equals && i < extractedList.size() && extractedList.get(i) != null) {
                    kitchenwareResourceHandler.insert(extractedList.get(i), 1, transaction);
                    ServerPayloadSender.sendCupBoardItemResourceConsumedMessage(serverPlayer, extractedList.get(i));
                }
            }

            transaction.commit();
        }
    }

    public static void extractItemToKitchenware(ServerPlayer serverPlayer, ItemResource itemResource, BlockPos kitchenwarePos) {
        ServerLevel serverLevel = serverPlayer.level();
        if (!serverLevel.isLoaded(kitchenwarePos) || !(serverLevel.getBlockEntity(kitchenwarePos) instanceof KitchenwareBlockEntity kitchenwareBlockEntity)) {
            return;
        }

        PlayerInventoryWrapper playerResourceHandler = PlayerInventoryWrapper.of(serverPlayer);
        ResourceHandler<ItemResource> kitchenwareResourceHandler = kitchenwareBlockEntity.getItemHandler();
        ResourceHandler<ItemResource> combinedResourceHandler = new CombinedResourceHandler<>(getItemResourceHandler(serverPlayer),
                playerResourceHandler);

        try (Transaction transaction = Transaction.openRoot()) {
            if (combinedResourceHandler.extract(itemResource, 1, transaction) == 1) {
                int inserted = kitchenwareResourceHandler.insert(itemResource, 1, transaction);
                if (inserted == 0) {
                    transaction.close();
                    return;
                }
            }
            transaction.commit();
            ServerPayloadSender.sendCupBoardItemResourceConsumedMessage(serverPlayer, itemResource);
        }
    }
}
