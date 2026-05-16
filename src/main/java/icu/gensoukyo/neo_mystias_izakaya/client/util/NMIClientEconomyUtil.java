package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonItemUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NMIClientEconomyUtil {

    public static Integer getPrice(Identifier itemId) {
        return NMIDataAccessor.server().getEconomyMap().getItemPriceMap().get(itemId);
    }

    public static Integer getItemPrice(Item item) {
        return getPrice(NMICommonItemUtil.get(item));
    }

    public static Integer getItemStackPriceBase(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 0;
        }
        Integer i = itemStack.get(NMIDataComponentTypes.PRICE);
        if (i!=null){
            return i;
        }
        return getPrice(NMICommonItemUtil.get(itemStack.getItem()));
    }
    public static Integer getItemStackPrice(ItemStack itemStack) {
        return getItemStackPriceBase(itemStack) * itemStack.getCount();
    }

    public static void setItemStackPrice(ItemStack itemStack, Integer price) {
        itemStack.set(NMIDataComponentTypes.PRICE, price);
    }
}
