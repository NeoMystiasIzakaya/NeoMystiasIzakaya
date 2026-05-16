package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NMIServerEconomyUtil {

    public static int getPrice(Identifier itemId) {
        return NMIDataAccessor.server().getEconomyMap().getItemPriceMap().getOrDefault(itemId, 0);
    }

    public static int getPrice(Item item) {
        return getPrice(NMICommonItemUtil.get(item));
    }

    public static int getItemStackPrice(ItemStack itemStack) {
        return getPrice(NMICommonItemUtil.get(itemStack.getItem())) * itemStack.getCount();
    }
}
