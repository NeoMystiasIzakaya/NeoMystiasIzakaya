package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class NMIClientUtil {
    public static void updateKitchenwareScreen() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof KitchenwareScreen kitchenwareScreen) {
            kitchenwareScreen.updateRecipes();
        }
    }
}
