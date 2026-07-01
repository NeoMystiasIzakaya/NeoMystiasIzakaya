/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.Optional;

/**
 * 女仆将手持饮品送达目标餐桌。
 * <p>
 * 前置条件：{@link MaidFetchBeverageTask} 已设置 {@code TARGET_POS} 且女仆持有所需饮品。
 */
public class MaidDeliverBeverageTask extends MaidCheckRateTask {
    private static final double CLOSE_ENOUGH = 2.5;
    private static final float SPEED = 0.6f;

    public MaidDeliverBeverageTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                NMIMemoryTypes.TARGET_POS.get(), MemoryStatus.VALUE_PRESENT
        ));
        this.setMaxCheckRate(5);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, EntityMaid maid) {
        if (!super.checkExtraStartConditions(level, maid) || !maid.canBrainMoving()) return false;

        Optional<BlockPos> tableMem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        if (tableMem.isEmpty()) return false;

        BlockPos tablePos = tableMem.get();
        if (!(level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table)) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
            return false;
        }

        // 餐桌已完成 → 无需再送
        if (!table.getBeverage().isEmpty() || !table.isOccupied()) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
            return false;
        }

        Identifier requiredId = table.getCurrentOrder().beverage();
        if (requiredId.equals(IzakayaOrder.EMPTY_ORDER) || table.getCurrentOrder().isRare()) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
            return false;
        }

        // 确认女仆仍持有所需饮品
        if (!hasBeverage(maid, requiredId)) {
            // 饮品丢失，让 MaidFetchBeverageTask 重新取
            return false;
        }

        // 导航或送达
        if (isCloseEnough(maid, tablePos)) {
            return true; // 触发 start() 执行送达
        }
        BehaviorUtils.setWalkAndLookTargetMemories(maid, tablePos, SPEED, 1);
        this.setNextCheckTickCount(5);
        return false;
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        Optional<BlockPos> tableMem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        if (tableMem.isEmpty()) return;

        BlockPos tablePos = tableMem.get();
        if (level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table) {
            Identifier requiredId = table.getCurrentOrder().beverage();
            Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(requiredId);
            if (itemReference.isEmpty()) return;
            Item targetItem = itemReference.get().value();

            // 从女仆库存取出饮品放上餐桌
            ItemStack beverage = extractBeverageFromMaid(maid, targetItem);
            if (!beverage.isEmpty()) {
                table.setBeverage(beverage);
            }
        }

        maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
        maid.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        maid.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    // ==================== 工具方法 ====================

    private static boolean isCloseEnough(EntityMaid maid, BlockPos pos) {
        return pos.distToCenterSqr(maid.position()) < CLOSE_ENOUGH * CLOSE_ENOUGH;
    }

    private static boolean hasBeverage(EntityMaid maid, Identifier beverageId) {
        Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(beverageId);
        if (itemReference.isEmpty()) return false;
        Item targetItem = itemReference.get().value();
        var taskInv = maid.getItemManager().getTaskInv();
        for (int i = 0; i < taskInv.size(); i++) {
            ItemStack stack = taskInv.getResource(i).toStack();
            if (stack.is(targetItem)) return true;
        }
        return false;
    }

    /** 从女仆 TaskInv 取出指定饮品 */
    private static ItemStack extractBeverageFromMaid(EntityMaid maid, Item targetItem) {
        var taskInv = maid.getItemManager().getTaskInv();
        for (int i = 0; i < taskInv.size(); i++) {
            ItemStack stack = taskInv.getResource(i).toStack();
            if (stack.is(targetItem)) {
                ItemStack result = stack.split(1);
                if (stack.isEmpty()) {
                    taskInv.set(i, ItemResource.EMPTY, 0);
                }
                return result;
            }
        }
        return ItemStack.EMPTY;
    }
}
