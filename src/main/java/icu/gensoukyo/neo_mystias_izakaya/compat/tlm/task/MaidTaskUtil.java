/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBrains;
import icu.gensoukyo.neo_mystias_izakaya.api.common.ICupboard;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * 女仆送餐/送酒 Task 的共用工具方法。
 */
public final class MaidTaskUtil {

    public static final double CLOSE_ENOUGH = 2.5;
    public static final float SPEED = 0.6f;

    private MaidTaskUtil() {}

    // ==================== 导航 ====================

    public static boolean isCloseEnough(EntityMaid maid, BlockPos pos) {
        return pos.distToCenterSqr(maid.position()) < CLOSE_ENOUGH * CLOSE_ENOUGH;
    }

    public static void setNavigateTo(EntityMaid maid, BlockPos pos) {
        BehaviorUtils.setWalkAndLookTargetMemories(maid, pos, SPEED, 1);
    }

    // ==================== 记忆清理 ====================

    /** Fetch Task 的 start() 清理：清除 TLMTARGET + WALK + LOOK */
    public static void clearFetchStartMemories(EntityMaid maid) {
        maid.getBrain().eraseMemory(InitBrains.TARGET_POS.get());
        maid.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        maid.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    /** Deliver Task 的 start() 清理：清除 TARGET_POS + WALK + LOOK */
    public static void clearDeliverStartMemories(EntityMaid maid) {
        maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
        maid.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        maid.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    // ==================== 控制器获取 ====================

    /** 从女仆记忆获取餐厅控制器，无效时擦除记忆 */
    @Nullable
    public static CanteenControllerBlockEntity getController(ServerLevel level, EntityMaid maid) {
        Optional<BlockPos> mem = maid.getBrain().getMemory(NMIMemoryTypes.CONTROLLER_POS.get());
        if (mem.isEmpty()) return null;
        if (level.getBlockEntity(mem.get()) instanceof CanteenControllerBlockEntity controller) {
            return controller;
        }
        maid.getBrain().eraseMemory(NMIMemoryTypes.CONTROLLER_POS.get());
        return null;
    }

    // ==================== 餐桌 ====================

    /** 从女仆记忆获取目标餐桌，无效或缺失时擦除记忆返回 null */
    @Nullable
    public static DiningTableBlockEntity getTargetTable(ServerLevel level, EntityMaid maid) {
        Optional<BlockPos> mem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        if (mem.isEmpty()) return null;
        if (level.getBlockEntity(mem.get()) instanceof DiningTableBlockEntity table) {
            return table;
        }
        maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
        return null;
    }

    /** 如果餐桌已完成（slot已填或无人），擦除 TARGET_POS 并返回 true */
    public static boolean isTableSlotFilled(DiningTableBlockEntity table, boolean checkCuisine) {
        boolean filled = checkCuisine ? !table.getCuisine().isEmpty() : !table.getBeverage().isEmpty();
        return filled || !table.isOccupied();
    }

    // ==================== TaskInv ====================

    /** 检查 TaskInv 中是否有指定物品ID的堆叠 */
    public static boolean hasItemInTaskInv(EntityMaid maid, Identifier itemId) {
        Optional<Holder.Reference<Item>> ref = BuiltInRegistries.ITEM.get(itemId);
        if (ref.isEmpty()) return false;
        Item target = ref.get().value();
        var taskInv = maid.getItemManager().getTaskInv();
        for (int i = 0; i < taskInv.size(); i++) {
            if (taskInv.getResource(i).toStack().is(target)) return true;
        }
        return false;
    }

    /** 从 TaskInv 取出指定物品 1 个 */
    public static ItemStack extractFromTaskInv(EntityMaid maid, Item targetItem) {
        var taskInv = maid.getItemManager().getTaskInv();
        for (int i = 0; i < taskInv.size(); i++) {
            ItemStack stack = taskInv.getResource(i).toStack();
            if (stack.is(targetItem)) {
                ItemStack result = stack.split(1);
                if (stack.isEmpty()) {
                    taskInv.set(i, ItemResource.EMPTY, 0);
                }
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    /** 将指定物品 1 个放入 TaskInv 的第一个空位 */
    public static boolean putIntoTaskInv(EntityMaid maid, Item targetItem) {
        var taskInv = maid.getItemManager().getTaskInv();
        ItemResource resource = ItemResource.of(new ItemStack(targetItem, 1));
        for (int i = 0; i < taskInv.size(); i++) {
            if (taskInv.getResource(i).isEmpty()) {
                taskInv.set(i, resource, 1);
                return true;
            }
        }
        return false;
    }

    // ==================== 橱柜 ====================

    /** 找到第一个存有指定物品的橱柜 */
    @Nullable
    public static BlockPos findCupboardWithItem(ServerLevel level, CanteenControllerBlockEntity controller,
                                                 Identifier itemId) {
        Optional<Holder.Reference<Item>> ref = BuiltInRegistries.ITEM.get(itemId);
        if (ref.isEmpty()) return null;
        Item target = ref.get().value();
        for (BlockPos pos : controller.getCupboardList()) {
            if (level.getBlockEntity(pos) instanceof ICupboard cupboard && cupboardHasItem(cupboard, target)) {
                return pos;
            }
        }
        return null;
    }

    public static boolean cupboardHasItem(ICupboard cupboard, Item targetItem) {
        var handler = cupboard.getItemHandler();
        for (int i = 0; i < handler.size(); i++) {
            if (targetItem.getDefaultInstance().is(handler.getResource(i).getItem())) {
                return true;
            }
        }
        return false;
    }

    /** 从橱柜提取 1 个指定物品放入女仆 TaskInv */
    public static boolean extractFromCupboard(ServerLevel level, BlockPos cupboardPos,
                                               Identifier itemId, EntityMaid maid) {
        if (!(level.getBlockEntity(cupboardPos) instanceof ICupboard cupboard)) return false;
        Optional<Holder.Reference<Item>> ref = BuiltInRegistries.ITEM.get(itemId);
        if (ref.isEmpty()) return false;
        Item target = ref.get().value();

        var handler = cupboard.getItemHandler();
        Transaction tx = Transaction.openRoot();
        for (int i = 0; i < handler.size(); i++) {
            var resource = handler.getResource(i);
            if (target.getDefaultInstance().is(handler.getResource(i).getItem())) {
                handler.extract(i, resource, 1, tx);
                tx.commit();
                return putIntoTaskInv(maid, target);
            }
        }
        return false;
    }

    // ==================== 订单ID解析 ====================

    @Nullable
    public static Item resolveItem(Identifier itemId) {
        Optional<Holder.Reference<Item>> ref = BuiltInRegistries.ITEM.get(itemId);
        return ref.map(Holder.Reference::value).orElse(null);
    }

    // ==================== Deliver Task 通用逻辑 ====================

    /**
     * Deliver Task 的 {@code checkExtraStartConditions} 通用实现。
     * @return true 表示已就绪可触发 start()；false 时调用方应调用 {@code setNextCheckTickCount(5)}
     */
    public static boolean checkDeliverConditions(ServerLevel level, EntityMaid maid, boolean isCuisine) {
        Optional<BlockPos> tableMem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        if (tableMem.isEmpty()) return false;

        BlockPos tablePos = tableMem.get();
        if (!(level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table)) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
            return false;
        }

        boolean slotFilled = isCuisine ? !table.getCuisine().isEmpty() : !table.getBeverage().isEmpty();
        if (slotFilled || !table.isOccupied()) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
            return false;
        }

        Identifier requiredId = isCuisine ? table.getCurrentOrder().cuisine() : table.getCurrentOrder().beverage();
        if (requiredId.equals(IzakayaOrder.EMPTY_ORDER) || table.getCurrentOrder().isRare()) {
            maid.getBrain().eraseMemory(NMIMemoryTypes.TARGET_POS.get());
            return false;
        }

        if (!hasItemInTaskInv(maid, requiredId)) {
            return false;
        }

        if (isCloseEnough(maid, tablePos)) {
            return true;
        }
        setNavigateTo(maid, tablePos);
        return false;
    }

    /**
     * Deliver Task 的 {@code start()} 通用实现。
     */
    public static void doDeliver(ServerLevel level, EntityMaid maid, boolean isCuisine) {
        Optional<BlockPos> tableMem = maid.getBrain().getMemory(NMIMemoryTypes.TARGET_POS.get());
        if (tableMem.isEmpty()) return;

        BlockPos tablePos = tableMem.get();
        if (level.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table) {
            Identifier requiredId = isCuisine ? table.getCurrentOrder().cuisine() : table.getCurrentOrder().beverage();
            Item targetItem = resolveItem(requiredId);
            if (targetItem == null) return;

            ItemStack stack = extractFromTaskInv(maid, targetItem);
            if (!stack.isEmpty()) {
                if (isCuisine) {
                    table.setCuisine(stack);
                } else {
                    table.setBeverage(stack);
                }
            }
        }

        clearDeliverStartMemories(maid);
    }
}
