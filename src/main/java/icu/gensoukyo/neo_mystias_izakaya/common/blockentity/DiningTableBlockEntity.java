/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class DiningTableBlockEntity extends RandomizableContainerBlockEntity {

    /**
     * 菜品槽位
     */
    public static final int SLOT_CUISINE = 0;
    /**
     * 饮品槽位
     */
    public static final int SLOT_BEVERAGE = 1;
    /**
     * 总槽位数
     */
    public static final int CONTAINER_SIZE = 2;

    NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);

    /**
     * 是否有顾客入座
     */
    @Getter
    private boolean isOccupied = false;
    /**
     * 当前入座顾客的ID
     */
    @Getter
    private Identifier customerId = IzakayaOrder.EMPTY_RARE_CUSTOMER;
    /**
     * 当前顾客的订单
     */
    @Getter
    private IzakayaOrder currentOrder = IzakayaOrder.EMPTY;
    /**
     * 餐桌序号（由控制器分配，从 1 开始；-1 表示未绑定）
     */
    @Getter
    private int tableIndex = -1;
    /**
     * 所属控制器坐标
     */
    @Getter
    private BlockPos controllerPos = BlockPos.ZERO;

    public DiningTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NMIBlockEntities.DINING_TABLE.get(), blockPos, blockState);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, DiningTableBlockEntity pBlockEntity) {
        // 预留：未来可在此处理顾客用餐计时等逻辑
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.neo_mystias_izakaya.dining_table");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemStacks) {
        this.items = NonNullList.copyOf(itemStacks);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    // === NBT ===

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putBoolean("IsOccupied", this.isOccupied);
        output.putString("CustomerId", this.customerId.toString());
        output.putInt("TableIndex", this.tableIndex);
        output.store("ControllerPos", BlockPos.CODEC, this.controllerPos);
        if (!this.currentOrder.equals(IzakayaOrder.EMPTY)) {
            output.store("Order", IzakayaOrder.CODEC, this.currentOrder);
        }
    }

    // === 网络同步 ===

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items.clear();
        ContainerHelper.loadAllItems(input, this.items);
        this.isOccupied = input.getBooleanOr("IsOccupied", false);
        this.customerId = input.getString("CustomerId")
                .map(Identifier::parse)
                .orElse(IzakayaOrder.EMPTY_RARE_CUSTOMER);
        this.tableIndex = input.getIntOr("TableIndex", -1);
        this.controllerPos = input.read("ControllerPos", BlockPos.CODEC).orElse(BlockPos.ZERO);
        this.currentOrder = input.read("Order", IzakayaOrder.CODEC).orElse(IzakayaOrder.EMPTY);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // === 业务方法 ===

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), NeoMystiasIzakaya.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            ContainerHelper.saveAllItems(output, this.items);
            output.putBoolean("IsOccupied", this.isOccupied);
            output.putString("CustomerId", this.customerId.toString());
            output.putInt("TableIndex", this.tableIndex);
            output.store("ControllerPos", BlockPos.CODEC, this.controllerPos);
            if (!this.currentOrder.equals(IzakayaOrder.EMPTY)) {
                output.store("Order", IzakayaOrder.CODEC, this.currentOrder);
            }
            return output.buildResult();
        }
    }

    /**
     * 顾客入座并派发订单
     */
    public void seatCustomer(IzakayaOrder order) {
        this.isOccupied = true;
        this.customerId = order.rareCustomer();
        this.currentOrder = order;
        markUpdated();
    }

    /**
     * 顾客离席
     */
    public void clearCustomer() {
        this.isOccupied = false;
        this.customerId = IzakayaOrder.EMPTY_RARE_CUSTOMER;
        this.currentOrder = IzakayaOrder.EMPTY;
        // 清空菜品和饮品
        this.items.clear();
        markUpdated();
    }

    /**
     * 餐桌是否空闲（无顾客且无物品）
     */
    public boolean isIdle() {
        return !this.isOccupied && this.items.stream().allMatch(ItemStack::isEmpty);
    }

    /**
     * 设置餐桌序号与所属控制器（由控制器调用）
     */
    public void bindToController(int index, BlockPos controller) {
        this.tableIndex = index;
        this.controllerPos = controller;
        markUpdated();
    }

    /**
     * 解除与控制器绑定
     */
    public void unbindFromController() {
        this.tableIndex = -1;
        this.controllerPos = BlockPos.ZERO;
        markUpdated();
    }

    /**
     * 获取菜品
     */
    public ItemStack getCuisine() {
        return this.items.get(SLOT_CUISINE);
    }

    /**
     * 设置菜品
     */
    public void setCuisine(ItemStack cuisine) {
        this.items.set(SLOT_CUISINE, cuisine);
        markUpdated();
    }

    public void clearCuisine() {
        this.setCuisine(ItemStack.EMPTY);
    }

    /**
     * 获取饮品
     */
    public ItemStack getBeverage() {
        return this.items.get(SLOT_BEVERAGE);
    }

    // === Tick ===

    /**
     * 设置饮品
     */
    public void setBeverage(ItemStack beverage) {
        this.items.set(SLOT_BEVERAGE, beverage);
        markUpdated();
    }

    public void clearBeverage() {
        this.setBeverage(ItemStack.EMPTY);
    }

    public boolean isFull() {
        return this.items.stream().noneMatch(ItemStack::isEmpty);
    }

    private void markUpdated() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    /**
     * 完全重置餐桌状态
     */
    public void clear() {
        this.items.clear();
        this.isOccupied = false;
        this.customerId = IzakayaOrder.EMPTY_RARE_CUSTOMER;
        this.currentOrder = IzakayaOrder.EMPTY;
        markUpdated();
    }
}
