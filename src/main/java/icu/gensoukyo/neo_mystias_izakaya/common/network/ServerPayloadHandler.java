/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.item.RecipeItem;
import icu.gensoukyo.neo_mystias_izakaya.common.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.client.network.NMIIzakayaMenuSyncMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.NMIKitchenwareCookMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.OpenDishServingMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.RecordRecipeMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.StorePurchaseMessage;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ToggleCanteenOpenMessage;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerStoreUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.IzakayaCookingUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class ServerPayloadHandler {
    public static void handleKitchenwareCookMessage(NMIKitchenwareCookMessage message, IPayloadContext context) {
        context.enqueueWork(() -> IzakayaCookingUtil.processCooking(context.player(),message.cuisine(),message.blockPos()));
    }

    public static void handleIzakayaMenuSyncMessage(NMIIzakayaMenuSyncMessage message, IPayloadContext context) {
        context.enqueueWork(()-> NMICommonIzakayaUtil.setMenu(context.player(), message.izakayaMenu()));
    }

    public static void handleOpenDishServingMessage(OpenDishServingMessage message, IPayloadContext context) {
        context.enqueueWork(()-> {
            Player player = context.player();
            ItemStack itemBySlot = player.getItemBySlot(EquipmentSlot.HEAD);
            // 餐桌列表
            List<BlockPos> diningTables = itemBySlot.get(NMIDataComponentTypes.BOUND_DINING_TABLES);

            if (diningTables != null && !diningTables.isEmpty()) {
                player.openMenu(new SimpleMenuProvider(
                        (containerId, inventory, _) -> new DishServingMenu(containerId, inventory, diningTables),
                        Component.literal("Dish Serving")
                ), byteBuf -> byteBuf.writeCollection(diningTables, BlockPos.STREAM_CODEC));
            }
        });
    }

    public static void handleStorePurchaseMessage(StorePurchaseMessage message, IPayloadContext context) {
        context.enqueueWork(()-> NMIServerStoreUtil.purchase(context.player(),message.cart(),NMIServerStoreUtil.getStore(context.player(), message.store())));
    }

    public static void handleRecordRecipeMessage(RecordRecipeMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Identifier recipeKey = message.recipeKey();

            // 遍历双手，找到 RecipeItem 并写入数据（直接覆盖旧值）
            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof RecipeItem) {
                    stack.set(NMIDataComponentTypes.RECORDED_RECIPE, recipeKey);
                    player.sendOverlayMessage(
                            Component.translatable("gui.neo_mystias_izakaya.recipe_recorded")
                    );
                    return;
                }
            }
            // 没有手持 RecipeItem
            player.sendOverlayMessage(
                    Component.translatable("gui.neo_mystias_izakaya.need_recipe_book")
            );
        });
    }

    public static void handleToggleCanteenOpen(ToggleCanteenOpenMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            BlockPos pos = message.controllerPos();
            if (player.level().getBlockEntity(pos) instanceof CanteenControllerBlockEntity controller) {
                ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
                if (headItem.is(NMIMainItems.MYSTIAS_HAT)) {
                    boolean open = !controller.isOpen();
                    controller.setOpen(open, open ? player.getUUID() : null);
                    player.sendSystemMessage(
                            Component.translatable(open
                                    ? "block.neo_mystias_izakaya.canteen_controller.open"
                                    : "block.neo_mystias_izakaya.canteen_controller.close")
                    );
                    if (!open) {
                        controller.getDiningTableList().forEach(tablePos -> {
                            if (player.level().getBlockEntity(tablePos) instanceof DiningTableBlockEntity diningTable) {
                                diningTable.clear();
                            }
                        });
                    }
                    if (open) {
                        headItem.set(NMIDataComponentTypes.BOUND_CONTROLLER, controller.getControllerPos());
                        headItem.set(NMIDataComponentTypes.BOUND_KITCHENWARE, new ArrayList<>(controller.getKitchenwareList()));
                        headItem.set(NMIDataComponentTypes.BOUND_DINING_TABLES, new ArrayList<>(controller.getDiningTableList()));
                    } else {
                        headItem.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
                        headItem.remove(NMIDataComponentTypes.BOUND_KITCHENWARE);
                        headItem.remove(NMIDataComponentTypes.BOUND_DINING_TABLES);
                    }
                }
            }
        });
    }
}
