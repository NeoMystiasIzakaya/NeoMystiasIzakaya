package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.client.ClientTagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIItemTagUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID,value = Dist.CLIENT)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();
        if (event.getEntity() instanceof LocalPlayer player) {
            ItemTagList itemTagList = NMIItemTagUtil.clientGet(item);
            NeoForge.EVENT_BUS.post(new ClientTagFoodItemEvent(itemTagList, item, player));
            NMIItemTagUtil.set(item, itemTagList);
        }
    }


    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event){
        ItemTagList itemTagList = NMIItemTagUtil.clientGet(event.getItemStack());

        List<Component> toolTip = event.getToolTip();

        itemTagList.positiveTags().forEach(e->toolTip.add(Component.literal("+ ").append(NMIComponentUtil.translatableTag(e))));
        itemTagList.negativeTags().forEach(e->toolTip.add(Component.literal("- ").append(NMIComponentUtil.translatableTag(e))));

    }
}
