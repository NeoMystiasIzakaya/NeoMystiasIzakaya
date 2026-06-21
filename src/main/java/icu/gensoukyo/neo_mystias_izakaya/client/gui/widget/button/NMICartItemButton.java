/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.button;

import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientStoreUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.CartItem;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.StoreItem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

@Getter
@Setter
public class NMICartItemButton extends NMIBaseItemButton {

    private CartItem cartItem;
    private Store store;

    protected NMICartItemButton(int x, int y, Component message, Button.OnPress onPress, ItemStack itemStack, @Nullable Tooltip tooltip,CartItem cartItem) {
        super(x, y,  message, onPress, itemStack, tooltip);
        this.cartItem = cartItem;
    }

    public void setStoreAndCartItem(Store store, CartItem cartItem) {
        StoreItem storeItem = store.getItemMap().get(cartItem.id());
        this.setItemStack(storeItem.stack().create());
        this.setDiscount(storeItem.discount());
        int cost = NMIClientStoreUtil.calculatePrice(Minecraft.getInstance().player, cartItem,store);
        this.setTooltip(Tooltip.create(Component.literal(this.getItemStack().getHoverName().getString() + "\n"+ cartItem.count() + "個\n" + cost + " 円")));
        this.cartItem = cartItem;
        this.store = store;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int size = Math.min(this.cartItem.count(), 10);
        for (int i = 0; i < size; i++) {
            guiGraphics.item(this.itemStack.get(), this.x , this.y- 2 * i);
        }
    }


    public static class Builder {
        private ItemStack itemStack=ItemStack.EMPTY;
        private int x;
        private int y;
        private final Component message ;
        private final Button.OnPress onPress ;
        private Tooltip tooltip;
        private CartItem cartItem;

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

        public Builder cartItem(CartItem cartItem) {
            this.cartItem = cartItem;
            return this;
        }

        public NMICartItemButton build(){
            return new NMICartItemButton(this.x, this.y, this.message, this.onPress, this.itemStack,this.tooltip,this.cartItem);
        }
    }
}
