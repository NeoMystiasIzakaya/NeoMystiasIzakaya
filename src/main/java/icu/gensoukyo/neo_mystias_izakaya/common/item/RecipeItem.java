/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class RecipeItem extends Item {
    public RecipeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            NMIClientUtil.openRecipeScreen();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        Identifier recipeKey = itemStack.get(NMIDataComponentTypes.RECORDED_RECIPE);
        if (recipeKey == null) {
            return;
        }

        NMIRecipeMap recipeMap = ClientNMIDataAccessor.INSTANCE.getRecipeMap();
        if (recipeMap == null) return;

        NMIRecipeHolder holder = recipeMap.getRecipeMap().get(recipeKey);
        if (holder == null) return;
        NMIRecipe recipe = holder.recipe();

        builder.accept(Component.translatable("gui.neo_mystias_izakaya.recorded_recipe")
                .withStyle(ChatFormatting.GOLD));

        // 食谱名称（输出物品名）
        builder.accept(Component.literal("  ")
                .append(Component.translatable(recipe.output().item().value().getDescriptionId()))
                .withStyle(ChatFormatting.YELLOW));

        // 所需食材
        for (Ingredient ingredient : recipe.input()) {
            HolderSet<Item> values = ingredient.getValues();
            if (values.size() > 0) {
                Item ingredientItem = values.get(0).value();
                builder.accept(Component.literal("    └ ")
                        .append(Component.translatable(ingredientItem.getDescriptionId()))
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
