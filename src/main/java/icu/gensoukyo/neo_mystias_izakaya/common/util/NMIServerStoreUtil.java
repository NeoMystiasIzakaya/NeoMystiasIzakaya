/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.api.event.common.PlayerStoreEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalanceUnits;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.CartItem;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.StoreItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;

import java.util.ArrayList;
import java.util.List;

public final class NMIServerStoreUtil {

    public static Store getStore(Player player, Identifier id) {
        Store store = NMIDataAccessor.server().getStoreMap().getStoreMap().get(id);
        if (store == null) {
            store = Store.EMPTY;
        }
        PlayerStoreEvent.Get post = NeoForge.EVENT_BUS.post(new PlayerStoreEvent.Get(player, store));
        return post.getStore();
    }

    public static Store getIngredientStore(Player player) {
        return getStore(player,Store.INGREDIENTS);
    }

    public static Store getBeveragesStore(Player player) {
        return getStore(player,Store.BEVERAGES);
    }

    public static int calculatePrice(Player player, CartItem item,Store store) {
        StoreItem storeItem = store.getItemMap().get(item.id());
        if (storeItem == null) {
            return 0;
        }
        return (int) (storeItem.price() * item.count() * (1 - storeItem.discount()));
    }

    public static int calculatePrice(Player player, Cart cart,Store store){
        int p = 0;
        for (int i = 0; i < cart.getItems().size(); i++) {
            p += calculatePrice(player, cart.getItems().get(i),store);
        }
        return p;
    }

    public static boolean purchase(Player player, Cart cart,Store store) {
        int price = calculatePrice(player, cart,store);
        int canExtract = NMICommonBalanceUtil.extractEn(player, price, true);
        if (canExtract < price) {
            return false;
        }
        NMICommonBalanceUtil.extract(player, NMIBalanceUnits.EN, price, false);

        List<ItemStack> readyToGive = new ArrayList<>();
        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            StoreItem storeItem = store.getItemMap().get(item.id());
            if (storeItem == null) {
                continue;
            }
            ItemStack stack = storeItem.stack().create();
            stack.setCount(item.count());
            readyToGive.add(stack);
        }

        readyToGive.forEach(e->player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), e)));

        return true;
    }
}
