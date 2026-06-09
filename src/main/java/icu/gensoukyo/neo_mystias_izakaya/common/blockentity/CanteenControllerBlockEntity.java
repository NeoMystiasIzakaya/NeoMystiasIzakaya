/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.block.AbstractKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.common.block.CanteenControllerBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.DiningTableBlock;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.Customer;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.CustomerMap;
import icu.gensoukyo.neo_mystias_izakaya.content.customer.RareCustomerHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
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
import java.util.Map;
import java.util.UUID;

public class CanteenControllerBlockEntity extends BlockEntity {
    private LinkedHashSet<BlockPos> kitchenwareList = new LinkedHashSet<>();
    private LinkedHashSet<BlockPos> dingingTableList = new LinkedHashSet<>();
    private boolean isOpen;
    @Nullable
    private UUID owner;

    public CanteenControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NMIBlockEntities.COUNTER.get(), blockPos, blockState);
    }

    // ==================== 代理层：EXTENSION 透明指向 MAIN ====================

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

    // ==================== 公开访问器（全部委托到 MAIN） ====================

    public LinkedHashSet<BlockPos> getKitchenwareList() {
        return getMain().kitchenwareList;
    }

    public LinkedHashSet<BlockPos> getDiningTableList() {
        return getMain().dingingTableList;
    }

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

    public void setOpen(boolean open, @Nullable UUID ownerUUID) {
        getMain().setOpenImpl(open, ownerUUID);
    }

    public int[] scanAndBind(Level level, BlockPos cornerA, BlockPos cornerB, int maxKitchenware, int maxDiningTables) {
        return getMain().scanAndBindImpl(level, cornerA, cornerB, maxKitchenware, maxDiningTables);
    }

    // ==================== 内部实现（仅在 MAIN 上执行） ====================

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

    private void setOpenImpl(boolean open, @Nullable UUID ownerUUID) {
        this.isOpen = open;
        this.owner = open ? ownerUUID : null;
        markUpdated();
    }

    private int[] scanAndBindImpl(Level level, BlockPos cornerA, BlockPos cornerB, int maxKitchenware, int maxDiningTables) {
        int minX = Math.min(cornerA.getX(), cornerB.getX());
        int minY = Math.min(cornerA.getY(), cornerB.getY());
        int minZ = Math.min(cornerA.getZ(), cornerB.getZ());
        int maxX = Math.max(cornerA.getX(), cornerB.getX());
        int maxY = Math.max(cornerA.getY(), cornerB.getY());
        int maxZ = Math.max(cornerA.getZ(), cornerB.getZ());

        int kitchenwareCount = 0;
        int diningTableCount = 0;

        for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof AbstractKitchenware) {
                if (kitchenwareList.size() < maxKitchenware && addKitchenwareImpl(pos.immutable())) {
                    kitchenwareCount++;
                }
            } else if (state.getBlock() instanceof DiningTableBlock) {
                if (dingingTableList.size() < maxDiningTables && addDiningTableImpl(pos.immutable())) {
                    diningTableCount++;
                }
            }
        }
        return new int[]{kitchenwareCount, diningTableCount};
    }

    /** 每轮每张空闲餐桌有客入座的概率（0.0 ~ 1.0） */
    private static final float SEAT_CHANCE = 0.3F;
    /** 派发间隔（tick） */
    private static final int DISPATCH_INTERVAL = 100;

    /**
     * 主控 Tick：营业期间按概率向空闲餐桌派发订单；
     * 同时校验关联方块完整性，发现已破坏的则移除并重排序号。
     */
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, CanteenControllerBlockEntity pBlockEntity) {
        // 每 5 秒（100 tick）尝试派发一次
        if (pLevel.getGameTime() % DISPATCH_INTERVAL != 0) return;
        // 校验关联方块完整性：餐桌和厨具
        pBlockEntity.validateLinkedBlocks(pLevel);

        if (!pBlockEntity.isOpen) return;

        // 为每张空闲餐桌按概率派发订单
        for (BlockPos tablePos : pBlockEntity.dingingTableList) {
            if (!pLevel.isLoaded(tablePos)) continue;
            if (!(pLevel.getBlockEntity(tablePos) instanceof DiningTableBlockEntity table)) continue;
            if (!table.isIdle()) continue;

            // 按概率派发：只有命中概率才入座
            if (pLevel.getRandom().nextFloat() >= SEAT_CHANCE) continue;

            IzakayaOrder order = generateOrder(pLevel, pBlockEntity);
            if (order != null) {
                table.seatCustomer(order);
            }
        }
    }

    /**
     * 校验所有关联方块是否仍有效，移除已被破坏的，必要时重排序号
     */
    private void validateLinkedBlocks(Level pLevel) {
        boolean changed = dingingTableList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof DiningTableBlockEntity)
        );
        changed |= kitchenwareList.removeIf(pos ->
                !pLevel.isLoaded(pos) || !(pLevel.getBlockEntity(pos) instanceof AbstractKitchenwareBE)
        );

        if (changed) {
            syncTableIndices();
            markUpdated();
        }
    }

    /** 稀客出现概率 */
    private static final float RARE_CUSTOMER_CHANCE = 0.15F;

    /**
     * 生成新订单：随机选顾客 → 匹配菜品/饮品标签 → 构建 IzakayaOrder
     */
    private static @Nullable IzakayaOrder generateOrder(Level level, CanteenControllerBlockEntity controller) {
        CustomerMap customerMap = NMIDataAccessor.server().getCustomerMap();
        NMIRecipeMap recipeMap = NMIDataAccessor.server().getRecipeMap();
        RandomSource random = level.getRandom();

        // 确保标签索引已构建（首次调用时构建，后续命中缓存）
        if (recipeMap.getOutputTagToItemMap().isEmpty()) {
            recipeMap.buildOutputTagToItemMap(NMIDataAccessor.server().getTagItemListMap());
        }

        // ① 选择顾客（概率加权）
        CustomerHolder holder = pickCustomer(customerMap, random);
        if (holder == null) return null;
        Customer customer = holder.customer();
        boolean isRare = holder instanceof RareCustomerHolder;

        Identifier cuisineId;
        Identifier beverageId;
        Identifier rareCustomerId = holder.key();

        if (isRare) {
            // 稀客：两个都是 Tag
            cuisineId = pickRandom(customer.likes(), random);
            if (cuisineId == null) return null;
            beverageId = pickRandom(customer.beverage(), random);
        } else {
            // 普客：两个都是物品
            cuisineId = pickItemByTags(recipeMap, customer.likes(), random);
            if (cuisineId == null) return null;
            beverageId = pickItemByTag(NMIDataAccessor.server().getTagItemListMap(), customer.beverage(), random);
        }
        if (beverageId == null) return null;

        return new IzakayaOrder(cuisineId, beverageId, rareCustomerId, isRare);
    }

    /**
     * 从顾客池中按概率选一位（稀客 15%，普客 85%）
     */
    private static @Nullable CustomerHolder pickCustomer(CustomerMap customerMap, RandomSource random) {
        boolean isRare = random.nextFloat() < RARE_CUSTOMER_CHANCE;
        List<? extends CustomerHolder> pool;

        if (isRare) {
            pool = customerMap.getRareCustomers();
            if (pool.isEmpty()) pool = customerMap.getCommonCustomers();
        } else {
            pool = customerMap.getCommonCustomers();
            if (pool.isEmpty()) pool = customerMap.getRareCustomers();
        }

        if (pool.isEmpty()) return null;
        return pool.get(random.nextInt(pool.size()));
    }

    /**
     * 从 recipeMap 的标签索引中随机匹配一个菜品产物
     */
    private static @Nullable Identifier pickItemByTags(NMIRecipeMap recipeMap, List<Identifier> tags, RandomSource random) {
        if (tags.isEmpty()) return null;

        Map<Identifier, List<Identifier>> tagIndex = recipeMap.getOutputTagToItemMap();
        if (tagIndex.isEmpty()) return null;

        // 随机选一个标签，查索引获取匹配的产物列表
        Identifier targetTag = tags.get(random.nextInt(tags.size()));
        List<Identifier> matchingItems = tagIndex.get(targetTag);
        if (matchingItems != null && !matchingItems.isEmpty()) {
            return matchingItems.get(random.nextInt(matchingItems.size()));
        }

        return null;
    }

    /**
     * 从 TagItemListMap 中根据标签列表随机选取一个酒水物品 ID
     */
    private static @Nullable Identifier pickItemByTag(TagItemListMap tagItemListMap, List<Identifier> tags, RandomSource random) {
        if (tags.isEmpty()) return null;

        // 随机选一个标签
        Identifier targetTag = tags.get(random.nextInt(tags.size()));
        TagItemListHolder holder = tagItemListMap.get(targetTag);
        if (holder == null) return null;

        List<Identifier> items = holder.tag().items();
        if (items.isEmpty()) return null;

        return items.get(random.nextInt(items.size()));
    }

    @Nullable
    private static <T> T pickRandom(List<T> list, RandomSource random) {
        if (list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        if (isMain()) return;
        super.saveAdditional(output);
        output.store("kitchenware", BlockPos.CODEC.listOf(), new ArrayList<>(this.kitchenwareList));
        output.store("diningTable", BlockPos.CODEC.listOf(), new ArrayList<>(this.dingingTableList));
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
