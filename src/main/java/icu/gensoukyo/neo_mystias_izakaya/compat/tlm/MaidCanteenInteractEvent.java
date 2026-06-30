/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.api.event.InteractMaidEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.item.CanteenConfigItem;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigData;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = NeoMystiasIzakaya.MODID)
public class MaidCanteenInteractEvent {

    private static final Component MSG_MAID_ASSIGNED = Component.translatable("item.neo_mystias_izakaya.canteen_config.maid_assigned");
    private static final Component MSG_MAID_NO_CONTROLLER = Component.translatable("item.neo_mystias_izakaya.canteen_config.no_controller");

    @SubscribeEvent
    public static void onInteractMaid(InteractMaidEvent event) {
        ItemStack stack = event.getStack();
        if (!(stack.getItem() instanceof CanteenConfigItem)) return;

        Player player = event.getPlayer();
        if (player.level().isClientSide()) return;

        EntityMaid maid = event.getMaid();
        CanteenConfigData data = CanteenConfigUtil.getConfig(stack);
        BlockPos controllerPos = data.controller();
        if (controllerPos == null) {
            player.sendOverlayMessage(MSG_MAID_NO_CONTROLLER);
            event.setCanceled(true);
            return;
        }

        maid.getBrain().setMemory(NMIMemoryTypes.CONTROLLER_POS.get(), controllerPos.immutable());
        player.sendOverlayMessage(MSG_MAID_ASSIGNED);
        event.setCanceled(true);
    }
}
