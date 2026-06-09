/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util.holder;

import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ItemStackHolder implements Supplier<ItemStack> {
    private ItemStack itemStack;

    public ItemStackHolder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackHolder() {
        this.itemStack = ItemStack.EMPTY;
    }

    @Override
    public ItemStack get() {
        return itemStack;
    }

    public void set(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
