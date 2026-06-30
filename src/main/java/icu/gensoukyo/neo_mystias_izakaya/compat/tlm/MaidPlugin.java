/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.ExtraMaidBrainManager;
import com.github.tartaricacid.touhoulittlemaid.entity.task.TaskManager;
import icu.gensoukyo.neo_mystias_izakaya.compat.tlm.ai.MystiaBeverageTask;
import icu.gensoukyo.neo_mystias_izakaya.compat.tlm.ai.MystiaCuisineTask;
import icu.gensoukyo.neo_mystias_izakaya.compat.tlm.ai.MystiaTask;

@LittleMaidExtension
public class MaidPlugin implements ILittleMaid {
    @Override
    public void addMaidTask(TaskManager manager) {
        manager.add(new MystiaTask());
        manager.add(new MystiaCuisineTask());
        manager.add(new MystiaBeverageTask());
    }

    @Override
    public void addExtraMaidBrain(ExtraMaidBrainManager manager) {
        manager.addExtraMaidBrain(new MaidExtraBrain());
    }
}
