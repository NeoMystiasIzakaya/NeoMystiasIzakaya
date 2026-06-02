/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.server.economy;

import lombok.Getter;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.transfer.transaction.Transaction;

@Getter
public abstract class NMIBalanceEvent extends Event implements ICancellableEvent {

    private final Player player;
    private final Transaction transaction;
    private final Identifier unit;
    private final int count;
    private final Identifier reason;
    private final String from;
    private final String to;
    private final boolean simulate;

    public NMIBalanceEvent(Player player, Transaction transaction, Identifier unit, int count,boolean simulate, Identifier reason, String from, String to) {
        this.player = player;
        this.transaction = transaction;
        this.unit = unit;
        this.count = count;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.simulate = simulate;
    }

    public static class Insert extends NMIBalanceEvent {

        public Insert(Player player, Transaction transaction, Identifier unit, int count,boolean simulate, Identifier reason, String from, String to) {
            super(player, transaction, unit, count,simulate, reason, from, to);
        }
    }

    public static class Extract extends NMIBalanceEvent {

        public Extract(Player player, Transaction transaction, Identifier unit, int count,boolean simulate, Identifier reason, String from, String to) {
            super(player, transaction, unit, count,simulate, reason, from, to);
        }
    }
}
