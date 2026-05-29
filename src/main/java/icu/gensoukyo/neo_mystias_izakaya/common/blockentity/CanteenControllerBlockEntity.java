/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CanteenControllerBlockEntity extends BlockEntity {
    @Getter
    private LinkedHashSet<BlockPos> kitchenwareList = new LinkedHashSet<>();
    private LinkedHashSet<BlockPos> dingingTableList = new LinkedHashSet<>();
    @Getter
    private boolean isOpen;
    public CanteenControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NMIBlockEntities.COUNTER.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("kitchenware", BlockPos.CODEC.listOf(), new ArrayList<>(this.kitchenwareList));
        output.store("diningTable", BlockPos.CODEC.listOf(), new ArrayList<>(this.dingingTableList));
        output.putBoolean("isOpen", this.isOpen);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.kitchenwareList = new LinkedHashSet<>(input.read("kitchenware", BlockPos.CODEC.listOf()).orElse(List.of()));
        this.dingingTableList = new LinkedHashSet<>(input.read("diningTable", BlockPos.CODEC.listOf()).orElse(List.of()));
        this.isOpen = input.getBooleanOr("isOpen", false);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), NeoMystiasIzakaya.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            output.store("kitchenware", BlockPos.CODEC.listOf(), new ArrayList<>(this.kitchenwareList));
            output.store("diningTable", BlockPos.CODEC.listOf(), new ArrayList<>(this.dingingTableList));
            output.putBoolean("isOpen", this.isOpen);
            tag.merge(output.buildResult());
        }
        return tag;
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CanteenControllerBlockEntity pBlockEntity) {

    }

    // === 绑定管理 ===

    /**
     * 添加厨房用具到关联列表（LinkedHashSet 自动去重）
     */
    public boolean addKitchenware(BlockPos pos) {
        boolean added = kitchenwareList.add(pos);
        if (added) markUpdated();
        return added;
    }

    /**
     * 移除厨房用具关联
     */
    public boolean removeKitchenware(BlockPos pos) {
        boolean removed = kitchenwareList.remove(pos);
        if (removed) markUpdated();
        return removed;
    }

    /**
     * 添加餐桌到关联列表（LinkedHashSet 自动去重）
     */
    public boolean addDiningTable(BlockPos pos) {
        boolean added = dingingTableList.add(pos);
        if (added) markUpdated();
        return added;
    }

    /**
     * 移除餐桌关联
     */
    public boolean removeDiningTable(BlockPos pos) {
        boolean removed = dingingTableList.remove(pos);
        if (removed) markUpdated();
        return removed;
    }

    public LinkedHashSet<BlockPos> getDiningTableList() {
        return dingingTableList;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        markUpdated();
    }

    private void markUpdated() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }
}
