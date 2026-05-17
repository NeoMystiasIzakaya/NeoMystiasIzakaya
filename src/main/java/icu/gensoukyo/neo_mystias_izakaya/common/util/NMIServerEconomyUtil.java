package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMIServerEconomyUtil {

    public static @NotNull Integer getPriceWithDefault(Identifier itemId, int defaultPrice) {
        return NMIDataAccessor.server().getEconomyMap().getItemPriceMap().getOrDefault(itemId,defaultPrice);
    }

    public static @Nullable Integer getPrice(Identifier itemId) {
        return NMIDataAccessor.server().getEconomyMap().getItemPriceMap().get(itemId);
    }

    public static @NotNull Integer getPriceWithDefault(Item item,int defaultPrice) {
        return getPriceWithDefault(NMICommonItemUtil.get(item),defaultPrice);
    }

    public static @Nullable Integer getItemPrice(Item item) {
        return getPrice(NMICommonItemUtil.get(item));
    }

    public static  @NotNull Integer getItemStackPriceBaseWithDefault(ItemStack itemStack,int defaultPrice) {
        if (itemStack.isEmpty()) {
            return defaultPrice;
        }
        Integer i = itemStack.get(NMIDataComponentTypes.PRICE);
        if (i != null) {
            return i;
        }
        return getPriceWithDefault(NMICommonItemUtil.get(itemStack.getItem()),defaultPrice);
    }

    public static @Nullable Integer getItemStackPriceBase(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 0;
        }
        Integer i = itemStack.get(NMIDataComponentTypes.PRICE);
        if (i != null) {
            return i;
        }
        return getPrice(NMICommonItemUtil.get(itemStack.getItem()));
    }

    public static int getItemStackPriceWithDefault(ItemStack itemStack,int defaultPrice) {
        return getItemStackPriceBaseWithDefault(itemStack,defaultPrice) * itemStack.getCount();
    }


    public static int getItemStackPrice(ItemStack itemStack) {
        Integer i = getItemStackPriceBase(itemStack);
        if (i == null) {
            return 0;
        }
        return i * itemStack.getCount();
    }

    public static void setItemStackPrice(ItemStack itemStack, Integer price) {
        itemStack.set(NMIDataComponentTypes.PRICE, price);
    }
}
