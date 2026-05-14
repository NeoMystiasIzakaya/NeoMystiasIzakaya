/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareMenu;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AbstractKitchenwareBE extends RandomizableContainerBlockEntity {
    /**
     * 0-4为食材格
     * 5为结果格
     * 6为保存烹饪过程中的目标物品
     */
    NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);
    @Setter
    private int cookingTime = 0;
    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return AbstractKitchenwareBE.this.cookingTime;
        }

        @Override
        public void set(int pIndex, int pValue) {
            AbstractKitchenwareBE.this.cookingTime = pValue;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public AbstractKitchenwareBE(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, AbstractKitchenwareBE pBlockEntity) {
        if (pState.getValue(BlockStateProperties.LIT)) {
            pBlockEntity.cookingTime--;
            if (pBlockEntity.cookingTime <= 0) {
                pBlockEntity.cookingTime = 0;
                ItemStack copy = pBlockEntity.getTargetItem().copy();
                pBlockEntity.getItems().clear();
                pBlockEntity.setResultItem(copy);
                pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.LIT, false), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerID, Inventory inventory) {
        return new KitchenwareMenu(containerID, inventory, this.getBlockPos(), dataAccess);
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = NonNullList.copyOf(nonNullList);
    }

    @Override
    public int getContainerSize() {
        return 7;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
        this.cookingTime = input.getIntOr("CookingTime", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt("CookingTime", this.cookingTime);
    }

    public boolean canStartCooking() {
        return this.getTargetItem().isEmpty() && this.getResultItem().isEmpty() && !this.getBlockState().getValue(BlockStateProperties.LIT);
    }

    public ItemStack getTargetItem() {
        return items.get(6);
    }

    public void setTargetItem(ItemStack stack) {
        items.set(6, stack);
    }

    public ItemStack getResultItem() {
        return items.get(5);
    }

    public void setResultItem(ItemStack stack) {
        items.set(5, stack);
    }

    public abstract KitchenwareMenu.KitchenwareType getKitchenwareType();
}
