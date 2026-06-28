/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.block.CanteenControllerBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.CupboardBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.DiningTableBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.KitchenwareBlock;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenConfigUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.CanteenOrderUtil;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public class CanteenControllerBlockEntity extends BlockEntity {
    /** 派发间隔（tick） */
    private static final int DISPATCH_INTERVAL = 100;
    private LinkedHashSet<BlockPos> kitchenwareList = new LinkedHashSet<>();
    private LinkedHashSet<BlockPos> dingingTableList = new LinkedHashSet<>();
    private LinkedHashSet<BlockPos> cupboardList = new LinkedHashSet<>();

    // ==================== 代理层：EXTENSION 透明指向 MAIN ====================
    private boolean isOpen;
    @Nullable
    private UUID owner;

    public CanteenControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NMIBlockEntities.COUNTER.get(), blockPos, blockState);
    }

    // ==================== 公开访问器（全部委托到 MAIN） ====================

    /**
     * 主控 Tick：营业期间向空闲餐桌派发订单（单桌派发逻辑见 {@link CanteenOrderUtil#dispatch}）；
     * 同时校验关联方块完整性，发现已破坏的则移除并重排序号。
     */
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CanteenControllerBlockEntity pBlockEntity) {
        // 每 5 秒（100 tick）尝试派发一次
        if (pLevel.getGameTime() % DISPATCH_INTERVAL != 0) return;
        // 校验关联方块完整性：餐桌和厨具
        pBlockEntity.validateLinkedBlocks(pLevel);

        if (!pBlockEntity.isOpen) return;

        // 逐桌委托派发：空桌判定、概率入座、生成订单均由 CanteenOrderUtil 处理
        for (BlockPos tablePos : pBlockEntity.dingingTableList) {
            if (!pLevel.isLoaded(tablePos)) continue;
            if (!(pLevel.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table)) continue;
            CanteenOrderUtil.dispatch(pLevel, pBlockEntity, table);
        }
    }

    /**
     * 获取持有实际数据的 MAIN BlockEntity。EXTENSION 沿 facing 顺时针解析到 MAIN。
     */
    private CanteenControllerBlockEntity getMain() {
        if (level == null) return this;
        BlockState state = getBlockState();
        if (!state.hasProperty(CanteenControllerBlock.PART)
                || state.getValue(CanteenControllerBlock.PART) == CanteenControllerBlock.CanteenPart.MAIN) {
            return this;
        }
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos mainPos = getBlockPos().relative(facing.getClockWise());
        if (level.getBlockEntity(mainPos) instanceof CanteenControllerBlockEntity main) {
            return main;
        }
        return this;
    }

    /**
     * 始终返回 MAIN 的实际坐标（用于外部存储引用，如帽子 DataComponent）
     */
    public BlockPos getControllerPos() {
        return getMain().worldPosition;
    }

    private boolean isMain() {
        BlockState state = getBlockState();
        return state.hasProperty(CanteenControllerBlock.PART)
                && state.getValue(CanteenControllerBlock.PART) != CanteenControllerBlock.CanteenPart.MAIN;
    }

    public LinkedHashSet<BlockPos> getKitchenwareList() {
        return getMain().kitchenwareList;
    }

    public LinkedHashSet<BlockPos> getDiningTableList() {
        return getMain().dingingTableList;
    }

    public LinkedHashSet<BlockPos> getCupboardList() {
        return getMain().cupboardList;
    }

    // ==================== 内部实现（仅在 MAIN 上执行） ====================

    public boolean isOpen() {
        return getMain().isOpen;
    }

    @Nullable
    public UUID getOwner() {
        return getMain().owner;
    }

    public boolean addKitchenware(BlockPos pos) {
        return getMain().addKitchenwareImpl(pos);
    }

    public boolean removeKitchenware(BlockPos pos) {
        return getMain().removeKitchenwareImpl(pos);
    }

    public boolean addDiningTable(BlockPos pos) {
        return getMain().addDiningTableImpl(pos);
    }

    public boolean removeDiningTable(BlockPos pos) {
        return getMain().removeDiningTableImpl(pos);
    }

    public boolean addCupboard(BlockPos pos) {
        return getMain().addCupboardImpl(pos);
    }

    public boolean removeCupboard(BlockPos pos) {
        return getMain().removeCupboardImpl(pos);
    }

    public void setOpen(boolean open, @Nullable UUID ownerUUID) {
        getMain().setOpenImpl(open, ownerUUID);
    }

    public CanteenConfigUtil.ScanResult scanAndBind(Level level, BlockPos cornerA, BlockPos cornerB, int maxKitchenware, int maxDiningTables, int maxCupboards) {
        return getMain().scanAndBindImpl(level, cornerA, cornerB, maxKitchenware, maxDiningTables, maxCupboards);
    }

    private boolean addKitchenwareImpl(BlockPos pos) {
        boolean added = kitchenwareList.add(pos);
        if (added) markUpdated();
        return added;
    }

    private boolean removeKitchenwareImpl(BlockPos pos) {
        boolean removed = kitchenwareList.remove(pos);
        if (removed) markUpdated();
        return removed;
    }

    private boolean addDiningTableImpl(BlockPos pos) {
        boolean added = dingingTableList.add(pos);
        if (added) {
            syncTableIndices();
            markUpdated();
        }
        return added;
    }

    private boolean removeDiningTableImpl(BlockPos pos) {
        if (level != null && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof DiningTableBlockEntity table) {
            table.clear();
            table.resetIndex();
            table.resetController();
        }
        boolean removed = dingingTableList.remove(pos);
        if (removed) {
            syncTableIndices();
            markUpdated();
        }
        return removed;
    }

    private boolean addCupboardImpl(BlockPos pos) {
        boolean added = cupboardList.add(pos);
        if (added) markUpdated();
        return added;
    }

    private boolean removeCupboardImpl(BlockPos pos) {
        boolean removed = cupboardList.remove(pos);
        if (removed) markUpdated();
        return removed;
    }

    private void setOpenImpl(boolean open, @Nullable UUID ownerUUID) {
        this.isOpen = open;
        this.owner = open ? ownerUUID : null;
        markUpdated();
    }

    private CanteenConfigUtil.ScanResult scanAndBindImpl(Level level, BlockPos cornerA, BlockPos cornerB, int maxKitchenware, int maxDiningTables, int maxCupboards) {
        int minX = Math.min(cornerA.getX(), cornerB.getX());
        int minY = Math.min(cornerA.getY(), cornerB.getY());
        int minZ = Math.min(cornerA.getZ(), cornerB.getZ());
        int maxX = Math.max(cornerA.getX(), cornerB.getX());
        int maxY = Math.max(cornerA.getY(), cornerB.getY());
        int maxZ = Math.max(cornerA.getZ(), cornerB.getZ());

        int kitchenwareCount = 0;
        int diningTableCount = 0;
        int cupboardCount = 0;

        for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof KitchenwareBlock) {
                if (kitchenwareList.size() < maxKitchenware && addKitchenwareImpl(pos.immutable())) {
                    kitchenwareCount++;
                }
            } else if (state.getBlock() instanceof DiningTableBlock) {
                if (dingingTableList.size() < maxDiningTables && addDiningTableImpl(pos.immutable())) {
                    diningTableCount++;
                }
            } else if (state.getBlock() instanceof CupboardBlock) {
                if (cupboardList.size() < maxCupboards && addCupboardImpl(pos.immutable())) {
                    cupboardCount++;
                }
            }
        }
        return new CanteenConfigUtil.ScanResult(kitchenwareCount, diningTableCount, cupboardCount);
    }

    /**
     * 校验所有关联方块是否仍有效，移除已被破坏的，必要时重排序号
     */
    private void validateLinkedBlocks(Level pLevel) {
        boolean changed = dingingTableList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof DiningTableBlockEntity)
        );
        changed |= kitchenwareList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof KitchenwareBlockEntity)
        );
        changed |= cupboardList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof CupboardBlockEntity)
        );

        if (changed) {
            syncTableIndices();
            markUpdated();
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        if (isMain()) return;
        super.saveAdditional(output);
        output.store("kitchenware", BlockPos.CODEC.listOf(), new ArrayList<>(this.kitchenwareList));
        output.store("diningTable", BlockPos.CODEC.listOf(), new ArrayList<>(this.dingingTableList));
        output.store("cupboard", BlockPos.CODEC.listOf(), new ArrayList<>(this.cupboardList));
        output.putBoolean("isOpen", this.isOpen);
        if (this.owner != null) {
            output.store("Owner", UUIDUtil.CODEC, this.owner);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        if (isMain()) return;
        super.loadAdditional(input);
        this.kitchenwareList = new LinkedHashSet<>(input.read("kitchenware", BlockPos.CODEC.listOf()).orElse(List.of()));
        this.dingingTableList = new LinkedHashSet<>(input.read("diningTable", BlockPos.CODEC.listOf()).orElse(List.of()));
        this.cupboardList = new LinkedHashSet<>(input.read("cupboard", BlockPos.CODEC.listOf()).orElse(List.of()));
        this.isOpen = input.getBooleanOr("isOpen", false);
        this.owner = input.read("Owner", UUIDUtil.CODEC).orElse(null);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(getMain());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return getMain().getUpdateTagImpl(registries);
    }

    private CompoundTag getUpdateTagImpl(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), NeoMystiasIzakaya.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            output.store("kitchenware", BlockPos.CODEC.listOf(), new ArrayList<>(this.kitchenwareList));
            output.store("diningTable", BlockPos.CODEC.listOf(), new ArrayList<>(this.dingingTableList));
            output.putBoolean("isOpen", this.isOpen);
            if (this.owner != null) {
                output.store("Owner", UUIDUtil.CODEC, this.owner);
            }
            tag.merge(output.buildResult());
        }
        return tag;
    }

    // === 内部工具方法 ===

    private void syncTableIndices() {
        if (level == null || level.isClientSide()) return;
        int index = 1;
        BlockPos controllerPos = getControllerPos();
        for (BlockPos pos : dingingTableList) {
            if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof DiningTableBlockEntity table) {
                table.bindToController(index, controllerPos);
            }
            index++;
        }
    }

    private void markUpdated() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }
}
