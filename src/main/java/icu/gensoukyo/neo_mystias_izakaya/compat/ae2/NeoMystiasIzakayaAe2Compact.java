package icu.gensoukyo.neo_mystias_izakaya.compat.ae2;


import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry.NMIMEBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry.NMIMEBlocks;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry.NMIMECreativeModeTabs;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry.NMIMEItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = NeoMystiasIzakaya.MODID,depends = "ae2")
public class NeoMystiasIzakayaAe2Compact {

    public NeoMystiasIzakayaAe2Compact(IEventBus modEventBus, ModContainer modContainer) {
        NMIMEBlocks.BLOCKS.register(modEventBus);
        NMIMEItems.ITEMS.register(modEventBus);
        NMIMECreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        NMIMEBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
