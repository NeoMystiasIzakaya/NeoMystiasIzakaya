/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ServerPayloadHandler {
    public static void handleKitchenwareCookMessage(NMIKitchenwareCookMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(message.blockPos());
            if (blockEntity instanceof AbstractKitchenwareBE kitchenware) {
                NMIRecipe cuisine = message.cuisine();
                List<ItemTagList> additionalList = kitchenware.getIngredientItems().stream()
                        .filter(stack -> cuisine.input().stream().noneMatch(ingredient -> ingredient.test(stack)))
                        .map(NMIServerItemTagUtil::get)
                        .toList();
                ItemTagList cuisineList = NMIServerItemTagUtil.get(cuisine.output().create());


                kitchenware.getItems().clear();
                kitchenware.setTargetItem(cuisine.output().create());
                kitchenware.setCookingTime(message.time() * 20);
                kitchenware.setTotalCookingTime(message.time() * 20);
                if (blockEntity.getLevel() != null) {
                    blockEntity.getLevel().setBlock(message.blockPos(), blockEntity.getBlockState().setValue(BlockStateProperties.LIT, true), Block.UPDATE_CLIENTS);
                }
            }
        });
    }
}
