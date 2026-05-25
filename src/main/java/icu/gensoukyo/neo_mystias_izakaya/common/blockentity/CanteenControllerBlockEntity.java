/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
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
import java.util.List;

public class CanteenControllerBlockEntity extends BlockEntity {
    private List<BlockPos> kitchenwareList = new ArrayList<>();
    private List<BlockPos> dingingTableList = new ArrayList<>();
    private boolean isOpen;
    public CanteenControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NMIBlockEntities.COUNTER.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("kitchenware", BlockPos.CODEC.listOf(), this.kitchenwareList);
        output.store("diningTable", BlockPos.CODEC.listOf(), this.dingingTableList);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.kitchenwareList = input.read("kitchenware", BlockPos.CODEC.listOf()).orElse(List.of());
        this.dingingTableList = input.read("diningTable", BlockPos.CODEC.listOf()).orElse(List.of());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), NeoMystiasIzakaya.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            output.store("kitchenware", BlockPos.CODEC.listOf(), this.kitchenwareList);
            output.store("diningTable", BlockPos.CODEC.listOf(), this.dingingTableList);
            return output.buildResult();
        }
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CanteenControllerBlockEntity pBlockEntity) {

    }
}
