/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import com.mojang.serialization.Codec;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigData;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
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


    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<Integer>> PRICE = DATA_COMPONENT_TYPES.registerComponentType(
            "price",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
    );

    /**
     * 食堂配置工具/帽子的全部绑定数据（控制器、厨具、餐桌、橱柜、角点）。
     */
    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<CanteenConfigData>> CANTEEN_CONFIG = DATA_COMPONENT_TYPES.registerComponentType(
            "canteen_config",
            builder -> builder
                    .persistent(CanteenConfigData.CODEC)
                    .networkSynchronized(CanteenConfigData.STREAM_CODEC)
    );

    /**
     * 食谱书中当前记录的食谱标识符
     */
    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<Identifier>> RECORDED_RECIPE = DATA_COMPONENT_TYPES.registerComponentType(
            "recorded_recipe",
            builder -> builder
                    .persistent(Identifier.CODEC)
                    .networkSynchronized(Identifier.STREAM_CODEC)
    );
}
