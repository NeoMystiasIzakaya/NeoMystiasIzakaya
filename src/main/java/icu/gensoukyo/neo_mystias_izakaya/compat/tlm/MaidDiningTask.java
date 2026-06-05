package icu.gensoukyo.neo_mystias_izakaya.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntitySit;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBrains;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.common.block.DiningTableBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIPoi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Random;

import static icu.gensoukyo.neo_mystias_izakaya.compat.tlm.MystiaTask.MystiasSeat;

public class MaidDiningTask extends MaidCheckRateTask {
    float speed;
    int closeEnoughDist;

    public MaidDiningTask(float movementSpeed, int closeEnoughDist) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                InitBrains.TARGET_POS.get(), MemoryStatus.VALUE_ABSENT));
        this.closeEnoughDist = closeEnoughDist;
        this.speed = movementSpeed;
        this.setMaxCheckRate(10);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel worldIn, EntityMaid maid) {
        if (super.checkExtraStartConditions(worldIn, maid) && maid.canBrainMoving()) {
            BlockPos diningTable = findDiningTable(worldIn, maid);
            if (diningTable != null && maid.canPathReach(diningTable)) {
                if (diningTable.distToCenterSqr(maid.position()) < Math.pow(this.closeEnoughDist, 2)) {
                    maid.getBrain().setMemory(InitBrains.TARGET_POS.get(), new BlockPosTracker(diningTable));
                    return true;
                }
                BehaviorUtils.setWalkAndLookTargetMemories(maid, diningTable, speed, 1);
                this.setNextCheckTickCount(5);
            } else {
                maid.getBrain().eraseMemory(InitBrains.TARGET_POS.get());
            }
        }
        return false;
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        maid.getBrain().getMemory(InitBrains.TARGET_POS.get()).ifPresent((targetPos) -> {
            BlockPos pos = targetPos.currentBlockPosition();
            BlockState blockState = level.getBlockState(pos);
            if (blockState.getBlock() instanceof DiningTableBlock) {
                this.startMaidSit(maid, blockState, level, pos);
                maid.getBrain().setMemory(NMIMemoryTypes.TARGET_POS.get(), pos);
            }
        });
        maid.getBrain().eraseMemory(InitBrains.TARGET_POS.get());
        maid.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        maid.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    public void startMaidSit(EntityMaid maid, BlockState state, Level worldIn, BlockPos pos) {
        if (worldIn instanceof ServerLevel serverLevel) {
            BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
            if (blockEntity instanceof DiningTableBlockEntity diningTableBlock) {
                BlockPos position = diningTableBlock.getBlockPos();
                Random random = new Random();
                Direction direction = Direction.values()[random.nextInt(4) + 2];
                Vec3i normal = direction.getUnitVec3i();
                EntitySit newSitEntity = new EntitySit(worldIn, new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5).add(Vec3.atLowerCornerOf(normal)), MystiasSeat, position);
                newSitEntity.setYRot(direction.getOpposite().toYRot());
                worldIn.addFreshEntity(newSitEntity);
                diningTableBlock.setSeatEntityId(newSitEntity.getUUID());
                maid.startRiding(newSitEntity);
            }
        }
    }

    @Nullable
    private BlockPos findDiningTable(ServerLevel world, EntityMaid maid) {
        BlockPos blockPos = maid.getBrainSearchPos();
        PoiManager poiManager = world.getPoiManager();
        int range = (int) maid.searchRadius();
        return poiManager.getInRange(type -> type.value().equals(NMIPoi.DINING_TABLE.get()), blockPos, range, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos).filter(pos -> !isOccupied(world, pos))
                .min(Comparator.comparingDouble(pos -> pos.distSqr(maid.blockPosition()))).orElse(null);
    }

    private boolean isOccupied(ServerLevel worldIn, BlockPos pos) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (te instanceof DiningTableBlockEntity tableBlockEntityTE) {
            return tableBlockEntityTE.isOccupied();
        }
        return true;
    }
}
