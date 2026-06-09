/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class NMISimpleButton extends Button {

    public final int x;
    public final int y;
    public final int w;
    public final int h;

    protected NMISimpleButton(int x, int y, int w, int h, Component message, OnPress onPress, @Nullable Tooltip tooltip) {
        super(x, y,w,h,  message, onPress, Button.DEFAULT_NARRATION);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.setTooltip(tooltip);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int i, int i1, float v) {
        guiGraphics.fill(getX()-1, getY()-1, getX()+w+1, getY()+h+1, 0xFFFEEBD1);
        if(this.isHovered()) {
            guiGraphics.fill(getX(), getY(), getX()+w, getY()+h, 0xFFfbefcb);
        }
        else {
            guiGraphics.fill(getX(), getY(), getX()+w, getY()+h, 0xFFf0e0b0);
        }
        this.extractDefaultLabel(guiGraphics.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE));
    }

    public static class Builder {
        private int x;
        private int y;
        private int w;
        private int h;
        private final Component message ;
        private final OnPress onPress ;
        private Tooltip tooltip;

        public Builder(Component message, OnPress onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder size(int w, int h) {
            this.w = w;
            this.h = h;
            return this;
        }

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public NMISimpleButton build(){
            return new NMISimpleButton(this.x, this.y,this.w,this.h, this.message, this.onPress,this.tooltip);
        }
    }

}
