package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BeverageSlot extends Slot {
    public BeverageSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        if (!(this.container instanceof DiningTableBlockEntity diningTable) || !diningTable.isOccupied()) {
            return false;
        }
        ItemTagList itemTagList = NMICommonItemTagUtil.get(itemStack);
        return itemTagList != null && itemTagList.hasBeveragesTag();
    }
}
