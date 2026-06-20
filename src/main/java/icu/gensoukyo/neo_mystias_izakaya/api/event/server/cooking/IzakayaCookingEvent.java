/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

@Getter
public abstract class IzakayaCookingEvent extends Event {
    private final Player player;
    private final KitchenwareBlockEntity kitchenwareBE;

    public IzakayaCookingEvent(Player player, KitchenwareBlockEntity kitchenwareBE) {
        this.player = player;
        this.kitchenwareBE = kitchenwareBE;
    }


    public abstract static class SpawnDarkMatter extends IzakayaCookingEvent {

        public SpawnDarkMatter(Player player, KitchenwareBlockEntity kitchenwareBE) {
            super(player, kitchenwareBE);
        }

        public static class Pre extends SpawnDarkMatter implements ICancellableEvent {

            public Pre(Player player, KitchenwareBlockEntity kitchenwareBE) {
                super(player, kitchenwareBE);
            }
        }

        public static class Post extends SpawnDarkMatter {

            public Post(Player player, KitchenwareBlockEntity kitchenwareBE) {
                super(player, kitchenwareBE);
            }
        }

    }

    @Getter
    @Setter
    public static class SpawnResult extends IzakayaCookingEvent {

        private final ItemStack result;

        public SpawnResult(Player player, KitchenwareBlockEntity kitchenwareBE, ItemStack result) {
            super(player, kitchenwareBE);
            this.result = result;
        }

        public static class Pre extends SpawnResult implements ICancellableEvent {

            public Pre(Player player, KitchenwareBlockEntity kitchenwareBE, ItemStack result) {
                super(player, kitchenwareBE, result);
            }
        }

        public static class Post extends SpawnResult {

            public Post(Player player, KitchenwareBlockEntity kitchenwareBE, ItemStack result) {
                super(player, kitchenwareBE, result);
            }
        }
    }

    @Getter
    public abstract static class ConsumeIngredients extends IzakayaCookingEvent {
        private final NonNullList<ItemStack> originIngredients;
        @Setter
        private NonNullList<ItemStack> resultIngredients;

        public ConsumeIngredients(Player player, KitchenwareBlockEntity kitchenwareBE, NonNullList<ItemStack> originIngredients, NonNullList<ItemStack> resultIngredients) {
            super(player, kitchenwareBE);
            this.originIngredients = originIngredients;
            this.resultIngredients = resultIngredients;
        }

        public static class Pre extends ConsumeIngredients implements ICancellableEvent {

            public Pre(Player player, KitchenwareBlockEntity kitchenwareBE, NonNullList<ItemStack> ingredients, NonNullList<ItemStack> consumedIngredients) {
                super(player, kitchenwareBE, ingredients, consumedIngredients);
            }
        }

        public static class Post extends ConsumeIngredients {

            public Post(Player player, KitchenwareBlockEntity kitchenwareBE, NonNullList<ItemStack> ingredients, NonNullList<ItemStack> consumedIngredients) {
                super(player, kitchenwareBE, ingredients, consumedIngredients);
            }
        }
    }

    @Getter
    @Setter
    public static class SetCookingTime extends IzakayaCookingEvent {
        private final int cookingTimeTick;
        private final int cookingTotalTimeTick;

        public SetCookingTime(Player player, KitchenwareBlockEntity kitchenwareBE, int cookingTime) {
            super(player, kitchenwareBE);
            this.cookingTimeTick = cookingTime * 20;
            this.cookingTotalTimeTick = cookingTime * 20;
        }
    }

    public static class Trigger extends IzakayaCookingEvent implements ICancellableEvent{
        public Trigger(Player player, KitchenwareBlockEntity kitchenwareBE) {
            super(player, kitchenwareBE);
        }
    }

}
