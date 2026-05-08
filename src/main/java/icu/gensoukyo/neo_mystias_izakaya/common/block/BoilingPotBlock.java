package icu.gensoukyo.neo_mystias_izakaya.common.block;

import com.mojang.serialization.MapCodec;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.BoilingPotBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class BoilingPotBlock extends AbstractCooker {
    public static final MapCodec<BoilingPotBlock> CODEC = simpleCodec(BoilingPotBlock::new);

    public BoilingPotBlock(Properties properties) {
        super(properties
                .setId(ResourceKey.create(Registries.BLOCK, NeoMystiasIzakaya.id("boiling_pot")))
                .noOcclusion()
                .destroyTime(3.5F)
                .sound(SoundType.STONE)
                .lightLevel(litBlockEmission(13))
                .requiresCorrectToolForDrops());
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BoilingPotBE(blockPos, blockState);
    }
}
