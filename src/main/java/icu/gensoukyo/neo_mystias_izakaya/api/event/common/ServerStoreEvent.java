/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.common;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.store.Store;
import lombok.Getter;
import net.neoforged.bus.api.Event;

import java.util.List;

public class ServerStoreEvent extends Event {

    @Getter
    public static class Init extends ServerStoreEvent {
        private final List<Store> stores;

        public Init(List<Store> stores) {
            this.stores = stores;
        }
    }
}
