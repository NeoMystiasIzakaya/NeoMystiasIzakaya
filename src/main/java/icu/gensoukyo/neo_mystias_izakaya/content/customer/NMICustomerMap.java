/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer;

import lombok.Getter;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Getter
public class NMICustomerMap {

    private final List<CommonCustomerHolder> commonCustomers;
    private final List<RareCustomerHolder> rareCustomers;
    private final List<NMICustomerHolder> allCustomers;
    private final Map<Identifier, CommonCustomerHolder> commonCustomerMap;
    private final Map<Identifier, RareCustomerHolder> rareCustomerMap;
    private final Map<Identifier, NMICustomerHolder> allCustomerMap;

    private NMICustomerMap(List<CommonCustomerHolder> commonCustomers, List<RareCustomerHolder> rareCustomers) {
        this.commonCustomers = List.copyOf(commonCustomers);
        this.rareCustomers = List.copyOf(rareCustomers);
        this.allCustomers = buildAllCustomerList(commonCustomers, rareCustomers);
        this.commonCustomerMap = commonCustomers.stream().collect(Collectors.toMap(CommonCustomerHolder::key, c -> c));
        this.rareCustomerMap = rareCustomers.stream().collect(Collectors.toMap(RareCustomerHolder::key, c -> c));
        this.allCustomerMap = Stream.<NMICustomerHolder>concat(commonCustomers.stream(), rareCustomers.stream()).collect(Collectors.toMap(NMICustomerHolder::key, c -> c));
    }
    
    public static NMICustomerMap create(List<CommonCustomerHolder> commonCustomers, List<RareCustomerHolder> rareCustomers) {
        return new NMICustomerMap(commonCustomers, rareCustomers);
    }
    
    private List<NMICustomerHolder> buildAllCustomerList(List<CommonCustomerHolder> commonCustomers, List<RareCustomerHolder> rareCustomers) {
        return Stream.<NMICustomerHolder>concat(commonCustomers.stream(), rareCustomers.stream()).toList();
    }
}
