package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerRecipeUtil;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CuisineSlot extends Slot {
    public CuisineSlot(DiningTableBlockEntity container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return this.container instanceof DiningTableBlockEntity diningTable
                && diningTable.isOccupied()
                && NMIServerRecipeUtil.isCuisine(itemStack);
    }
}
