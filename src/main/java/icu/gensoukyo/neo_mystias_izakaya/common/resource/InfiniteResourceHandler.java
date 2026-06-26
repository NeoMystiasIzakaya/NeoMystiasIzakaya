/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.resource;

import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.resource.Resource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.List;

public class InfiniteResourceHandler<T extends Resource> implements ResourceHandler<T> {

    private final List<T> availableResources;

    public InfiniteResourceHandler(List<T> availableResources) {
        this.availableResources = availableResources;
    }

    @Override
    public int size() {
        return availableResources.size();
    }

    @Override
    public T getResource(int index) {
        return availableResources.get(index);
    }

    @Override
    public long getAmountAsLong(int index) {
        return Long.MAX_VALUE;
    }

    @Override
    public long getCapacityAsLong(int index, T resource) {
        return Long.MAX_VALUE;
    }

    @Override
    public boolean isValid(int index, T resource) {
        return availableResources.get(index).equals(resource);
    }

    @Override
    public int insert(int index, T resource, int amount, TransactionContext transaction) {
        return availableResources.get(index).equals(resource)? amount : 0;
    }

    @Override
    public int extract(int index, T resource, int amount, TransactionContext transaction) {
        return availableResources.get(index).equals(resource)? amount : 0;
    }

    @Override
    public int extract(T resource, int amount, TransactionContext transaction) {
        return availableResources.contains(resource)? amount : 0;
    }

    @Override
    public int insert(T resource, int amount, TransactionContext transaction) {
        return availableResources.contains(resource)? amount : 0;
    }
}
