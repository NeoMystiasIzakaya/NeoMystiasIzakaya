/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.CanteenScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.RecipeScreen;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.StoreScreen;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.consts.CustomerEvaluation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
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

    public static void openStoreScreen() {
        Minecraft.getInstance().setScreen(new StoreScreen());
    }

    public static void openCanteenScreen() {
        Minecraft.getInstance().setScreen(new CanteenScreen());
    }

    public static void openCanteenScreen(BlockPos controllerPos) {
        Minecraft.getInstance().setScreen(new CanteenScreen(controllerPos));
    }

    public static void renderScaledText(GuiGraphicsExtractor graphics, Font font, Component component, int x, int y, int color, boolean dropShadow, float scale) {
        graphics.pose().scale(scale, scale);
        graphics.text(font, component, (int) (x / scale), (int) (y / scale), color, dropShadow);
        graphics.pose().scale(1 / scale, 1 / scale);
    }

    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void showToast(int price, CustomerEvaluation evaluation) {
        Minecraft minecraft = Minecraft.getInstance();
        Component title = Component.translatable("block.neo_mystias_izakaya.dining_table.sale", price);
        Component desc = Component.translatable(evaluation.getTranslationKey());
        minecraft.getToastManager().addToast(SystemToast.multiline(minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE, title, desc));
    }
}
