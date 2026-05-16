/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.dal;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.NMIEconomyMap;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientNMIDataAccessor implements NMIDataAccessor {

    public static final ClientNMIDataAccessor INSTANCE = new ClientNMIDataAccessor();

    private TagItemListMap tagItemListMap = TagItemListMap.EMPTY;

    private NMIRecipeMap recipeMap = NMIRecipeMap.EMPTY;

    private CustomerMap customerMap = CustomerMap.EMPTY;

    private NMIEconomyMap economyMap = NMIEconomyMap.EMPTY;
}
