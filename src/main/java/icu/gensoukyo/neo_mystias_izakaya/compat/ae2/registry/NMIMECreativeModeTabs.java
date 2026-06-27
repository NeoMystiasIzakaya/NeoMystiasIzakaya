/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMECreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB,NeoMystiasIzakaya.MODID);

    public static final Identifier AE2_ID = NeoMystiasIzakaya.id("ae2");

    public static final DeferredHolder<CreativeModeTab,?> AE2 = CREATIVE_MODE_TABS.register("ae2", () ->
            CreativeModeTab.builder()
                    .title(NMICommonComponentUtil.translatableItemGroup(AE2_ID))
                    .icon(()-> NMIMainItems.CHROME_BALL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        NMIMEItems.ITEMS.getEntries().forEach(
                                item -> output.accept(item.get())
                        );
                    })
                    .build()
    );
}
