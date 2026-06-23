package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.entity.ChromeBallEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMIEntities {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(NeoMystiasIzakaya.MODID);

    public static final Supplier<EntityType<ChromeBallEntity>> CHROME_BALL_ENTITY = ENTITY_TYPES.registerEntityType(
            "chrome_ball", ChromeBallEntity::new, MobCategory.MISC,
            builder -> builder.noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
}
