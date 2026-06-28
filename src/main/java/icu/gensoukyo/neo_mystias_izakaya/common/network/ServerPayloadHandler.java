/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.client.network.*;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.item.RecipeItem;
import icu.gensoukyo.neo_mystias_izakaya.common.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigData;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonIzakayaUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerStoreUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.IzakayaCookingUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CupboardUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IncubatorUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public class ServerPayloadHandler {
    public static void handleKitchenwareCookMessage(NMIKitchenwareCookMessage message, IPayloadContext context) {
        context.enqueueWork(() -> IzakayaCookingUtil.processCooking(context.player(), message.cuisine(), message.blockPos()));
    }

    public static void handleIzakayaMenuSyncMessage(NMIIzakayaMenuSyncMessage message, IPayloadContext context) {
        context.enqueueWork(() -> NMICommonIzakayaUtil.setMenu(context.player(), message.izakayaMenu()));
    }

    public static void handleOpenDishServingMessage(OpenDishServingMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            ItemStack itemBySlot = player.getItemBySlot(EquipmentSlot.HEAD);
            CanteenConfigData data = itemBySlot.get(NMIDataComponentTypes.CANTEEN_CONFIG);
            List<BlockPos> diningTables = data != null ? data.diningTableList() : List.of();

            if (!diningTables.isEmpty()) {
                player.openMenu(new SimpleMenuProvider(
                        (containerId, inventory, _) -> new DishServingMenu(containerId, inventory, diningTables),
                        Component.literal("Dish Serving")
                ), byteBuf -> byteBuf.writeCollection(diningTables, BlockPos.STREAM_CODEC));
            }
        });
    }

    public static void handleStorePurchaseMessage(StorePurchaseMessage message, IPayloadContext context) {
        context.enqueueWork(() -> NMIServerStoreUtil.purchase(context.player(), message.cart(), NMIServerStoreUtil.getStore(context.player(), message.store())));
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
                        CanteenConfigUtil.syncControllerData(headItem, controller);
                    } else {
                        CanteenConfigUtil.clearControllerData(headItem);
                    }
                }
            }
        });
    }

    public static void handleRequestCupboardIngredientInfoMessage(RequestCupboardIngredientInfoMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                ServerPayloadSender.sendCupboardItemResourceFullSyncMessage(serverPlayer, CupboardUtil.extractIngredientItemResourceList(serverPlayer));
            }
        });
    }

    public static void handleRequestCupboardBeveragesInfoMessage(RequestCupboardBeveragesInfoMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                ServerPayloadSender.sendCupboardItemResourceFullSyncMessage(serverPlayer, CupboardUtil.extractBeveragesItemResourceList(serverPlayer));
            }
        });
    }

    public static void handleRequestExtractMenuToKitchenwareMessage(RequestExtractMenuToKitchenwareMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                CupboardUtil.extractMenuIngredientToKitchenware(serverPlayer, message.menuId(), message.blockPos());
            }
        });
    }


    public static void handleRequestExtractItemToKitchenwareMessage(RequestExtractItemToKitchenwareMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                CupboardUtil.extractItemToKitchenware(serverPlayer, message.resource(), message.blockPos());
            }
        });
    }

    public static void handleRequestCupboardExtractItemToPlayerHandMessage(RequestCupboardExtractItemToPlayerHandMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                CupboardUtil.extractItemToPlayerHand(serverPlayer, message.resource());
            }
        });
    }

    public static void handleRequestIncubatorCuisinesInfoMessage(RequestIncubatorCuisinesInfoMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                ServerPayloadSender.sendIncubatorItemResourceFullSyncMessage(serverPlayer, IncubatorUtil.extractCuisinesItemResourceList(serverPlayer));
            }
        });
    }

    public static void handleRequestIncubatorExtractItemToPlayerHandMessage(RequestIncubatorExtractItemToPlayerHandMessage message, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                IncubatorUtil.extractItemToPlayerHand(serverPlayer, message.resource());
            }
        });
    }
}

