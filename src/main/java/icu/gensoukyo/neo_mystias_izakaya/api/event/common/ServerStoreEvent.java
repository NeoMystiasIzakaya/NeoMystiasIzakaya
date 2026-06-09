/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.common;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.function.Function;

public class ServerStoreEvent extends Event {

    @Getter
    public static class Reload extends ServerStoreEvent {
        private final List<Store> stores;

        public Reload(List<Store> stores) {
            this.stores = stores;
        }
    }

    @Getter
    @Setter
    public static class CreateDiscountFunction extends ServerStoreEvent {

        private Function<Identifier, Double> discountFunction;

        public CreateDiscountFunction(Function<Identifier,Double> discountFunction) {
            this.discountFunction = discountFunction;
        }
    }
}
