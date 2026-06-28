/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.server.izakaya;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * 食堂配置物品（{@code CanteenConfigItem}）绑定/解绑/区域扫描相关事件。
 * <p>对应 {@link icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil} 的各逻辑步骤。
 */
@Getter
public abstract class CanteenConfigEvent extends Event {
    private final Player player;
    private final ItemStack heldItem;

    public CanteenConfigEvent(Player player, ItemStack heldItem) {
        this.player = player;
        this.heldItem = heldItem;
    }

    /** 围绕「把方块绑定到控制器」的动作事件。 */
    @Getter
    public abstract static class Bind extends CanteenConfigEvent {
        private final CanteenControllerBlockEntity controller;
        private final BlockPos target;
        private final CanteenConfigUtil.BindType bindType;

        public Bind(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos target, CanteenConfigUtil.BindType bindType) {
            super(player, heldItem);
            this.controller = controller;
            this.target = target;
            this.bindType = bindType;
        }

        /** 绑定前触发，可取消以阻止本次绑定。 */
        public static class Pre extends Bind implements ICancellableEvent {
            public Pre(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos target, CanteenConfigUtil.BindType bindType) {
                super(player, heldItem, controller, target, bindType);
            }
        }

        /** 绑定处理后触发，{@code bound} 表示是否新绑定成功。 */
        public static class Post extends Bind {
            @Getter
            private final boolean bound;

            public Post(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos target, CanteenConfigUtil.BindType bindType, boolean bound) {
                super(player, heldItem, controller, target, bindType);
                this.bound = bound;
            }
        }
    }

    /** 围绕「从控制器解绑方块」的动作事件。 */
    @Getter
    public abstract static class Unbind extends CanteenConfigEvent {
        private final CanteenControllerBlockEntity controller;
        private final BlockPos target;

        public Unbind(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos target) {
            super(player, heldItem);
            this.controller = controller;
            this.target = target;
        }

        /** 解绑前触发，可取消以阻止本次解绑。 */
        public static class Pre extends Unbind implements ICancellableEvent {
            public Pre(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos target) {
                super(player, heldItem, controller, target);
            }
        }

        /** 解绑处理后触发，{@code removed} 表示是否真的移除了绑定。 */
        public static class Post extends Unbind {
            @Getter
            private final boolean removed;

            public Post(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos target, boolean removed) {
                super(player, heldItem, controller, target);
                this.removed = removed;
            }
        }
    }

    /** 围绕「区域扫描绑定」的动作事件。 */
    @Getter
    public abstract static class Scan extends CanteenConfigEvent {
        private final CanteenControllerBlockEntity controller;
        private final BlockPos cornerA;
        private final BlockPos cornerB;

        public Scan(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos cornerA, BlockPos cornerB) {
            super(player, heldItem);
            this.controller = controller;
            this.cornerA = cornerA;
            this.cornerB = cornerB;
        }

        /** 扫描前触发，可取消以跳过本次扫描（返回 {0,0}）。 */
        public static class Pre extends Scan implements ICancellableEvent {
            public Pre(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos cornerA, BlockPos cornerB) {
                super(player, heldItem, controller, cornerA, cornerB);
            }
        }

        /** 扫描完成后触发，{@code result} 为 {厨具数, 餐桌数}，可改写。 */
        @Getter
        @Setter
        public static class Post extends Scan {
            private CanteenConfigUtil.ScanResult result;

            public Post(Player player, ItemStack heldItem, CanteenControllerBlockEntity controller, BlockPos cornerA, BlockPos cornerB, CanteenConfigUtil.ScanResult result) {
                super(player, heldItem, controller, cornerA, cornerB);
                this.result = result;
            }
        }
    }
}
