/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.cooking;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public record Kitchenware(TagKey<Block> blockTagKey, Item kitchenwareItem,Block kitchenwareBlock,Identifier kitchenwareTag,
                          BlockEntityType<? extends KitchenwareBlockEntity> blockEntityType,
                          BiFunction<BlockPos, BlockState, KitchenwareBlockEntity> blockEntitySupplier) {

    public KitchenwareBlockEntity getKitchenwareBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockEntitySupplier.apply(blockPos, blockState);
    }


    public static final Identifier CUTTING_BOARD_ID = NeoMystiasIzakaya.id("cutting_board");
    public static final Identifier BOILING_POT_ID = NeoMystiasIzakaya.id("boiling_pot");
    public static final Identifier FRYING_PAN_ID = NeoMystiasIzakaya.id("frying_pan");
    public static final Identifier STEAMER_ID = NeoMystiasIzakaya.id("steamer");
    public static final Identifier GRILL_ID = NeoMystiasIzakaya.id("grill");
}
