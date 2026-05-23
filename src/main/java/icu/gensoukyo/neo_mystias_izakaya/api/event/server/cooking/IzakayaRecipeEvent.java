package icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class IzakayaRecipeEvent extends Event {

    private final Player player;

    public IzakayaRecipeEvent(Player player) {
        this.player = player;
    }

    @Getter
    public static class Collect extends IzakayaRecipeEvent {
        @Setter
        private List<Identifier> recipes;

        @Nullable
        private final TagKey<Block> kitchenware;
        private final List<ItemStack> inputs;
        public Collect(Player player, List<Identifier> recipes,@Nullable TagKey<Block> kitchenware, List<ItemStack> inputs) {
            super(player);
            this.recipes = recipes;
            this.kitchenware = kitchenware;
            this.inputs = inputs;
        }
    }
}
