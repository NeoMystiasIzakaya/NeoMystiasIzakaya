/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.reverie_dreams;


import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Objects;

@Mod(value = NeoMystiasIzakaya.MODID)
public class NeoMystiasIzakayaRDCompact {

    public NeoMystiasIzakayaRDCompact(IEventBus modEventBus, ModContainer modContainer) {
        NMIRDItemAlias.ITEMS.register(modEventBus);
    }
}
