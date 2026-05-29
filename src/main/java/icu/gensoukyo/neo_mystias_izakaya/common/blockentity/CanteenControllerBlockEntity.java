/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class CanteenControllerBlockEntity extends BlockEntity {
    @Getter
    private LinkedHashSet<BlockPos> kitchenwareList = new LinkedHashSet<>();
    private LinkedHashSet<BlockPos> dingingTableList = new LinkedHashSet<>();
    @Getter
    private boolean isOpen;

    public CanteenControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NMIBlockEntities.COUNTER.get(), blockPos, blockState);
    }

    /** 每轮每张空闲餐桌有客入座的概率（0.0 ~ 1.0） */
    private static final float SEAT_CHANCE = 0.3F;
    /** 派发间隔（tick） */
    private static final int DISPATCH_INTERVAL = 100;

    /**
     * 主控 Tick：营业期间按概率向空闲餐桌派发订单；
     * 同时校验关联方块完整性，发现已破坏的则移除并重排序号。
     */
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CanteenControllerBlockEntity pBlockEntity) {
        if (!pBlockEntity.isOpen) return;

        // 每 5 秒（100 tick）尝试派发一次
        if (pLevel.getGameTime() % DISPATCH_INTERVAL != 0) return;

        // 校验关联方块完整性：餐桌和厨具
        boolean needsResync = pBlockEntity.validateLinkedBlocks(pLevel);

        // 为每张空闲餐桌按概率派发订单
        for (BlockPos tablePos : pBlockEntity.dingingTableList) {
            if (!pLevel.isLoaded(tablePos)) continue;
            if (!(pLevel.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table)) continue;
            if (!table.isIdle()) continue;

            // 按概率派发：只有命中概率才入座
            if (pLevel.getRandom().nextFloat() >= SEAT_CHANCE) continue;

            IzakayaOrder order = generateOrder(pBlockEntity);
            if (order != null) {
                Identifier customerId = NeoMystiasIzakaya.id("customer_" + table.getTableIndex() + "_" + pLevel.getGameTime());
                table.seatCustomer(customerId, order);
            }
        }
    }

    /**
     * 校验所有关联方块是否仍有效，移除已被破坏的，必要时重排序号
     * @return 是否有方块被移除
     */
    private boolean validateLinkedBlocks(Level pLevel) {
        boolean changed = dingingTableList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof DiningTableBlockEntity)
        );
        changed |= kitchenwareList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof AbstractKitchenwareBE)
        );

        if (changed) {
            syncTableIndices();
            markUpdated();
        }
        return changed;
    }

    /**
     * 生成新订单（TODO: 接入顾客系统与菜单系统）
     */
    private static @Nullable IzakayaOrder generateOrder(CanteenControllerBlockEntity controller) {
        // TODO: 根据 IzakayaMenu、当前解锁配方、稀客/普客概率生成订单
        return null;
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
     * 添加餐桌到关联列表并同步序号
     */
    public boolean addDiningTable(BlockPos pos) {
        boolean added = dingingTableList.add(pos);
        if (added) {
            syncTableIndices();
            markUpdated();
        }
        return added;
    }

    /**
     * 移除餐桌关联并同步序号
     */
    public boolean removeDiningTable(BlockPos pos) {
        // 先清除该餐桌的绑定信息
        if (level != null && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof DiningTableBlockEntity table) {
            table.unbindFromController();
        }
        boolean removed = dingingTableList.remove(pos);
        if (removed) {
            syncTableIndices();
            markUpdated();
        }
        return removed;
    }

    /**
     * 将所有餐桌序号（从 1 开始）同步到各自的 BlockEntity
     */
    private void syncTableIndices() {
        if (level == null || level.isClientSide()) return;
        int index = 1;
        for (BlockPos pos : dingingTableList) {
            if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof DiningTableBlockEntity table) {
                table.bindToController(index, this.worldPosition);
            }
            index++;
        }
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
