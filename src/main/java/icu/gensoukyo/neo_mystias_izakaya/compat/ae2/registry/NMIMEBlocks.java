package icu.gensoukyo.neo_mystias_izakaya.compat.ae2.registry;

import appeng.block.AEBaseEntityBlock;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.compat.ae2.block.MECupBoardBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMEBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoMystiasIzakaya.MODID);

    public static final DeferredBlock<AEBaseEntityBlock<?>> ME_CUPBOARD = BLOCKS.registerBlock("me_cupboard", MECupBoardBlock::new);
}
