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

public class KateenConfigItem extends Item {

    private static final Component MSG_CONTROLLER_SELECTED = Component.translatable("item.neo_mystias_izakaya.kateen_config.controller_selected");
    private static final Component MSG_KITCHENWARE_BOUND = Component.translatable("item.neo_mystias_izakaya.kateen_config.kitchenware_bound");
    private static final Component MSG_DINING_TABLE_BOUND = Component.translatable("item.neo_mystias_izakaya.kateen_config.dining_table_bound");
    private static final Component MSG_NO_CONTROLLER = Component.translatable("item.neo_mystias_izakaya.kateen_config.no_controller");
    private static final Component MSG_CONTROLLER_NOT_FOUND = Component.translatable("item.neo_mystias_izakaya.kateen_config.controller_not_found");
    private static final Component MSG_CONTROLLER_CLEARED = Component.translatable("item.neo_mystias_izakaya.kateen_config.controller_cleared");
    private static final Component MSG_ALREADY_BOUND = Component.translatable("item.neo_mystias_izakaya.kateen_config.already_bound");
    private static final Component MSG_UNBOUND = Component.translatable("item.neo_mystias_izakaya.kateen_config.unbound");

    public KateenConfigItem(Properties properties) {
        super(properties);
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

        // 右键控制器 → 选择/存储控制器
        if (clickedBE instanceof CanteenControllerBlockEntity controllerBE) {
            heldItem.set(NMIDataComponentTypes.BOUND_CONTROLLER, clickedPos);
            if (player != null) {
                player.sendOverlayMessage(MSG_CONTROLLER_SELECTED);
            }
            return InteractionResult.SUCCESS;
        }

        // 右键厨房用具 → 绑定到已选的控制器
        if (clickedState.getBlock() instanceof AbstractKitchenware) {
            return bindToController(level, clickedPos, player, heldItem,
                    CanteenControllerBlockEntity::addKitchenware,
                    MSG_KITCHENWARE_BOUND, MSG_ALREADY_BOUND);
        }

        // 右键餐桌 → 绑定到已选的控制器
        if (clickedState.getBlock() instanceof DiningTableBlock) {
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
            heldItem.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
            return InteractionResult.FAIL;
        }

        boolean added = bindFunc.apply(controller, targetPos);
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

        // 若点击的是控制器自身 → 清除物品中存储的控制器引用
        if (clickedBE instanceof CanteenControllerBlockEntity) {
            heldItem.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
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
            heldItem.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
            return;
        }

        // 尝试从控制器中移除目标方块
        boolean removed = controller.removeKitchenware(clickedPos) || controller.removeDiningTable(clickedPos);
        if (player != null) {
            // 静默解绑不显示消息也可以，改由具体调用方决定
        }
    }

    @FunctionalInterface
    private interface BindingFunction {
        boolean apply(CanteenControllerBlockEntity controller, BlockPos pos);
    }
}
