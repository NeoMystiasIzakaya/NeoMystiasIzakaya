package icu.gensoukyo.neo_mystias_izakaya.compat.jade.client;

import icu.gensoukyo.neo_mystias_izakaya.compat.jade.MystiaJadePlugin;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.server.KitchenwareData;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.server.KitchenwareServerDataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Optional;

public class KitchenwareComponentProvider implements IBlockComponentProvider {

    public static final KitchenwareComponentProvider INSTANCE = new KitchenwareComponentProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        Optional<KitchenwareData> data = KitchenwareServerDataProvider.INSTANCE.decodeFromData(blockAccessor);
        if(data.isEmpty())return;
        KitchenwareData kitchenwareData = data.get();
        iTooltip.add(Component.translatable("gui.neo_mystias_izakaya.progress").append(Component.literal(kitchenwareData.totalCookingTime()-kitchenwareData.cookingTime() + "/" + kitchenwareData.totalCookingTime())));
//        iTooltip.add();
    }

    @Override
    public Identifier getUid() {
        return MystiaJadePlugin.KITCHENWARE_DATA_PROVIDER;
    }
}
