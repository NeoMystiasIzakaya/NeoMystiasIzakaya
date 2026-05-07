package icu.gensoukyo.neo_mystias_izakaya.api.event;

import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import lombok.Getter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class TagFoodItemEvent extends Event {
    @Getter
    private final TagItemListHolder tagItemListHolder;
    @Getter
    private final ItemStack itemStack;
    @Getter
    private final Player player;
    @Getter
    private final boolean client;

    public TagFoodItemEvent(TagItemListHolder tagItemListHolder, ItemStack itemStack, Player player, boolean client) {
        this.tagItemListHolder = tagItemListHolder;
        this.itemStack = itemStack;
        this.player = player;
        this.client = client;
    }

}
