package icu.gensoukyo.neo_mystias_izakaya.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.world.item.ItemStack;

public class NMIItemTagUtil {

    public static ItemTagList serverGet(ItemStack stack) {
        ItemTagList itemTagList = stack.get(NMIDataComponentTypes.ITEM_TAG_LIST);
        if (itemTagList != null) {
            return itemTagList;
        }
        ItemTagList tagsForItem = NMIDataAccessor.server().getTagItemListMap().getTagsForItem(NMIItemStackUtil.get(stack));
        return tagsForItem == null ? ItemTagList.EMPTY : tagsForItem;
    }

    public static ItemTagList clientGet(ItemStack stack) {
        ItemTagList itemTagList = stack.get(NMIDataComponentTypes.ITEM_TAG_LIST);
        if (itemTagList != null) {
            return itemTagList;
        }
        ItemTagList tagsForItem = NMIDataAccessor.client().getTagItemListMap().getTagsForItem(NMIItemStackUtil.get(stack));
        return tagsForItem == null ? ItemTagList.EMPTY : tagsForItem;
    }
}
