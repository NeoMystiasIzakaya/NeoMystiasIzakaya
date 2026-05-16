/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.event.client.ClientTagFoodItemEvent;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientEconomyUtil;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
            ItemTagList itemTagList = NMIClientItemTagUtil.get(item);
            NeoForge.EVENT_BUS.post(new ClientTagFoodItemEvent(itemTagList, item, player));
            NMIServerItemTagUtil.set(item, itemTagList);
        }
    }


    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event){
        ItemTagList itemTagList = NMIClientItemTagUtil.get(event.getItemStack());

        List<Component> toolTip = event.getToolTip();

        itemTagList.positiveTags().forEach(e->toolTip.add(Component.literal("+ ").append(NMICommonComponentUtil.translatableTag(e))));
        itemTagList.negativeTags().forEach(e->toolTip.add(Component.literal("- ").append(NMICommonComponentUtil.translatableTag(e))));

        Integer price = NMIClientEconomyUtil.getItemPrice(event.getItemStack());
        if (price != null){
            int count = event.getItemStack().getCount();
            MutableComponent unitEn = NMICommonComponentUtil.unitEn();
            MutableComponent priceC = Component.translatable("tooltip.neo_mystias_izakaya.price", price, unitEn);
            toolTip.add(priceC);

            if (count > 1) {
                MutableComponent totalPriceC = Component.translatable("tooltip.neo_mystias_izakaya.price.total", price * count, unitEn);
                toolTip.add(totalPriceC);
            }
        }


    }
}
