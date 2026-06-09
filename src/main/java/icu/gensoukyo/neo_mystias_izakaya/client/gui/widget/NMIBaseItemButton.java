/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.common.util.holder.DoubleHolder;
import icu.gensoukyo.neo_mystias_izakaya.common.util.holder.ItemStackHolder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public abstract class NMIBaseItemButton extends Button {
    protected final ItemStackHolder itemStack= new ItemStackHolder();
    protected final int x;
    protected final int y;
    protected final DoubleHolder discount;

    protected NMIBaseItemButton(int x, int y, Component message, OnPress onPress, ItemStack itemStack, @Nullable Tooltip tooltip) {
        super(x, y, 16, 16, message, onPress, Button.DEFAULT_NARRATION);
        this.x = x;
        this.y = y;
        this.setTooltip(tooltip);
        this.itemStack.set(itemStack);
        this.discount = new DoubleHolder(0.6+0.4*Math.random());
    }

    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public ItemStack getItemStack() {
        return this.itemStack.get();
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack.set(itemStack);
    }

    public void enableRender(){
        this.visible=true;
    }

    public void disableRender(){
        this.visible=false;
    }

}
