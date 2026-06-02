/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.RecipeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public final class NMIClientUtil {
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
        graphics.text(font, component, (int) (x / scale), (int) (y / scale), color, dropShadow);
        graphics.pose().scale(1 / scale, 1 / scale);
    }

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
