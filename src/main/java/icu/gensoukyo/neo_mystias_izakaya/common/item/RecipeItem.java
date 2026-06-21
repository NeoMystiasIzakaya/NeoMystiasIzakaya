/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.IzakayaCookingUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.Kitchenware;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Consumer;

public class RecipeItem extends Item {
    public RecipeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        BlockPos clickedPos = context.getClickedPos();
        Level level = player.level();
        if (level.getBlockEntity(clickedPos) instanceof KitchenwareBlockEntity kitchenware) {
            if (!level.isClientSide()) {
                ItemStack stack = context.getItemInHand();
                Identifier recipeKey = stack.get(NMIDataComponentTypes.RECORDED_RECIPE);
                if (recipeKey == null) {
                    player.sendSystemMessage(Component.translatable("gui.neo_mystias_izakaya.not_recorded"));
                    return InteractionResult.FAIL;
                }

                // 获取食谱
                NMIRecipeHolder holder = NMIDataAccessor.server()
                        .getRecipeMap().getRecipeMap().get(recipeKey);
                if (holder == null) return InteractionResult.FAIL;
                NMIRecipe recipe = holder.recipe();

                // 检查厨具类型是否匹配
                var kitchenwareObj = NMIKitchenware.REGISTRY.getValue(kitchenware.getKitchenwareTypeId());
                if (kitchenwareObj == null || !kitchenwareObj.blockTagKey().equals(recipe.kitchenware())) {
                    player.sendSystemMessage(Component.translatable("gui.neo_mystias_izakaya.wrong_kitchenware"));
                    return InteractionResult.FAIL;
                }

                // 检查厨具是否空闲
                if (!kitchenware.canStartCooking()) {
                    player.sendSystemMessage(Component.translatable("gui.neo_mystias_izakaya.kitchenware_busy"));
                    return InteractionResult.FAIL;
                }

                // 从背包查找食材
                List<Ingredient> requiredInputs = recipe.input();
                NonNullList<ItemStack> ingredients = NonNullList.withSize(5, ItemStack.EMPTY);
                boolean allFound = true;

                for (int i = 0; i < requiredInputs.size() && i < 5; i++) {
                    Ingredient ingredient = requiredInputs.get(i);
                    boolean found = false;
                    for (ItemStack invStack : player.getInventory().getNonEquipmentItems()) {
                        if (ingredient.test(invStack)) {
                            ItemStack consumed = invStack.split(1);
                            ingredients.set(i, consumed);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        allFound = false;
                        break;
                    }
                }

                if (!allFound) {
                    // 归还已消耗的物品
                    for (ItemStack ing : ingredients) {
                        if (!ing.isEmpty()) {
                            player.getInventory().add(ing);
                        }
                    }
                    player.sendSystemMessage(Component.translatable("gui.neo_mystias_izakaya.missing_ingredients"));
                    return InteractionResult.FAIL;
                }

                // 放入厨具
                kitchenware.setIngredients(ingredients);

                if (player.isShiftKeyDown()) {
                    IzakayaCookingUtil.processCooking(player, recipeKey, clickedPos);
                    player.sendSystemMessage(Component.translatable("gui.neo_mystias_izakaya.ingredients_placed_started"));
                } else {
                    player.sendSystemMessage(Component.translatable("gui.neo_mystias_izakaya.ingredients_placed"));
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public @NonNull InteractionResult use(Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (level.isClientSide()) {
            NMIClientUtil.openRecipeScreen();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext context, @NonNull TooltipDisplay display, @NonNull Consumer<Component> builder, @NonNull TooltipFlag tooltipFlag) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        Identifier recipeKey = itemStack.get(NMIDataComponentTypes.RECORDED_RECIPE);
        if (recipeKey == null) {
            builder.accept(Component.translatable("gui.neo_mystias_izakaya.recorded_recipe")
                    .withStyle(ChatFormatting.GOLD));
            builder.accept(Component.literal("  ")
                    .append(Component.translatable("gui.neo_mystias_izakaya.not_recorded"))
                    .withStyle(ChatFormatting.GRAY));
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

        // 所需厨具（从 REGISTRY 中查找正确的显示 tag）
        var kitchenwareTag = NMIKitchenware.REGISTRY.stream()
                .filter(kw -> kw.blockTagKey().equals(recipe.kitchenware()))
                .findFirst()
                .map(Kitchenware::kitchenwareTag)
                .orElse(recipe.kitchenware().location());
        builder.accept(Component.literal("    -- ")
                .append(NMICommonComponentUtil.translatableTag(kitchenwareTag))
                .withStyle(ChatFormatting.DARK_AQUA));

        // 所需食材
        for (Ingredient ingredient : recipe.input()) {
            HolderSet<Item> values = ingredient.getValues();
            if (values.size() > 0) {
                Item ingredientItem = values.get(0).value();
                ChatFormatting formatting = player.getInventory().contains(new ItemStack(ingredientItem)) ? ChatFormatting.GREEN : ChatFormatting.RED;
                builder.accept(Component.literal("    └ ")
                        .append(Component.translatable(ingredientItem.getDescriptionId()))
                        .withStyle(formatting));
            }
        }
    }
}
