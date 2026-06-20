/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.cooking;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

@Getter
public class Kitchenware {

    private final TagKey<Block> blockTagKey;
    private final Item kitchenwareItem;
    private final Identifier kitchenwareTag;
    private final BlockEntityType<? extends KitchenwareBlockEntity> blockEntityType;
    private final BiFunction<BlockPos,BlockState, KitchenwareBlockEntity> blockEntitySupplier;

    public Kitchenware(TagKey<Block> blockTagKey, Item kitchenwareItem, Identifier kitchenwareTag, BlockEntityType<? extends KitchenwareBlockEntity> blockEntityType, BiFunction<BlockPos,BlockState, KitchenwareBlockEntity> blockEntitySupplier) {
        this.blockTagKey = blockTagKey;
        this.kitchenwareItem = kitchenwareItem;
        this.kitchenwareTag = kitchenwareTag;
        this.blockEntityType = blockEntityType;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public KitchenwareBlockEntity getKitchenwareBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockEntitySupplier.apply(blockPos,blockState);
    }


    public static final Identifier CUTTING_BOARD = NeoMystiasIzakaya.id("cutting_board");
    public static final Identifier BOILING_POT = NeoMystiasIzakaya.id("boiling_pot");
    public static final Identifier FRYING_PAN = NeoMystiasIzakaya.id("frying_pan");
    public static final Identifier STEAMER = NeoMystiasIzakaya.id("steamer");
    public static final Identifier GRILL = NeoMystiasIzakaya.id("grill");
}
