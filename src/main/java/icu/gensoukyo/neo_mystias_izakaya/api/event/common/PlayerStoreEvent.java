/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.common;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Cart;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

@Getter
public class PlayerStoreEvent extends Event {

    private final Player player;

    public PlayerStoreEvent(Player player) {
        this.player = player;
    }

    @Getter
    @Setter
    public static class Get extends PlayerStoreEvent {

        private Store store;

        public Get(Player player, Store store) {
            super(player);
            this.store = store;
        }
    }

    @Getter
    @Setter
    public static class Purchase extends PlayerStoreEvent implements ICancellableEvent {

        private final Cart cart;
        private final Store store;

        public Purchase(Player player, Cart cart, Store store) {
            super(player);
            this.cart = cart;
            this.store = store;
        }
    }
}
