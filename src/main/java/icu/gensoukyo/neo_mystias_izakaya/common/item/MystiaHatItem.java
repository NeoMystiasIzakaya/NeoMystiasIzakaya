package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIArmorUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

//TODO 帽子材质要做
public class MystiaHatItem extends Item {
    public MystiaHatItem(Properties properties) {
        super(properties.humanoidArmor(NMIArmorUtil.MYSTIAS_HAT, ArmorType.HELMET));
    }
}
