package icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@Getter
public abstract class IzakayaCookingTagEvent extends IzakayaCookingEvent{

    private final ItemTagList cuisine;
    private final List<ItemTagList> additional;

    public IzakayaCookingTagEvent(Player player, AbstractKitchenwareBE kitchenwareBE, ItemTagList cuisine, List<ItemTagList> additional) {
        super(player, kitchenwareBE);
        this.cuisine = cuisine;
        this.additional = additional;
    }

    @Getter
    @Setter
    public static class Check extends IzakayaCookingTagEvent{

        private boolean hasConflict;

        public Check(Player player, AbstractKitchenwareBE kitchenwareBE, ItemTagList cuisine, List<ItemTagList> additional,boolean hasConflict) {
            super(player, kitchenwareBE, cuisine, additional);
            this.hasConflict = hasConflict;
        }

    }

    @Getter
    @Setter
    public static class Collect extends IzakayaCookingTagEvent{

        List<Identifier> result;

        public Collect(Player player, AbstractKitchenwareBE kitchenwareBE, ItemTagList cuisine, List<ItemTagList> additional) {
            super(player, kitchenwareBE, cuisine, additional);
        }
    }
}
