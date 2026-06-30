/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.ae2.resource;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.MEStorage;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.List;


public class MEStorageInvHandler implements ResourceHandler<ItemResource> {
    private final MEStorage inv;
    private final List<AEItemKey> aeKeys;

    public MEStorageInvHandler(MEStorage inv) {
        this.inv = inv;
        KeyCounter availableStacks = inv.getAvailableStacks();
        aeKeys = availableStacks.keySet().stream().filter(k -> k instanceof AEItemKey)
                .map(k -> (AEItemKey) k).toList();
    }

    @Override
    public int insert(ItemResource resource, int maxAmount, TransactionContext transaction) {
        return insert(0,resource, maxAmount,transaction);
    }

    @Override
    public int extract(ItemResource resource, int maxAmount, TransactionContext transaction) {
        return extract(0,resource, maxAmount,transaction);
    }

    @Override
    public int size() {
        return aeKeys.size();
    }

    @Override
    public ItemResource getResource(int index) {
        return ItemResource.of(aeKeys.get(index).getItem());
    }

    @Override
    public long getAmountAsLong(int index) {
        return inv.getAvailableStacks().get(aeKeys.get(index));
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        return Long.MAX_VALUE;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return aeKeys.contains(AEItemKey.of(resource));
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        return Math.toIntExact(inv.insert(AEItemKey.of(resource), amount, Actionable.MODULATE, IActionSource.empty()));
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        return Math.toIntExact(inv.extract(AEItemKey.of(resource), amount, Actionable.MODULATE, IActionSource.empty()));
    }

}
