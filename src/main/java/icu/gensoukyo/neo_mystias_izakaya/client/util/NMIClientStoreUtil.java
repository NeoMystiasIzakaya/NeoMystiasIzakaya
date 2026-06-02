/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.CartItem;

public final class NMIClientStoreUtil {

    public static int calculatePrice(CartItem item) {
        Integer price = NMIClientEconomyUtil.getPrice(item.id());
        if (price == null) {
            return 0;
        }
        return price * item.count();
    }

    public static int calculatePrice(Cart cart){
        int p = 0;
        for (int i = 0; i < cart.items().size(); i++) {
            p += calculatePrice(cart.items().get(i));
        }
        return p;
    }
}
