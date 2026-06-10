package icu.gensoukyo.neo_mystias_izakaya.common.block;

import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StoreBlock extends Block {
    public static final VoxelShape SHAPE = box(3, 0, 3, 13, 5, 13);

    public StoreBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            NMIClientUtil.openStoreScreen();
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.SUCCESS_SERVER;
        }
    }
}
