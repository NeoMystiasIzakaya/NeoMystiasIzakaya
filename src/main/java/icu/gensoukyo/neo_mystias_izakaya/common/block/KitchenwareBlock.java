/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.block;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.Kitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.ToIntFunction;

public class KitchenwareBlock extends BaseEntityBlock{

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MapCodec<KitchenwareBlock> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(propertiesCodec(),
                    Identifier.CODEC.fieldOf("KitchenwareType").forGetter(KitchenwareBlock::getKitchenwareTypeId)
            ).apply(instance, KitchenwareBlock::new));

    @Getter
    private final Identifier kitchenwareTypeId;

    public KitchenwareBlock(Properties properties, Identifier kitchenwareType) {
        super(properties
                .setId(ResourceKey.create(Registries.BLOCK, kitchenwareType))
                .noOcclusion()
                .requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
                .setValue(BlockStateProperties.LIT, false)
        );
        this.kitchenwareTypeId = kitchenwareType;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        Kitchenware kitchenware = NMIKitchenware.REGISTRY.getValue(kitchenwareTypeId);
        if (kitchenware == null){
            LOGGER.warn("Failed to create BlockEntity for KitchenwareBlock at {}, because the KitchenwareType {} is not registered.", blockPos, kitchenwareTypeId);
            return null;
        }
        return kitchenware.getKitchenwareBlockEntity(blockPos,blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        Kitchenware kitchenware = NMIKitchenware.REGISTRY.getValue(kitchenwareTypeId);
        if (kitchenware ==null){
            LOGGER.warn("Failed to create BlockEntityTicker for KitchenwareBlock at {}, because the KitchenwareType {} is not registered.", blockState, kitchenwareTypeId);
            return null;
        }
        return createCookTicker(level, type, kitchenware.getBlockEntityType());
    }


    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createCookTicker(
            Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<? extends KitchenwareBlockEntity> pClientType
    ) {
        return pLevel.isClientSide() ? null : createTickerHelper(pServerType, pClientType, KitchenwareBlockEntity::serverTick);
    }

    protected static ToIntFunction<BlockState> litBlockEmission(int lightEmission) {
        return state -> state.getValue(BlockStateProperties.LIT) ? lightEmission : 0;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (player.getMainHandItem().is(NMIMainItems.CANTEEN_CONFIG)) {
                return super.useWithoutItem(state, level, pos, player, hitResult);
            }
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof KitchenwareBlockEntity kitchenwareBE) {
                player.openMenu(kitchenwareBE, friendlyByteBuf -> friendlyByteBuf.writeBlockPos(kitchenwareBE.getBlockPos()));
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(BlockStateProperties.LIT, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(BlockStateProperties.LIT)) {
            for (int i = 0; i < 5; i++) {
                level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0, 0.1, 0.0);
            }
        }
    }


    public static class CuttingBoard extends KitchenwareBlock {

        public static final VoxelShape SHAPE = Shapes.join(box(1,0,1,15,1,15),box(0,0,0,0,0,0), BooleanOp.FIRST);

        public CuttingBoard(Identifier identifier) {
            super(BlockBehaviour.Properties.of()
                            .destroyTime(1.5F)
                            .sound(SoundType.WOOD)
                            .lightLevel(litBlockEmission(0))
                    , identifier);
        }

        @Override
        protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }
    public static class FryingPan extends KitchenwareBlock {

        public static final VoxelShape SHAPE = Shapes.join(box(1,0,1,15,3,15),box(0,0,0,0,0,0), BooleanOp.FIRST);

        public FryingPan(Identifier identifier) {
            super(BlockBehaviour.Properties.of()
                            .destroyTime(2.5F)
                            .sound(SoundType.METAL)
                            .lightLevel(litBlockEmission(8))
                    ,identifier);
        }

        @Override
        protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    public static class Grill extends KitchenwareBlock {

        public static final VoxelShape SHAPE = Shapes.join(box(2,0,2,14,4,14),box(0,0,0,0,0,0), BooleanOp.FIRST);

        public Grill(Identifier identifier) {
            super(BlockBehaviour.Properties.of()
                            .destroyTime(3.0F)
                            .sound(SoundType.METAL)
                            .lightLevel(litBlockEmission(10))
                    ,identifier);
        }

        @Override
        protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

    public static class Steamer extends KitchenwareBlock {

        public static final VoxelShape SHAPE = Shapes.join(box(2,0,2,14,7,14),box(0,0,0,0,0,0), BooleanOp.FIRST);

        public Steamer(Identifier identifier) {
            super(BlockBehaviour.Properties.of()
                            .destroyTime(3.0F)
                            .sound(SoundType.WOOD)
                            .lightLevel(litBlockEmission(9))
                    ,identifier);
        }

        @Override
        protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }
    public static class BoilingPot extends KitchenwareBlock {
        public static final VoxelShape SHAPE = Shapes.join(box(3,0,3,13,6,13),box(0,0,0,0,0,0), BooleanOp.FIRST);

        public BoilingPot(Identifier identifier) {
            super(BlockBehaviour.Properties.of()
                            .destroyTime(3.5F)
                            .sound(SoundType.STONE)
                            .lightLevel(litBlockEmission(13))
                    ,identifier);
        }

        @Override
        protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return SHAPE;
        }
    }

}
