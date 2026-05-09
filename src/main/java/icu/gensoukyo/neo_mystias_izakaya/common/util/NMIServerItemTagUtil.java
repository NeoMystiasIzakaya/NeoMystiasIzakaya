package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.world.item.ItemStack;

public final class NMIServerItemTagUtil {

    public static ItemTagList get(ItemStack stack) {
        ItemTagList itemTagList = stack.get(NMIDataComponentTypes.ITEM_TAG_LIST);
        if (itemTagList != null) {
            return itemTagList;
        }
        ItemTagList tagsForItem = NMIDataAccessor.server().getTagItemListMap().getTagsForItem(NMICommonItemStackUtil.get(stack));
        return tagsForItem == null ? ItemTagList.EMPTY : tagsForItem;
    }

    public static ItemTagList copy(ItemStack stack) {
        ItemTagList itemTagList = stack.get(NMIDataComponentTypes.ITEM_TAG_LIST);
        if (itemTagList != null) {
            return itemTagList;
        }
        ItemTagList tagsForItem = NMIDataAccessor.server().getTagItemListMap().getTagsForItem(NMICommonItemStackUtil.get(stack));
        return tagsForItem == null ? ItemTagList.EMPTY : tagsForItem.copy();
    }

    public static void set(ItemStack stack, ItemTagList itemTagList) {
        stack.set(NMIDataComponentTypes.ITEM_TAG_LIST, itemTagList.sort());
    }
}
