/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.api.common.ICupboard;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.InfiniteItemResourceHandler;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
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

public class CreativeCupboardBlockEntity extends BlockEntity implements ICupboard {

    private final InfiniteItemResourceHandler infiniteItemResourceHandler;

    public CreativeCupboardBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.CREATIVE_CUPBOARD.get(), worldPosition, blockState);

        List<Item> itemList = new ArrayList<>();
        itemList.addAll(NMIIngredientItems.ITEM_LIST.stream().map(DeferredItem::get).toList());
        itemList.addAll(NMIBeveragesItems.ITEM_LIST.stream().map(DeferredItem::get).toList());
        itemList.add(Items.PUFFERFISH);
        itemList.add(Items.PUMPKIN);
        itemList.add(Items.KELP);
        itemList.add(Items.BROWN_MUSHROOM);
        itemList.add(Items.POTATO);
        itemList.add(Items.PORKCHOP);
        itemList.add(Items.BEEF);
        itemList.add(Items.EGG);
        itemList.add(Items.ICE);
        itemList.add(Items.HONEY_BOTTLE);
        itemList.add(Items.COCOA_BEANS);
        this.infiniteItemResourceHandler = InfiniteItemResourceHandler.withItem(itemList);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
    }

    public ResourceHandler<ItemResource> getItemHandler() {
        return infiniteItemResourceHandler;
    }

}
