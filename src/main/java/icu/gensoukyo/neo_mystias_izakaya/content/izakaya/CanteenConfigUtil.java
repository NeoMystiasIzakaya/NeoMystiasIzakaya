/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import icu.gensoukyo.neo_mystias_izakaya.api.event.server.izakaya.CanteenConfigEvent;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

/**
 * 食堂配置物品（{@code CanteenConfigItem}）绑定/解绑/区域扫描工具类。
 * <p>只做控制器状态变更与 {@link CanteenConfigEvent} 事件触发，不发送聊天消息；
 * 通过 {@link BindResult}/{@link UnbindResult} 把结果交回调用方翻译为消息。手持物品与帽子的
 * 数据同步（表现层）由调用方负责，使用本类的 {@link #syncControllerData}/{@link #clearControllerData}。
 */
public final class CanteenConfigUtil {

    public enum BindResult { NO_CONTROLLER, NOT_FOUND, CANCELLED, BOUND, ALREADY_BOUND }
    public enum UnbindResult { NO_CONTROLLER, NOT_FOUND, CANCELLED, REMOVED, NOT_BOUND }

    public record ScanResult(int kitchenwareCount,int diningTableCount,int cupboardCount ){}

    // ==================== 控制器数据读写 ====================

    /** 获取物品上的配置数据，不存在则返回空配置。 */
    public static CanteenConfigData getConfig(ItemStack item) {
        CanteenConfigData data = item.get(NMIDataComponentTypes.CANTEEN_CONFIG);
        return data != null ? data : CanteenConfigData.EMPTY;
    }

    /** 把控制器数据写入指定物品（手持/帽子均用此）。 */
    public static void syncControllerData(ItemStack item, CanteenControllerBlockEntity controller) {
        item.set(NMIDataComponentTypes.CANTEEN_CONFIG,
                new CanteenConfigData(
                        controller.getControllerPos(),
                        new ArrayList<>(controller.getKitchenwareList()),
                        new ArrayList<>(controller.getDiningTableList()),
                        new ArrayList<>(controller.getCupboardList()),
                        null, null
                ));
    }

    /** 清除指定物品的控制器绑定（保留角点）。 */
    public static void clearControllerData(ItemStack item) {
        CanteenConfigData data = getConfig(item);
        item.set(NMIDataComponentTypes.CANTEEN_CONFIG, data.clearController());
    }

    /** 解析物品绑定的控制器；未绑定或失效返回 null。 */
    public static @Nullable CanteenControllerBlockEntity getBoundController(Level level, ItemStack item) {
        CanteenConfigData data = item.get(NMIDataComponentTypes.CANTEEN_CONFIG);
        if (data == null || data.controller() == null) return null;
        if (!level.isLoaded(data.controller())) return null;
        BlockEntity be = level.getBlockEntity(data.controller());
        return be instanceof CanteenControllerBlockEntity controller ? controller : null;
    }

    // ==================== 绑定/解绑/扫描 ====================

    /**
     * 将目标方块绑定到物品记录的控制器。
     * 触发 {@link CanteenConfigEvent.Bind.Pre}/{@code .Post}。
     */
    public static BindResult bind(Level level, Player player, ItemStack heldItem, BlockPos target, boolean isKitchenware) {
        CanteenConfigData data = getConfig(heldItem);
        BlockPos controllerPos = data.controller();
        if (controllerPos == null) return BindResult.NO_CONTROLLER;
        if (!level.isLoaded(controllerPos)
                || !(level.getBlockEntity(controllerPos) instanceof CanteenControllerBlockEntity controller)) {
            return BindResult.NOT_FOUND;
        }

        CanteenConfigEvent.Bind.Pre pre = NeoForge.EVENT_BUS.post(
                new CanteenConfigEvent.Bind.Pre(player, heldItem, controller, target, isKitchenware));
        if (pre.isCanceled()) return BindResult.CANCELLED;

        boolean added = isKitchenware ? controller.addKitchenware(target) : controller.addDiningTable(target);

        NeoForge.EVENT_BUS.post(new CanteenConfigEvent.Bind.Post(player, heldItem, controller, target, isKitchenware, added));
        return added ? BindResult.BOUND : BindResult.ALREADY_BOUND;
    }

