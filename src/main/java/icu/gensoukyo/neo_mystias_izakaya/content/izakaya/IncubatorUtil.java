/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import icu.gensoukyo.neo_mystias_izakaya.api.common.IIncubator;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ResourceHandlerUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.CombinedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

import java.util.ArrayList;
import java.util.List;

public final class IncubatorUtil {

    public static ResourceHandler<ItemResource> getItemResourceHandler(ServerPlayer serverPlayer) {
        ItemStack hat = serverPlayer.getItemBySlot(EquipmentSlot.HEAD);
        if (!hat.is(NMIMainItems.MYSTIAS_HAT)) return new ItemStacksResourceHandler(0);
        CanteenConfigData data = hat.get(NMIDataComponentTypes.CANTEEN_CONFIG);
        if (data == null) return new ItemStacksResourceHandler(0);
        ServerLevel serverLevel = serverPlayer.level();
        List<BlockPos> blockPosList = data.incubatorList();
        List<ResourceHandler<ItemResource>> resourceHandlerList = new ArrayList<>();
        for (BlockPos blockPos : blockPosList) {
            if (serverLevel.isLoaded(blockPos) && serverLevel.getBlockEntity(blockPos) instanceof IIncubator entity) {
                resourceHandlerList.add(entity.getItemHandler());
            }
        }
        return new CombinedResourceHandler<>(resourceHandlerList);
    }

    public static List<ItemResourceWithCount> extractCuisinesItemResourceList(ServerPlayer serverPlayer) {
        return ResourceHandlerUtil.extractCuisinesItemResourceList(getItemResourceHandler(serverPlayer));
    }

    public static void extractItemToPlayerHand(ServerPlayer serverPlayer, ItemResource itemResource) {
        int i = ResourceHandlerUtil.extractItemToPlayerHand(serverPlayer, itemResource, getItemResourceHandler(serverPlayer));
        ServerPayloadSender.sendIncubatorItemResourceUpdatedMessage(serverPlayer, ItemResourceWithCount.of(itemResource,i));
    }

    public static void insertItemFromPlayerHand(ServerPlayer serverPlayer){
        ItemResourceWithCount itemResource = ResourceHandlerUtil.insertItemFromPlayerHand(serverPlayer, getItemResourceHandler(serverPlayer));
        ServerPayloadSender.sendIncubatorItemResourceUpdatedMessage(serverPlayer, itemResource);
    }
}
