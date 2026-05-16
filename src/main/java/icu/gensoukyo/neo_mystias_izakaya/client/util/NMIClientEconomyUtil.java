package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonItemUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NMIClientEconomyUtil {

    public static int getPrice(Identifier itemId) {
        return NMIDataAccessor.client().getEconomyMap().getItemPriceMap().get(itemId);
    }

    public static int getPrice(Item item) {
        return getPrice(NMICommonItemUtil.get(item));
    }

    public static int getItemStackPrice(ItemStack itemStack) {
        return getPrice(NMICommonItemUtil.get(itemStack.getItem())) * itemStack.getCount();
    }
}
