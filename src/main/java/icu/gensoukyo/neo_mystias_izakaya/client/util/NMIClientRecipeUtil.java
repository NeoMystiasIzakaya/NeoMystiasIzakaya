/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking.IzakayaRecipeEvent;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonItemStackUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class NMIClientRecipeUtil {

    public static List<NMIRecipeHolder> getRecipesByInput(@Nullable Player player, List<ItemStack> input) {
        Set<Identifier> recipesIds = new HashSet<>();
        for (ItemStack stack : input) {
            List<Identifier> identifiers = NMIDataAccessor.client().getRecipeMap().getInputItemToRecipeMap().get(NMICommonItemStackUtil.get(stack));
            if (identifiers != null) {
                recipesIds.addAll(identifiers);
            }
        }
        // 验证所有原料均可被 input 列表满足
        recipesIds.removeIf(id -> canCraft(id, input));
        IzakayaRecipeEvent.Collect post = NeoForge.EVENT_BUS.post(new IzakayaRecipeEvent.Collect(player, new ArrayList<>(recipesIds), null, input));;
        return getRecipes(new ArrayList<>(post.getRecipes()));
    }

    public static List<NMIRecipeHolder> getRecipesByOutput(@Nullable Player player, Identifier output) {
        List<Identifier> recipesIds = NMIDataAccessor.client().getRecipeMap().getOutputItemToRecipeMap().get(output);
        return getRecipes(recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipesByOutputAndKitchenware(@Nullable Player player, Identifier output, TagKey<Block> kitchenware) {
        List<Identifier> byOutput = NMIDataAccessor.client().getRecipeMap().getOutputItemToRecipeMap().get(output);
        List<Identifier> byKitchenware = NMIDataAccessor.client().getRecipeMap().getKitchenwareToRecipeMap().get(kitchenware);

        // 取交集
        List<Identifier> recipesIds = new ArrayList<>();
        if (byOutput != null && byKitchenware != null) {
            for (Identifier id : byOutput) {
                if (byKitchenware.contains(id)) {
                    recipesIds.add(id);
                }
            }
        }

        return getRecipes(recipesIds);
    }


    public static List<NMIRecipeHolder> getRecipesByKitchenware(@Nullable Player player, TagKey<Block> kitchenware) {
        List<Identifier> recipesIds = NMIDataAccessor.client().getRecipeMap().getKitchenwareToRecipeMap().get(kitchenware);
        IzakayaRecipeEvent.Collect post = NeoForge.EVENT_BUS.post(new IzakayaRecipeEvent.Collect(player, recipesIds, kitchenware, List.of()));
        return getRecipes(post.getRecipes());
    }

    public static List<NMIRecipeHolder> getRecipesByInputAndKitchenware(@Nullable Player player, List<ItemStack> input, TagKey<Block> kitchenware) {
        Set<Identifier> inputRecipeIds = new HashSet<>();
        for (ItemStack stack : input) {
            List<Identifier> identifiers = NMIDataAccessor.client().getRecipeMap().getInputItemToRecipeMap().get(NMICommonItemStackUtil.get(stack));
            if (identifiers != null) {
                inputRecipeIds.addAll(identifiers);
            }
        }

        List<Identifier> kitchenwareRecipeIds = NMIDataAccessor.client().getRecipeMap().getKitchenwareToRecipeMap().get(kitchenware);
        if (kitchenwareRecipeIds != null) {
            inputRecipeIds.retainAll(kitchenwareRecipeIds);
        } else {
            inputRecipeIds.clear();
        }
        // 验证所有原料均可被 input 列表满足
        inputRecipeIds.removeIf(id -> canCraft(id, input));

        IzakayaRecipeEvent.Collect post = NeoForge.EVENT_BUS.post(new IzakayaRecipeEvent.Collect(player, new ArrayList<>(inputRecipeIds), kitchenware, input));
        return getRecipes(new ArrayList<>(post.getRecipes()));
    }

    public static List<NMIRecipeHolder> getRecipes(List<Identifier> recipesIds) {
        if (recipesIds == null) {
            return List.of();
        }
        List<NMIRecipeHolder> recipes = new ArrayList<>(recipesIds.size());
        recipesIds.forEach(id -> {
            NMIRecipeHolder recipe = NMIDataAccessor.client().getRecipeMap().getRecipeMap().get(id);
            if (recipe != null) {
                recipes.add(recipe);
            }
        });
        return recipes;
    }

    public static NMIRecipeHolder getRecipe(Identifier id) {
        return NMIDataAccessor.client().getRecipeMap().getRecipeMap().get(id);
    }

    /**
     * 检查配方是否可被给定的物品列表合成
     * @param recipeId 配方 ID
     * @param availableItems 可用的物品列表
     * @return 当配方的所有原料槽位都能被 availableItems 中至少一个物品满足时返回 true
     */
    private static boolean canCraft(Identifier recipeId, List<ItemStack> availableItems) {
        if (availableItems.isEmpty()) return true;
        NMIRecipeHolder holder = NMIDataAccessor.client().getRecipeMap().getRecipeMap().get(recipeId);
        if (holder == null) return true;
        return !holder.recipe().input().stream()
                .allMatch(ingredient -> availableItems.stream().anyMatch(ingredient));
    }

}
