/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.api.entity.ai.IExtraMaidBrain;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.List;

public class MaidExtraBrain implements IExtraMaidBrain {
    public List<MemoryModuleType<?>> getExtraMemoryTypes() {
        return List.of(NMIMemoryTypes.TARGET_POS.get());
    }
}
