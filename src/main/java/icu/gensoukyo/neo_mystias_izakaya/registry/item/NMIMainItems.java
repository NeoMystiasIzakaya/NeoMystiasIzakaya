package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMainItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static final DeferredItem<Item> BOILING_POT = ITEMS.registerItem("cooker/boiling_pot",(properties) -> new BlockItem(NMIBlocks.BOILING_POT.get(), properties), properties -> properties);
    public static final DeferredItem<Item> GRILL = ITEMS.registerItem("cooker/grill",(properties) -> new BlockItem(NMIBlocks.GRILL.get(), properties), properties -> properties);
    public static final DeferredItem<Item> FRYING_PAN = ITEMS.registerItem("cooker/frying_pan",(properties) -> new BlockItem(NMIBlocks.FRYING_PAN.get(), properties), properties -> properties);
    public static final DeferredItem<Item> STEAMER = ITEMS.registerItem("cooker/steamer",(properties) -> new BlockItem(NMIBlocks.STEAMER.get(), properties), properties -> properties);
    public static final DeferredItem<Item> CUTTING_BOARD = ITEMS.registerItem("cooker/cutting_board",(properties) -> new BlockItem(NMIBlocks.CUTTING_BOARD.get(), properties), properties -> properties);
    
    public static final DeferredItem<Item> CHROME_BALL = ITEMS.registerItem("misc/chrome_ball", Item::new, properties -> properties);
}
