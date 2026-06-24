/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.common.block.KitchenwareBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.DiningTableBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil.BindResult;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil.UnbindResult;
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

    // ==================== 控制器数据同步（表现层：手持 + 帽子） ====================

    /** 同步控制器数据到手持物品 + 帽子（HUD 读取源） */
    private static void syncToHatAndHand(ItemStack heldItem, CanteenControllerBlockEntity controller, Player player) {
        CanteenConfigUtil.syncControllerData(heldItem, controller);
        if (player != null) {
            ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
            if (headItem.is(NMIMainItems.MYSTIAS_HAT)) {
                CanteenConfigUtil.syncControllerData(headItem, controller);
            }
        }
    }

    /** 清除手持物品 + 帽子的控制器数据 */
    private static void clearHatAndHand(ItemStack heldItem, Player player) {
        CanteenConfigUtil.clearControllerData(heldItem);
        if (player != null) {
            ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
            if (headItem.is(NMIMainItems.MYSTIAS_HAT)) {
                CanteenConfigUtil.clearControllerData(headItem);
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

        boolean isKitchenware = clickedState.getBlock() instanceof KitchenwareBlock;
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
                int[] result = CanteenConfigUtil.scan(level, player, heldItem, controllerBE, cornerA, cornerB, MAX_KITCHENWARE, MAX_DINING_TABLES);
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
                    return doBind(level, player, heldItem, clickedPos, true, MSG_KITCHENWARE_BOUND, MSG_ALREADY_BOUND);
                } else {
                    if (isLimitReached(heldItem, false)) {
                        if (player != null) player.sendOverlayMessage(MSG_DINING_TABLE_FULL);
                        return InteractionResult.FAIL;
                    }
                    return doBind(level, player, heldItem, clickedPos, false, MSG_DINING_TABLE_BOUND, MSG_ALREADY_BOUND);
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

    private InteractionResult doBind(Level level, Player player, ItemStack heldItem, BlockPos target,
                                     boolean isKitchenware, Component successMsg, Component alreadyMsg) {
        BindResult result = CanteenConfigUtil.bind(level, player, heldItem, target, isKitchenware);
        switch (result) {
            case NO_CONTROLLER -> {
                if (player != null) player.sendOverlayMessage(MSG_NO_CONTROLLER);
                return InteractionResult.FAIL;
            }
            case NOT_FOUND -> {
                if (player != null) player.sendOverlayMessage(MSG_CONTROLLER_NOT_FOUND);
                clearHatAndHand(heldItem, player);
                return InteractionResult.FAIL;
            }
            case BOUND -> {
                if (player != null) player.sendOverlayMessage(successMsg);
                CanteenControllerBlockEntity controller = CanteenConfigUtil.getBoundController(level, heldItem);
                if (controller != null) syncToHatAndHand(heldItem, controller, player);
                return InteractionResult.SUCCESS;
            }
            case ALREADY_BOUND -> {
                if (player != null) player.sendOverlayMessage(alreadyMsg);
                return InteractionResult.SUCCESS;
            }
            case CANCELLED -> {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void handleUnbindMode(Level level, BlockPos clickedPos, BlockEntity clickedBE, Player player, ItemStack heldItem) {
        if (clickedBE instanceof CanteenControllerBlockEntity) {
            clearHatAndHand(heldItem, player);
            if (player != null) player.sendOverlayMessage(MSG_CONTROLLER_CLEARED);
            return;
        }

        UnbindResult result = CanteenConfigUtil.unbind(level, player, heldItem, clickedPos);
        switch (result) {
            case NO_CONTROLLER -> { if (player != null) player.sendOverlayMessage(MSG_NO_CONTROLLER); }
            case NOT_FOUND -> {
                if (player != null) player.sendOverlayMessage(MSG_CONTROLLER_NOT_FOUND);
                clearHatAndHand(heldItem, player);
            }
            case REMOVED -> {
                if (player != null) player.sendOverlayMessage(MSG_UNBOUND);
                CanteenControllerBlockEntity controller = CanteenConfigUtil.getBoundController(level, heldItem);
                if (controller != null) syncToHatAndHand(heldItem, controller, player);
            }
            case NOT_BOUND -> { if (player != null) player.sendOverlayMessage(MSG_ALREADY_BOUND); }
            case CANCELLED -> { }
        }
    }
}
