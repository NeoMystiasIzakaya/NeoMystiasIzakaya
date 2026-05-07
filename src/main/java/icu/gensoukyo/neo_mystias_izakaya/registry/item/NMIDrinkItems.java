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

    public static final DeferredItem<Item> AFFGADO = registerItem(ITEMS, "drink/affgado", Item::new, props -> props);

    public static final DeferredItem<Item> BEER = registerItem(ITEMS, "drink/beer", Item::new, props -> props);

    public static final DeferredItem<Item> BIG_POPSICLE = registerItem(ITEMS, "drink/big_popsicle", Item::new, props -> props);

    public static final DeferredItem<Item> BLESSING_WIND = registerItem(ITEMS, "drink/blessing_wind", Item::new, props -> props);

    public static final DeferredItem<Item> COFFEE = registerItem(ITEMS, "drink/coffee", Item::new, props -> props);

    public static final DeferredItem<Item> DAIGINJO = registerItem(ITEMS, "drink/daiginjo", Item::new, props -> props);

    public static final DeferredItem<Item> DAUGHTER_OF_THE_SEA = registerItem(ITEMS, "drink/daughter_of_the_sea", Item::new, props -> props);

    public static final DeferredItem<Item> DAWN = registerItem(ITEMS, "drink/dawn", Item::new, props -> props);

    public static final DeferredItem<Item> DEMONIC_COFFEE = registerItem(ITEMS, "drink/demonic_coffee", Item::new, props -> props);

    public static final DeferredItem<Item> DEMON_SLAYER = registerItem(ITEMS, "drink/demon_slayer", Item::new, props -> props);

    public static final DeferredItem<Item> DRUNK_ACTOR = registerItem(ITEMS, "drink/drunk_actor", Item::new, props -> props);

    public static final DeferredItem<Item> FAIRY_RAIN = registerItem(ITEMS, "drink/fairy_rain", Item::new, props -> props);

    public static final DeferredItem<Item> FIRE_RAT_FUR = registerItem(ITEMS, "drink/fire_rat_fur", Item::new, props -> props);

    public static final DeferredItem<Item> FOURTEENTH_NIGHT = registerItem(ITEMS, "drink/fourteenth_night", Item::new, props -> props);

    public static final DeferredItem<Item> FRUITY_HIGH_BALL = registerItem(ITEMS, "drink/fruity_high_ball", Item::new, props -> props);

    public static final DeferredItem<Item> FRUITY_SOUR = registerItem(ITEMS, "drink/fruity_sour", Item::new, props -> props);

    public static final DeferredItem<Item> GODFATHER = registerItem(ITEMS, "drink/godfather", Item::new, props -> props);

    public static final DeferredItem<Item> GODS_WHEAT = registerItem(ITEMS, "drink/gods_wheat", Item::new, props -> props);

    public static final DeferredItem<Item> GREEN_TEA = registerItem(ITEMS, "drink/green_tea", Item::new, props -> props);

    public static final DeferredItem<Item> GYOKURO_TEA = registerItem(ITEMS, "drink/gyokuro_tea", Item::new, props -> props);

    public static final DeferredItem<Item> HEAVEN_AND_EARTH_ARE_USELESS = registerItem(ITEMS, "drink/heaven_and_earth_are_useless", Item::new, props -> props);

    public static final DeferredItem<Item> ICEBERG_MAPLE_FROZEN_LEMON = registerItem(ITEMS, "drink/iceberg_maple_frozen_lemon", Item::new, props -> props);

    public static final DeferredItem<Item> KOMEIJI_ICE_CREAM = registerItem(ITEMS, "drink/komeiji_ice_cream", Item::new, props -> props);

    public static final DeferredItem<Item> MANGO_POMELO_SAGO = registerItem(ITEMS, "drink/mango_pomelo_sago", Item::new, props -> props);

    public static final DeferredItem<Item> MILK = registerItem(ITEMS, "drink/milk", Item::new, props -> props);

    public static final DeferredItem<Item> MOJITO_BURST_BALL = registerItem(ITEMS, "drink/mojito_burst_ball", Item::new, props -> props);

    public static final DeferredItem<Item> MOON_ROCKET = registerItem(ITEMS, "drink/moon_rocket", Item::new, props -> props);

    public static final DeferredItem<Item> NEGRONI = registerItem(ITEMS, "drink/negroni", Item::new, props -> props);

    public static final DeferredItem<Item> ORDINARY_FITNESS_TEA = registerItem(ITEMS, "drink/ordinary_fitness_tea", Item::new, props -> props);

    public static final DeferredItem<Item> OTTER_FESTIVAL = registerItem(ITEMS, "drink/otter_festival", Item::new, props -> props);

    public static final DeferredItem<Item> PALEO_CREAMY_SMOOTHIE = registerItem(ITEMS, "drink/paleo_creamy_smoothie", Item::new, props -> props);

    public static final DeferredItem<Item> PLUM_WINE = registerItem(ITEMS, "drink/plum_wine", Item::new, props -> props);

    public static final DeferredItem<Item> QI = registerItem(ITEMS, "drink/qi", Item::new, props -> props);

    public static final DeferredItem<Item> QILIN = registerItem(ITEMS, "drink/qilin", Item::new, props -> props);

    public static final DeferredItem<Item> QI_HEALTH = registerItem(ITEMS, "drink/qi_health", Item::new, props -> props);

    public static final DeferredItem<Item> RED_GRAPEFRUIT_JUICE = registerItem(ITEMS, "drink/red_grapefruit_juice", Item::new, props -> props);

    public static final DeferredItem<Item> RED_MIST = registerItem(ITEMS, "drink/red_mist", Item::new, props -> props);

    public static final DeferredItem<Item> SATELLITE_ICED_COFFEE = registerItem(ITEMS, "drink/satellite_iced_coffee", Item::new, props -> props);

    public static final DeferredItem<Item> SCARLET_DEVIL = registerItem(ITEMS, "drink/scarlet_devil", Item::new, props -> props);

    public static final DeferredItem<Item> SCARLET_DEVIL_MANSION_BLACK_TEA = registerItem(ITEMS, "drink/scarlet_devil_mansion_black_tea", Item::new, props -> props);

    public static final DeferredItem<Item> SODA = registerItem(ITEMS, "drink/soda", Item::new, props -> props);

    public static final DeferredItem<Item> SPACE_BEER = registerItem(ITEMS, "drink/space_beer", Item::new, props -> props);

    public static final DeferredItem<Item> SPARROW_SAKE = registerItem(ITEMS, "drink/sparrow_sake", Item::new, props -> props);

    public static final DeferredItem<Item> SUN_MOON_STAR = registerItem(ITEMS, "drink/sun_moon_star", Item::new, props -> props);

    public static final DeferredItem<Item> TENGU_DANCE = registerItem(ITEMS, "drink/tengu_dance", Item::new, props -> props);

    public static final DeferredItem<Item> WINTER_BREW = registerItem(ITEMS, "drink/winter_brew", Item::new, props -> props);

}
