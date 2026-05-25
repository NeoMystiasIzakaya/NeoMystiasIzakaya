/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.block;

import com.mojang.serialization.MapCodec;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class DiningTableBlock extends BaseEntityBlock {

    public static final MapCodec<DiningTableBlock> CODEC = simpleCodec(DiningTableBlock::new);

    /** 桌面 (高 14-16) + 四条桌腿 */
    public static final VoxelShape SHAPE = Shapes.join(
            Shapes.or(
                    box(0, 0, 0, 3, 14, 3),    // 西北腿
                    box(13, 0, 0, 16, 14, 3),   // 东北腿
                    box(0, 0, 13, 3, 14, 16),   // 西南腿
                    box(13, 0, 13, 16, 14, 16)  // 东南腿
            ),
            box(0, 14, 0, 16, 16, 16),           // 桌面
            BooleanOp.OR
    );

    public DiningTableBlock(Properties properties) {
        super(properties
                .setId(ResourceKey.create(Registries.BLOCK, NeoMystiasIzakaya.id("dining_table")))
                .noOcclusion()
                .destroyTime(2.0F)
                .sound(SoundType.WOOD)
                .requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DiningTableBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createDiningTableTicker(level, type, NMIBlockEntities.DINING_TABLE.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createDiningTableTicker(
            Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<? extends DiningTableBlockEntity> pClientType
    ) {
        return pLevel.isClientSide() ? null : createTickerHelper(pServerType, pClientType, DiningTableBlockEntity::serverTick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof DiningTableBlockEntity diningTableBE) {
                player.openMenu(diningTableBE, friendlyByteBuf -> friendlyByteBuf.writeBlockPos(diningTableBE.getBlockPos()));
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.SUCCESS;
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
    @SuppressWarnings("all")
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
