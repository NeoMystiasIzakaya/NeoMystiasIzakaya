package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.block.BoilingPotBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.GrillBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.FryingPanBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.SteamerBlock;
import icu.gensoukyo.neo_mystias_izakaya.common.block.CuttingBoardBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoMystiasIzakaya.MODID);
    public static final DeferredBlock<Block> BOILING_POT = BLOCKS.register("boiling_pot", () -> new BoilingPotBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> GRILL = BLOCKS.register("grill", () -> new GrillBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> FRYING_PAN = BLOCKS.register("frying_pan", () -> new FryingPanBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> STEAMER = BLOCKS.register("steamer", () -> new SteamerBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CUTTING_BOARD = BLOCKS.register("cutting_board", () -> new CuttingBoardBlock(BlockBehaviour.Properties.of()));
}
