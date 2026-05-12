/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public abstract class AbstractKitchenwareBE extends RandomizableContainerBlockEntity {
    NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);

    public AbstractKitchenwareBE(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerID, Inventory inventory) {
        return new KitchenwareMenu(containerID, inventory, this.getBlockPos());
    }

    @Override
    protected void setItems(@NonNull NonNullList<ItemStack> nonNullList) {
        this.items = NonNullList.copyOf(nonNullList);
    }

    @Override
    public int getContainerSize() {
        return 7;
    }
}
