/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBrains;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.api.common.ICupboard;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.Optional;

/**
 * 女仆从橱柜取出饮品并导航到目标餐桌。
 * <p>
 * 流程：找需上酒的桌子 → 找橱柜取饮品 → 导航到餐桌 → 交给 {@link MaidDeliverBeverageTask}
 */
public class MaidFetchBeverageTask extends MaidCheckRateTask {
    private static final double CLOSE_ENOUGH = 2.5;
    private static final float SPEED = 0.6f;

    public MaidFetchBeverageTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                InitBrains.TARGET_POS.get(), MemoryStatus.VALUE_ABSENT
        ));
        this.setMaxCheckRate(10);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, EntityMaid maid) {
        if (!super.checkExtraStartConditions(level, maid) || !maid.canBrainMoving()) return false;

        // 必须有已分配的餐厅控制器
        Optional<BlockPos> controllerMem = maid.getBrain().getMemory(NMIMemoryTypes.CONTROLLER_POS.get());
        if (controllerMem.isEmpty()) return false;

        BlockPos controllerPos = controllerMem.get();
        BlockEntity be = level.getBlockEntity(controllerPos);
        if (!(be instanceof CanteenControllerBlockEntity controller)) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.CONTROLLER_POS.get());
            return false;
        }

        // 阶段1：找一张需要上酒的餐桌
        Optional<BlockPos> tableMem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        BlockPos tablePos;
        if (tableMem.isPresent()) {
            tablePos = tableMem.get();
            // 目标餐桌已完成（酒已上好），清除
            if (level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity dt && !dt.getBeverage().isEmpty()) {
                maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
                maid.getBrain().eraseMemory(InitBrains.TARGET_POS.get());
                return false;
            }
        } else {
            tablePos = findTableNeedingBeverage(level, controller);
            if (tablePos == null) return false;
            maid.getBrain().setMemory(NMIMemoryTypes.TARGET_POS.get(), tablePos);
        }

        // 阶段2：判断是否需要取饮品
        if (!maidHasBeverageFor(level, maid, tablePos)) {
            // 找到最近的存有所需饮品的橱柜
            Identifier beverageId = getRequiredBeverage(level, tablePos);
            if (beverageId == null) return false;
            BlockPos cupboardPos = findCupboardWithItem(level, controller, beverageId);
            if (cupboardPos == null) return false;

            if (isCloseEnough(maid, cupboardPos)) {
                // 到达橱柜 → 提取饮品 → 导航到餐桌
                extractBeverage(level, cupboardPos, beverageId, maid);
                setNavigateTo(maid, tablePos);
            } else {
                setNavigateTo(maid, cupboardPos);
            }
            this.setNextCheckTickCount(5);
            return false;
        }

        // 阶段3：已持有饮品，导航到餐桌
        if (isCloseEnough(maid, tablePos)) {
            maid.getBrain().setMemory(InitBrains.TARGET_POS.get(),
                    new net.minecraft.world.entity.ai.behavior.BlockPosTracker(tablePos));
            return true; // 触发 start()，移交 MaidDeliverBeverageTask
        }
        setNavigateTo(maid, tablePos);
        this.setNextCheckTickCount(5);
        return false;
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        // TARGET_POS 已设置，MaidDeliverBeverageTask 会接管
        maid.getBrain().eraseMemory(InitBrains.TARGET_POS.get());
        maid.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        maid.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    // ==================== 工具方法 ====================

    /** 找到第一张需要上酒的餐桌（跳过稀客，稀客的 beverage 是标签而非物品） */
    private static BlockPos findTableNeedingBeverage(ServerLevel level, CanteenControllerBlockEntity controller) {
        for (BlockPos pos : controller.getDiningTableList()) {
            if (level.getBlockEntity(pos) instanceof DiningTableBlockEntity table) {
                if (table.isOccupied() && table.getBeverage().isEmpty()
                        && !table.getCurrentOrder().isRare()) {
                    Identifier beverage = table.getCurrentOrder().beverage();
                    if (!beverage.equals(IzakayaOrder.EMPTY_ORDER)) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    /** 获取目标餐桌所需的饮品ID */
    private static Identifier getRequiredBeverage(ServerLevel level, BlockPos tablePos) {
        if (level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table) {
            return table.getCurrentOrder().beverage();
        }
        return null;
    }

    /** 检查女仆的 TaskInv 中是否已持有所需饮品 */
    private static boolean maidHasBeverageFor(ServerLevel level, EntityMaid maid, BlockPos tablePos) {
        Identifier required = getRequiredBeverage(level, tablePos);
        if (required == null) return false;
        Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(required);
        if (itemReference.isEmpty()) return false;
        Item targetItem = itemReference.get().value();

        var taskInv = maid.getItemManager().getTaskInv();
        for (int i = 0; i < taskInv.size(); i++) {
            ItemStack stack = taskInv.getResource(i).toStack();
            if (stack.is(targetItem)) return true;
        }
        return false;
    }

    /** 找到第一个存有指定物品的橱柜 */
    private static BlockPos findCupboardWithItem(ServerLevel level, CanteenControllerBlockEntity controller,
                                                  Identifier itemId) {
        Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(itemId);
        if (itemReference.isEmpty()) return null;
        Item targetItem = itemReference.get().value();

        for (BlockPos pos : controller.getCupboardList()) {
            if (level.getBlockEntity(pos) instanceof ICupboard cupboard) {
                if (cupboardHasItem(cupboard, targetItem)) {
                    return pos;
                }
            }
        }
        return null;
    }

    private static boolean cupboardHasItem(ICupboard cupboard, Item targetItem) {
        var handler = cupboard.getItemHandler();
        for (int i = 0; i < handler.size(); i++) {
            if (targetItem.getDefaultInstance().is(handler.getResource(i).getItem())) {
                return true;
            }
        }
        return false;
    }

    /** 从橱柜提取饮品放入女仆 TaskInv */
    private static void extractBeverage(ServerLevel level, BlockPos cupboardPos, Identifier beverageId, EntityMaid maid) {
        if (!(level.getBlockEntity(cupboardPos) instanceof ICupboard cupboard)) return;
        Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(beverageId);
        if (itemReference.isEmpty()) return;
        Item targetItem = itemReference.get().value();

        var cupboardHandler = cupboard.getItemHandler();
        Transaction tx = Transaction.openRoot();
        for (int i = 0; i < cupboardHandler.size(); i++) {
            var resource = cupboardHandler.getResource(i);
            if (targetItem.getDefaultInstance().is(cupboardHandler.getResource(i).getItem())) {
                cupboardHandler.extract(i, resource, 1, tx);
                tx.commit();

                // 放入女仆 TaskInv 的空位
                var taskInv = maid.getItemManager().getTaskInv();
                ItemResource beverageResource = ItemResource.of(new ItemStack(targetItem, 1));
                for (int j = 0; j < taskInv.size(); j++) {
                    if (taskInv.getResource(j).isEmpty()) {
                        taskInv.set(j, beverageResource, 1);
                        return;
                    }
                }
                return;
            }
        }
    }

    // ==================== 导航辅助 ====================

    private static boolean isCloseEnough(EntityMaid maid, BlockPos pos) {
        return pos.distToCenterSqr(maid.position()) < CLOSE_ENOUGH * CLOSE_ENOUGH;
    }

    private static void setNavigateTo(EntityMaid maid, BlockPos pos) {
        BehaviorUtils.setWalkAndLookTargetMemories(maid, pos, SPEED, 1);
    }
}
