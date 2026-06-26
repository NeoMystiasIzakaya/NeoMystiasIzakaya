/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.overlay;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.gui.GuiLayer;

import java.util.Optional;

public class DinningTableInfoOverlay implements GuiLayer {

    public static final int DARK_RED = 0xFFAA0000;
    public static final int DARK_GREEN = 0xFF00FF00;

    private static final int ITEM_RECT_WIDTH = 80;
    private static final int ITEM_RECT_HEIGHT = 24;
    private static final int FILL_COLOR = 0x40FFFFFF;
    private static final int BORDER_COLOR = 0xFFFFFFFF;
    private static final Identifier CONFIRM_SPRITE = Identifier.withDefaultNamespace("container/beacon/confirm");
    private static final Identifier CANCEL_SPRITE = Identifier.withDefaultNamespace("container/beacon/cancel");

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        ClientLevel level = Minecraft.getInstance().level;
        if (player == null || level == null) {
            return;
        }

        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (level.getBlockEntity(blockPos) instanceof DiningTableBlockEntity tableBlockEntity) {
                var window = Minecraft.getInstance().getWindow();
                int screenWidth = window.getGuiScaledWidth();
                int screenHeight = window.getGuiScaledHeight();
                int centerY = screenHeight / 2;
                int x0 = screenWidth / 2 + 16;
                int y0 = centerY - ITEM_RECT_HEIGHT / 2;

                drawItemRect(guiGraphics, x0, y0, x0 + ITEM_RECT_WIDTH, y0 + ITEM_RECT_HEIGHT);

                var font = Minecraft.getInstance().font;
                if (tableBlockEntity.isOccupied()) {
                    renderOccupiedTable(guiGraphics, font, tableBlockEntity, x0, y0);
                } else {
                    renderIdleTable(guiGraphics, font, x0, y0);
                }
            }
        }
    }

    /**
     * 渲染空闲餐桌
     */
    private void renderIdleTable(GuiGraphicsExtractor guiGraphics, Font font, int x0, int y0) {
        guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.idle"), x0 + 4, y0 + 7, 0xFF000000, false);
    }

    /**
     * 渲染有客餐桌：订单未完成显示需求，已完成显示物品
     */
    private void renderOccupiedTable(GuiGraphicsExtractor guiGraphics, Font font,
                                     DiningTableBlockEntity diningTable, int x0, int y0) {
        IzakayaOrder order = diningTable.getCurrentOrder();
        Identifier customerId = diningTable.getCustomerId();

        if (IzakayaOrder.isEmpty(order)) {
            // 女仆占桌（无顾客订单）：直接显示桌上已有物品
            ItemStack cuisine = diningTable.getCuisine();
            ItemStack beverage = diningTable.getBeverage();
            if (!cuisine.isEmpty()) {
                guiGraphics.item(cuisine, x0 + 2, y0 + 3);
            }
            if (!beverage.isEmpty()) {
                guiGraphics.item(beverage, x0 + 20, y0 + 3);
            }
            Component maidLabel = Component.translatable("gui.neo_mystias_izakaya.maid_dining");
            guiGraphics.text(font, maidLabel, x0 + 38, y0 + 7, 0xFF000000, false);
            return;
        }

        // 订单未完成：显示需求
        if (order.isRare()) {
            // 稀客：两个都是 Tag，显示 Tag 名称
            Component cuisineTag = Component.translatable(order.cuisine().toLanguageKey("tag"));
            Component beverageTag = Component.translatable(order.beverage().toLanguageKey("tag"));

            int cuisineColor = NMIClientItemTagUtil.get(diningTable.getCuisine()).positiveTags().contains(order.cuisine()) ? DARK_GREEN : DARK_RED;
            int beverageColor = NMIClientItemTagUtil.get(diningTable.getBeverage()).positiveTags().contains(order.beverage()) ? DARK_GREEN : DARK_RED;

            guiGraphics.text(font, cuisineTag, x0 + 4, y0 + 3, cuisineColor, false);
            guiGraphics.text(font, beverageTag, x0 + 4, y0 + 13, beverageColor, false);
        } else {
            // 普客：两个都是物品，显示需求菜品 + 饮品图标
            ClientNMIDataAccessor instance = ClientNMIDataAccessor.INSTANCE;
            ItemStack cuisineItem = instance.getRecipeMap().getRecipeMap().get(order.cuisine()).recipe().output().item().value().getDefaultInstance();
            Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(order.beverage());
            ItemStack beverageItem = ItemStack.EMPTY;
            if (itemReference.isPresent()) {
                beverageItem = itemReference.get().value().getDefaultInstance();
            }
            guiGraphics.item(cuisineItem, x0 + 2, y0 + 3);
            guiGraphics.item(beverageItem, x0 + 20, y0 + 3);

            if (!diningTable.getCuisine().isEmpty()) {
                if (diningTable.getCuisine().is(cuisineItem.getItem())) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIRM_SPRITE, x0 + 2, y0 + 2, 18, 18);
                } else {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CANCEL_SPRITE, x0 + 2, y0 + 2, 18, 18);
                }
            }

            if (!diningTable.getBeverage().isEmpty()) {
                if (diningTable.getBeverage().is(beverageItem.getItem())) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CONFIRM_SPRITE, x0 + 20, y0 + 2, 18, 18);
                } else {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, CANCEL_SPRITE, x0 + 20, y0 + 2, 18, 18);
                }
            }
        }

        // 客人名
        Component customerName = Component.translatable("customer.neo_mystias_izakaya." + customerId.getPath());
        int width = font.width(customerName);
        if (width > 40) {
            NMIClientUtil.renderScaledText(guiGraphics, font, customerName, x0 + 38, y0 + 7, 0xFF000000, false, (float) 40 / width);
        } else {
            guiGraphics.text(font, customerName, x0 + 38, y0 + 7, 0xFF000000, false);
        }
    }

    /**
     * 绘制单个物品矩形（填充 + 边框）
     */
    private void drawItemRect(GuiGraphicsExtractor guiGraphics, int x0, int y0, int x1, int y1) {
        guiGraphics.fill(x0 + 1, y0 + 1, x1 - 1, y1 - 1, FILL_COLOR);
        guiGraphics.fill(x0, y0, x1, y0 + 1, BORDER_COLOR);
        guiGraphics.fill(x0, y1 - 1, x1, y1, BORDER_COLOR);
        guiGraphics.fill(x0, y0, x0 + 1, y1, BORDER_COLOR);
        guiGraphics.fill(x1 - 1, y0, x1, y1, BORDER_COLOR);
    }
}
