/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jade.client;

import icu.gensoukyo.neo_mystias_izakaya.compat.jade.MystiaJadePlugin;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.server.KitchenwareData;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.server.KitchenwareServerDataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.Element;
import snownee.jade.api.ui.JadeUI;
import snownee.jade.api.ui.ResizeableElement;
import snownee.jade.api.view.ProgressView;

import java.util.Optional;

public class KitchenwareComponentProvider implements IBlockComponentProvider {

    public static final KitchenwareComponentProvider INSTANCE = new KitchenwareComponentProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        Optional<KitchenwareData> data = KitchenwareServerDataProvider.INSTANCE.decodeFromData(blockAccessor);
        if(data.isEmpty())return;
        KitchenwareData kitchenwareData = data.get();


        if (kitchenwareData.target().isPresent()) {
            Element item = JadeUI.item(kitchenwareData.target().get());
            iTooltip.add(item);
        }
        float v;
        if(kitchenwareData.totalCookingTime()!=0) {
            v = ((float) kitchenwareData.totalCookingTime() - kitchenwareData.cookingTime()) / kitchenwareData.totalCookingTime();
        }else{
            v = 0;
        }

        ResizeableElement progress = JadeUI.progress(new ProgressView(ProgressView.Part.of(v), Component.translatable("gui.neo_mystias_izakaya.progress"), JadeUI.progressStyle(), BoxStyle.nestedBox()));
        iTooltip.add(progress);
    }

    @Override
    public Identifier getUid() {
        return MystiaJadePlugin.KITCHENWARE_DATA_PROVIDER;
    }
}
