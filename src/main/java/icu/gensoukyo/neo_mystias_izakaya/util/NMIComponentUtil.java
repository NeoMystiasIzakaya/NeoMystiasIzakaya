package icu.gensoukyo.neo_mystias_izakaya.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class NMIComponentUtil {

    public static MutableComponent translatableTag(Identifier tag){
        return Component.translatable(tag.toLanguageKey("tag"));
    }
}