    /**
     * 从物品记录的控制器解绑目标方块（厨具或餐桌）。
     * 触发 {@link CanteenConfigEvent.Unbind.Pre}/{@code .Post}。
     */
    public static UnbindResult unbind(Level level, Player player, ItemStack heldItem, BlockPos target) {
        CanteenConfigData data = getConfig(heldItem);
        BlockPos controllerPos = data.controller();
        if (controllerPos == null) return UnbindResult.NO_CONTROLLER;
        if (!level.isLoaded(controllerPos)
                || !(level.getBlockEntity(controllerPos) instanceof CanteenControllerBlockEntity controller)) {
            return UnbindResult.NOT_FOUND;
        }

        CanteenConfigEvent.Unbind.Pre pre = NeoForge.EVENT_BUS.post(
                new CanteenConfigEvent.Unbind.Pre(player, heldItem, controller, target));
        if (pre.isCanceled()) return UnbindResult.CANCELLED;

        boolean removed = controller.removeKitchenware(target) || controller.removeDiningTable(target)
                || controller.removeCupboard(target);

        NeoForge.EVENT_BUS.post(new CanteenConfigEvent.Unbind.Post(player, heldItem, controller, target, removed));
        return removed ? UnbindResult.REMOVED : UnbindResult.NOT_BOUND;
    }

    /**
     * 将目标橱柜绑定到物品记录的控制器。
     */
    public static BindResult bindCupboard(Level level, Player player, ItemStack heldItem, BlockPos target) {
        CanteenConfigData data = getConfig(heldItem);
        BlockPos controllerPos = data.controller();
        if (controllerPos == null) return BindResult.NO_CONTROLLER;
        if (!level.isLoaded(controllerPos)
                || !(level.getBlockEntity(controllerPos) instanceof CanteenControllerBlockEntity controller)) {
            return BindResult.NOT_FOUND;
        }

        CanteenConfigEvent.Bind.Pre pre = NeoForge.EVENT_BUS.post(
                new CanteenConfigEvent.Bind.Pre(player, heldItem, controller, target, false));
        if (pre.isCanceled()) return BindResult.CANCELLED;

        boolean added = controller.addCupboard(target);

        NeoForge.EVENT_BUS.post(new CanteenConfigEvent.Bind.Post(player, heldItem, controller, target, false, added));
        return added ? BindResult.BOUND : BindResult.ALREADY_BOUND;
    }

    /**
     * 区域扫描绑定：在两角点构成的范围内扫描厨具/餐桌/橱柜并绑定到控制器。
     * 触发 {@link CanteenConfigEvent.Scan.Pre}/{@code .Post}（Post 结果可改写）。
     *
     * @return {厨具绑定数, 餐桌绑定数, 橱柜绑定数}
     */
    public static ScanResult scan(Level level, Player player, ItemStack heldItem, CanteenControllerBlockEntity controller,
                             BlockPos cornerA, BlockPos cornerB, int maxKitchenware, int maxDiningTables, int maxCupboards) {
        CanteenConfigEvent.Scan.Pre pre = NeoForge.EVENT_BUS.post(
                new CanteenConfigEvent.Scan.Pre(player, heldItem, controller, cornerA, cornerB));
        if (pre.isCanceled()) return new ScanResult(0, 0, 0);

        ScanResult result = controller.scanAndBind(level, cornerA, cornerB, maxKitchenware, maxDiningTables, maxCupboards);

        CanteenConfigEvent.Scan.Post post = NeoForge.EVENT_BUS.post(
                new CanteenConfigEvent.Scan.Post(player, heldItem, controller, cornerA, cornerB, result));
        return post.getResult();
    }
}
