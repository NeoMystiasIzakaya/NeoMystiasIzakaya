package icu.gensoukyo.neo_mystias_izakaya.api.event.server;

import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public class ServerTagFoodItemEvent extends Event {
    @Getter
    private final TagItemListHolder tagItemListHolder;
    @Getter
    private final ItemStack itemStack;
    @Getter
    private final ServerPlayer player;

    public ServerTagFoodItemEvent(TagItemListHolder tagItemListHolder, ItemStack itemStack, ServerPlayer player) {
        this.tagItemListHolder = tagItemListHolder;
        this.itemStack = itemStack;
        this.player = player;
    }

}
