/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.balance;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.resources.Identifier;

public class NMIBalanceUnits {

    public static final Identifier EMERALD = Identifier.withDefaultNamespace("emerald");
    public static final Identifier EN = NeoMystiasIzakaya.id("en");
    public static final Identifier EMPTY = NeoMystiasIzakaya.id("empty");

    public static final NMIBalanceEntry EMERALD_ENTRY = new NMIBalanceEntry(EMERALD, 1);
    public static final NMIBalanceEntry EN_ENTRY = new NMIBalanceEntry(EN, 1);
    public static final NMIBalanceEntry EMPTY_ENTRY = new NMIBalanceEntry(EMPTY, 0);
}
