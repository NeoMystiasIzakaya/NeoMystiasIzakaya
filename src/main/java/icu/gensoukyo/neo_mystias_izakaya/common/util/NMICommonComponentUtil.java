/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public final class NMICommonComponentUtil {

    public static MutableComponent translatableTag(Identifier tag){
        return Component.translatable(tag.toLanguageKey("tag"));
    }
    public static MutableComponent translatableItemGroup(Identifier tag){
        return Component.translatable(tag.toLanguageKey("item_group"));
    }
    public static MutableComponent translatable(Identifier identifier){
        return Component.translatable(identifier.toLanguageKey());
    }
    public static MutableComponent translatable(Identifier identifier,String prefix){
        return Component.translatable(identifier.toLanguageKey(prefix));
    }
    public static MutableComponent translatableCustomer(Identifier customer){
        return Component.translatable(customer.toLanguageKey("customer"));
    }
    public static MutableComponent translatableCustomerChat(Identifier chat){
        return Component.translatable(chat.toLanguageKey("customer"));
    }
    public static MutableComponent translatableCustomerDescription(Identifier desc){
        return Component.translatable(desc.toLanguageKey("customer","desc"));
    }
    public static MutableComponent translatableCustomerDescriptionWithLevel(Identifier desc,String level){
        return Component.translatable(desc.toLanguageKey("customer","desc."+level));
    }
    public static MutableComponent translatableEvaluation(Identifier customer,String level){
        return Component.translatable(customer.toLanguageKey("evaluation",level));
    }

    public static MutableComponent unitEn(){
        return Component.translatable("unit.neo_mystias_izakaya.en");
    }
}
