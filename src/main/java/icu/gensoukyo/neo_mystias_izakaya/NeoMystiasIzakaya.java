package icu.gensoukyo.neo_mystias_izakaya;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.registry.ItemRegistry;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(NeoMystiasIzakaya.MODID)
public class NeoMystiasIzakaya {
    public static final String MODID = "neo_mystias_izakaya";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NeoMystiasIzakaya(IEventBus modEventBus, ModContainer modContainer) {
        ItemRegistry.ITEMS.register(modEventBus);
    }

    public static Identifier id(String key){
        return Identifier.fromNamespaceAndPath(MODID,key);
    }

    public static String ids(String key){
        return Identifier.fromNamespaceAndPath(MODID,key).toString();
    }

    public static String path(String path){
        return MODID + "/" + path;
    }
}
