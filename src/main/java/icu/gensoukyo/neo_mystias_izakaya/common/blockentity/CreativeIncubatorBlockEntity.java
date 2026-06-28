/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.api.common.ICupboard;
import icu.gensoukyo.neo_mystias_izakaya.api.common.IIncubator;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.InfiniteItemResourceHandler;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.ArrayList;
import java.util.List;

public class CreativeIncubatorBlockEntity extends BlockEntity implements IIncubator {

    private final InfiniteItemResourceHandler infiniteItemResourceHandler;

    public CreativeIncubatorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.CREATIVE_INCUBATOR.get(), worldPosition, blockState);

        this.infiniteItemResourceHandler = InfiniteItemResourceHandler.withDeferredItem(NMICuisinesItems.ITEM_LIST);
    }

    public ResourceHandler<ItemResource> getItemHandler() {
        return infiniteItemResourceHandler;
    }

}
