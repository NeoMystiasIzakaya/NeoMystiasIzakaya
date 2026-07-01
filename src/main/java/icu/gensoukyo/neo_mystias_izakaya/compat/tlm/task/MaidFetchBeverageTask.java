/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBrains;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Optional;

/**
 * 女仆从橱柜取出饮品并导航到目标餐桌。
 * <p>
 * 流程：找需上酒的桌子 → 找橱柜取饮品 → 导航到餐桌 → 交给 {@link MaidDeliverBeverageTask}
 */
public class MaidFetchBeverageTask extends MaidCheckRateTask {

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

        CanteenControllerBlockEntity controller = MaidTaskUtil.getController(level, maid);
        if (controller == null) return false;

        // 阶段1：找一张需要上酒的餐桌
        Optional<BlockPos> tableMem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        BlockPos tablePos;
        if (tableMem.isPresent()) {
            tablePos = tableMem.get();
            DiningTableBlockEntity dt = MaidTaskUtil.getTargetTable(level, maid);
            if (dt != null && !dt.getBeverage().isEmpty()) {
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
        Identifier beverageId = getRequiredBeverageId(level, tablePos);
        if (beverageId == null) return false;
        if (!MaidTaskUtil.hasItemInTaskInv(maid, beverageId)) {
            BlockPos cupboardPos = MaidTaskUtil.findCupboardWithItem(level, controller, beverageId);
            if (cupboardPos == null) return false;

            if (MaidTaskUtil.isCloseEnough(maid, cupboardPos)) {
                MaidTaskUtil.extractFromCupboard(level, cupboardPos, beverageId, maid);
                MaidTaskUtil.setNavigateTo(maid, tablePos);
            } else {
                MaidTaskUtil.setNavigateTo(maid, cupboardPos);
            }
            this.setNextCheckTickCount(5);
            return false;
        }

        // 阶段3：已持有饮品，导航到餐桌
        if (MaidTaskUtil.isCloseEnough(maid, tablePos)) {
            maid.getBrain().setMemory(InitBrains.TARGET_POS.get(), new BlockPosTracker(tablePos));
            return true;
        }
        MaidTaskUtil.setNavigateTo(maid, tablePos);
        this.setNextCheckTickCount(5);
        return false;
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        MaidTaskUtil.clearFetchStartMemories(maid);
    }

    // ==================== 业务方法 ====================

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

    private static Identifier getRequiredBeverageId(ServerLevel level, BlockPos tablePos) {
        if (level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table) {
            return table.getCurrentOrder().beverage();
        }
        return null;
    }
}
