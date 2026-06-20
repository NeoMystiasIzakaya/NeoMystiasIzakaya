/*
 * Copyright 2026 Kaguya154
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.kaguya;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value = Kaguya.MODID)
public class Kaguya {
    public static final String MODID = "kaguya";
    public static final Logger LOGGER = LogUtils.getLogger();
    public Kaguya(IEventBus modEventBus, ModContainer modContainer) {

    }
    public static Identifier id(String key) {
        return Identifier.fromNamespaceAndPath(MODID, key);
    }

    public static String ids(String key) {
        return Identifier.fromNamespaceAndPath(MODID, key).toString();
    }

    public static String path(String path) {
        return MODID + "/" + path;
    }
}
