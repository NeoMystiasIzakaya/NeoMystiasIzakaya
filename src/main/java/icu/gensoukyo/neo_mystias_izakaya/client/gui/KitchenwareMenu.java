package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class KitchenwareMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access = ContainerLevelAccess.NULL;
    protected final int INV_START = 6;
    protected static final int INV_SIZE = 36;

    public KitchenwareMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        super(NMIMenus.KITCHENWARE_MENU.get(), containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (index < INV_START) {
                if (!this.moveItemStackTo(slotStack, INV_START, INV_SIZE + INV_START, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else {
                if (index < INV_SIZE + INV_START && !this.moveItemStackTo(slotStack, 0, INV_START, false))
                    return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, slotStack);
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return access.evaluate((level, pos) -> player.isWithinBlockInteractionRange(pos, 4.0F), true);
    }

    public enum KitchenwareType {
        BOILING_POT, CUTTING_BOARD, FRYING_PAN, GRILL, STEAMER;
    }
}
