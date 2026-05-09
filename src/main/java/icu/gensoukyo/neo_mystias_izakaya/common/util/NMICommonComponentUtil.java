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
}
