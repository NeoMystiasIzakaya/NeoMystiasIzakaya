package icu.gensoukyo.neo_mystias_izakaya.client.render;

import icu.gensoukyo.neo_mystias_izakaya.registry.ModelLayersRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class NMIArmorRender implements IClientItemExtensions {
    @Override
    public HumanoidModel<?> getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayersRegistry.MYSTIAS_HAT);
    }
}
