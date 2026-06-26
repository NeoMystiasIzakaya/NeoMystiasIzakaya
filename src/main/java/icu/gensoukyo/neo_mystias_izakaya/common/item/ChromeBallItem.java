/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.common.entity.ChromeBallEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class ChromeBallItem extends Item implements ProjectileItem {
    public ChromeBallItem(Properties properties) {
        super(properties);
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(ChromeBallEntity::new, serverLevel, itemStack, player, 0.0F, 1.5F, 1.0F);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NonNull Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new ChromeBallEntity(level, position.x(), position.y(), position.z());
    }
}
