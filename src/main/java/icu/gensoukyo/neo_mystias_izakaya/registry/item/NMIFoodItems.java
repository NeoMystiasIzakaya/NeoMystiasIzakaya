package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class NMIFoodItems {
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

    public static final DeferredItem<Item> ALL_MEAT_FEAST = registerItem(ITEMS, "food/all_meat_feast", Item::new, props -> props);

    public static final DeferredItem<Item> PEACH_SHRIMP_SALAD = registerItem(ITEMS, "food/peach_shrimp_salad", Item::new, props -> props);

    public static final DeferredItem<Item> TEMPURA_PLATTER = registerItem(ITEMS, "food/tempura_platter", Item::new, props -> props);

    public static final DeferredItem<Item> LITTLE_SWEET_POISON = registerItem(ITEMS, "food/little_sweet_poison", Item::new, props -> props);

    public static final DeferredItem<Item> CREAMY_CRAB = registerItem(ITEMS, "food/creamy_crab", Item::new, props -> props);

    public static final DeferredItem<Item> BAKED_SWEET_POTATO = registerItem(ITEMS, "food/baked_sweet_potato", Item::new, props -> props);

    public static final DeferredItem<Item> PORK_BAMBOO_SHOOTS_STIR_FRY = registerItem(ITEMS, "food/pork_bamboo_shoots_stir_fry", Item::new, props -> props);

    public static final DeferredItem<Item> BAMBOO_MEAT_POT = registerItem(ITEMS, "food/bamboo_meat_pot", Item::new, props -> props);

    public static final DeferredItem<Item> STEAMED_EGG_BAMBOO_SHOOTS = registerItem(ITEMS, "food/steamed_egg_bamboo_shoots", Item::new, props -> props);

    public static final DeferredItem<Item> DRUNK_SHRIMP_IN_BAMBOO = registerItem(ITEMS, "food/drunk_shrimp_in_bamboo", Item::new, props -> props);

    public static final DeferredItem<Item> RICE_POWDER_MEAT = registerItem(ITEMS, "food/rice_powder_meat", Item::new, props -> props);

    public static final DeferredItem<Item> IMITATION_BEAR_PAW = registerItem(ITEMS, "food/imitation_bear_paw", Item::new, props -> props);

    public static final DeferredItem<Item> TWO_FLAVOR_BEEF_HOTPOT = registerItem(ITEMS, "food/two_flavor_beef_hotpot", Item::new, props -> props);

    public static final DeferredItem<Item> BEEF_BOWL = registerItem(ITEMS, "food/beef_bowl", Item::new, props -> props);

    public static final DeferredItem<Item> BEEF_WELLINGTON = registerItem(ITEMS, "food/beef_wellington", Item::new, props -> props);

    public static final DeferredItem<Item> KABUTO_STEAMED_CAKE = registerItem(ITEMS, "food/kabuto_steamed_cake", Item::new, props -> props);

    public static final DeferredItem<Item> BISCAY_BISCUITS = registerItem(ITEMS, "food/biscay_biscuits", Item::new, props -> props);

    public static final DeferredItem<Item> SICHUAN_BOILED_FISH = registerItem(ITEMS, "food/sichuan_boiled_fish", Item::new, props -> props);

    public static final DeferredItem<Item> KABAYAKI_LAMPREYS = registerItem(ITEMS, "food/kabayaki_lampreys", Item::new, props -> props);

    public static final DeferredItem<Item> BRAISED_PORK_WITH_PEACH = registerItem(ITEMS, "food/braised_pork_with_peach", Item::new, props -> props);

    public static final DeferredItem<Item> BURN_OUT_PUDDING = registerItem(ITEMS, "food/burn_out_pudding", Item::new, props -> props);

    public static final DeferredItem<Item> CLASSIC_STEAK = registerItem(ITEMS, "food/classic_steak", Item::new, props -> props);

    public static final DeferredItem<Item> CANDIED_CHESTNUTS = registerItem(ITEMS, "food/candied_chestnuts", Item::new, props -> props);

    public static final DeferredItem<Item> CANDIED_SWEET_POTATO = registerItem(ITEMS, "food/candied_sweet_potato", Item::new, props -> props);

    public static final DeferredItem<Item> CATS_PLAYING_IN_WATER = registerItem(ITEMS, "food/cats_playing_in_water", Item::new, props -> props);

    public static final DeferredItem<Item> CAT_FOOD = registerItem(ITEMS, "food/cat_food", Item::new, props -> props);

    public static final DeferredItem<Item> CAT_KULULI = registerItem(ITEMS, "food/cat_kululi", Item::new, props -> props);

    public static final DeferredItem<Item> CAT_PIZZA = registerItem(ITEMS, "food/cat_pizza", Item::new, props -> props);

    public static final DeferredItem<Item> CHEESE_EGG = registerItem(ITEMS, "food/cheese_egg", Item::new, props -> props);

    public static final DeferredItem<Item> COLD_DISH_CARVING = registerItem(ITEMS, "food/cold_dish_carving", Item::new, props -> props);

    public static final DeferredItem<Item> COLD_TOFU = registerItem(ITEMS, "food/cold_tofu", Item::new, props -> props);

    public static final DeferredItem<Item> COLORFUL_JADE_FRIED_BUNS = registerItem(ITEMS, "food/colorful_jade_fried_buns", Item::new, props -> props);

    public static final DeferredItem<Item> COOKING_TOFU = registerItem(ITEMS, "food/cooking_tofu", Item::new, props -> props);

    public static final DeferredItem<Item> CREAM_STEW = registerItem(ITEMS, "food/cream_stew", Item::new, props -> props);

    public static final DeferredItem<Item> CRISP_CYCLONE = registerItem(ITEMS, "food/crisp_cyclone", Item::new, props -> props);

    public static final DeferredItem<Item> DARK_CUISINE = registerItem(ITEMS, "food/dark_cuisine", Item::new, props -> props);

    public static final DeferredItem<Item> DEEP_FRIED_CICADA_SHELLS = registerItem(ITEMS, "food/deep_fried_cicada_shells", Item::new, props -> props);

    public static final DeferredItem<Item> DEPRESSED_CHEESE_STICKS = registerItem(ITEMS, "food/depressed_cheese_sticks", Item::new, props -> props);

    public static final DeferredItem<Item> DEW_BOILED_EGGS = registerItem(ITEMS, "food/dew_boiled_eggs", Item::new, props -> props);

    public static final DeferredItem<Item> DORAYAKI = registerItem(ITEMS, "food/dorayaki", Item::new, props -> props);

    public static final DeferredItem<Item> DUMPLING = registerItem(ITEMS, "food/dumpling", Item::new, props -> props);

    public static final DeferredItem<Item> EEL_EGG_DONBURI = registerItem(ITEMS, "food/eel_egg_donburi", Item::new, props -> props);

    public static final DeferredItem<Item> EGGS_BENEDICT = registerItem(ITEMS, "food/eggs_benedict", Item::new, props -> props);

    public static final DeferredItem<Item> ENERGY_STRING = registerItem(ITEMS, "food/energy_string", Item::new, props -> props);

    public static final DeferredItem<Item> FAILING_SAKURA_SNOW = registerItem(ITEMS, "food/failing_sakura_snow", Item::new, props -> props);

    public static final DeferredItem<Item> FANTASY_IS_ALL_THE_RAGE = registerItem(ITEMS, "food/fantasy_is_all_the_rage", Item::new, props -> props);

    public static final DeferredItem<Item> FISH_LEAPS_OVER_DRAGON_GATE = registerItem(ITEMS, "food/fish_leaps_over_dragon_gate", Item::new, props -> props);

    public static final DeferredItem<Item> FLOWERS_BIRDS_WIND_AND_MOON = registerItem(ITEMS, "food/flowers_birds_wind_and_moon", Item::new, props -> props);

    public static final DeferredItem<Item> FLOWING_WATER_NOODLES = registerItem(ITEMS, "food/flowing_water_noodles", Item::new, props -> props);

    public static final DeferredItem<Item> FRIED_HAGFISH = registerItem(ITEMS, "food/fried_hagfish", Item::new, props -> props);

    public static final DeferredItem<Item> FRIED_PORK_CUTLET = registerItem(ITEMS, "food/fried_pork_cutlet", Item::new, props -> props);

    public static final DeferredItem<Item> FRIED_PORK_SHREDS = registerItem(ITEMS, "food/fried_pork_shreds", Item::new, props -> props);

    public static final DeferredItem<Item> FRIED_SHRIMP_TEMPURA = registerItem(ITEMS, "food/fried_shrimp_tempura", Item::new, props -> props);

    public static final DeferredItem<Item> FRIED_TOFU = registerItem(ITEMS, "food/fried_tofu", Item::new, props -> props);

    public static final DeferredItem<Item> FRIED_TOMATO_STRIPS = registerItem(ITEMS, "food/fried_tomato_strips", Item::new, props -> props);

    public static final DeferredItem<Item> FRIGHT_ADVENTURE = registerItem(ITEMS, "food/fright_adventure", Item::new, props -> props);

    public static final DeferredItem<Item> GAME_SOUP = registerItem(ITEMS, "food/game_soup", Item::new, props -> props);

    public static final DeferredItem<Item> GENSOKYO_BUDDHA_JUMPS_OVER_THE_WALL = registerItem(ITEMS, "food/gensokyo_buddha_jumps_over_the_wall", Item::new, props -> props);

    public static final DeferredItem<Item> GENSOKYO_STAR_LOTUS_SHIP = registerItem(ITEMS, "food/gensokyo_star_lotus_ship", Item::new, props -> props);

    public static final DeferredItem<Item> GIANT_TAMAGOYAKI = registerItem(ITEMS, "food/giant_tamagoyaki", Item::new, props -> props);

    public static final DeferredItem<Item> GINKGO_AND_RADISH_PORK_RIB_SOUP = registerItem(ITEMS, "food/ginkgo_and_radish_pork_rib_soup", Item::new, props -> props);

    public static final DeferredItem<Item> GLOOMY_FRUIT_PIE = registerItem(ITEMS, "food/gloomy_fruit_pie", Item::new, props -> props);

    public static final DeferredItem<Item> GLUTINOUS_RICE_BALLS = registerItem(ITEMS, "food/glutinous_rice_balls", Item::new, props -> props);

    public static final DeferredItem<Item> GOLDEN_CRISPY_FISH_CAKE = registerItem(ITEMS, "food/golden_crispy_fish_cake", Item::new, props -> props);

    public static final DeferredItem<Item> GRAND_BANQUET = registerItem(ITEMS, "food/grand_banquet", Item::new, props -> props);

    public static final DeferredItem<Item> GREEN_BAMBOO_WELCOMES_SPRING = registerItem(ITEMS, "food/green_bamboo_welcomes_spring", Item::new, props -> props);

    public static final DeferredItem<Item> GREEN_FAIRY_MUSHROOM = registerItem(ITEMS, "food/green_fairy_mushroom", Item::new, props -> props);

    public static final DeferredItem<Item> GRILLED_HAGFISH = registerItem(ITEMS, "food/grilled_hagfish", Item::new, props -> props);

    public static final DeferredItem<Item> GRILLED_PORK_RICE_BALLS = registerItem(ITEMS, "food/grilled_pork_rice_balls", Item::new, props -> props);

    public static final DeferredItem<Item> HEART_PORRIDGE_GRUEL = registerItem(ITEMS, "food/heart_porridge_gruel", Item::new, props -> props);

    public static final DeferredItem<Item> HELL_THRILL_WARNING = registerItem(ITEMS, "food/hell_thrill_warning", Item::new, props -> props);

    public static final DeferredItem<Item> HOLY_WHITE_LOTUS_SEED_CAKE = registerItem(ITEMS, "food/holy_white_lotus_seed_cake", Item::new, props -> props);

    public static final DeferredItem<Item> HONEY_BBQ_PORK = registerItem(ITEMS, "food/honey_bbq_pork", Item::new, props -> props);

    public static final DeferredItem<Item> HORAI_DAMA_NO_EDA = registerItem(ITEMS, "food/horai-dama_no_eda", Item::new, props -> props);

    public static final DeferredItem<Item> HOT_WAFFLES = registerItem(ITEMS, "food/hot_waffles", Item::new, props -> props);

    public static final DeferredItem<Item> HULA_SOUP = registerItem(ITEMS, "food/hula_soup", Item::new, props -> props);

    public static final DeferredItem<Item> LION_HEAD = registerItem(ITEMS, "food/lion_head", Item::new, props -> props);

    public static final DeferredItem<Item> LONGYIN_PEACH = registerItem(ITEMS, "food/longyin_peach", Item::new, props -> props);

    public static final DeferredItem<Item> LOOKING_UP_AT_THE_CEILING_FRUIT_PIE = registerItem(ITEMS, "food/looking_up_at_the_ceiling_fruit_pie", Item::new, props -> props);

    public static final DeferredItem<Item> LOTUS_FISH_RICE_BOWL = registerItem(ITEMS, "food/lotus_fish_rice_bowl", Item::new, props -> props);

    public static final DeferredItem<Item> LUOHAN_VEGETARIAN = registerItem(ITEMS, "food/luohan_vegetarian", Item::new, props -> props);

    public static final DeferredItem<Item> MAD_HATTER_TEA_PARTY = registerItem(ITEMS, "food/mad_hatter_tea_party", Item::new, props -> props);

    public static final DeferredItem<Item> MAGMA = registerItem(ITEMS, "food/magma", Item::new, props -> props);

    public static final DeferredItem<Item> MAOYU_LAVA_TOFU = registerItem(ITEMS, "food/maoyu_lava_tofu", Item::new, props -> props);

    public static final DeferredItem<Item> MAOYU_TRICOLOR_ICE_CREAM = registerItem(ITEMS, "food/maoyu_tricolor_ice_cream", Item::new, props -> props);

    public static final DeferredItem<Item> MAPO_TOFU = registerItem(ITEMS, "food/mapo_tofu", Item::new, props -> props);

    public static final DeferredItem<Item> MILKY_MUSHROOM_SOUP = registerItem(ITEMS, "food/milky_mushroom_soup", Item::new, props -> props);

    public static final DeferredItem<Item> MOCHI = registerItem(ITEMS, "food/mochi", Item::new, props -> props);

    public static final DeferredItem<Item> MOLECULAR_EGG = registerItem(ITEMS, "food/molecular_egg", Item::new, props -> props);

    public static final DeferredItem<Item> MOONLIGHT_DUMPLINGS = registerItem(ITEMS, "food/moonlight_dumplings", Item::new, props -> props);

    public static final DeferredItem<Item> MOONLIGHT_OVER_LOTUS_POND = registerItem(ITEMS, "food/moonlight_over_lotus_pond", Item::new, props -> props);

    public static final DeferredItem<Item> MOON_CAKE = registerItem(ITEMS, "food/moon_cake", Item::new, props -> props);

    public static final DeferredItem<Item> MOON_LOVERS = registerItem(ITEMS, "food/moon_lovers", Item::new, props -> props);

    public static final DeferredItem<Item> MUSHROOM_GIRLS_DANCE_STEW = registerItem(ITEMS, "food/mushroom_girls_dance_stew", Item::new, props -> props);

    public static final DeferredItem<Item> MUSHROOM_MEAT_SLICES = registerItem(ITEMS, "food/mushroom_meat_slices", Item::new, props -> props);

    public static final DeferredItem<Item> NIGIRI_SUSHI = registerItem(ITEMS, "food/nigiri_sushi", Item::new, props -> props);

    public static final DeferredItem<Item> OEDO_BOAT_FESTIVAL = registerItem(ITEMS, "food/oedo_boat_festival", Item::new, props -> props);

    public static final DeferredItem<Item> OKONOMIYAKI = registerItem(ITEMS, "food/okonomiyaki", Item::new, props -> props);

    public static final DeferredItem<Item> ONE_HIT_KILL = registerItem(ITEMS, "food/one_hit_kill", Item::new, props -> props);

    public static final DeferredItem<Item> ORDINARY_SMALL_CAKE = registerItem(ITEMS, "food/ordinary_small_cake", Item::new, props -> props);

    public static final DeferredItem<Item> PAN_FRIED_MUSHROOM_MEAT_ROLL = registerItem(ITEMS, "food/pan_fried_mushroom_meat_roll", Item::new, props -> props);

    public static final DeferredItem<Item> PAN_FRIED_SALMON = registerItem(ITEMS, "food/pan_fried_salmon", Item::new, props -> props);

    public static final DeferredItem<Item> PEACH_BLOSSOM_GLAZE_ROLL = registerItem(ITEMS, "food/peach_blossom_glaze_roll", Item::new, props -> props);

    public static final DeferredItem<Item> PEACH_BLOSSOM_SOUP = registerItem(ITEMS, "food/peach_blossom_soup", Item::new, props -> props);

    public static final DeferredItem<Item> PHOENIX = registerItem(ITEMS, "food/phoenix", Item::new, props -> props);

    public static final DeferredItem<Item> PICKLED_CUCUMBERS = registerItem(ITEMS, "food/pickled_cucumbers", Item::new, props -> props);

    public static final DeferredItem<Item> PIG_DEER_BUTTERFLY = registerItem(ITEMS, "food/pig_deer_butterfly", Item::new, props -> props);

    public static final DeferredItem<Item> PINE_NUT_CAKE = registerItem(ITEMS, "food/pine_nut_cake", Item::new, props -> props);

    public static final DeferredItem<Item> PIRATE_BACON = registerItem(ITEMS, "food/pirate_bacon", Item::new, props -> props);

    public static final DeferredItem<Item> PLUM_TEA_RICE = registerItem(ITEMS, "food/plum_tea_rice", Item::new, props -> props);

    public static final DeferredItem<Item> POETRY_AND_GINKGO = registerItem(ITEMS, "food/poetry_and_ginkgo", Item::new, props -> props);

    public static final DeferredItem<Item> POISONOUS_GARDEN = registerItem(ITEMS, "food/poisonous_garden", Item::new, props -> props);

    public static final DeferredItem<Item> PORK_AND_TROUT_SMOKED = registerItem(ITEMS, "food/pork_and_trout_smoked", Item::new, props -> props);

    public static final DeferredItem<Item> PORK_RICE = registerItem(ITEMS, "food/pork_rice", Item::new, props -> props);

    public static final DeferredItem<Item> POTATO_CROQUETTES = registerItem(ITEMS, "food/potato_croquettes", Item::new, props -> props);

    public static final DeferredItem<Item> PSEUDO_JIRITAMA = registerItem(ITEMS, "food/pseudo_jiritama", Item::new, props -> props);

    public static final DeferredItem<Item> PUMPKIN_SHRIMP_CAKE = registerItem(ITEMS, "food/pumpkin_shrimp_cake", Item::new, props -> props);

    public static final DeferredItem<Item> RAPUNZEL = registerItem(ITEMS, "food/rapunzel", Item::new, props -> props);

    public static final DeferredItem<Item> REAL_SEAFOOD_MISO_SOUP = registerItem(ITEMS, "food/real_seafood_miso_soup", Item::new, props -> props);

    public static final DeferredItem<Item> RED_BEAN_DAIFUKU = registerItem(ITEMS, "food/red_bean_daifuku", Item::new, props -> props);

    public static final DeferredItem<Item> REFRESHING_PUDDING = registerItem(ITEMS, "food/refreshing_pudding", Item::new, props -> props);

    public static final DeferredItem<Item> REVERSING_THE_WORLD = registerItem(ITEMS, "food/reversing_the_world", Item::new, props -> props);

    public static final DeferredItem<Item> RICE_BALL = registerItem(ITEMS, "food/rice_ball", Item::new, props -> props);

    public static final DeferredItem<Item> RISOTTO = registerItem(ITEMS, "food/risotto", Item::new, props -> props);

    public static final DeferredItem<Item> ROASTED_MUSHROOMS = registerItem(ITEMS, "food/roasted_mushrooms", Item::new, props -> props);

    public static final DeferredItem<Item> SAKURA_PUDDING = registerItem(ITEMS, "food/sakura_pudding", Item::new, props -> props);

    public static final DeferredItem<Item> SALMON_TEMPURA = registerItem(ITEMS, "food/salmon_tempura", Item::new, props -> props);

    public static final DeferredItem<Item> SASHIMI_PLATTER = registerItem(ITEMS, "food/sashimi_platter", Item::new, props -> props);

    public static final DeferredItem<Item> SCARLET_DEVILS_CAKE = registerItem(ITEMS, "food/scarlet_devils_cake", Item::new, props -> props);

    public static final DeferredItem<Item> SCONES = registerItem(ITEMS, "food/scones", Item::new, props -> props);

    public static final DeferredItem<Item> SCREAMING_ODEN = registerItem(ITEMS, "food/screaming_oden", Item::new, props -> props);

    public static final DeferredItem<Item> SEAFOOD_MISO_SOUP = registerItem(ITEMS, "food/seafood_miso_soup", Item::new, props -> props);

    public static final DeferredItem<Item> SEA_URCHIN_SASHIMI = registerItem(ITEMS, "food/sea_urchin_sashimi", Item::new, props -> props);

    public static final DeferredItem<Item> SEA_URCHIN_SHINGEN_PANCAKE = registerItem(ITEMS, "food/sea_urchin_shingen_pancake", Item::new, props -> props);

    public static final DeferredItem<Item> SECRET_DRIED_FISH = registerItem(ITEMS, "food/secret_dried_fish", Item::new, props -> props);

    public static final DeferredItem<Item> SECRET_MUSHROOM_CASSEROLE = registerItem(ITEMS, "food/secret_mushroom_casserole", Item::new, props -> props);

    public static final DeferredItem<Item> SEVEN_COLORED_YOKAN = registerItem(ITEMS, "food/seven_colored_yokan", Item::new, props -> props);

    public static final DeferredItem<Item> SHIRAGA_SADAMATSU = registerItem(ITEMS, "food/shiraga_sadamatsu", Item::new, props -> props);

    public static final DeferredItem<Item> SKINNY_HORSE_DUMPLING = registerItem(ITEMS, "food/skinny_horse_dumpling", Item::new, props -> props);

    public static final DeferredItem<Item> SNOW_WHITE = registerItem(ITEMS, "food/snow_white", Item::new, props -> props);

    public static final DeferredItem<Item> STEAMED_EGG_WITH_SEA_URCHIN = registerItem(ITEMS, "food/steamed_egg_with_sea_urchin", Item::new, props -> props);

    public static final DeferredItem<Item> STINKY_TOFU = registerItem(ITEMS, "food/stinky_tofu", Item::new, props -> props);

    public static final DeferredItem<Item> STRENGTH_SOUP = registerItem(ITEMS, "food/strength_soup", Item::new, props -> props);

    public static final DeferredItem<Item> SUPERME_SEAFOOD_NOODLES = registerItem(ITEMS, "food/superme_seafood_noodles", Item::new, props -> props);

    public static final DeferredItem<Item> TAICHI_BAGUA_FISH_MAW = registerItem(ITEMS, "food/taichi_bagua_fish_maw", Item::new, props -> props);

    public static final DeferredItem<Item> TAKETORIHIME = registerItem(ITEMS, "food/taketorihime", Item::new, props -> props);

    public static final DeferredItem<Item> TAKOYAKI = registerItem(ITEMS, "food/takoyaki", Item::new, props -> props);

    public static final DeferredItem<Item> THE_BEAUTY_OF_HAN_PALACE = registerItem(ITEMS, "food/the_beauty_of_han_palace", Item::new, props -> props);

    public static final DeferredItem<Item> THE_DREAM = registerItem(ITEMS, "food/the_dream", Item::new, props -> props);

    public static final DeferredItem<Item> THE_MARS = registerItem(ITEMS, "food/the_mars", Item::new, props -> props);

    public static final DeferredItem<Item> THE_SOURCE_OF_LIFE = registerItem(ITEMS, "food/the_source_of_life", Item::new, props -> props);

    public static final DeferredItem<Item> TIANSHI_BRAISED_CHESTNUT_MUSHROOMS = registerItem(ITEMS, "food/tianshi_braised_chestnut_mushrooms", Item::new, props -> props);

    public static final DeferredItem<Item> TOFU_MISO = registerItem(ITEMS, "food/tofu_miso", Item::new, props -> props);

    public static final DeferredItem<Item> TOFU_POT = registerItem(ITEMS, "food/tofu_pot", Item::new, props -> props);

    public static final DeferredItem<Item> TONKOTSU_RAMEN = registerItem(ITEMS, "food/tonkotsu_ramen", Item::new, props -> props);

    public static final DeferredItem<Item> TOON_PANCAKES = registerItem(ITEMS, "food/toon_pancakes", Item::new, props -> props);

    public static final DeferredItem<Item> TWO_HEAVENS_ONE_STYLE = registerItem(ITEMS, "food/two_heavens_one_style", Item::new, props -> props);

    public static final DeferredItem<Item> UDUMBARA_CAKE = registerItem(ITEMS, "food/udumbara_cake", Item::new, props -> props);

    public static final DeferredItem<Item> UNCONSCIOUS_MONSTER_MOUSSE = registerItem(ITEMS, "food/unconscious_monster_mousse", Item::new, props -> props);

    public static final DeferredItem<Item> VEGETABLE_SPECIAL = registerItem(ITEMS, "food/vegetable_special", Item::new, props -> props);

    public static final DeferredItem<Item> WARM_RICE_BALL = registerItem(ITEMS, "food/warm_rice_ball", Item::new, props -> props);

    public static final DeferredItem<Item> WHITE_PEACH_EIGHT_BRIDGE = registerItem(ITEMS, "food/white_peach_eight_bridge", Item::new, props -> props);

    public static final DeferredItem<Item> YUNSHAN_COTTON_CANDY = registerItem(ITEMS, "food/yunshan_cotton_candy", Item::new, props -> props);

    public static final DeferredItem<Item> ZHAJI = registerItem(ITEMS, "food/zhaji", Item::new, props -> props);

}
