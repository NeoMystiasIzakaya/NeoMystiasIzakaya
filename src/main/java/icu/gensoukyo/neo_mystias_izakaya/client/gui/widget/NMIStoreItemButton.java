/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.StoreItem;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class NMIStoreItemButton extends NMIBaseItemButton {

    private final boolean renderBg;

    @Getter
    private StoreItem storeItem;

    protected NMIStoreItemButton(int x, int y, Component message, Button.OnPress onPress, ItemStack itemStack, @Nullable Tooltip tooltip,StoreItem storeItem, boolean renderBg) {
        super(x, y,  message, onPress, itemStack, tooltip);
        this.renderBg = renderBg;
        this.storeItem = storeItem;
    }

    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
        this.setItemStack(storeItem.stack().create());
        this.setDiscount(storeItem.discount());
        int cost = (int) (storeItem.price() * (1-storeItem.discount()));
        this.setTooltip(Tooltip.create(Component.literal(this.getItemStack().getHoverName().getString() + "\n" + cost + " \u5186")));
    }

    public void clearStoreItem() {
        this.storeItem = null;
        this.setItemStack(ItemStack.EMPTY);
        this.setDiscount(0);
        this.setTooltip(null);
    }


    public void extractBg(GuiGraphicsExtractor guiGraphics){
        if(!this.renderBg) return;
        guiGraphics.fill(getX()-1, getY()-1, getX()+17, getY()+17, 0xFF8B4513);
        guiGraphics.fill(getX(), getY(), getX()+16, getY()+16, 0xFFf0e0b0);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int i, int i1, float v) {
        this.extractBg(guiGraphics);

        if(getItemStack().isEmpty())return;
        if(this.isFocused()) {
            guiGraphics.fill(getX() - 1, getY() - 1, getX() + 17, getY() + 17, 0x40FFA54F);
        }
        if(this.isHovered()) {
            guiGraphics.fill(getX(), getY(), getX() + 16, getY() + 16, 0x80fbefcb);
        }
        guiGraphics.item(this.itemStack.get(), getX(), getY());
    }

    public static class Builder {
        private ItemStack itemStack=ItemStack.EMPTY;
        private int x;
        private int y;
        private final Component message ;
        private final Button.OnPress onPress ;
        private Tooltip tooltip;
        private StoreItem storeItem;
        private boolean renderBg = true;

        public Builder(Component message, Button.OnPress onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder disableBg(){
            this.renderBg = false;
            return this;
        }

        public Builder storeItem(StoreItem storeItem){
            this.storeItem = storeItem;
            return this;
        }


        public NMIStoreItemButton build(){
            return new NMIStoreItemButton(this.x, this.y, this.message, this.onPress, this.itemStack,this.tooltip,this.storeItem,this.renderBg);
        }
    }

}
