package icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

import java.util.List;

@Getter
public abstract class GetAdditionalItemsEvent extends IzakayaCookingEvent {

    private final NMIRecipe recipe;
    private final List<ItemStack> inputs;

    protected GetAdditionalItemsEvent(Player player, AbstractKitchenwareBE kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs) {
        super(player, kitchenwareBE);
        this.recipe = recipe;
        this.inputs = inputs;
    }

    public static class Pre extends GetAdditionalItemsEvent {
        public Pre(Player player, AbstractKitchenwareBE kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs) {
            super(player, kitchenwareBE, recipe, inputs);
        }
    }

    @Setter
    @Getter
    public static class Post extends GetAdditionalItemsEvent {
        private final List<ItemStack> additionalItems;
        public Post(Player player, AbstractKitchenwareBE kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs, List<ItemStack> additionalItems) {
            super(player, kitchenwareBE, recipe, inputs);
            this.additionalItems = additionalItems;
        }
    }
}
