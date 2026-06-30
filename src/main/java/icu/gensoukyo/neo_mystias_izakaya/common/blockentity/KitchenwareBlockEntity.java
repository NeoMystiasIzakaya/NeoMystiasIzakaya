/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.menu.KitchenwareMenu;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.Kitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
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
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import org.slf4j.Logger;

import java.util.List;

public class KitchenwareBlockEntity extends RandomizableContainerBlockEntity {

    private static final Logger LOGGER = LogUtils.getLogger();
    /**
     * 0-4为食材格
     * 5为结果格
     * 6为保存烹饪过程中的目标物品
     */
    NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);


    private final ResourceHandler<ItemResource> itemStacksResourceHandler= VanillaContainerWrapper.of(this);

    @Setter
    private int cookingTime = 0;
    @Setter
    private int totalCookingTime = 0;
    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> KitchenwareBlockEntity.this.cookingTime;
                case 1 -> KitchenwareBlockEntity.this.totalCookingTime;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> KitchenwareBlockEntity.this.cookingTime = pValue;
                case 1 -> KitchenwareBlockEntity.this.totalCookingTime = pValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    @Getter
    private Identifier kitchenwareTypeId;

    public KitchenwareBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }
    public KitchenwareBlockEntity(Identifier kitchenwareTypeId, BlockPos worldPosition, BlockState blockState) {
        super(NMIKitchenware.REGISTRY.getValue(kitchenwareTypeId).blockEntityType(), worldPosition, blockState);
        this.kitchenwareTypeId = kitchenwareTypeId;
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, KitchenwareBlockEntity pBlockEntity) {
        if (pState.getValue(BlockStateProperties.LIT)) {
            pBlockEntity.cookingTime--;
            ServerPayloadSender.sendKitchenwareSyncMessage(pBlockEntity.getBlockPos(), pBlockEntity.cookingTime);
            if (pBlockEntity.cookingTime <= 0) {
                pBlockEntity.cookingTime = 0;
                ItemStack copy = pBlockEntity.getTargetItem().copy();
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
    public void setItems(NonNullList<ItemStack> itemStacks) {
        if (itemStacks.size() != 7) {
            LOGGER.error("Attempted to set items with a list of size {}, but expected size is 7. This may cause unexpected behavior.", itemStacks.size());
        }
        this.items = itemStacks;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerID, Inventory inventory) {
        return new KitchenwareMenu(containerID, inventory, this.getBlockPos(), dataAccess);
    }

    public void setIngredients(NonNullList<ItemStack> itemStacks) {
        if (itemStacks.size() != 5) {
            LOGGER.error("Attempted to set ingredient items with a list of size {}, but expected size is 5. This may cause unexpected behavior.", itemStacks.size());
        }
        for (int i = 0; i < 5; i++) {
            this.items.set(i, i < itemStacks.size() ? itemStacks.get(i).copy() : ItemStack.EMPTY);
        }
    }

    @Override
    public int getContainerSize() {
        return 7;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items.clear();
        ContainerHelper.loadAllItems(input, this.items);
        this.cookingTime = input.getIntOr("CookingTime", 0);
        this.totalCookingTime = input.getIntOr("TotalCookingTime", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt("CookingTime", this.cookingTime);
        output.putInt("TotalCookingTime", this.totalCookingTime);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(kitchenwareTypeId.toLanguageKey("blockentity"));
    }

    public boolean canStartCooking() {
        return this.getTargetItem().isEmpty() && this.getResultItem().isEmpty() && !this.getBlockState().getValue(BlockStateProperties.LIT);
    }

    public float getCookingProgress() {
        return (float) this.cookingTime / (float) this.totalCookingTime;
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

    public List<ItemStack> getIngredientItems() {
        return items.subList(0, 5);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), NeoMystiasIzakaya.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            ContainerHelper.saveAllItems(output, this.items);
            output.putInt("CookingTime", this.cookingTime);
            output.putInt("TotalCookingTime", this.totalCookingTime);
            return output.buildResult();
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            this.getIngredientItems().forEach(stack -> Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), stack));
            Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), this.getResultItem());
        }
    }

    public ResourceHandler<ItemResource> getItemHandler() {
        return itemStacksResourceHandler;
    }

    public static class BoilingPot extends KitchenwareBlockEntity {
        public BoilingPot(BlockPos worldPosition, BlockState blockState) {
            super(Kitchenware.BOILING_POT_ID, worldPosition, blockState);
        }
    }
    public static class FryingPan extends KitchenwareBlockEntity {
        public FryingPan(BlockPos worldPosition, BlockState blockState) {
            super(Kitchenware.FRYING_PAN_ID, worldPosition, blockState);
        }
    }
    public static class CuttingBoard extends KitchenwareBlockEntity {
        public CuttingBoard(BlockPos worldPosition, BlockState blockState) {
            super(Kitchenware.CUTTING_BOARD_ID, worldPosition, blockState);
        }
    }
    public static class Grill extends KitchenwareBlockEntity {
        public Grill(BlockPos worldPosition, BlockState blockState) {
            super(Kitchenware.GRILL_ID, worldPosition, blockState);
        }
    }
    public static class Steamer extends KitchenwareBlockEntity {
        public Steamer(BlockPos worldPosition, BlockState blockState) {
            super(Kitchenware.STEAMER_ID, worldPosition, blockState);
        }
    }
}
