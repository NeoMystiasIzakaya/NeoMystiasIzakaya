/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.screen;

import icu.gensoukyo.kaguya.client.graphic.KaguyaUtil;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.NMICartItemButton;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.NMISimpleButton;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.NMIStoreItemButton;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientStoreUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonBalanceUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.CartItem;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.StoreItem;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StoreScreen extends Screen {

    public static final Identifier ID = NeoMystiasIzakaya.id("store_screen");

    private int leftPos;
    private int topPos;
    private int iWidth;
    private int iHeight;

    private List<NMIStoreItemButton> storeButtons;
    private List<NMICartItemButton> cartButtons;
    private NMISimpleButton nextPageButton;
    private NMISimpleButton purchaseButton;

    private NMIStoreItemButton ingredientsStoreButton;
    private NMIStoreItemButton beveragesStoreButton;

    private int currentPage;
    private Store currentStore;

    private Cart cart;

    public StoreScreen() {
        super(Component.empty());

        this.iWidth = 176;
        this.iHeight = 166;

        this.currentStore = Store.EMPTY;
        this.cart = Cart.empty();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractLabels(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int rightPos = this.leftPos + this.iWidth;
        int bottomPos = this.topPos + this.iHeight;
        KaguyaUtil.fillXYXY(guiGraphics,this.leftPos, this.topPos, rightPos, bottomPos, 0xD0ffffff);
        KaguyaUtil.fillXYXY(guiGraphics,rightPos - 86, this.topPos + 87, rightPos - 2, bottomPos - 2, 0xfff0e0b0);
        KaguyaUtil.fillXYXY(guiGraphics,rightPos - 84, this.topPos + 89, rightPos - 4, bottomPos - 4, 0x808b4513);
    }

    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        MutableComponent balance = NMICommonComponentUtil.translatableGUI(ID, "balance", NMICommonBalanceUtil.getEn(getMinecraft().player));
        MutableComponent total = NMICommonComponentUtil.translatableGUI(ID, "total", NMIClientStoreUtil.calculatePrice(getMinecraft().player, cart, currentStore));

        guiGraphics.text(getFont(), balance, leftPos + 88 - font.width(balance) / 2, topPos + 10, -12829636, false);
        guiGraphics.text(getFont(), total, leftPos + 10, topPos + 145, -12829636, false);
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.leftPos = centerX - this.iWidth / 2;
        this.topPos = centerY - this.iHeight / 2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int p = j + i * 4;
                storeButtons.get(p).setPosition(leftPos + 10 + 20 * j, topPos + 30 + 20 * i);
            }
        }
        ingredientsStoreButton.setPosition(leftPos + 10, topPos + 30);
        beveragesStoreButton.setPosition(leftPos + 10, topPos + 50);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                int p = j + i * 5;
                cartButtons.get(p).setPosition(leftPos + iWidth - 20 - 16 * j, topPos + iHeight - 20 - 20 * i);
            }
        }
        nextPageButton.setPosition(leftPos + 10, topPos + 10 + 20 * 4);
        purchaseButton.setPosition(leftPos + 10, topPos + 15 + 20 * 5);
    }

    @Override
    public void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.leftPos = centerX - this.iWidth / 2;
        this.topPos = centerY - this.iHeight / 2;

        storeButtons = new ArrayList<>();
        cartButtons = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int p = j + i * 4;
                storeButtons.add(new NMIStoreItemButton.Builder(Component.empty(), e -> {
                    int count = getMinecraft().hasShiftDown() ? 10 : 1;
                    addCart(storeButtons.get(p).getStoreItem().asCartItem(count));
                    reloadCart();
                }).pos(leftPos + 10 + 20 * j, topPos + 30 + 20 * i).build());
            }
        }
        reloadStore();

        ingredientsStoreButton = new NMIStoreItemButton.Builder(Component.empty(), e -> {
            ingredientsStoreButton.disableRender();
            beveragesStoreButton.disableRender();
            this.currentStore = NMIClientStoreUtil.getIngredientStore(getMinecraft().player);
            reloadStore();
        }).pos(leftPos + 10, topPos + 30).itemStack(NMIIngredientItems.LAMPREY.toStack())
                .tooltip(Tooltip.create(NMICommonComponentUtil.translatableGUI(ID, "ingredients"))).build();

        beveragesStoreButton = new NMIStoreItemButton.Builder(Component.empty(), e -> {
            ingredientsStoreButton.disableRender();
            beveragesStoreButton.disableRender();
            this.currentStore = NMIClientStoreUtil.getBeveragesStore(getMinecraft().player);
            reloadStore();
        }).pos(leftPos + 10, topPos + 50).itemStack(NMIBeveragesItems.GREEN_TEA.toStack())
                .tooltip(Tooltip.create(NMICommonComponentUtil.translatableGUI(ID, "beverages"))).build();


        nextPageButton = new NMISimpleButton.Builder(NMICommonComponentUtil.translatableGUI(ID, "next_page"), e -> {
            currentPage++;
            reloadStore();
            reloadCart();
        }).pos(leftPos + 10, topPos + 10 + 20 * 4).size(54, 20).build();

        purchaseButton = new NMISimpleButton.Builder(NMICommonComponentUtil.translatableGUI(ID, "purchase"), e -> {
            ClientPayloadSender.sendStorePurchaseMessage(cart, currentStore.getId());
            cart = Cart.empty();
            reloadCart();
            onClose();
        }).pos(leftPos + 10, topPos + 15 + 20 * 5).size(54, 20).build();


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                int p = j + i * 5;
                cartButtons.add(new NMICartItemButton.Builder(Component.empty(), e -> {
                    int count = getMinecraft().hasShiftDown() ? 10 : 1;
                    removeCart(cartButtons.get(p).getCartItem().copyWithCount(count));
                    reloadCart();
                }).pos(leftPos + iWidth - 20 - 16 * (4 - j), topPos + iHeight - 20 - 20 * (3 - i)).build());
            }
        }
        reloadCart();


        this.addRenderableWidget(nextPageButton);
        this.addRenderableWidget(purchaseButton);
        this.addRenderableWidget(ingredientsStoreButton);
        this.addRenderableWidget(beveragesStoreButton);

        storeButtons.forEach(this::addRenderableWidget);
        cartButtons.forEach(this::addRenderableWidget);
    }

    private void reloadStore() {
        List<StoreItem> items = currentStore.getItems();
        int offset = currentPage * 12;
        if (offset >= items.size()) {
            currentPage = 0;
            offset = 0;
        }
        for (int i = 0; i < 12; i++) {
            if (i + offset >= items.size()) {
                storeButtons.get(i).clearStoreItem();
                storeButtons.get(i).disableRender();
                continue;
            }
            StoreItem item = items.get(i + offset);
            storeButtons.get(i).setStoreItem(item);
            storeButtons.get(i).enableRender();
        }
    }

    private void reloadCart() {
        List<CartItem> cartItems = cart.getItems();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            cartButtons.get(cartButtons.size() - i - 1).setStoreAndCartItem(currentStore, cartItem);
            cartButtons.get(cartButtons.size() - i - 1).enableRender();
        }
        for (int i = cartItems.size(); i < 20; i++) {
            cartButtons.get(cartButtons.size() - i - 1).setItemStack(ItemStack.EMPTY);
            cartButtons.get(cartButtons.size() - i - 1).disableRender();
        }
    }

    private void addCart(CartItem item) {
        cart.addItem(item);
    }

    private void removeCart(CartItem item) {
        cart.removeItem(item);
    }

}
