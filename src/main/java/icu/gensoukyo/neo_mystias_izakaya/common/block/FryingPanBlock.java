package icu.gensoukyo.neo_mystias_izakaya.common.block;

import com.mojang.serialization.MapCodec;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.FryingPanBE;
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

public class FryingPanBlock extends AbstractKitchenware {
    public static final MapCodec<FryingPanBlock> CODEC = simpleCodec(FryingPanBlock::new);
    public static final VoxelShape SHAPE = Shapes.join(box(1,0,1,15,3,15),box(0,0,0,0,0,0), BooleanOp.FIRST);

    public FryingPanBlock(Properties properties) {
        super(properties
                .setId(ResourceKey.create(Registries.BLOCK, NeoMystiasIzakaya.id("frying_pan")))
                .noOcclusion()
                .destroyTime(2.5F)
                .sound(SoundType.METAL)
                .lightLevel(litBlockEmission(8))
                .requiresCorrectToolForDrops());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FryingPanBE(blockPos, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
