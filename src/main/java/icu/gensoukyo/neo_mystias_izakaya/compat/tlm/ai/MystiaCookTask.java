package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.ai;

import com.github.tartaricacid.touhoulittlemaid.api.task.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitSounds;
import com.github.tartaricacid.touhoulittlemaid.util.SoundUtil;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task.MaidPrepCookTask;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MystiaCookTask implements IMaidTask {
    @Override
    public Identifier getUid() {
        return NeoMystiasIzakaya.id("mystias_izakaya_cook");
    }

    @Override
    public ItemStack getIcon() {
        return NMIMainItems.STEAMER.toStack();
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound(EntityMaid entityMaid) {
        return SoundUtil.environmentSound(entityMaid, InitSounds.MAID_IDLE.get(), 0.5f);
    }

    @Override
    public List<Pair<Integer, BehaviorControl<? super EntityMaid>>> createBrainTasks(EntityMaid maid) {
        return Lists.newArrayList(
                Pair.of(5, new MaidPrepCookTask())
        );
    }
}
