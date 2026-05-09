package icu.gensoukyo.neo_mystias_izakaya.common.block;

import com.mojang.serialization.MapCodec;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.SteamerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class SteamerBlock extends AbstractKitchenware {
    public static final MapCodec<SteamerBlock> CODEC = simpleCodec(SteamerBlock::new);
    public static final VoxelShape SHAPE = Shapes.join(box(2,0,2,14,7,14),box(0,0,0,0,0,0), BooleanOp.FIRST);

    public SteamerBlock(Properties properties) {
        super(properties
                .setId(ResourceKey.create(Registries.BLOCK, NeoMystiasIzakaya.id("steamer")))
                .noOcclusion()
                .destroyTime(3.0F)
                .sound(SoundType.WOOD)
                .lightLevel(litBlockEmission(9))
                .requiresCorrectToolForDrops());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SteamerBE(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
