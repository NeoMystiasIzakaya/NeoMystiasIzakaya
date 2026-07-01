/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

/**
 * 女仆将饮品送达目标餐桌。
 * <p>
 * 前置条件：{@link MaidFetchBeverageTask} 已设置 {@code TARGET_POS} 且女仆持有所需饮品。
 */
public class MaidDeliverBeverageTask extends MaidCheckRateTask {

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
        boolean ready = MaidTaskUtil.checkDeliverConditions(level, maid, false);
        if (!ready) this.setNextCheckTickCount(5);
        return ready;
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        MaidTaskUtil.doDeliver(level, maid, false);
    }
}
