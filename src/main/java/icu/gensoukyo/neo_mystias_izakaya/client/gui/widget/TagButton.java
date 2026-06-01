/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import static icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen.POSITIVE_IN_COLOR;
import static icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen.POSITIVE_OUT_COLOR;

public class TagButton extends Button {
    @Getter
    @Setter
    private boolean selected;

    public TagButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphicsExtractor, int mouseX, int mouseY, float partialTick) {
        // 渲染背景
        int bgColor = selected ? POSITIVE_IN_COLOR : POSITIVE_OUT_COLOR;
        int textColor = selected ? POSITIVE_OUT_COLOR : POSITIVE_IN_COLOR;
        guiGraphicsExtractor.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), bgColor);

        // 渲染文本
        Font font = Minecraft.getInstance().font;
        Component text = getMessage();
        var textToRender = text.getVisualOrderText();
        int fontWidth = font.width(textToRender);
        int textX = getX();
        int textY = getY() + 1;
        if (fontWidth > 40) {
            NMIClientUtil.renderScaledText(guiGraphicsExtractor, font, text, textX, textY, textColor, false, (float) 40 / fontWidth);
        } else {
            textX = getX() + (getWidth() - font.width(textToRender)) / 2;
            guiGraphicsExtractor.text(font, textToRender, textX, textY, textColor, false);
        }
    }
}
