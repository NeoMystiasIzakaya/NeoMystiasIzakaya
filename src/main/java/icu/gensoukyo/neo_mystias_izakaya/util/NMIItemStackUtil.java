package icu.gensoukyo.neo_mystias_izakaya.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class NMIItemStackUtil {

    public static Identifier get(ItemStack stack) {
        return NMIItemUtil.get(stack.getItem());
    }

    public static ItemStack get(Identifier id, int count) {
        return new ItemStack(NMIItemUtil.mustGet(id), count);
    }

    public static ItemStack get(Identifier id) {
        return get(id, 1);
    }
}
