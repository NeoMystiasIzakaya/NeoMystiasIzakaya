package icu.gensoukyo.neo_mystias_izakaya.client.gui.screen;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.menu.DishServingMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DishServingScreen extends AbstractContainerScreen<DishServingMenu> {
    public DishServingScreen(DishServingMenu menu, Inventory inventory) {
        super(menu, inventory, Component.literal("Dish Serving"));
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    }
}
