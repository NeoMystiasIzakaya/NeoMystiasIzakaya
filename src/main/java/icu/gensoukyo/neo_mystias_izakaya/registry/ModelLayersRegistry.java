/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModelLayersRegistry {
    public static final ModelLayerLocation MYSTIAS_HAT;

    static {
        MYSTIAS_HAT = register("mystias_hat");
    }

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(NeoMystiasIzakaya.id(name), name);
    }
}
