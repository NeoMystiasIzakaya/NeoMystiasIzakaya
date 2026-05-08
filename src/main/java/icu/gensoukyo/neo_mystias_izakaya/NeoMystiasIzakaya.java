package icu.gensoukyo.neo_mystias_izakaya;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMiscItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIDrinkItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIFoodItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
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
        NMIMiscItems.ITEMS.register(modEventBus);
        NMIDrinkItems.ITEMS.register(modEventBus);
        NMIFoodItems.ITEMS.register(modEventBus);
        NMIIngredientItems.ITEMS.register(modEventBus);
        NMIDataComponentTypes.DATA_COMPONENT_TYPES.register(modEventBus);
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
