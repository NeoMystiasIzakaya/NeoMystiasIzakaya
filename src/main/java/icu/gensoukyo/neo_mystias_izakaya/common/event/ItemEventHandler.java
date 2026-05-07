package icu.gensoukyo.neo_mystias_izakaya.common.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.ServerTagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import net.minecraft.core.registries.BuiltInRegistries;
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
            // TODO 读组件
            TagItemListHolder holder = NMIDataAccessor.server().getTagItemListMap().get(BuiltInRegistries.ITEM.getKey(item.getItem()));
            NeoForge.EVENT_BUS.post(new ServerTagFoodItemEvent(holder, item, player));
        }
    }
}
