/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.render;

import icu.gensoukyo.neo_mystias_izakaya.client.model.MystiasHatModel;
import icu.gensoukyo.neo_mystias_izakaya.registry.ModelLayersRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.util.Lazy;

public class NMIArmorRender implements IClientItemExtensions {
    private static final Lazy<ModelPart> MODEL_PART = Lazy.of(() ->
            Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayersRegistry.MYSTIAS_HAT));

    @Override
    public EntityModel<?> getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
        return new MystiasHatModel(MODEL_PART.get());
    }
}
