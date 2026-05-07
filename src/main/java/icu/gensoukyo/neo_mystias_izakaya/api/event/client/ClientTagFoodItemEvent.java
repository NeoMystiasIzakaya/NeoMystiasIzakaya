package icu.gensoukyo.neo_mystias_izakaya.api.event.client;

import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import lombok.Getter;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class ClientTagFoodItemEvent extends Event {
    @Getter
    private final TagItemListHolder tagItemListHolder;
    @Getter
    private final ItemStack itemStack;
    @Getter
    private final LocalPlayer player;

    public ClientTagFoodItemEvent(TagItemListHolder tagItemListHolder, ItemStack itemStack, LocalPlayer player) {
        this.tagItemListHolder = tagItemListHolder;
        this.itemStack = itemStack;
        this.player = player;
    }

}
