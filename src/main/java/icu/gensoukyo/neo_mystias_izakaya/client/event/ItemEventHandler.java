package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.api.event.client.ClientTagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID,value = Dist.CLIENT)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onLivingEntityUseItemEventFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack item = event.getItem();
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof LocalPlayer player) {
            // TODO 读组件
            TagItemListHolder holder = NMIDataAccessor.client().getTagItemListMap().get(BuiltInRegistries.ITEM.getKey(item.getItem()));
            NeoForge.EVENT_BUS.post(new ClientTagFoodItemEvent(holder, item, player));
        }
    }
}
