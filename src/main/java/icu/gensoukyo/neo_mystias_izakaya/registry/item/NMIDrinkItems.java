package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class NMIDrinkItems {
    public static final List<DeferredItem<Item>> ITEM_LIST = new ArrayList<>();
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static DeferredItem<Item> registerItem(
            DeferredRegister.Items register,
            String name,
            Function<Item.Properties, Item> func,
            UnaryOperator<Item.Properties> properties
    ) {
        DeferredItem<Item> item = register.registerItem(name, func, properties);
        ITEM_LIST.add(item);
        return item;
    }

    public static final DeferredItem<Item> AFFOGATO = registerItem(ITEMS, "drink/affogato", Item::new, props -> props);
    public static final DeferredItem<Item> AKATSUKI = registerItem(ITEMS, "drink/akatsuki", Item::new, props -> props);
    public static final DeferredItem<Item> BLOODY_REMY = registerItem(ITEMS, "drink/bloody_remy", Item::new, props -> props);
    public static final DeferredItem<Item> CASSIA_WINE = registerItem(ITEMS, "drink/cassia_wine", Item::new, props -> props);
    public static final DeferredItem<Item> COFFEE = registerItem(ITEMS, "drink/coffee", Item::new, props -> props);
    public static final DeferredItem<Item> DAIGINJO = registerItem(ITEMS, "drink/daiginjo", Item::new, props -> props);
    public static final DeferredItem<Item> DASSAI = registerItem(ITEMS, "drink/dassai", Item::new, props -> props);
    public static final DeferredItem<Item> DAUGHTER_OF_THE_SEA = registerItem(ITEMS, "drink/daughter_of_the_sea", Item::new, props -> props);
    public static final DeferredItem<Item> DIVINE_WHEAT = registerItem(ITEMS, "drink/divine_wheat", Item::new, props -> props);
    public static final DeferredItem<Item> DRUNKEN_BARD = registerItem(ITEMS, "drink/drunken_bard", Item::new, props -> props);
    public static final DeferredItem<Item> FAIRY_DEW = registerItem(ITEMS, "drink/fairy_dew", Item::new, props -> props);
    public static final DeferredItem<Item> FIRE_RATS_ROBE = registerItem(ITEMS, "drink/fire_rats_robe", Item::new, props -> props);
    public static final DeferredItem<Item> FRUITY_HIGHBALL = registerItem(ITEMS, "drink/fruity_highball", Item::new, props -> props);
    public static final DeferredItem<Item> FRUITY_SOUR = registerItem(ITEMS, "drink/fruity_sour", Item::new, props -> props);
    public static final DeferredItem<Item> FULL_MOONS_EVE = registerItem(ITEMS, "drink/full_moons_eve", Item::new, props -> props);
    public static final DeferredItem<Item> GIANT_POPSICLE = registerItem(ITEMS, "drink/giant_popsicle", Item::new, props -> props);
    public static final DeferredItem<Item> GODFATHER = registerItem(ITEMS, "drink/godfather", Item::new, props -> props);
    public static final DeferredItem<Item> GRAPEFRUIT_JUICE = registerItem(ITEMS, "drink/grapefruit_juice", Item::new, props -> props);
    public static final DeferredItem<Item> GREEN_TEA = registerItem(ITEMS, "drink/green_tea", Item::new, props -> props);
    public static final DeferredItem<Item> ICE_CREAM_SMOOTHIE = registerItem(ITEMS, "drink/ice_cream_smoothie", Item::new, props -> props);
    public static final DeferredItem<Item> ICEBERG_KEDAMA_LEMONADE = registerItem(ITEMS, "drink/iceberg_kedama_lemonade", Item::new, props -> props);
    public static final DeferredItem<Item> JADE_DEW_TEA = registerItem(ITEMS, "drink/jade_dew_tea", Item::new, props -> props);
    public static final DeferredItem<Item> KOMEIJI_ICE_CREAM = registerItem(ITEMS, "drink/komeiji_ice_cream", Item::new, props -> props);
    public static final DeferredItem<Item> MAKAI_COFFEE = registerItem(ITEMS, "drink/makai_coffee", Item::new, props -> props);
    public static final DeferredItem<Item> MANGO_POMELO_SAGO = registerItem(ITEMS, "drink/mango_pomelo_sago", Item::new, props -> props);
    public static final DeferredItem<Item> MILK = registerItem(ITEMS, "drink/milk", Item::new, props -> props);
    public static final DeferredItem<Item> MIO = registerItem(ITEMS, "drink/mio", Item::new, props -> props);
    public static final DeferredItem<Item> MOJITO_SPHERE = registerItem(ITEMS, "drink/mojito_sphere", Item::new, props -> props);
    public static final DeferredItem<Item> MOON_ROCKET = registerItem(ITEMS, "drink/moon_rocket", Item::new, props -> props);
    public static final DeferredItem<Item> NEGRONI = registerItem(ITEMS, "drink/negroni", Item::new, props -> props);
    public static final DeferredItem<Item> ONI_KILLER = registerItem(ITEMS, "drink/oni_killer", Item::new, props -> props);
    public static final DeferredItem<Item> ORDINARY_FITNESS_TEA = registerItem(ITEMS, "drink/ordinary_fitness_tea", Item::new, props -> props);
    public static final DeferredItem<Item> QILIN = registerItem(ITEMS, "drink/qilin", Item::new, props -> props);
    public static final DeferredItem<Item> RAMUNE = registerItem(ITEMS, "drink/ramune", Item::new, props -> props);
    public static final DeferredItem<Item> RIBOVITAN = registerItem(ITEMS, "drink/ribovitan", Item::new, props -> props);
    public static final DeferredItem<Item> SANGETSUSEI = registerItem(ITEMS, "drink/sangetsusei", Item::new, props -> props);
    public static final DeferredItem<Item> SATELLITE_ICE_COFFEE = registerItem(ITEMS, "drink/satellite_ice_coffee", Item::new, props -> props);
    public static final DeferredItem<Item> SCARLET_MIST = registerItem(ITEMS, "drink/scarlet_mist", Item::new, props -> props);
    public static final DeferredItem<Item> SCARLET_TEA = registerItem(ITEMS, "drink/scarlet_tea", Item::new, props -> props);
    public static final DeferredItem<Item> SPACE_BEER = registerItem(ITEMS, "drink/space_beer", Item::new, props -> props);
    public static final DeferredItem<Item> SPARROW_SAKE = registerItem(ITEMS, "drink/sparrow_sake", Item::new, props -> props);
    public static final DeferredItem<Item> TENGU_TANGO = registerItem(ITEMS, "drink/tengu_tango", Item::new, props -> props);
    public static final DeferredItem<Item> THIS_SIDE_UP = registerItem(ITEMS, "drink/this_side_up", Item::new, props -> props);
    public static final DeferredItem<Item> UMESHU = registerItem(ITEMS, "drink/umeshu", Item::new, props -> props);
    public static final DeferredItem<Item> WIND_PRIESTESS = registerItem(ITEMS, "drink/wind_priestess", Item::new, props -> props);
    public static final DeferredItem<Item> ZUN_BEER = registerItem(ITEMS, "drink/zun_beer", Item::new, props -> props);

}
