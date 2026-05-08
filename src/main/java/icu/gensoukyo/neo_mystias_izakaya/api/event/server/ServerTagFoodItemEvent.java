package icu.gensoukyo.neo_mystias_izakaya.api.event.server;

import icu.gensoukyo.neo_mystias_izakaya.api.event.common.TagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

@Getter
public class ServerTagFoodItemEvent extends TagFoodItemEvent {
    private final ServerPlayer serverPlayer;

    public ServerTagFoodItemEvent(ItemTagList itemTagList, ItemStack itemStack, ServerPlayer player) {
        super(itemTagList, itemStack, player);
        this.serverPlayer = player;
    }

}
