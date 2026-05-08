package icu.gensoukyo.neo_mystias_izakaya.api.event.client;

import icu.gensoukyo.neo_mystias_izakaya.api.event.common.TagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import lombok.Getter;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

@Getter
public class ClientTagFoodItemEvent extends TagFoodItemEvent {

    private final LocalPlayer localPlayer;

    public ClientTagFoodItemEvent(ItemTagList itemTagList, ItemStack itemStack, LocalPlayer player) {
        super(itemTagList, itemStack, player);
        this.localPlayer = player;
    }
}
