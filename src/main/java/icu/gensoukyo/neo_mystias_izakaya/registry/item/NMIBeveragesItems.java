package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class NMIBeveragesItems {
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

    public static final DeferredItem<Item> AFFOGATO = registerItem(ITEMS, "beverages/affogato", Item::new, props -> props);
    public static final DeferredItem<Item> AKATSUKI = registerItem(ITEMS, "beverages/akatsuki", Item::new, props -> props);
    public static final DeferredItem<Item> BLOODY_REMY = registerItem(ITEMS, "beverages/bloody_remy", Item::new, props -> props);
    public static final DeferredItem<Item> CASSIA_WINE = registerItem(ITEMS, "beverages/cassia_wine", Item::new, props -> props);
    public static final DeferredItem<Item> COFFEE = registerItem(ITEMS, "beverages/coffee", Item::new, props -> props);
    public static final DeferredItem<Item> DAIGINJO = registerItem(ITEMS, "beverages/daiginjo", Item::new, props -> props);
    public static final DeferredItem<Item> DASSAI = registerItem(ITEMS, "beverages/dassai", Item::new, props -> props);
    public static final DeferredItem<Item> DAUGHTER_OF_THE_SEA = registerItem(ITEMS, "beverages/daughter_of_the_sea", Item::new, props -> props);
    public static final DeferredItem<Item> DIVINE_WHEAT = registerItem(ITEMS, "beverages/divine_wheat", Item::new, props -> props);
    public static final DeferredItem<Item> DRUNKEN_BARD = registerItem(ITEMS, "beverages/drunken_bard", Item::new, props -> props);
    public static final DeferredItem<Item> FAIRY_DEW = registerItem(ITEMS, "beverages/fairy_dew", Item::new, props -> props);
    public static final DeferredItem<Item> FIRE_RATS_ROBE = registerItem(ITEMS, "beverages/fire_rats_robe", Item::new, props -> props);
    public static final DeferredItem<Item> FRUITY_HIGHBALL = registerItem(ITEMS, "beverages/fruity_highball", Item::new, props -> props);
    public static final DeferredItem<Item> FRUITY_SOUR = registerItem(ITEMS, "beverages/fruity_sour", Item::new, props -> props);
    public static final DeferredItem<Item> FULL_MOONS_EVE = registerItem(ITEMS, "beverages/full_moons_eve", Item::new, props -> props);
    public static final DeferredItem<Item> GIANT_POPSICLE = registerItem(ITEMS, "beverages/giant_popsicle", Item::new, props -> props);
    public static final DeferredItem<Item> GODFATHER = registerItem(ITEMS, "beverages/godfather", Item::new, props -> props);
    public static final DeferredItem<Item> GRAPEFRUIT_JUICE = registerItem(ITEMS, "beverages/grapefruit_juice", Item::new, props -> props);
    public static final DeferredItem<Item> GREEN_TEA = registerItem(ITEMS, "beverages/green_tea", Item::new, props -> props);
    public static final DeferredItem<Item> ICE_CREAM_SMOOTHIE = registerItem(ITEMS, "beverages/ice_cream_smoothie", Item::new, props -> props);
    public static final DeferredItem<Item> ICEBERG_KEDAMA_LEMONADE = registerItem(ITEMS, "beverages/iceberg_kedama_lemonade", Item::new, props -> props);
    public static final DeferredItem<Item> JADE_DEW_TEA = registerItem(ITEMS, "beverages/jade_dew_tea", Item::new, props -> props);
    public static final DeferredItem<Item> KOMEIJI_ICE_CREAM = registerItem(ITEMS, "beverages/komeiji_ice_cream", Item::new, props -> props);
    public static final DeferredItem<Item> MAKAI_COFFEE = registerItem(ITEMS, "beverages/makai_coffee", Item::new, props -> props);
    public static final DeferredItem<Item> MANGO_POMELO_SAGO = registerItem(ITEMS, "beverages/mango_pomelo_sago", Item::new, props -> props);
    public static final DeferredItem<Item> MILK = registerItem(ITEMS, "beverages/milk", Item::new, props -> props);
    public static final DeferredItem<Item> MIO = registerItem(ITEMS, "beverages/mio", Item::new, props -> props);
    public static final DeferredItem<Item> MOJITO_SPHERE = registerItem(ITEMS, "beverages/mojito_sphere", Item::new, props -> props);
    public static final DeferredItem<Item> MOON_ROCKET = registerItem(ITEMS, "beverages/moon_rocket", Item::new, props -> props);
    public static final DeferredItem<Item> NEGRONI = registerItem(ITEMS, "beverages/negroni", Item::new, props -> props);
    public static final DeferredItem<Item> ONI_KILLER = registerItem(ITEMS, "beverages/oni_killer", Item::new, props -> props);
    public static final DeferredItem<Item> ORDINARY_FITNESS_TEA = registerItem(ITEMS, "beverages/ordinary_fitness_tea", Item::new, props -> props);
    public static final DeferredItem<Item> QILIN = registerItem(ITEMS, "beverages/qilin", Item::new, props -> props);
    public static final DeferredItem<Item> RAMUNE = registerItem(ITEMS, "beverages/ramune", Item::new, props -> props);
    public static final DeferredItem<Item> RIBOVITAN = registerItem(ITEMS, "beverages/ribovitan", Item::new, props -> props);
    public static final DeferredItem<Item> SANGETSUSEI = registerItem(ITEMS, "beverages/sangetsusei", Item::new, props -> props);
    public static final DeferredItem<Item> SATELLITE_ICE_COFFEE = registerItem(ITEMS, "beverages/satellite_ice_coffee", Item::new, props -> props);
    public static final DeferredItem<Item> SCARLET_MIST = registerItem(ITEMS, "beverages/scarlet_mist", Item::new, props -> props);
    public static final DeferredItem<Item> SCARLET_TEA = registerItem(ITEMS, "beverages/scarlet_tea", Item::new, props -> props);
    public static final DeferredItem<Item> SPACE_BEER = registerItem(ITEMS, "beverages/space_beer", Item::new, props -> props);
    public static final DeferredItem<Item> SPARROW_SAKE = registerItem(ITEMS, "beverages/sparrow_sake", Item::new, props -> props);
    public static final DeferredItem<Item> TENGU_TANGO = registerItem(ITEMS, "beverages/tengu_tango", Item::new, props -> props);
    public static final DeferredItem<Item> THIS_SIDE_UP = registerItem(ITEMS, "beverages/this_side_up", Item::new, props -> props);
    public static final DeferredItem<Item> UMESHU = registerItem(ITEMS, "beverages/umeshu", Item::new, props -> props);
    public static final DeferredItem<Item> WIND_PRIESTESS = registerItem(ITEMS, "beverages/wind_priestess", Item::new, props -> props);
    public static final DeferredItem<Item> ZUN_BEER = registerItem(ITEMS, "beverages/zun_beer", Item::new, props -> props);

}
