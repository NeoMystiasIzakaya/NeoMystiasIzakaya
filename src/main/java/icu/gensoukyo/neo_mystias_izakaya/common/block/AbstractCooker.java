package icu.gensoukyo.neo_mystias_izakaya.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.ToIntFunction;

public abstract class AbstractCooker extends BaseEntityBlock {
    public AbstractCooker(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }

        //TODO 打开menu
        return super.useWithoutItem(state, level, pos, player, hitResult);
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
    @SuppressWarnings("all")
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    protected static ToIntFunction<BlockState> litBlockEmission(int lightEmission) {
        return state -> state.getValue(BlockStateProperties.LIT) ? lightEmission : 0;
    }

}
