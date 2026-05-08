package icu.gensoukyo.neo_mystias_izakaya.api.event.common;

import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import lombok.Getter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

@Getter
public abstract class TagFoodItemEvent extends Event {
    private final ItemTagList itemTagList;
    private final ItemStack itemStack;
    private final Player player;

    public TagFoodItemEvent(ItemTagList itemTagList, ItemStack itemStack, Player player) {
        this.itemTagList = itemTagList;
        this.itemStack = itemStack;
        this.player = player;
    }

}
