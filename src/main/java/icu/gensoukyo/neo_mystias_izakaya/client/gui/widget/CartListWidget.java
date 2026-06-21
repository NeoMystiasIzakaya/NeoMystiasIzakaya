/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import icu.gensoukyo.kaguya.client.graphic.KaguyaUtil;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientStoreUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.holder.ItemStackHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.CartItem;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.StoreItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class CartListWidget extends ObjectSelectionList<CartListWidget.Entry> {

    private final int listWidth;

    public CartListWidget(Minecraft minecraft, int width, int height, int y) {
        super(minecraft, width, height, y, 17);
        this.listWidth = width;
    }

    @Override
    public int getRowWidth() {
        return this.listWidth - 12;
    }

    @Override
    protected int scrollBarX() {
        return (super.scrollBarX() - 8);
    }

    @Override
    protected void extractListBackground(GuiGraphicsExtractor graphics) {
    }

    @Override
    protected void extractListSeparators(GuiGraphicsExtractor graphics) {
    }

    @Override
    protected void extractSelection(GuiGraphicsExtractor graphics, CartListWidget.Entry entry, int outlineColor) {
        int outlineX0 = entry.getX() + 16;
        int outlineY0 = entry.getY();
        int outlineX1 = outlineX0 + entry.getWidth() - 16;
        int outlineY1 = outlineY0 + entry.getHeight();
        graphics.fill(outlineX0, outlineY0, outlineX1, outlineY1, outlineColor);
        graphics.fill(outlineX0 + 1, outlineY0 + 1, outlineX1 - 1, outlineY1 - 1, -16777216);
    }

    @Override
    public int addEntry(CartListWidget.Entry entry) {
        return super.addEntry(entry);
    }

    @Override
    protected void extractScrollbar(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int scrollbarX = this.scrollBarX();
        int scrollerHeight = this.scrollerHeight();
        int scrollerY = this.scrollBarY();

        if (this.scrollable()) {
//            KaguyaUtil.fillXYWH(graphics, getX(), getY(), this.scrollbarWidth(), this.getHeight(), 0x40E3CA40);
            KaguyaUtil.fillXYWH(graphics, getX(), scrollerY, this.scrollbarWidth(), scrollerHeight, 0x40947B58);
//            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.scrollbarSettings.backgroundSprite(), scrollbarX, this.getY(), this.scrollbarWidth(), this.getHeight());
//            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.scrollbarSettings.scrollerSprite(), scrollbarX, scrollerY, this.scrollbarWidth(), scrollerHeight);
            if (this.isOverScrollbar(mouseX, mouseY)) {
                graphics.requestCursor(this.scrolling ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND);
            }
        }
    }

    public static class Entry extends ObjectSelectionList.Entry<Entry> {

        protected final ItemStackHolder itemStack = new ItemStackHolder();
        protected final Consumer<CartItem> removeCart;
        private CartItem cartItem;
        private Store store;

        public Entry(Consumer<CartItem> removeCart, Store store, CartItem cartItem) {
            this.removeCart = removeCart;
            StoreItem storeItem = store.getItemMap().get(cartItem.id());
            this.itemStack.set(storeItem.stack().create());
            this.cartItem = cartItem;
            this.store = store;
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            if (event.button() == InputConstants.MOUSE_BUTTON_LEFT) {
                int count = event.hasShiftDown() ? 10 : 1;
                removeCart.accept(cartItem.copyWithCount(count));
                return true;
            }
            return false;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean b, float v) {
            guiGraphics.item(this.itemStack.get(), this.getX(), this.getY());
            Font font = Minecraft.getInstance().font;
            int cost = NMIClientStoreUtil.calculatePrice(Minecraft.getInstance().player, cartItem, store);
            guiGraphics.text(font, Component.literal("x"), this.getX() + 20, this.getY() + 3, 0xFF000000, false);
            guiGraphics.text(font, Component.literal(cartItem.count() + "個"), this.getX() + 45 - font.width(cartItem.count() + "個"), this.getY() + 3, 0xFF000000, false);
            guiGraphics.text(font, Component.literal(cost + " 円"), this.getX() + 75 - font.width(cost + " 円"), this.getY() + 3, 0xFF000000, false);
        }
    }
}
