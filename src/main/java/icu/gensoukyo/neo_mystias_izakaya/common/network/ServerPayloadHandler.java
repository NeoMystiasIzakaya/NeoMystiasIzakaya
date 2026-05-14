/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class ServerPayloadHandler {
    public static void handleKitchenwareCookMessage(NMIKitchenwareCookMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(message.blockPos());
            if (blockEntity instanceof AbstractKitchenwareBE kitchenware) {
                NMIRecipe cuisine = message.cuisine();
                List<ItemTagList> additionalList = kitchenware.getIngredientItems().stream()
                        .filter(stack -> cuisine.input().stream()
                                .noneMatch(ingredient -> ingredient.test(stack)))
                        .map(NMIServerItemTagUtil::get)
                        .toList();
                ItemStack stack = cuisine.output().create();
                ItemTagList cuisineList = NMIServerItemTagUtil.get(stack);

                // 检查 cuisineList 的 negativeTags 与 additionalList 中的 positiveTags 是否有交集
                boolean hasConflict = additionalList.stream()
                        .anyMatch(additional ->
                                additional.positiveTags().stream().anyMatch(cuisineList.negativeTags()::contains));

                if (hasConflict) {
                    kitchenware.getItems().clear();
                    kitchenware.setTargetItem(NMICuisinesItems.DARK_MATTER.toStack());
                } else {
                    // 没有冲突，计算两者的 positiveTags 的并集
                    List<Identifier> resultPositiveTags = new ArrayList<>(cuisineList.positiveTags());
                    for (ItemTagList additional : additionalList) {
                        for (Identifier tag : additional.positiveTags()) {
                            if (!resultPositiveTags.contains(tag)) {
                                resultPositiveTags.add(tag);
                            }
                        }
                    }
                    kitchenware.getItems().clear();
                    NMIServerItemTagUtil.set(stack, new ItemTagList(resultPositiveTags, cuisineList.negativeTags()));
                    kitchenware.setTargetItem(stack);
                }

                kitchenware.setCookingTime(message.time() * 20);
                kitchenware.setTotalCookingTime(message.time() * 20);
                if (blockEntity.getLevel() != null) {
                    blockEntity.getLevel().setBlock(message.blockPos(), blockEntity.getBlockState().setValue(BlockStateProperties.LIT, true), Block.UPDATE_CLIENTS);
                }
            }
        });
    }
}
