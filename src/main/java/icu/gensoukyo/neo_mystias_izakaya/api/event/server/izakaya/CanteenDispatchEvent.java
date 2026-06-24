/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.api.event.server.izakaya;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import java.util.List;

/**
 * 食堂控制器订单派发相关事件。
 * <p>对应 {@link icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenOrderUtil} 的各逻辑步骤，
 * 供外部 addon 取消派发或改写订单/顾客/菜品。
 */
@Getter
public abstract class CanteenDispatchEvent extends Event {
    private final Level level;
    private final CanteenControllerBlockEntity controller;

    public CanteenDispatchEvent(Level level, CanteenControllerBlockEntity controller) {
        this.level = level;
        this.controller = controller;
    }

    /** 围绕「向某张餐桌派发订单」的动作事件。 */
    @Getter
    public abstract static class Dispatch extends CanteenDispatchEvent {
        private final DiningTableBlockEntity table;

        public Dispatch(Level level, CanteenControllerBlockEntity controller, DiningTableBlockEntity table) {
            super(level, controller);
            this.table = table;
        }

        /** 派发前触发，可取消以阻止本次入座。 */
        public static class Pre extends Dispatch implements ICancellableEvent {
            public Pre(Level level, CanteenControllerBlockEntity controller, DiningTableBlockEntity table) {
                super(level, controller, table);
            }
        }

        /** 入座完成后触发，携带最终派发的订单。 */
        public static class Post extends Dispatch {
            @Getter
            private final IzakayaOrder order;

            public Post(Level level, CanteenControllerBlockEntity controller, DiningTableBlockEntity table, IzakayaOrder order) {
                super(level, controller, table);
                this.order = order;
            }
        }
    }

    /** 生成订单后触发，可改写 {@code order}（置 null 或 {@link IzakayaOrder#EMPTY} 则否决入座）。 */
    @Getter
    @Setter
    public static class GenerateOrder extends CanteenDispatchEvent {
        private final DiningTableBlockEntity table;
        private final CustomerHolder holder;
        private IzakayaOrder order;

        public GenerateOrder(Level level, CanteenControllerBlockEntity controller, DiningTableBlockEntity table, CustomerHolder holder, IzakayaOrder order) {
            super(level, controller);
            this.table = table;
            this.holder = holder;
            this.order = order;
        }
    }

    /** 选定顾客后触发，可改写 {@code holder} 替换为其他顾客。 */
    @Getter
    @Setter
    public static class PickCustomer extends CanteenDispatchEvent {
        private final DiningTableBlockEntity table;
        private final boolean isRare;
        private CustomerHolder holder;

        public PickCustomer(Level level, CanteenControllerBlockEntity controller, DiningTableBlockEntity table, boolean isRare, CustomerHolder holder) {
            super(level, controller);
            this.table = table;
            this.isRare = isRare;
            this.holder = holder;
        }
    }

    /** 选定菜品/饮品后触发，可改写 {@code picked} 覆盖加权随机的结果。 */
    @Getter
    @Setter
    public static class PickMenuItem extends CanteenDispatchEvent {
        private final DiningTableBlockEntity table;
        private final boolean isCuisine;
        private final List<Identifier> menuItems;
        private final List<Identifier> customerTags;
        private Identifier picked;

        public PickMenuItem(Level level, CanteenControllerBlockEntity controller, DiningTableBlockEntity table,
                            boolean isCuisine, List<Identifier> menuItems, List<Identifier> customerTags, Identifier picked) {
            super(level, controller);
            this.table = table;
            this.isCuisine = isCuisine;
            this.menuItems = menuItems;
            this.customerTags = customerTags;
            this.picked = picked;
        }
    }
}
