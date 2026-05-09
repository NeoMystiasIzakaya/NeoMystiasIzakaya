/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class NMICommonItemStackUtil {

    public static Identifier get(ItemStack stack) {
        return NMICommonItemUtil.get(stack.getItem());
    }

    public static ItemStack get(Identifier id, int count) {
        return new ItemStack(NMICommonItemUtil.mustGet(id), count);
    }

    public static ItemStack get(Identifier id) {
        return get(id, 1);
    }
}
