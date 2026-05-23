package icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
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
    private final AbstractKitchenwareBE kitchenwareBE;

    public IzakayaCookingEvent(Player player, AbstractKitchenwareBE kitchenwareBE) {
        this.player = player;
        this.kitchenwareBE = kitchenwareBE;
    }


    public abstract static class SpawnDarkMatter extends IzakayaCookingEvent {

        public SpawnDarkMatter(Player player, AbstractKitchenwareBE kitchenwareBE) {
            super(player, kitchenwareBE);
        }

        public static class Pre extends SpawnDarkMatter implements ICancellableEvent {

            public Pre(Player player, AbstractKitchenwareBE kitchenwareBE) {
                super(player, kitchenwareBE);
            }
        }

        public static class Post extends SpawnDarkMatter {

            public Post(Player player, AbstractKitchenwareBE kitchenwareBE) {
                super(player, kitchenwareBE);
            }
        }

    }

    @Getter
    @Setter
    public static class SpawnResult extends IzakayaCookingEvent {

        private final ItemStack result;

        public SpawnResult(Player player, AbstractKitchenwareBE kitchenwareBE, ItemStack result) {
            super(player, kitchenwareBE);
            this.result = result;
        }

        public static class Pre extends SpawnResult implements ICancellableEvent {

            public Pre(Player player, AbstractKitchenwareBE kitchenwareBE, ItemStack result) {
                super(player, kitchenwareBE, result);
            }
        }

        public static class Post extends SpawnResult {

            public Post(Player player, AbstractKitchenwareBE kitchenwareBE, ItemStack result) {
                super(player, kitchenwareBE, result);
            }
        }
    }

    @Getter
    @Setter
    public static class ConsumeIngredients extends IzakayaCookingEvent {
        private final NonNullList<ItemStack> ingredients;

        public ConsumeIngredients(Player player, AbstractKitchenwareBE kitchenwareBE, NonNullList<ItemStack> ingredients) {
            super(player, kitchenwareBE);
            this.ingredients = ingredients;
        }
    }

    @Getter
    @Setter
    public static class SetCookingTime extends IzakayaCookingEvent {
        private final int cookingTimeTick;
        private final int cookingTotalTimeTick;

        public SetCookingTime(Player player, AbstractKitchenwareBE kitchenwareBE, int cookingTime) {
            super(player, kitchenwareBE);
            this.cookingTimeTick = cookingTime * 20;
            this.cookingTotalTimeTick = cookingTime * 20;
        }
    }
}
