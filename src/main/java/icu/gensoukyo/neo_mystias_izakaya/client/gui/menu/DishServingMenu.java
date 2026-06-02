package icu.gensoukyo.neo_mystias_izakaya.client.gui.menu;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class DishServingMenu extends AbstractContainerMenu {
    public DishServingMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        super(NMIMenus.DISH_SERVING_MENU.get(), containerId);
    }

    public DishServingMenu(int containerId, Inventory inventory) {
        super(NMIMenus.DISH_SERVING_MENU.get(), containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
