/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 食堂配置工具（CanteenConfigItem / 帽子）携带的全部绑定数据。
 * <p>作为单个 DataComponent 存储，替代之前分散的 6 个组件。</p>
 *
 * @param controller      已绑定的控制器坐标（null 表示未选择）
 * @param kitchenwareList 已绑定的厨房用具坐标列表
 * @param diningTableList 已绑定的餐桌坐标列表
 * @param cupboardList    已绑定的橱柜坐标列表
 * @param cornerA         区域扫描角点 A（null 表示未设置）
 * @param cornerB         区域扫描角点 B（null 表示未设置）
 */
public record CanteenConfigData(
        @Nullable BlockPos controller,
        List<BlockPos> kitchenwareList,
        List<BlockPos> diningTableList,
        List<BlockPos> cupboardList,
        @Nullable BlockPos cornerA,
        @Nullable BlockPos cornerB
) {
    /** 空配置 */
    public static final CanteenConfigData EMPTY = new CanteenConfigData(
            null, List.of(), List.of(), List.of(), null, null
    );

    public static final Codec<CanteenConfigData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockPos.CODEC.optionalFieldOf("controller").forGetter(d -> java.util.Optional.ofNullable(d.controller)),
                    BlockPos.CODEC.listOf().optionalFieldOf("kitchenware_list", List.of()).forGetter(CanteenConfigData::kitchenwareList),
                    BlockPos.CODEC.listOf().optionalFieldOf("dining_table_list", List.of()).forGetter(CanteenConfigData::diningTableList),
                    BlockPos.CODEC.listOf().optionalFieldOf("cupboard_list", List.of()).forGetter(CanteenConfigData::cupboardList),
                    BlockPos.CODEC.optionalFieldOf("corner_a").forGetter(d -> java.util.Optional.ofNullable(d.cornerA)),
                    BlockPos.CODEC.optionalFieldOf("corner_b").forGetter(d -> java.util.Optional.ofNullable(d.cornerB))
            ).apply(instance, (ctrl, kw, dt, cb, ca, cb2) ->
                    new CanteenConfigData(ctrl.orElse(null), kw, dt, cb, ca.orElse(null), cb2.orElse(null)))
    );

    public static final StreamCodec<ByteBuf, CanteenConfigData> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional), d -> java.util.Optional.ofNullable(d.controller),
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), CanteenConfigData::kitchenwareList,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), CanteenConfigData::diningTableList,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), CanteenConfigData::cupboardList,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional), d -> java.util.Optional.ofNullable(d.cornerA),
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional), d -> java.util.Optional.ofNullable(d.cornerB),
            (ctrl, kw, dt, cb, ca, cb2) -> new CanteenConfigData(
                    ctrl.orElse(null), kw, dt, cb, ca.orElse(null), cb2.orElse(null))
    );

    // ==================== 便捷方法 ====================

    /** 以指定控制器创建新配置（保留其他字段重置），用于 syncControllerData */
    public CanteenConfigData withController(@Nullable BlockPos newController) {
        return new CanteenConfigData(newController, kitchenwareList, diningTableList, cupboardList, cornerA, cornerB);
    }

    /** 设置控制器并用新的列表填充 */
    public CanteenConfigData withController(BlockPos controllerPos,
                                            List<BlockPos> kitchenware,
                                            List<BlockPos> diningTables,
                                            List<BlockPos> cupboards) {
        return new CanteenConfigData(controllerPos,
                new ArrayList<>(kitchenware),
                new ArrayList<>(diningTables),
                new ArrayList<>(cupboards),
                cornerA, cornerB);
    }

    /** 清除控制器及各列表 */
    public CanteenConfigData clearController() {
        return new CanteenConfigData(null, List.of(), List.of(), List.of(), cornerA, cornerB);
    }

    /** 设置角点 A */
    public CanteenConfigData withCornerA(BlockPos pos) {
        return new CanteenConfigData(controller, kitchenwareList, diningTableList, cupboardList, pos, cornerB);
    }

    /** 设置角点 B */
    public CanteenConfigData withCornerB(BlockPos pos) {
        return new CanteenConfigData(controller, kitchenwareList, diningTableList, cupboardList, cornerA, pos);
    }

    /** 清除角点 */
    public CanteenConfigData clearCorners() {
        return new CanteenConfigData(controller, kitchenwareList, diningTableList, cupboardList, null, null);
    }
}
