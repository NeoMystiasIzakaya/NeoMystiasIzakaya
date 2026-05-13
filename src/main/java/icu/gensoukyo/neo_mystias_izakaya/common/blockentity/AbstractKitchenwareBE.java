/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AbstractKitchenwareBE extends RandomizableContainerBlockEntity {
    NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);

    public AbstractKitchenwareBE(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerID, Inventory inventory) {
        return new KitchenwareMenu(containerID, inventory, this.getBlockPos());
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = NonNullList.copyOf(nonNullList);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
    }

    public abstract KitchenwareMenu.KitchenwareType getKitchenwareType();
}
