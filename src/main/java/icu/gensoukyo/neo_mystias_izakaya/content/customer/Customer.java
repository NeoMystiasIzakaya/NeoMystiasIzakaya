/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer;

import net.minecraft.resources.Identifier;

import java.util.List;

public interface Customer {

    CustomerBudget budget();

    List<Identifier> locations();

    List<Identifier> likes();

    List<Identifier> dislikes();

    List<Identifier> beverage();

    List<Identifier> tagRequests();

    List<Identifier> spellCards();

}
