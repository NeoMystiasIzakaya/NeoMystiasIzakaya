/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlocks;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NMIItemModelProvider extends ModelProvider {
    public NMIItemModelProvider(PackOutput output) {
        super(output, NeoMystiasIzakaya.MODID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(NMIMainItems.CHROME_BALL.asItem(), ModelTemplates.FLAT_ITEM);

        this.registerBlockModels(blockModels);
        this.registerItemModels(itemModels);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {
        horizontallyBlock(blockModels, NMIBlocks.BOILING_POT.get());
        horizontallyBlock(blockModels, NMIBlocks.GRILL.get());
        horizontallyBlock(blockModels, NMIBlocks.FRYING_PAN.get());
        horizontallyBlock(blockModels, NMIBlocks.STEAMER.get());
        horizontallyBlock(blockModels, NMIBlocks.CUTTING_BOARD.get());
    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        for (List<DeferredItem<Item>> items : List.of(NMIBeveragesItems.ITEM_LIST, NMICuisinesItems.ITEM_LIST, NMIIngredientItems.ITEM_LIST)) {
            for (DeferredItem<Item> item : items) {
                itemModels.generateFlatItem(item.asItem(), ModelTemplates.FLAT_ITEM);
            }
        }
    }


    private static void horizontallyBlock(BlockModelGenerators blockModels, Block block) {
        Variant variant = new Variant(ModelLocationUtils.getModelLocation(block));
        blockModels.blockStateOutput.accept(MultiVariantGenerator
                .dispatch(block, BlockModelGenerators.variant(variant))
                .with(PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.EAST, BlockModelGenerators.NOP)
                        .select(Direction.NORTH, BlockModelGenerators.NOP)
                        .select(Direction.SOUTH, BlockModelGenerators.NOP)
                        .select(Direction.WEST, BlockModelGenerators.NOP)
                ));
    }
}
