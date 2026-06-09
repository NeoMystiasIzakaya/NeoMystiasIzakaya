/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.common.block.AbstractKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.common.block.DiningTableBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CanteenConfigItem extends Item {

    // 手动绑定/解绑消息
    private static final Component MSG_CONTROLLER_SELECTED = Component.translatable("item.neo_mystias_izakaya.canteen_config.controller_selected");
    private static final Component MSG_KITCHENWARE_BOUND = Component.translatable("item.neo_mystias_izakaya.canteen_config.kitchenware_bound");
    private static final Component MSG_DINING_TABLE_BOUND = Component.translatable("item.neo_mystias_izakaya.canteen_config.dining_table_bound");
    private static final Component MSG_NO_CONTROLLER = Component.translatable("item.neo_mystias_izakaya.canteen_config.no_controller");
    private static final Component MSG_CONTROLLER_NOT_FOUND = Component.translatable("item.neo_mystias_izakaya.canteen_config.controller_not_found");
    private static final Component MSG_CONTROLLER_CLEARED = Component.translatable("item.neo_mystias_izakaya.canteen_config.controller_cleared");
    private static final Component MSG_ALREADY_BOUND = Component.translatable("item.neo_mystias_izakaya.canteen_config.already_bound");
    private static final Component MSG_UNBOUND = Component.translatable("item.neo_mystias_izakaya.canteen_config.unbound");
    private static final Component MSG_KITCHENWARE_FULL = Component.translatable("item.neo_mystias_izakaya.canteen_config.kitchenware_full");
    private static final Component MSG_DINING_TABLE_FULL = Component.translatable("item.neo_mystias_izakaya.canteen_config.dining_table_full");
    // 区域扫描消息
    private static final Component MSG_CORNER_A_SET = Component.translatable("item.neo_mystias_izakaya.canteen_config.corner_a_set");
    private static final Component MSG_CORNER_B_SET = Component.translatable("item.neo_mystias_izakaya.canteen_config.corner_b_set");
    private static final Component MSG_CORNERS_CLEARED = Component.translatable("item.neo_mystias_izakaya.canteen_config.corners_cleared");
    private static final String SCAN_RESULT_KEY = "item.neo_mystias_izakaya.canteen_config.scan_result";

    private static final int MAX_KITCHENWARE = 8;
    private static final int MAX_DINING_TABLES = 8;

    public CanteenConfigItem(Properties properties) {
        super(properties.useCooldown(1.0F));
    }

    // ==================== 控制器数据同步 ====================

    private static void syncControllerData(ItemStack item, CanteenControllerBlockEntity controller) {
        item.set(NMIDataComponentTypes.BOUND_CONTROLLER, controller.getControllerPos());
        item.set(NMIDataComponentTypes.BOUND_KITCHENWARE, new ArrayList<>(controller.getKitchenwareList()));
        item.set(NMIDataComponentTypes.BOUND_DINING_TABLES, new ArrayList<>(controller.getDiningTableList()));
    }

    private static void clearControllerData(ItemStack item) {
        item.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
        item.remove(NMIDataComponentTypes.BOUND_KITCHENWARE);
        item.remove(NMIDataComponentTypes.BOUND_DINING_TABLES);
    }

    /** 同步控制器数据到手持物品 + 帽子（HUD 读取源） */
    private static void syncToHatAndHand(ItemStack heldItem, CanteenControllerBlockEntity controller, Player player) {
        syncControllerData(heldItem, controller);
        if (player != null) {
            ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
            if (headItem.is(NMIMainItems.MYSTIAS_HAT)) {
                syncControllerData(headItem, controller);
            }
        }
    }

    /** 清除手持物品 + 帽子的控制器数据 */
    private static void clearHatAndHand(ItemStack heldItem, Player player) {
        clearControllerData(heldItem);
        if (player != null) {
            ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
            if (headItem.is(NMIMainItems.MYSTIAS_HAT)) {
                clearControllerData(headItem);
            }
        }
    }

    private static boolean isLimitReached(ItemStack item, boolean isKitchenware) {
        List<BlockPos> list = item.get(isKitchenware ? NMIDataComponentTypes.BOUND_KITCHENWARE : NMIDataComponentTypes.BOUND_DINING_TABLES);
        int max = isKitchenware ? MAX_KITCHENWARE : MAX_DINING_TABLES;
        return list != null && list.size() >= max;
    }

    // ==================== 角点管理 ====================

    private static void clearCorners(ItemStack item) {
        item.remove(NMIDataComponentTypes.SCAN_CORNER_A);
        item.remove(NMIDataComponentTypes.SCAN_CORNER_B);
    }

    // ==================== 交互 ====================

    /**
     * 右键空气 → 清除两个角点
     */
    @Override
    public @NonNull InteractionResult use(Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        ItemStack heldItem = player.getItemInHand(hand);
        clearCorners(heldItem);
        player.sendOverlayMessage(MSG_CORNERS_CLEARED);
        return InteractionResult.SUCCESS;
    }

    /**
     * 右键方块：控制器→选择/扫描，厨具/餐桌→手动绑定，普通方块→角点A
     * Shift+右键方块：厨具/餐桌/控制器→解绑，普通方块→角点B
     */
    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        BlockEntity clickedBE = level.getBlockEntity(clickedPos);
        Player player = context.getPlayer();
        ItemStack heldItem = context.getItemInHand();

        boolean isKitchenware = clickedState.getBlock() instanceof AbstractKitchenware;
        boolean isDiningTable = clickedState.getBlock() instanceof DiningTableBlock;
        boolean isController = clickedBE instanceof CanteenControllerBlockEntity;
        boolean isSpecial = isKitchenware || isDiningTable || isController;

        // ── Shift + 右键 ──
        if (player != null && player.isShiftKeyDown()) {
            if (isSpecial) {
                // 厨具/餐桌/控制器 → 解绑
                handleUnbindMode(level, clickedPos, clickedBE, player, heldItem);
            } else {
                // 普通方块 → 设置角点B
                heldItem.set(NMIDataComponentTypes.SCAN_CORNER_B, clickedPos.immutable());
                player.sendOverlayMessage(MSG_CORNER_B_SET);
            }
            return InteractionResult.SUCCESS;
        }

        // ── 右键 ──

        // 控制器
        if (isController) {
            CanteenControllerBlockEntity controllerBE = (CanteenControllerBlockEntity) clickedBE;
            BlockPos cornerA = heldItem.get(NMIDataComponentTypes.SCAN_CORNER_A);
            BlockPos cornerB = heldItem.get(NMIDataComponentTypes.SCAN_CORNER_B);
            if (cornerA != null && cornerB != null) {
                // 两角点齐全 → 区域扫描绑定
                int[] result = controllerBE.scanAndBind(level, cornerA, cornerB, MAX_KITCHENWARE, MAX_DINING_TABLES);
                if (player != null) {
                    player.sendOverlayMessage(Component.translatable(SCAN_RESULT_KEY, result[0], result[1]));
                }
                clearCorners(heldItem);
            } else {
                // 选择控制器（存储当前关联数据用于手动绑定，同时同步帽子）
                syncToHatAndHand(heldItem, controllerBE, player);
                if (player != null) {
                    player.sendOverlayMessage(MSG_CONTROLLER_SELECTED);
                }
            }
            return InteractionResult.SUCCESS;
        }

        // 厨具 / 餐桌 → 手动绑定（需已选择控制器）
        if (isKitchenware || isDiningTable) {
            BlockPos controllerPos = heldItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);
            if (controllerPos != null && level.isLoaded(controllerPos)) {
                if (isKitchenware) {
                    if (isLimitReached(heldItem, true)) {
                        if (player != null) player.sendOverlayMessage(MSG_KITCHENWARE_FULL);
                        return InteractionResult.FAIL;
                    }
                    return bindToController(level, clickedPos, player, heldItem,
                            CanteenControllerBlockEntity::addKitchenware,
                            MSG_KITCHENWARE_BOUND, MSG_ALREADY_BOUND);
                } else {
                    if (isLimitReached(heldItem, false)) {
                        if (player != null) player.sendOverlayMessage(MSG_DINING_TABLE_FULL);
                        return InteractionResult.FAIL;
                    }
                    return bindToController(level, clickedPos, player, heldItem,
                            CanteenControllerBlockEntity::addDiningTable,
                            MSG_DINING_TABLE_BOUND, MSG_ALREADY_BOUND);
                }
            }
            // 未选控制器 → 设为角点A
            heldItem.set(NMIDataComponentTypes.SCAN_CORNER_A, clickedPos.immutable());
            if (player != null) player.sendOverlayMessage(MSG_CORNER_A_SET);
            return InteractionResult.SUCCESS;
        }

        // 普通方块 → 设置角点A
        heldItem.set(NMIDataComponentTypes.SCAN_CORNER_A, clickedPos.immutable());
        if (player != null) player.sendOverlayMessage(MSG_CORNER_A_SET);
        return InteractionResult.SUCCESS;
    }

    // ==================== 手动绑定/解绑 ====================

    private InteractionResult bindToController(Level level, BlockPos targetPos, Player player, ItemStack heldItem,
                                               BindingFunction bindFunc, Component successMsg, Component alreadyMsg) {
        BlockPos controllerPos = heldItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);
        if (controllerPos == null) {
            if (player != null) player.sendOverlayMessage(MSG_NO_CONTROLLER);
            return InteractionResult.FAIL;
        }

        BlockEntity controllerBE = level.getBlockEntity(controllerPos);
        if (!(controllerBE instanceof CanteenControllerBlockEntity controller)) {
            if (player != null) player.sendOverlayMessage(MSG_CONTROLLER_NOT_FOUND);
            clearHatAndHand(heldItem, player);
            return InteractionResult.FAIL;
        }

        boolean added = bindFunc.apply(controller, targetPos);
        if (added) syncToHatAndHand(heldItem, controller, player);
        if (player != null) player.sendOverlayMessage(added ? successMsg : alreadyMsg);
        return InteractionResult.SUCCESS;
    }

    private void handleUnbindMode(Level level, BlockPos clickedPos, BlockEntity clickedBE, Player player, ItemStack heldItem) {
        BlockPos controllerPos = heldItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);

        if (clickedBE instanceof CanteenControllerBlockEntity) {
            clearHatAndHand(heldItem, player);
            if (player != null) player.sendOverlayMessage(MSG_CONTROLLER_CLEARED);
            return;
        }

        if (controllerPos == null) {
            if (player != null) player.sendOverlayMessage(MSG_NO_CONTROLLER);
            return;
        }

        BlockEntity controllerBE = level.getBlockEntity(controllerPos);
        if (!(controllerBE instanceof CanteenControllerBlockEntity controller)) {
            if (player != null) player.sendOverlayMessage(MSG_CONTROLLER_NOT_FOUND);
            clearHatAndHand(heldItem, player);
            return;
        }

        boolean removed = controller.removeKitchenware(clickedPos) || controller.removeDiningTable(clickedPos);
        if (removed) syncToHatAndHand(heldItem, controller, player);
        if (player != null) player.sendOverlayMessage(removed ? MSG_UNBOUND : MSG_ALREADY_BOUND);
    }

    @FunctionalInterface
    private interface BindingFunction {
        boolean apply(CanteenControllerBlockEntity controller, BlockPos pos);
    }
}
