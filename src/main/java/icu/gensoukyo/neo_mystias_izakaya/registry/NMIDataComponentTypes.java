package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class NMIDataComponentTypes {

    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NeoMystiasIzakaya.MODID);


    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<ItemTagList>> ITEM_TAG_LIST = DATA_COMPONENT_TYPES.registerComponentType(
            "item_tag_list",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(ItemTagList.CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(ItemTagList.STREAM_CODEC)
    );

}
