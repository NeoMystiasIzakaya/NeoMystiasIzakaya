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
import icu.gensoukyo.neo_mystias_izakaya.compat.tlm.event.MaidCanteenInteractEvent;
import net.neoforged.neoforge.common.NeoForge;

@LittleMaidExtension
public class MaidPlugin implements ILittleMaid {

    public MaidPlugin() {
        // 在 TLM 扩展初始化时注册事件监听器，确保 TLM 存在时才加载
        NeoForge.EVENT_BUS.register(new MaidCanteenInteractEvent());
    }

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
