/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class NMIClientUtil {
    public static void updateKitchenwareScreen() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof KitchenwareScreen kitchenwareScreen) {
            kitchenwareScreen.updateRecipes();
        }
    }

    public static void openRecipeScreen() {
        Minecraft.getInstance().setScreen(new RecipeScreen());
    }

    public static void renderScaledText(GuiGraphicsExtractor graphics, Font font, Component component, int x, int y, int color, boolean dropShadow, float scale) {
        graphics.pose().scale(scale, scale);
        graphics.text(font, component, x, y, color, dropShadow);
        graphics.pose().scale(1 / scale, 1 / scale);
    }
}
