package icu.gensoukyo.neo_mystias_izakaya.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.world.item.ItemStack;

public class NMIItemTagUtil {

    private final NMIDataAccessor accessor;

    public NMIItemTagUtil(NMIDataAccessor accessor) {
        this.accessor = accessor;
    }

    private static final NMIItemTagUtil SERVER = new NMIItemTagUtil(NMIDataAccessor.server());
    private static final NMIItemTagUtil CLIENT = new NMIItemTagUtil(NMIDataAccessor.client());

    public static NMIItemTagUtil server() {
        return SERVER;
    }

    public static NMIItemTagUtil client() {
        return CLIENT;
    }

    public ItemTagList get(ItemStack stack) {
        return get(accessor, stack);
    }

    public static ItemTagList get(NMIDataAccessor accessor,ItemStack stack) {
        ItemTagList itemTagList = stack.get(NMIDataComponentTypes.ITEM_TAG_LIST);
        if (itemTagList != null) {
            return itemTagList;
        }
        ItemTagList tagsForItem = accessor.getTagItemListMap().getTagsForItem(NMIItemStackUtil.get(stack));
        return tagsForItem == null ? ItemTagList.EMPTY : tagsForItem;
    }

    public static ItemTagList copy(NMIDataAccessor accessor,ItemStack stack) {
        ItemTagList itemTagList = stack.get(NMIDataComponentTypes.ITEM_TAG_LIST);
        if (itemTagList != null) {
            return itemTagList;
        }
        ItemTagList tagsForItem = accessor.getTagItemListMap().getTagsForItem(NMIItemStackUtil.get(stack));
        return tagsForItem == null ? ItemTagList.EMPTY : tagsForItem.copy();
    }

    public static void set(ItemStack stack, ItemTagList itemTagList) {
        stack.set(NMIDataComponentTypes.ITEM_TAG_LIST, itemTagList.sort());
    }
}
