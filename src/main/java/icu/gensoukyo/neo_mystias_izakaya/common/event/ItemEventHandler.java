package icu.gensoukyo.neo_mystias_izakaya.common.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.ServerTagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIItemTagUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID,value = Dist.DEDICATED_SERVER)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof ServerPlayer player) {
            ItemTagList itemTagList = NMIItemTagUtil.serverGet(item);
            NeoForge.EVENT_BUS.post(new ServerTagFoodItemEvent(itemTagList, item, player));
            NMIItemTagUtil.set(item, itemTagList);
        }
    }

}
