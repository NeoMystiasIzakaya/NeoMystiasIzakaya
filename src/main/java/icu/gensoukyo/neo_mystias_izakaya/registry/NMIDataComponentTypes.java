/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import com.mojang.serialization.Codec;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
     * 厨房配置工具已绑定的控制器坐标
     */
    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<BlockPos>> BOUND_CONTROLLER = DATA_COMPONENT_TYPES.registerComponentType(
            "bound_controller",
            builder -> builder
                    .persistent(BlockPos.CODEC)
                    .networkSynchronized(BlockPos.STREAM_CODEC)
    );

    /**
     * 厨房配置工具已绑定的厨房用具坐标列表
     */
    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<List<BlockPos>>> BOUND_KITCHENWARE = DATA_COMPONENT_TYPES.registerComponentType(
            "bound_kitchenware",
            builder -> builder
                    .persistent(BlockPos.CODEC.listOf())
                    .networkSynchronized(BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()))
    );

    /**
     * 厨房配置工具已绑定的餐桌坐标列表
     */
    public static final DeferredHolder<DataComponentType<?>, @NotNull DataComponentType<List<BlockPos>>> BOUND_DINING_TABLES = DATA_COMPONENT_TYPES.registerComponentType(
            "bound_dining_tables",
            builder -> builder
                    .persistent(BlockPos.CODEC.listOf())
                    .networkSynchronized(BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()))
    );
}
