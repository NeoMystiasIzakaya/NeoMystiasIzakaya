package icu.gensoukyo.neo_mystias_izakaya.common.entity;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

public class ChromeBallEntity extends ThrowableItemProjectile {
    public ChromeBallEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(NMIEntities.CHROME_BALL_ENTITY.get(), level);
    }

    public ChromeBallEntity(Level level, double x, double y, double z) {
        super(NMIEntities.CHROME_BALL_ENTITY.get(), x, y, z, level, NMIMainItems.CHROME_BALL.toStack());
    }

    public ChromeBallEntity(ServerLevel serverLevel, LivingEntity livingEntity, ItemStack itemStack) {
        super(NMIEntities.CHROME_BALL_ENTITY.get(), livingEntity, serverLevel, itemStack);
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return NMIMainItems.CHROME_BALL.get();
    }

    private ParticleOptions getParticle() {
        ItemStack item = this.getItem();
        return item.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));
    }

    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particle = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F, 0.0F);
            }
        }

    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}
