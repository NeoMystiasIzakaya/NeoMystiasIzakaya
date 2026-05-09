/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class NMICuisinesItems {
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

    public static final DeferredItem<Item> AGAINST_THE_WORLD = registerItem(ITEMS, "cuisines/against_the_world", Item::new, props -> props);
    public static final DeferredItem<Item> AGONY_ODEN = registerItem(ITEMS, "cuisines/agony_oden", Item::new, props -> props);
    public static final DeferredItem<Item> ALL_MEAT_FEAST = registerItem(ITEMS, "cuisines/all_meat_feast", Item::new, props -> props);
    public static final DeferredItem<Item> BAKED_SWEET_POTATO = registerItem(ITEMS, "cuisines/baked_sweet_potato", Item::new, props -> props);
    public static final DeferredItem<Item> BAMBOO_MEAT_POT = registerItem(ITEMS, "cuisines/bamboo_meat_pot", Item::new, props -> props);
    public static final DeferredItem<Item> BAMBOO_SPRING = registerItem(ITEMS, "cuisines/bamboo_spring", Item::new, props -> props);
    public static final DeferredItem<Item> BEEF_BOWL = registerItem(ITEMS, "cuisines/beef_bowl", Item::new, props -> props);
    public static final DeferredItem<Item> BEEF_WELLINGTON = registerItem(ITEMS, "cuisines/beef_wellington", Item::new, props -> props);
    public static final DeferredItem<Item> BISCAY_BISCUITS = registerItem(ITEMS, "cuisines/biscay_biscuits", Item::new, props -> props);
    public static final DeferredItem<Item> BOILED_TOFU = registerItem(ITEMS, "cuisines/boiled_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> BUDDHA_JUMPS_OVER_THE_WALL = registerItem(ITEMS, "cuisines/buddha_jumps_over_the_wall", Item::new, props -> props);
    public static final DeferredItem<Item> BUDDHAS_DELIGHT = registerItem(ITEMS, "cuisines/buddhas_delight", Item::new, props -> props);
    public static final DeferredItem<Item> BURN_OUT_PUDDING = registerItem(ITEMS, "cuisines/burn_out_pudding", Item::new, props -> props);
    public static final DeferredItem<Item> CANDIED_SWEET_POTATO = registerItem(ITEMS, "cuisines/candied_sweet_potato", Item::new, props -> props);
    public static final DeferredItem<Item> CANTONESE_CHAR_SIU = registerItem(ITEMS, "cuisines/cantonese_char_siu", Item::new, props -> props);
    public static final DeferredItem<Item> CARVED_ROSE_SALAD = registerItem(ITEMS, "cuisines/carved_rose_salad", Item::new, props -> props);
    public static final DeferredItem<Item> CAUTION_HELLISH_SPICE = registerItem(ITEMS, "cuisines/caution_hellish_spice", Item::new, props -> props);
    public static final DeferredItem<Item> CEILING_LONGING_PIE = registerItem(ITEMS, "cuisines/ceiling_longing_pie", Item::new, props -> props);
    public static final DeferredItem<Item> CHEESE_OMELETTE = registerItem(ITEMS, "cuisines/cheese_omelette", Item::new, props -> props);
    public static final DeferredItem<Item> CLASSIC_STEAK = registerItem(ITEMS, "cuisines/classic_steak", Item::new, props -> props);
    public static final DeferredItem<Item> CREAM_OF_MUSHROOM_SOUP = registerItem(ITEMS, "cuisines/cream_of_mushroom_soup", Item::new, props -> props);
    public static final DeferredItem<Item> CREAMY_CRAB = registerItem(ITEMS, "cuisines/creamy_crab", Item::new, props -> props);
    public static final DeferredItem<Item> CREAMY_VEGETABLE_CHOWDER = registerItem(ITEMS, "cuisines/creamy_vegetable_chowder", Item::new, props -> props);
    public static final DeferredItem<Item> CRISPY_SPIRALS = registerItem(ITEMS, "cuisines/crispy_spirals", Item::new, props -> props);
    public static final DeferredItem<Item> CUBIC_KEDAMA_ICE_CREAM = registerItem(ITEMS, "cuisines/cubic_kedama_ice_cream", Item::new, props -> props);
    public static final DeferredItem<Item> CUBIC_KEDAMA_VOLCANIC_TOFU = registerItem(ITEMS, "cuisines/cubic_kedama_volcanic_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> DAIMYOS_FEAST = registerItem(ITEMS, "cuisines/daimyos_feast", Item::new, props -> props);
    public static final DeferredItem<Item> DARK_MATTER = registerItem(ITEMS, "cuisines/dark_matter", Item::new, props -> props);
    public static final DeferredItem<Item> DEEP_FRIED_SHRIMP_TEMPURA = registerItem(ITEMS, "cuisines/deep_fried_shrimp_tempura", Item::new, props -> props);
    public static final DeferredItem<Item> DEEP_FRIED_TOFU = registerItem(ITEMS, "cuisines/deep_fried_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> DEW_RUNNY_EGGS = registerItem(ITEMS, "cuisines/dew_runny_eggs", Item::new, props -> props);
    public static final DeferredItem<Item> DORAYAKI = registerItem(ITEMS, "cuisines/dorayaki", Item::new, props -> props);
    public static final DeferredItem<Item> DRAGON_CARP = registerItem(ITEMS, "cuisines/dragon_carp", Item::new, props -> props);
    public static final DeferredItem<Item> DRAGONSONG_PEACH = registerItem(ITEMS, "cuisines/dragonsong_peach", Item::new, props -> props);
    public static final DeferredItem<Item> DRUNK_SHRIMP_IN_BAMBOO = registerItem(ITEMS, "cuisines/drunk_shrimp_in_bamboo", Item::new, props -> props);
    public static final DeferredItem<Item> DUMPLINGS = registerItem(ITEMS, "cuisines/dumplings", Item::new, props -> props);
    public static final DeferredItem<Item> EEL_BOWL_WITH_EGG = registerItem(ITEMS, "cuisines/eel_bowl_with_egg", Item::new, props -> props);
    public static final DeferredItem<Item> EGGS_BENEDICT = registerItem(ITEMS, "cuisines/eggs_benedict", Item::new, props -> props);
    public static final DeferredItem<Item> EIGHT_TRIGRAM_FISH_MAWS = registerItem(ITEMS, "cuisines/eight_trigram_fish_maws", Item::new, props -> props);
    public static final DeferredItem<Item> ENERGY_PUDDING = registerItem(ITEMS, "cuisines/energy_pudding", Item::new, props -> props);
    public static final DeferredItem<Item> ENERGY_SKEWER = registerItem(ITEMS, "cuisines/energy_skewer", Item::new, props -> props);
    public static final DeferredItem<Item> FAINT_DREAM = registerItem(ITEMS, "cuisines/faint_dream", Item::new, props -> props);
    public static final DeferredItem<Item> FALLING_BLOSSOMS = registerItem(ITEMS, "cuisines/falling_blossoms", Item::new, props -> props);
    public static final DeferredItem<Item> FLOWING_SOMEN = registerItem(ITEMS, "cuisines/flowing_somen", Item::new, props -> props);
    public static final DeferredItem<Item> FRESH_TOFU = registerItem(ITEMS, "cuisines/fresh_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> FRIED_CICADA_SLOUGHS = registerItem(ITEMS, "cuisines/fried_cicada_sloughs", Item::new, props -> props);
    public static final DeferredItem<Item> FRIED_LAMPREYS = registerItem(ITEMS, "cuisines/fried_lampreys", Item::new, props -> props);
    public static final DeferredItem<Item> FRIED_PORK_CUTLET = registerItem(ITEMS, "cuisines/fried_pork_cutlet", Item::new, props -> props);
    public static final DeferredItem<Item> FUJIS_LAVA = registerItem(ITEMS, "cuisines/fujis_lava", Item::new, props -> props);
    public static final DeferredItem<Item> GIANT_TAMAGOYAKI = registerItem(ITEMS, "cuisines/giant_tamagoyaki", Item::new, props -> props);
    public static final DeferredItem<Item> GLOOMY_FRUIT_PIE = registerItem(ITEMS, "cuisines/gloomy_fruit_pie", Item::new, props -> props);
    public static final DeferredItem<Item> GOLDEN_CRISPY_FISH_CAKES = registerItem(ITEMS, "cuisines/golden_crispy_fish_cakes", Item::new, props -> props);
    public static final DeferredItem<Item> GOLDEN_RIBS_SOUP = registerItem(ITEMS, "cuisines/golden_ribs_soup", Item::new, props -> props);
    public static final DeferredItem<Item> GOLDEN_TWO_SHROOM_WRAP = registerItem(ITEMS, "cuisines/golden_two_shroom_wrap", Item::new, props -> props);
    public static final DeferredItem<Item> GRILLED_LAMPREY = registerItem(ITEMS, "cuisines/grilled_lamprey", Item::new, props -> props);
    public static final DeferredItem<Item> HEART_THROBBING_SURPRISE = registerItem(ITEMS, "cuisines/heart_throbbing_surprise", Item::new, props -> props);
    public static final DeferredItem<Item> HEART_WARMING_CONGEE = registerItem(ITEMS, "cuisines/heart_warming_congee", Item::new, props -> props);
    public static final DeferredItem<Item> HODGEPODGE = registerItem(ITEMS, "cuisines/hodgepodge", Item::new, props -> props);
    public static final DeferredItem<Item> HONEYED_CHESTNUT = registerItem(ITEMS, "cuisines/honeyed_chestnut", Item::new, props -> props);
    public static final DeferredItem<Item> HOT_PEPPER_SOUP = registerItem(ITEMS, "cuisines/hot_pepper_soup", Item::new, props -> props);
    public static final DeferredItem<Item> HOURAI_BRANCH = registerItem(ITEMS, "cuisines/hourai_branch", Item::new, props -> props);
    public static final DeferredItem<Item> HUNTERS_CASSEROLE = registerItem(ITEMS, "cuisines/hunters_casserole", Item::new, props -> props);
    public static final DeferredItem<Item> IMITATION_BEAR_PAW = registerItem(ITEMS, "cuisines/imitation_bear_paw", Item::new, props -> props);
    public static final DeferredItem<Item> IMITATION_SHIRIKODAMA = registerItem(ITEMS, "cuisines/imitation_shirikodama", Item::new, props -> props);
    public static final DeferredItem<Item> IMMORTAL_TURKEY = registerItem(ITEMS, "cuisines/immortal_turkey", Item::new, props -> props);
    public static final DeferredItem<Item> INO_SHIKA_CHOU = registerItem(ITEMS, "cuisines/ino_shika_chou", Item::new, props -> props);
    public static final DeferredItem<Item> INSTANT_DEATH = registerItem(ITEMS, "cuisines/instantdeath", Item::new, props -> props);
    public static final DeferredItem<Item> ITALIAN_RISOTTO = registerItem(ITEMS, "cuisines/italian_risotto", Item::new, props -> props);
    public static final DeferredItem<Item> KABAYAKI_LAMPREYS = registerItem(ITEMS, "cuisines/kabayaki_lampreys", Item::new, props -> props);
    public static final DeferredItem<Item> KABUTO_STEAMED_CAKE = registerItem(ITEMS, "cuisines/kabuto_steamed_cake", Item::new, props -> props);
    public static final DeferredItem<Item> KAGUYA_HIME = registerItem(ITEMS, "cuisines/kaguya_hime", Item::new, props -> props);
    public static final DeferredItem<Item> KITTEN_CANELE = registerItem(ITEMS, "cuisines/kitten_canele", Item::new, props -> props);
    public static final DeferredItem<Item> KITTEN_PIZZA = registerItem(ITEMS, "cuisines/kitten_pizza", Item::new, props -> props);
    public static final DeferredItem<Item> KITTENS_WATER_PLAY = registerItem(ITEMS, "cuisines/kittens_water_play", Item::new, props -> props);
    public static final DeferredItem<Item> LIONS_HEAD = registerItem(ITEMS, "cuisines/lions_head", Item::new, props -> props);
    public static final DeferredItem<Item> LITTLE_SWEET_POISON = registerItem(ITEMS, "cuisines/little_sweet_poison", Item::new, props -> props);
    public static final DeferredItem<Item> LONG_HAIR_PRINCESS = registerItem(ITEMS, "cuisines/long_hair_princess", Item::new, props -> props);
    public static final DeferredItem<Item> LOTUS_FISH_LAMPS = registerItem(ITEMS, "cuisines/lotus_fish_lamps", Item::new, props -> props);
    public static final DeferredItem<Item> LUNAR_DANGO = registerItem(ITEMS, "cuisines/lunar_dango", Item::new, props -> props);
    public static final DeferredItem<Item> LUNAR_LOVER_BISCUITS = registerItem(ITEMS, "cuisines/lunar_lover_biscuits", Item::new, props -> props);
    public static final DeferredItem<Item> MAD_HATTERS_TEA_PARTY = registerItem(ITEMS, "cuisines/mad_hatters_tea_party", Item::new, props -> props);
    public static final DeferredItem<Item> MAPO_TOFU = registerItem(ITEMS, "cuisines/mapo_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> MIASMA_GARDEN = registerItem(ITEMS, "cuisines/miasma_garden", Item::new, props -> props);
    public static final DeferredItem<Item> MISERY_CHEESE_STICKS = registerItem(ITEMS, "cuisines/misery_cheese_sticks", Item::new, props -> props);
    public static final DeferredItem<Item> MISO_TOFU = registerItem(ITEMS, "cuisines/miso_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> MOCHI = registerItem(ITEMS, "cuisines/mochi", Item::new, props -> props);
    public static final DeferredItem<Item> MOLECULAR_EGG = registerItem(ITEMS, "cuisines/molecular_egg", Item::new, props -> props);
    public static final DeferredItem<Item> MOON_CAKE = registerItem(ITEMS, "cuisines/moon_cake", Item::new, props -> props);
    public static final DeferredItem<Item> MOONLIGHT_OVER_THE_LOTUS_POND = registerItem(ITEMS, "cuisines/moonlight_over_the_lotus_pond", Item::new, props -> props);
    public static final DeferredItem<Item> MUSHROOM_HERB_ROAD = registerItem(ITEMS, "cuisines/mushroom_herb_road", Item::new, props -> props);
    public static final DeferredItem<Item> MUSHROOM_MAIDENS_TIP_TAP_POT = registerItem(ITEMS, "cuisines/mushroom_maidens_tip_tap_pot", Item::new, props -> props);
    public static final DeferredItem<Item> NATURES_BEAUTY = registerItem(ITEMS, "cuisines/natures_beauty", Item::new, props -> props);
    public static final DeferredItem<Item> NEKO_MANMA = registerItem(ITEMS, "cuisines/neko_manma", Item::new, props -> props);
    public static final DeferredItem<Item> NIGIRI_SUSHI = registerItem(ITEMS, "cuisines/nigiri_sushi", Item::new, props -> props);
    public static final DeferredItem<Item> NITEN_ICHIRYU = registerItem(ITEMS, "cuisines/niten_ichiryu", Item::new, props -> props);
    public static final DeferredItem<Item> OEDO_BOAT_FEAST = registerItem(ITEMS, "cuisines/oedo_boat_feast", Item::new, props -> props);
    public static final DeferredItem<Item> OKONOMIYAKI = registerItem(ITEMS, "cuisines/okonomiyaki", Item::new, props -> props);
    public static final DeferredItem<Item> ORDINARY_EAT_ME_CUPCAKE = registerItem(ITEMS, "cuisines/ordinary_eat_me_cupcake", Item::new, props -> props);
    public static final DeferredItem<Item> ORIGIN_OF_LIFE = registerItem(ITEMS, "cuisines/origin_of_life", Item::new, props -> props);
    public static final DeferredItem<Item> PALACE_OF_THE_HAN = registerItem(ITEMS, "cuisines/palace_of_the_han", Item::new, props -> props);
    public static final DeferredItem<Item> PANCAKES_WITH_SYRUP = registerItem(ITEMS, "cuisines/pancakes_with_syrup", Item::new, props -> props);
    public static final DeferredItem<Item> PEACH_BRAISED_PORK = registerItem(ITEMS, "cuisines/peach_braised_pork", Item::new, props -> props);
    public static final DeferredItem<Item> PEACH_FLOWER_CRYSTAL_ROLL = registerItem(ITEMS, "cuisines/peach_flower_crystal_roll", Item::new, props -> props);
    public static final DeferredItem<Item> PEACH_SHRIMP_SALAD = registerItem(ITEMS, "cuisines/peach_shrimp_salad", Item::new, props -> props);
    public static final DeferredItem<Item> PEACH_TAPIOCA = registerItem(ITEMS, "cuisines/peach_tapioca", Item::new, props -> props);
    public static final DeferredItem<Item> PEACH_YATSUHASHI = registerItem(ITEMS, "cuisines/peach_yatsuhashi", Item::new, props -> props);
    public static final DeferredItem<Item> PICKLES = registerItem(ITEMS, "cuisines/pickles", Item::new, props -> props);
    public static final DeferredItem<Item> PINE_NUT_CAKE = registerItem(ITEMS, "cuisines/pine_nut_cake", Item::new, props -> props);
    public static final DeferredItem<Item> PINK_RICE_BALL = registerItem(ITEMS, "cuisines/pink_rice_ball", Item::new, props -> props);
    public static final DeferredItem<Item> PLANET_MARS = registerItem(ITEMS, "cuisines/planet_mars", Item::new, props -> props);
    public static final DeferredItem<Item> PLUM_TEA_RICE = registerItem(ITEMS, "cuisines/plum_tea_rice", Item::new, props -> props);
    public static final DeferredItem<Item> PORK_BAMBOO_SHOOTS_STIR_FRY = registerItem(ITEMS, "cuisines/pork_bamboo_shoots_stir_fry", Item::new, props -> props);
    public static final DeferredItem<Item> PORK_BOWL = registerItem(ITEMS, "cuisines/pork_bowl", Item::new, props -> props);
    public static final DeferredItem<Item> PORK_MUSHROOM_STIR_FRY = registerItem(ITEMS, "cuisines/pork_mushroom_stir_fry", Item::new, props -> props);
    public static final DeferredItem<Item> PORK_RICE_BALL = registerItem(ITEMS, "cuisines/pork_rice_ball", Item::new, props -> props);
    public static final DeferredItem<Item> PORK_STIR_FRY = registerItem(ITEMS, "cuisines/pork_stir_fry", Item::new, props -> props);
    public static final DeferredItem<Item> PORK_TROUT_KEBAB = registerItem(ITEMS, "cuisines/pork_trout_kebab", Item::new, props -> props);
    public static final DeferredItem<Item> POTATO_CROQUETTES = registerItem(ITEMS, "cuisines/potato_croquettes", Item::new, props -> props);
    public static final DeferredItem<Item> POWER_SOUP = registerItem(ITEMS, "cuisines/power_soup", Item::new, props -> props);
    public static final DeferredItem<Item> PURE_WHITE_LOTUS = registerItem(ITEMS, "cuisines/pure_white_lotus", Item::new, props -> props);
    public static final DeferredItem<Item> RAINBOW_PAN_FRIED_PORK_BUNS = registerItem(ITEMS, "cuisines/rainbow_pan_fried_pork_buns", Item::new, props -> props);
    public static final DeferredItem<Item> REAL_SEAFOOD_MISO_SOUP = registerItem(ITEMS, "cuisines/real_seafood_miso_soup", Item::new, props -> props);
    public static final DeferredItem<Item> RED_BEAN_DAIFUKU = registerItem(ITEMS, "cuisines/red_bean_daifuku", Item::new, props -> props);
    public static final DeferredItem<Item> RICE_BALL = registerItem(ITEMS, "cuisines/rice_ball", Item::new, props -> props);
    public static final DeferredItem<Item> RICE_POWDER_MEAT = registerItem(ITEMS, "cuisines/rice_powder_meat", Item::new, props -> props);
    public static final DeferredItem<Item> ROASTED_MUSHROOM = registerItem(ITEMS, "cuisines/roasted_mushroom", Item::new, props -> props);
    public static final DeferredItem<Item> SAKURA_PUDDING = registerItem(ITEMS, "cuisines/sakura_pudding", Item::new, props -> props);
    public static final DeferredItem<Item> SALMON_STEAK = registerItem(ITEMS, "cuisines/salmon_steak", Item::new, props -> props);
    public static final DeferredItem<Item> SALMON_TEMPURA = registerItem(ITEMS, "cuisines/salmon_tempura", Item::new, props -> props);
    public static final DeferredItem<Item> SASHIMI_PLATTER = registerItem(ITEMS, "cuisines/sashimi_platter", Item::new, props -> props);
    public static final DeferredItem<Item> SCARLET_DEVIL_CAKE = registerItem(ITEMS, "cuisines/scarlet_devil_cake", Item::new, props -> props);
    public static final DeferredItem<Item> SCHOLARS_GINKGO = registerItem(ITEMS, "cuisines/scholars_ginkgo", Item::new, props -> props);
    public static final DeferredItem<Item> SCONE = registerItem(ITEMS, "cuisines/scone", Item::new, props -> props);
    public static final DeferredItem<Item> SCRUMPTIOUS_STORM = registerItem(ITEMS, "cuisines/scrumptious_storm", Item::new, props -> props);
    public static final DeferredItem<Item> SEA_URCHIN_SASHIMI = registerItem(ITEMS, "cuisines/sea_urchin_sashimi", Item::new, props -> props);
    public static final DeferredItem<Item> SEAFOOD_MISO_SOUP = registerItem(ITEMS, "cuisines/seafood_miso_soup", Item::new, props -> props);
    public static final DeferredItem<Item> SECRET_DRIED_FISH_CRISPS = registerItem(ITEMS, "cuisines/secret_dried_fish_crisps", Item::new, props -> props);
    public static final DeferredItem<Item> SECRET_SAVORY_MUSHROOM_HOTPOT = registerItem(ITEMS, "cuisines/secret_savory_mushroom_hotpot", Item::new, props -> props);
    public static final DeferredItem<Item> SEVEN_COLORED_YOKAN = registerItem(ITEMS, "cuisines/seven_colored_yokan", Item::new, props -> props);
    public static final DeferredItem<Item> SHIRAYUKI = registerItem(ITEMS, "cuisines/shirayuki", Item::new, props -> props);
    public static final DeferredItem<Item> SHRIMP_STUFFED_PUMPKIN = registerItem(ITEMS, "cuisines/shrimp_stuffed_pumpkin", Item::new, props -> props);
    public static final DeferredItem<Item> SICHUAN_BOILED_FISH = registerItem(ITEMS, "cuisines/sichuan_boiled_fish", Item::new, props -> props);
    public static final DeferredItem<Item> SMOKED_BUCCANEER = registerItem(ITEMS, "cuisines/smoked_buccaneer", Item::new, props -> props);
    public static final DeferredItem<Item> STAR_LOTUS_SHIP = registerItem(ITEMS, "cuisines/star_lotus_ship", Item::new, props -> props);
    public static final DeferredItem<Item> STEAMED_EGG_BAMBOO_SHOOTS = registerItem(ITEMS, "cuisines/steamed_egg_bamboo_shoots", Item::new, props -> props);
    public static final DeferredItem<Item> STINKY_TOFU = registerItem(ITEMS, "cuisines/stinky_tofu", Item::new, props -> props);
    public static final DeferredItem<Item> SUPREME_SEAFOOD_NOODLES = registerItem(ITEMS, "cuisines/supreme_seafood_noodles", Item::new, props -> props);
    public static final DeferredItem<Item> TAKOYAKI = registerItem(ITEMS, "cuisines/takoyaki", Item::new, props -> props);
    public static final DeferredItem<Item> TANGYUAN = registerItem(ITEMS, "cuisines/tangyuan", Item::new, props -> props);
    public static final DeferredItem<Item> TEMPURA_PLATTER = registerItem(ITEMS, "cuisines/tempura_platter", Item::new, props -> props);
    public static final DeferredItem<Item> TIANSHIS_STEWED_MUSHROOMS = registerItem(ITEMS, "cuisines/tianshis_stewed_mushrooms", Item::new, props -> props);
    public static final DeferredItem<Item> TOFU_STEW = registerItem(ITEMS, "cuisines/tofu_stew", Item::new, props -> props);
    public static final DeferredItem<Item> TOMATO_FRIES = registerItem(ITEMS, "cuisines/tomato_fries", Item::new, props -> props);
    public static final DeferredItem<Item> TONKOTSU_RAMEN = registerItem(ITEMS, "cuisines/tonkotsu_ramen", Item::new, props -> props);
    public static final DeferredItem<Item> TOON_PANCAKE = registerItem(ITEMS, "cuisines/toon_pancake", Item::new, props -> props);
    public static final DeferredItem<Item> TWO_FLAVOR_BEEF_HOTPOT = registerItem(ITEMS, "cuisines/two_flavor_beef_hotpot", Item::new, props -> props);
    public static final DeferredItem<Item> UDUMBARA_CAKE = registerItem(ITEMS, "cuisines/udumbara_cake", Item::new, props -> props);
    public static final DeferredItem<Item> UNCONSCIOUS_YOUKAI_MOUSSE = registerItem(ITEMS, "cuisines/unconscious_youkai_mousse", Item::new, props -> props);
    public static final DeferredItem<Item> UNZAN_COTTON_CANDY = registerItem(ITEMS, "cuisines/unzan_cotton_candy", Item::new, props -> props);
    public static final DeferredItem<Item> URCHIN_RAINDROP_CAKE = registerItem(ITEMS, "cuisines/urchin_raindrop_cake", Item::new, props -> props);
    public static final DeferredItem<Item> URCHIN_STEAMED_EGG = registerItem(ITEMS, "cuisines/urchin_steamed_egg", Item::new, props -> props);
    public static final DeferredItem<Item> VEGETABLE_SALAD = registerItem(ITEMS, "cuisines/vegetable_salad", Item::new, props -> props);
    public static final DeferredItem<Item> WHITE_DEER_UNYIELDING_PINE = registerItem(ITEMS, "cuisines/white_deer_unyielding_pine", Item::new, props -> props);
    public static final DeferredItem<Item> YASHOUMA_DANGO = registerItem(ITEMS, "cuisines/yashouma_dango", Item::new, props -> props);

}
