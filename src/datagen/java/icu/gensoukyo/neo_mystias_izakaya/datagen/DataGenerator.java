package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID, value = Dist.CLIENT)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var vanillaPack = generator.getVanillaPack(true);

        event.createProvider(output -> new ModLanguage(output, "en_us"));
        event.createProvider(output -> new ModLanguage(output, "zh_cn"));
        event.createProvider(output -> new ModTagItemList(output, NeoMystiasIzakaya.MODID));

        event.createProvider(ModItem::new);
    }
}
