/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.cooking;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
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
    private final BlockEntityType<? extends AbstractKitchenwareBE> blockEntityType;
    private final BiFunction<BlockPos,BlockState, AbstractKitchenwareBE> blockEntitySupplier;

    public Kitchenware(TagKey<Block> blockTagKey, Item kitchenwareItem, Identifier kitchenwareTag,BlockEntityType<? extends AbstractKitchenwareBE> blockEntityType,  BiFunction<BlockPos,BlockState,AbstractKitchenwareBE> blockEntitySupplier) {
        this.blockTagKey = blockTagKey;
        this.kitchenwareItem = kitchenwareItem;
        this.kitchenwareTag = kitchenwareTag;
        this.blockEntityType = blockEntityType;
        this.blockEntitySupplier = blockEntitySupplier;
    }

    public AbstractKitchenwareBE getKitchenwareBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockEntitySupplier.apply(blockPos,blockState);
    }

}
