/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMEItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static final DeferredItem<BlockItem> ME_CUPBOARD = ITEMS.registerSimpleBlockItem("me_cupboard", NMIMEBlocks.ME_CUPBOARD);
    public static final DeferredItem<BlockItem> ME_INCUBATOR = ITEMS.registerSimpleBlockItem("me_incubator", NMIMEBlocks.ME_INCUBATOR);
}
