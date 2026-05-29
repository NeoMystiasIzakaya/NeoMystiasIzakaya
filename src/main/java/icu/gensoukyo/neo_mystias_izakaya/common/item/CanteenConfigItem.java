/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.common.block.AbstractKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.common.block.DiningTableBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
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

    private static final int MAX_KITCHENWARE = 8;
    private static final int MAX_DINING_TABLES = 8;

    public CanteenConfigItem(Properties properties) {
        super(properties);
    }

    /**
     * 将控制器及其所有关联方块同步存储到物品 DataComponent
     */
    private static void syncControllerData(ItemStack item, CanteenControllerBlockEntity controller) {
        item.set(NMIDataComponentTypes.BOUND_CONTROLLER, controller.getBlockPos());
        item.set(NMIDataComponentTypes.BOUND_KITCHENWARE, new ArrayList<>(controller.getKitchenwareList()));
        item.set(NMIDataComponentTypes.BOUND_DINING_TABLES, new ArrayList<>(controller.getDiningTableList()));
    }

    /**
     * 清除物品中存储的所有控制器关联数据
     */
    private static void clearControllerData(ItemStack item) {
        item.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
        item.remove(NMIDataComponentTypes.BOUND_KITCHENWARE);
        item.remove(NMIDataComponentTypes.BOUND_DINING_TABLES);
    }

    /**
     * 检查是否已达到绑定上限
     * @param isKitchenware true=厨房用具, false=餐桌
     */
    private static boolean isLimitReached(ItemStack item, boolean isKitchenware) {
        List<BlockPos> list = item.get(isKitchenware ? NMIDataComponentTypes.BOUND_KITCHENWARE : NMIDataComponentTypes.BOUND_DINING_TABLES);
        int max = isKitchenware ? MAX_KITCHENWARE : MAX_DINING_TABLES;
        return list != null && list.size() >= max;
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        BlockEntity clickedBE = level.getBlockEntity(clickedPos);
        Player player = context.getPlayer();
        ItemStack heldItem = context.getItemInHand();

        // Shift + 右键 → 清除绑定
        if (player != null && player.isShiftKeyDown()) {
            handleUnbindMode(level, clickedPos, clickedBE, player, heldItem);
            return InteractionResult.SUCCESS;
        }

        // 右键控制器 → 选择控制器并存储所有关联方块
        if (clickedBE instanceof CanteenControllerBlockEntity controllerBE) {
            syncControllerData(heldItem, controllerBE);
            if (player != null) {
                player.sendOverlayMessage(MSG_CONTROLLER_SELECTED);
            }
            return InteractionResult.SUCCESS;
        }

        // 右键厨房用具 → 绑定到已选的控制器
        if (clickedState.getBlock() instanceof AbstractKitchenware) {
            if (isLimitReached(heldItem, true)) {
                if (player != null) player.sendOverlayMessage(MSG_KITCHENWARE_FULL);
                return InteractionResult.FAIL;
            }
            return bindToController(level, clickedPos, player, heldItem,
                    CanteenControllerBlockEntity::addKitchenware,
                    MSG_KITCHENWARE_BOUND, MSG_ALREADY_BOUND);
        }

        // 右键餐桌 → 绑定到已选的控制器
        if (clickedState.getBlock() instanceof DiningTableBlock) {
            if (isLimitReached(heldItem, false)) {
                if (player != null) player.sendOverlayMessage(MSG_DINING_TABLE_FULL);
                return InteractionResult.FAIL;
            }
            return bindToController(level, clickedPos, player, heldItem,
                    CanteenControllerBlockEntity::addDiningTable,
                    MSG_DINING_TABLE_BOUND, MSG_ALREADY_BOUND);
        }

        return InteractionResult.PASS;
    }

    /**
     * 将目标方块绑定到物品中存储的控制器
     */
    private InteractionResult bindToController(Level level, BlockPos targetPos, Player player, ItemStack heldItem,
                                               BindingFunction bindFunc, Component successMsg, Component alreadyMsg) {
        BlockPos controllerPos = heldItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);
        if (controllerPos == null) {
            if (player != null) {
                player.sendOverlayMessage(MSG_NO_CONTROLLER);
            }
            return InteractionResult.FAIL;
        }

        BlockEntity controllerBE = level.getBlockEntity(controllerPos);
        if (!(controllerBE instanceof CanteenControllerBlockEntity controller)) {
            if (player != null) {
                player.sendOverlayMessage(MSG_CONTROLLER_NOT_FOUND);
            }
            clearControllerData(heldItem);
            return InteractionResult.FAIL;
        }

        boolean added = bindFunc.apply(controller, targetPos);
        if (added) {
            syncControllerData(heldItem, controller);
        }
        if (player != null) {
            player.sendOverlayMessage(added ? successMsg : alreadyMsg);
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * 处理 Shift+右键 的解绑模式
     */
    private void handleUnbindMode(Level level, BlockPos clickedPos, BlockEntity clickedBE, Player player, ItemStack heldItem) {
        BlockPos controllerPos = heldItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);

        // 若点击的是控制器自身 → 清除物品中存储的所有关联数据
        if (clickedBE instanceof CanteenControllerBlockEntity) {
            clearControllerData(heldItem);
            if (player != null) {
                player.sendOverlayMessage(MSG_CONTROLLER_CLEARED);
            }
            return;
        }

        // 没有已选控制器 → 无法解绑
        if (controllerPos == null) {
            if (player != null) {
                player.sendOverlayMessage(MSG_NO_CONTROLLER);
            }
            return;
        }

        BlockEntity controllerBE = level.getBlockEntity(controllerPos);
        if (!(controllerBE instanceof CanteenControllerBlockEntity controller)) {
            if (player != null) {
                player.sendOverlayMessage(MSG_CONTROLLER_NOT_FOUND);
            }
            clearControllerData(heldItem);
            return;
        }

        // 尝试从控制器中移除目标方块
        boolean removed = controller.removeKitchenware(clickedPos) || controller.removeDiningTable(clickedPos);
        if (removed) {
            syncControllerData(heldItem, controller);
        }
    }

    @FunctionalInterface
    private interface BindingFunction {
        boolean apply(CanteenControllerBlockEntity controller, BlockPos pos);
    }
}
