package icu.gensoukyo.neo_mystias_izakaya;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.registry.ItemRegistry;
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
}
