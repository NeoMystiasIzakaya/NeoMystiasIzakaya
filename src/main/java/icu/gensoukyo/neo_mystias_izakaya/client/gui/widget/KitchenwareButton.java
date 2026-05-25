/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import static icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen.POSITIVE_IN_COLOR;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen.POSITIVE_OUT_COLOR;

public class KitchenwareButton extends Button {
    @Getter
    @Setter
    private boolean selected;

    @Getter
    private final ItemStack icon;

    public KitchenwareButton(int x, int y, int width, int height, Component message, ItemStack icon, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
        this.icon = icon;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        // 渲染背景
        int bgColor = selected ? POSITIVE_IN_COLOR : POSITIVE_OUT_COLOR;
        int textColor = selected ? POSITIVE_OUT_COLOR : POSITIVE_IN_COLOR;
        guiGraphicsExtractor.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), bgColor);

        // 渲染图标
        guiGraphicsExtractor.item(icon, getX() + 2, getY());

        // 渲染文本
        var font = Minecraft.getInstance().font;
        var text = getMessage();
        var textToRender = text.getVisualOrderText();
        guiGraphicsExtractor.text(font, textToRender, getX() + 22, getY() + 6, textColor, false);
    }
}
