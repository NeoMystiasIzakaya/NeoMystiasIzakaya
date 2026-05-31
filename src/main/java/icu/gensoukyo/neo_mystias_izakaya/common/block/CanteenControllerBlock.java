/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.block;

import com.mojang.serialization.MapCodec;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class CanteenControllerBlock extends BaseEntityBlock {
    public static final MapCodec<CanteenControllerBlock> CODEC = simpleCodec(CanteenControllerBlock::new);

    public CanteenControllerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        );
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createCounterTicker(
            Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<? extends CanteenControllerBlockEntity> pClientType
    ) {
        return pLevel.isClientSide() ? null : createTickerHelper(pServerType, pClientType, CanteenControllerBlockEntity::serverTick);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CanteenControllerBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createCounterTicker(level, type, NMIBlockEntities.COUNTER.get());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof CanteenControllerBlockEntity controller) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.isEmpty()) {
                boolean open = !controller.isOpen();
                controller.setOpen(open);
                player.sendSystemMessage(
                        Component.translatable(open
                                ? "block.neo_mystias_izakaya.canteen_controller.open"
                                : "block.neo_mystias_izakaya.canteen_controller.close")
                );

                //闭店时抹去餐桌的信息
                if (!open) {
                    controller.getDiningTableList().forEach(tablePos -> {
                        BlockEntity tableBE = level.getBlockEntity(tablePos);
                        if (tableBE instanceof DiningTableBlockEntity diningTable) {
                            diningTable.clear();
                        }
                    });
                }

                //开店时同步信息进入帽子进行高亮显示
                ItemStack headItem = player.getItemBySlot(EquipmentSlot.HEAD);
                if (headItem.is(NMIMainItems.MYSTIAS_HAT)) {
                    if (open) {
                        headItem.set(NMIDataComponentTypes.BOUND_CONTROLLER, controller.getBlockPos());
                        headItem.set(NMIDataComponentTypes.BOUND_KITCHENWARE, new ArrayList<>(controller.getKitchenwareList()));
                        headItem.set(NMIDataComponentTypes.BOUND_DINING_TABLES, new ArrayList<>(controller.getDiningTableList()));
                    } else {
                        headItem.remove(NMIDataComponentTypes.BOUND_CONTROLLER);
                        headItem.remove(NMIDataComponentTypes.BOUND_KITCHENWARE);
                        headItem.remove(NMIDataComponentTypes.BOUND_DINING_TABLES);
                    }
                }
            }
            if (mainHandItem.is(NMIMainItems.CANTEEN_CONFIG)) {
                player.sendSystemMessage(
                        Component.translatable("block.neo_mystias_izakaya.canteen_controller.status",
                                controller.getKitchenwareList().size(),
                                controller.getDiningTableList().size())
                );
                return super.useWithoutItem(state, level, pos, player, hitResult);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
