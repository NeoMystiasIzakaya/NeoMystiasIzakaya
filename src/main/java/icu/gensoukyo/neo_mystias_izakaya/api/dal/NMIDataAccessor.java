/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.dal;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.dal.ServerNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.NMIEconomyMap;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;

public interface NMIDataAccessor {

    static NMIDataAccessor server() {
        return ServerNMIDataAccessor.INSTANCE;
    }

    static NMIDataAccessor client() {
        return ClientNMIDataAccessor.INSTANCE;
    }

    TagItemListMap getTagItemListMap();

    NMIRecipeMap getRecipeMap();

    CustomerMap getCustomerMap();

    NMIEconomyMap getEconomyMap();
}
