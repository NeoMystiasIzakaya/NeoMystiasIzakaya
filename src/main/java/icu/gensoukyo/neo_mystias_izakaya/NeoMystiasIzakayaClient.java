package icu.gensoukyo.neo_mystias_izakaya;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NeoMystiasIzakaya.MODID, dist = Dist.CLIENT)
public class NeoMystiasIzakayaClient {
    public NeoMystiasIzakayaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
