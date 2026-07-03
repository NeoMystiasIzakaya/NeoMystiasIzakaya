/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@Getter
public abstract class GetAdditionalItemsEvent extends IzakayaCookingEvent {

    private final NMIRecipe recipe;
    private final List<ItemStack> inputs;

    protected GetAdditionalItemsEvent(LivingEntity player, KitchenwareBlockEntity kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs) {
        super(player, kitchenwareBE);
        this.recipe = recipe;
        this.inputs = inputs;
    }

    public static class Pre extends GetAdditionalItemsEvent {
        public Pre(LivingEntity player, KitchenwareBlockEntity kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs) {
            super(player, kitchenwareBE, recipe, inputs);
        }
    }

    @Setter
    @Getter
    public static class Post extends GetAdditionalItemsEvent {
        private final List<ItemStack> additionalItems;
        public Post(LivingEntity player, KitchenwareBlockEntity kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs, List<ItemStack> additionalItems) {
            super(player, kitchenwareBE, recipe, inputs);
            this.additionalItems = additionalItems;
        }
    }
}
