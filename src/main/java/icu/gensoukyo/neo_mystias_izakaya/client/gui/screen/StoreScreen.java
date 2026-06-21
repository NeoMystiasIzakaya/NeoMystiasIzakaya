/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.screen;

import icu.gensoukyo.kaguya.client.graphic.KaguyaUtil;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CartListWidget;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.button.NMISimpleButton;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.button.NMIStoreItemButton;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientStoreUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonBalanceUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.CartItem;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.StoreItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

public class StoreScreen extends Screen {

    public static final Identifier BACKGROUND = NeoMystiasIzakaya.id("textures/gui/store.png");
    public static final Identifier ID = NeoMystiasIzakaya.id("store_screen");

    private int leftPos;
    private int topPos;
    private final int iWidth;
    private final int iHeight;

    private List<NMIStoreItemButton> storeButtons;
//    private List<NMICartItemButton> cartButtons;
    private NMISimpleButton lastPageButton;
    private NMISimpleButton nextPageButton;
    private NMISimpleButton purchaseButton;

    private CartListWidget cartListWidget;

    private int currentPage;
    private Store currentStore;

    private Cart cart;

    public StoreScreen() {
        super(Component.empty());

        this.iWidth = 181*2;
        this.iHeight = 113*2;

        this.currentStore = NMIClientStoreUtil.getStore(Minecraft.getInstance().player,Store.ALL);
        this.cart = Cart.empty();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
        this.extractLabels(guiGraphics, mouseX, mouseY);
        this.extractHighLights(guiGraphics,mouseX,mouseY);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        KaguyaUtil.blitXYWHUVWH(guiGraphics, BACKGROUND, leftPos, topPos, iWidth, iHeight, 0, 0, 181, 113);
    }

    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
//        Matrix3x2fStack pose = guiGraphics.pose();
//        pose.pushMatrix();
//        pose.translate(leftPos, topPos);
//        pose.scale(2,2);
        MutableComponent balance_text = NMICommonComponentUtil.translatableGUI(ID, "balance_text");
        MutableComponent balance = Component.literal(NMICommonBalanceUtil.getEn(getMinecraft().player)+"円");
        MutableComponent total_text = NMICommonComponentUtil.translatableGUI(ID, "total_text");
        MutableComponent total = Component.literal(NMIClientStoreUtil.calculatePrice(getMinecraft().player, cart, currentStore)+"円");

        guiGraphics.text(getFont(), balance_text, leftPos + 22 - font.width(balance_text) / 2, topPos + 124, -12829636, false);
        guiGraphics.text(getFont(), balance, leftPos + 22 - font.width(balance) / 2, topPos + 134, -12829636, false);
        guiGraphics.text(getFont(), total_text, leftPos + 306, topPos + 200, -12829636, false);
        guiGraphics.text(getFont(), total, leftPos + 310, topPos + 210, -12829636, false);
//        pose.popMatrix();
    }

    protected void extractHighLights(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        if (lastPageButton.isHoveredOrFocused()){
            KaguyaUtil.blitXYWHUVWH(guiGraphics,BACKGROUND,leftPos+27*2,topPos+102*2,27*2,11*2,0,114,27,11);
        }
        if (nextPageButton.isHoveredOrFocused()){
            KaguyaUtil.blitXYWHUVWH(guiGraphics,BACKGROUND,leftPos+106*2,topPos+102*2,27*2,11*2,0,125,27,11);
        }
        if (purchaseButton.isHoveredOrFocused()){
            KaguyaUtil.blitXYWHUVWH(guiGraphics,BACKGROUND,leftPos+140*2,topPos+98*2,13*2,13*2,27,114,13,13);
        }
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
            for (int j = 0; j < 6; j++) {
                int p = j + i * 6;
                storeButtons.get(p).setPosition(leftPos + 36*2 + 16*2 * j, topPos + 36*2 + 19*2 * i);
            }
        }

        cartListWidget.setPosition(leftPos+280,topPos+100);
        reloadCart();

        lastPageButton.setPosition(leftPos + 28*2, topPos + 103*2);
        nextPageButton.setPosition(leftPos + 107*2, topPos + 103*2);
        purchaseButton.setPosition(leftPos + 141*2, topPos + 99*2);
    }

    @Override
    public void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        this.leftPos = centerX - this.iWidth / 2;
        this.topPos = centerY - this.iHeight / 2;

        storeButtons = new ArrayList<>();
//        cartButtons = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                int p = j + i * 6;
                storeButtons.add(new NMIStoreItemButton.Builder(Component.empty(), e -> {
                    int count = getMinecraft().hasShiftDown() ? 10 : 1;
                    addCart(storeButtons.get(p).getStoreItem().asCartItem(count));
                }).pos(leftPos + 36*2 + 16*2 * j, topPos + 36*2 + 19*2 * i).disableBg().build());
            }
        }
        reloadStore();

        lastPageButton = new NMISimpleButton.Builder(Component.empty(), e -> {
            currentPage--;
            reloadStore();
            reloadCart();
        }).pos(leftPos + 28*2, topPos + 103*2).size(25*2, 9*2).build();

        nextPageButton = new NMISimpleButton.Builder(Component.empty(), e -> {
            currentPage++;
            reloadStore();
            reloadCart();
        }).pos(leftPos + 107*2, topPos + 103*2).size(25*2, 9*2).build();

        purchaseButton = new NMISimpleButton.Builder(Component.empty(), e -> {
            ClientPayloadSender.sendStorePurchaseMessage(cart, currentStore.getId());
            cart = Cart.empty();
            reloadCart();
            onClose();
        }).pos(leftPos + 141*2, topPos + 99*2).size(11*2, 11*2).build();

        cartListWidget = new CartListWidget(getMinecraft(),80,90,100);
        cartListWidget.setPosition(leftPos+280,topPos+100);

//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 5; j++) {
//                int p = j + i * 5;
//                cartButtons.add(new NMICartItemButton.Builder(Component.empty(), e -> {
//                    int count = getMinecraft().hasShiftDown() ? 10 : 1;
//                    removeCart(cartButtons.get(p).getCartItem().copyWithCount(count));
//                    reloadCart();
//                }).pos(leftPos + iWidth - 20 - 16 * (4 - j), topPos + iHeight - 20 - 20 * (3 - i)).build());
//            }
//        }


        this.addRenderableWidget(lastPageButton);
        this.addRenderableWidget(nextPageButton);
        this.addRenderableWidget(purchaseButton);

        storeButtons.forEach(this::addRenderableWidget);

        this.addRenderableWidget(cartListWidget);
//        cartButtons.forEach(this::addRenderableWidget);
    }

    private void reloadStore() {
        List<StoreItem> items = currentStore.getItems();
        int offset = currentPage * 18;
        if (offset >= items.size()) {
            currentPage--;
            offset = currentPage * 18;
        }
        if (offset < 0) {
            currentPage = 0;
            offset = 0;
        }
        for (int i = 0; i < 18; i++) {
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
        cartListWidget.clearEntries();
        cartItems.forEach(e->cartListWidget.addEntry(new CartListWidget.Entry(this::removeCart,currentStore,e)));
    }

    private void addCart(CartItem item) {
        cart.addItem(item);
        reloadCart();
    }

    private void removeCart(CartItem item) {
        cart.removeItem(item);
        reloadCart();
    }

}
