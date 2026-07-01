/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jade;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.block.KitchenwareBlock;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.client.KitchenwareComponentProvider;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.server.KitchenwareServerDataProvider;
import net.minecraft.resources.Identifier;
import snownee.jade.api.*;

@WailaPlugin
public class MystiaJadePlugin implements IWailaPlugin {


    public static final Identifier KITCHENWARE_DATA_PROVIDER = NeoMystiasIzakaya.id("kitchenware_data_provider");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(KitchenwareServerDataProvider.INSTANCE, KitchenwareBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(KitchenwareComponentProvider.INSTANCE,KitchenwareBlock.class);
    }
}
