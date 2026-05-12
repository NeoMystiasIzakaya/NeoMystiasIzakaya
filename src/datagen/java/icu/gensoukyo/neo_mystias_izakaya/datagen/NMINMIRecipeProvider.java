/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.datagen.api.NMIRecipeProvider;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class NMINMIRecipeProvider extends NMIRecipeProvider {

    protected NMINMIRecipeProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void addRecipes() {
        this.addBoilingPotRecipes();
        this.addCuttingBoardRecipes();
        this.addFryingPanRecipes();
        this.addGrillRecipes();
        this.addSteamerRecipes();
        this.addOtherRecipes();
    }

    private Ingredient getKelp() {
        return Ingredient.of(Items.KELP, Items.DRIED_KELP);
    }

    private Ingredient getMushroom() {
        return Ingredient.of(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM);
    }

    public Ingredient getEgg() {
        return Ingredient.of(Items.EGG, Items.BLUE_EGG, Items.BROWN_EGG);
    }

    public Ingredient getIce() {
        return Ingredient.of(Items.ICE, Items.BLUE_ICE, Items.PACKED_ICE);
    }

    private void addBoilingPotRecipes() {
        this.builder(NMICuisinesItems.SEAFOOD_MISO_SOUP)
                .input(this.getKelp())
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.MISO_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.POWER_SOUP)
                .input(this.getKelp())
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HUNTERS_CASSEROLE)
                .input(Items.POTATO)
                .input(Items.PUMPKIN)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PORK_BOWL)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.BEEF_BOWL)
                .input(Items.BEEF)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.SHIRAYUKI)
                .input(Items.PUFFERFISH)
                .input(NMIIngredientItems.LAMPREY)
                .input(this.getKelp())
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.TOFU_STEW)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.HODGEPODGE)
                .input(this.getKelp())
                .input(NMIIngredientItems.TOFU)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.DAIMYOS_FEAST)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .input(Items.PUFFERFISH)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.TONKOTSU_RAMEN)
                .input(Items.PORKCHOP)
                .input(this.getEgg())
                .input(this.getKelp())
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.FUJIS_LAVA)
                .input(Items.BEEF)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .input(Items.PUFFERFISH)
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PEACH_TAPIOCA)
                .input(NMIIngredientItems.PEACH)
                .input(this.getIce())
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.REAL_SEAFOOD_MISO_SOUP)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.BOILED_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.GOLDEN_RIBS_SOUP)
                .input(NMIIngredientItems.GINKGO_NUT)
                .input(NMIIngredientItems.RADISH)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.SICHUAN_BOILED_FISH)
                .input(NMIIngredientItems.TROUT)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DUMPLINGS)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.TANGYUAN)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.IMITATION_SHIRIKODAMA)
                .input(NMIIngredientItems.VENISON)
                .input(NMIIngredientItems.TRUFFLE)
                .input(NMIIngredientItems.CICADA_SLOUGH)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SECRET_SAVORY_MUSHROOM_HOTPOT)
                .input(NMIIngredientItems.TRUFFLE)
                .input(this.getMushroom())
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.MUSHROOM_MAIDENS_TIP_TAP_POT)
                .input(this.getMushroom())
                .input(NMIIngredientItems.SHRIMP)
                .input(NMIIngredientItems.OCTOPUS)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.CREAM_OF_MUSHROOM_SOUP)
                .input(this.getMushroom())
                .input(Items.POTATO)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.BUDDHA_JUMPS_OVER_THE_WALL)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(Items.PUFFERFISH)
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.AGONY_ODEN)
                .input(NMIIngredientItems.CHILI)
                .input(NMIIngredientItems.CHILI)
                .input(Items.BEEF)
                .input(NMIIngredientItems.RADISH)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.LIONS_HEAD)
                .input(Items.BEEF)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.BUDDHAS_DELIGHT)
                .input(NMIIngredientItems.UDUMBARA)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(NMIIngredientItems.TRUFFLE)
                .input(NMIIngredientItems.PINE_NUT)
                .input(NMIIngredientItems.LOTUS_SEED)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.EIGHT_TRIGRAM_FISH_MAWS)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .input(this.getMushroom())
                .input(NMIIngredientItems.RADISH)
                .input(this.getEgg())
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.TIANSHIS_STEWED_MUSHROOMS)
                .input(NMIIngredientItems.CHESTNUT)
                .input(this.getMushroom())
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PALACE_OF_THE_HAN)
                .input(NMIIngredientItems.LAMPREY)
                .input(NMIIngredientItems.TOFU)
                .input(NMIIngredientItems.CRAB)
                .input(Items.BAMBOO)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.BAMBOO_MEAT_POT)
                .input(Items.BAMBOO)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(Items.BEEF)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.PLUM_TEA_RICE)
                .input(NMIIngredientItems.PLUM)
                .input(this.getKelp())
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(4)
                .build();

        this.builder(NMICuisinesItems.MUSHROOM_HERB_ROAD)
                .input(NMIIngredientItems.RED_TOON)
                .input(this.getMushroom())
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.MIASMA_GARDEN)
                .input(Items.PUFFERFISH)
                .input(NMIIngredientItems.PLUM)
                .input(NMIIngredientItems.LAMPREY)
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.TWO_FLAVOR_BEEF_HOTPOT)
                .input(NMIIngredientItems.CHILI)
                .input(NMIIngredientItems.RADISH)
                .input(NMIIngredientItems.TRUFFLE)
                .input(Items.BEEF)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.URCHIN_RAINDROP_CAKE)
                .input(NMIIngredientItems.SEA_URCHIN)
                .input(NMIIngredientItems.TUNA)
                .input(NMIIngredientItems.SNOW_FUNGUS)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HEART_WARMING_CONGEE)
                .input(NMIIngredientItems.SNOW_FUNGUS)
                .input(NMIIngredientItems.LOTUS_SEED)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SUPREME_SEAFOOD_NOODLES)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .input(this.getKelp())
                .input(NMIIngredientItems.OCTOPUS)
                .input(NMIIngredientItems.CRAB)
                .input(NMIIngredientItems.SHRIMP)
                .kitchenware(NMIBlockVanillaTags.BOILING_POT)
                .time(10)
                .build();

//        this.builder(毛茸茸蘑菇汤)
//                .input(棉花糖)
//                .input(NMIIngredientItems.IBERICO_PORK)
//                .input(this.getMushroom())
//                .kitchenware(NMIBlockTags.BOILING_POT)
//                .time(6)
//                .build();
//
//        this.builder(俄罗斯酸辣汤)
//                .input(NMIIngredientItems.ONION)
//                .input(Items.BEEF)
//                .input(Items.POTATO)
//                .input(NMIIngredientItems.LEMON)
//                .input(NMIIngredientItems.CUCUMBER)
//                .kitchenware(NMIBlockTags.BOILING_POT)
//                .time(10)
//                .build();
    }

    private void addCuttingBoardRecipes() {
        this.builder(NMICuisinesItems.RICE_BALL)
                .input(this.getKelp())
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.PORK_RICE_BALL)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.PINK_RICE_BALL)
                .input(NMIIngredientItems.ONION)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.FALLING_BLOSSOMS)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.FRESH_TOFU)
                .input(NMIIngredientItems.RADISH)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.VEGETABLE_SALAD)
                .input(Items.POTATO)
                .input(NMIIngredientItems.ONION)
                .input(Items.PUMPKIN)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SASHIMI_PLATTER)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.TUNA)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SECRET_DRIED_FISH_CRISPS)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CARVED_ROSE_SALAD)
                .input(NMIIngredientItems.RADISH)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.PEACH_SHRIMP_SALAD)
                .input(NMIIngredientItems.PEACH)
                .input(this.getIce())
                .input(NMIIngredientItems.SHRIMP)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.LUNAR_DANGO)
                .input(NMIIngredientItems.LUNAR_HERB)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.MOCHI)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.PEACH_YATSUHASHI)
                .input(NMIIngredientItems.STICKY_RICE)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.LUNAR_LOVER_BISCUITS)
                .input(NMIIngredientItems.BUTTER)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getEgg())
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.FLOWING_SOMEN)
                .input(NMIIngredientItems.FLOUR)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.CUBIC_KEDAMA_ICE_CREAM)
                .input(NMIIngredientItems.DEW)
                .input(NMIIngredientItems.TOFU)
                .input(Items.HONEY_BOTTLE)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CUBIC_KEDAMA_VOLCANIC_TOFU)
                .input(NMIIngredientItems.TOFU)
                .input(NMIIngredientItems.CHILI)
                .input(Items.BEEF)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.SCARLET_DEVIL_CAKE)
                .input(NMIIngredientItems.DEW)
                .input(Items.PUMPKIN)
                .input(Items.POTATO)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.UNCONSCIOUS_YOUKAI_MOUSSE)
                .input(NMIIngredientItems.TOFU)
                .input(NMIIngredientItems.BUTTER)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PICKLES)
                .input(NMIIngredientItems.CUCUMBER)
                .input(NMIIngredientItems.BLACK_SALT)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.SEA_URCHIN_SASHIMI)
                .input(NMIIngredientItems.SEA_URCHIN)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.NIGIRI_SUSHI)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.TUNA)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GLOOMY_FRUIT_PIE)
                .input(NMIIngredientItems.LEMON)
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.CHEESE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CRISPY_SPIRALS)
                .input(NMIIngredientItems.FLOUR)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.CICADA_SLOUGH)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.OEDO_BOAT_FEAST)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.TUNA)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .input(NMIIngredientItems.TROUT)
                .input(this.getIce())
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(24)
                .build();

        this.builder(NMICuisinesItems.NEKO_MANMA)
                .input(NMIIngredientItems.TROUT)
                .input(NMIIngredientItems.DEW)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.YASHOUMA_DANGO)
                .input(NMIIngredientItems.STICKY_RICE)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.STAR_LOTUS_SHIP)
                .input(Items.PUMPKIN)
                .input(NMIIngredientItems.LOTUS_SEED)
                .input(NMIIngredientItems.TUNA)
                .input(NMIIngredientItems.BINGDI_LOTUS)
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(13)
                .build();

        this.builder(NMICuisinesItems.HONEYED_CHESTNUT)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.CHESTNUT)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.AGAINST_THE_WORLD)
                .input(Items.BAMBOO)
                .input(NMIIngredientItems.FLOWER)
                .input(NMIIngredientItems.PLUM)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.RED_BEAN_DAIFUKU)
                .input(NMIIngredientItems.RED_BEAN)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.DRUNK_SHRIMP_IN_BAMBOO)
                .input(Items.BAMBOO)
                .input(NMIIngredientItems.SHRIMP)
                .input(NMIIngredientItems.BROCCOLI)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.KITTENS_WATER_PLAY)
                .input(NMIIngredientItems.PEACH)
                .input(NMIIngredientItems.CREEPING_FIG)
                .input(NMIIngredientItems.CREAM)
                .input(NMIIngredientItems.FLOUR)
                .input(Items.COCOA_BEANS)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.MOONLIGHT_OVER_THE_LOTUS_POND)
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.CREEPING_FIG)
                .input(NMIIngredientItems.CREAM)
                .input(NMIIngredientItems.SNOW_FUNGUS)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.DRAGONSONG_PEACH)
                .input(Items.COCOA_BEANS)
                .input(NMIIngredientItems.PEACH)
                .input(NMIIngredientItems.PEACH)
                .input(NMIIngredientItems.PEACH)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.MOLECULAR_EGG)
                .input(Items.COCOA_BEANS)
                .input(Items.PUMPKIN)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockVanillaTags.CUTTING_BOARD)
                .time(7)
                .build();

//        this.builder(山泉双色果盘)
//                .input(NMIIngredientItems.PEACH)
//                .input(NMIIngredientItems.GRAPES)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(3)
//                .build();
//
//        this.builder(正义执行雕塑)
//                .input(Items.COCOA_BEANS)
//                .input(Items.COCOA_BEANS)
//                .input(炼乳)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(5)
//                .build();
//
//        this.builder(超位·业火烧烤宴)
//                .input(Items.BEEF)
//                .input(NMIIngredientItems.VENISON)
//                .input(NMIIngredientItems.WAGYU_BEEF)
//                .input(金箔)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(5)
//                .build();
//
//        this.builder(甜心三明治)
//                .input(NMIIngredientItems.TUNA)
//                .input(NMIIngredientItems.GRAPES)
//                .input(NMIIngredientItems.CHILI)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(3)
//                .build();
//
//        this.builder(月见饼)
//                .input(NMIIngredientItems.LUNAR_HERB)
//                .input(NMIIngredientItems.FLOUR)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(4)
//                .build();
//
//        this.builder(星月桃子糕)
//                .input(NMIIngredientItems.PEACH)
//                .input(NMIIngredientItems.UDUMBARA)
//                .input(NMIIngredientItems.LUNAR_HERB)
//                .input(NMIIngredientItems.DEW)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(4)
//                .build();
//
//        this.builder(黯月魔境慕斯)
//                .input(this.getEgg())
//                .input(NMIIngredientItems.CREAM)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(8)
//                .build();
//
//        this.builder(俄罗斯冻汤)
//                .input(大葱)
//                .input(NMIIngredientItems.CUCUMBER)
//                .input(炼乳)
//                .input(Items.POTATO)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(10)
//                .build();
//
//        this.builder(西班牙冷汤)
//                .input(大蒜)
//                .input(NMIIngredientItems.FLOWER)
//                .input(NMIIngredientItems.TOMATO)
//                .input(NMIIngredientItems.CUCUMBER)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(13)
//                .build();
//
//        this.builder(胜春朝)
//                .input(NMIIngredientItems.GRAPES)
//                .input(NMIIngredientItems.SWEET_POTATO)
//                .input(NMIIngredientItems.CHESTNUT)
//                .input(炼乳)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(10)
//                .build();
//
//        this.builder(秋神的工作)
//                .input(Items.HONEY_BOTTLE)
//                .input(NMIIngredientItems.SWEET_POTATO)
//                .input(NMIIngredientItems.CHESTNUT)
//                .input(金箔)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(16)
//                .build();
//
//        this.builder(惊吓万圣夜)
//                .input(Items.PUMPKIN)
//                .input(Items.COCOA_BEANS)
//                .input(棉花糖)
//                .input(NMIIngredientItems.TRUFFLE)
//                .input(玉米)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(12)
//                .build();
    }

    private void addFryingPanRecipes() {
        this.builder(NMICuisinesItems.PORK_STIR_FRY)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.KABAYAKI_LAMPREYS)
                .input(NMIIngredientItems.ONION)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.POTATO_CROQUETTES)
                .input(Items.POTATO)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.FRIED_LAMPREYS)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.FRIED_CICADA_SLOUGHS)
                .input(NMIIngredientItems.CICADA_SLOUGH)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DEEP_FRIED_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.FRIED_PORK_CUTLET)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.CLASSIC_STEAK)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .input(NMIIngredientItems.BUTTER)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.BEEF_WELLINGTON)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getEgg())
                .input(NMIIngredientItems.BUTTER)
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.EGGS_BENEDICT)
                .input(this.getEgg())
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(NMIIngredientItems.BUTTER)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.PANCAKES_WITH_SYRUP)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.SALMON_STEAK)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.PORK_BAMBOO_SHOOTS_STIR_FRY)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.STINKY_TOFU)
                .input(NMIIngredientItems.TOFU)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.RAINBOW_PAN_FRIED_PORK_BUNS)
                .input(this.getMushroom())
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.MAPO_TOFU)
                .input(NMIIngredientItems.TOFU)
                .input(Items.PORKCHOP)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.DEEP_FRIED_SHRIMP_TEMPURA)
                .input(NMIIngredientItems.SHRIMP)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GOLDEN_CRISPY_FISH_CAKES)
                .input(NMIIngredientItems.TROUT)
                .input(NMIIngredientItems.FLOUR)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.CREAMY_CRAB)
                .input(NMIIngredientItems.CREAM)
                .input(NMIIngredientItems.CRAB)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.OKONOMIYAKI)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getEgg())
                .input(NMIIngredientItems.RADISH)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.TAKOYAKI)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getKelp())
                .input(NMIIngredientItems.OCTOPUS)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PORK_MUSHROOM_STIR_FRY)
                .input(this.getMushroom())
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GIANT_TAMAGOYAKI)
                .input(NMIIngredientItems.FLOUR)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getEgg())
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SALMON_TEMPURA)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.BUTTER)
                .input(this.getEgg())
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CHEESE_OMELETTE)
                .input(this.getEgg())
                .input(NMIIngredientItems.CHEESE)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.CAUTION_HELLISH_SPICE)
                .input(NMIIngredientItems.CHILI)
                .input(NMIIngredientItems.CHILI)
                .input(NMIIngredientItems.CHILI)
                .input(NMIIngredientItems.CHEESE)
                .input(Items.BEEF)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.UNZAN_COTTON_CANDY)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CANDIED_SWEET_POTATO)
                .input(NMIIngredientItems.SWEET_POTATO)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GOLDEN_TWO_SHROOM_WRAP)
                .input(Items.PORKCHOP)
                .input(this.getMushroom())
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.TEMPURA_PLATTER)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(NMIIngredientItems.TRUFFLE)
                .input(NMIIngredientItems.LAMPREY)
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.TOMATO_FRIES)
                .input(NMIIngredientItems.TOMATO)
                .input(Items.POTATO)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.PEACH_BRAISED_PORK)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.PEACH)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DORAYAKI)
                .input(NMIIngredientItems.RED_BEAN)
                .input(this.getEgg())
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.TOON_PANCAKE)
                .input(NMIIngredientItems.RED_TOON)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.EEL_BOWL_WITH_EGG)
                .input(NMIIngredientItems.LAMPREY)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.HOT_PEPPER_SOUP)
                .input(NMIIngredientItems.CHILI)
                .input(Items.BEEF)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.FRYING_PAN)
                .time(8)
                .build();

//        this.builder(鱼饼)
//                .input(NMIIngredientItems.TROUT)
//                .input(Items.POTATO)
//                .input(NMIIngredientItems.TOFU)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(4)
//                .build();
//
//        this.builder(恶魔的甜甜圈)
//                .input(NMIIngredientItems.FLOUR)
//                .input(NMIIngredientItems.WAGYU_BEEF)
//                .input(NMIIngredientItems.CHEESE)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(4)
//                .build();
//
//        this.builder(血色下午茶)
//                .input(NMIIngredientItems.WAGYU_BEEF)
//                .input(this.getEgg())
//                .input(Items.HONEY_BOTTLE)
//                .input(NMIIngredientItems.BUTTER)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(5)
//                .build();
//
//        this.builder(樱绯星屑缀玉烩)
//                .input(NMIIngredientItems.CHILI)
//                .input(this.getMushroom())
//                .input(NMIIngredientItems.FLOWER)
//                .input(NMIIngredientItems.STICKY_RICE)
//                .input(NMIIngredientItems.CHEESE)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(20)
//                .build();
//
//        this.builder(奶油甜煎饼卷)
//                .input(NMIIngredientItems.FLOUR)
//                .input(NMIIngredientItems.CREAM)
//                .input(Items.COCOA_BEANS)
//                .input(金箔)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(13)
//                .build();
    }

    private void addGrillRecipes() {
        this.builder(NMICuisinesItems.PORK_TROUT_KEBAB)
                .input(NMIIngredientItems.TROUT)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.GRILLED_LAMPREY)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.ENERGY_SKEWER)
                .input(Items.BEEF)
                .input(NMIIngredientItems.ONION)
                .input(Items.PUMPKIN)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.NITEN_ICHIRYU)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.ROASTED_MUSHROOM)
                .input(this.getMushroom())
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.CANTONESE_CHAR_SIU)
                .input(Items.PORKCHOP)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.IMMORTAL_TURKEY)
                .input(NMIIngredientItems.FLOUR)
                .input(Items.HONEY_BOTTLE)
                .input(Items.POTATO)
                .input(NMIIngredientItems.ONION)
                .input(NMIIngredientItems.RADISH)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HOURAI_BRANCH)
                .input(Items.BAMBOO)
                .input(Items.PORKCHOP)
                .input(NMIIngredientItems.SALMON)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .input(NMIIngredientItems.VENISON)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(13)
                .build();

        this.builder(NMICuisinesItems.ALL_MEAT_FEAST)
                .input(NMIIngredientItems.BOAR_MEAT)
                .input(NMIIngredientItems.VENISON)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.INSTANT_DEATH)
                .input(NMIIngredientItems.BOAR_MEAT)
                .input(NMIIngredientItems.VENISON)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.BAKED_SWEET_POTATO)
                .input(NMIIngredientItems.SWEET_POTATO)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.BISCAY_BISCUITS)
                .input(NMIIngredientItems.FLOUR)
                .input(NMIIngredientItems.CHEESE)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SMOKED_BUCCANEER)
                .input(Items.BEEF)
                .input(NMIIngredientItems.BLACK_SALT)
                .input(NMIIngredientItems.CHILI)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.SCRUMPTIOUS_STORM)
                .input(NMIIngredientItems.ONION)
                .input(NMIIngredientItems.BOAR_MEAT)
                .input(Items.BEEF)
                .input(NMIIngredientItems.TRUFFLE)
                .input(NMIIngredientItems.TOMATO)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.KITTEN_CANELE)
                .input(Items.COCOA_BEANS)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.KITTEN_PIZZA)
                .input(this.getMushroom())
                .input(NMIIngredientItems.ONION)
                .input(NMIIngredientItems.BROCCOLI)
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockVanillaTags.GRILL)
                .time(10)
                .build();

//        this.builder(烬色魔纹千层酥)
//                .input(NMIIngredientItems.FLOUR)
//                .input(this.getEgg())
//                .input(Items.COCOA_BEANS)
//                .input(NMIIngredientItems.BUTTER)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(8)
//                .build();
//
//        this.builder(肉汁奶酪薯条)
//                .input(Items.POTATO)
//                .input(Items.PORKCHOP)
//                .input(NMIIngredientItems.CHEESE)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(5)
//                .build();
//
//        this.builder(烤玉米)
//                .input(玉米)
//                .input(NMIIngredientItems.BUTTER)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(6)
//                .build();
//
//        this.builder(板栗饼)
//                .input(NMIIngredientItems.BUTTER)
//                .input(NMIIngredientItems.FLOUR)
//                .input(NMIIngredientItems.CHESTNUT)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(6)
//                .build();
//
//        this.builder(南瓜派)
//                .input(Items.PUMPKIN)
//                .input(NMIIngredientItems.BUTTER)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(5)
//                .build();
//
//        this.builder(芝士玉米焗红薯)
//                .input(玉米)
//                .input(NMIIngredientItems.SWEET_POTATO)
//                .input(NMIIngredientItems.GRAPES)
//                .input(NMIIngredientItems.CHEESE)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(7)
//                .build();
    }

    private void addSteamerRecipes() {
        this.builder(NMICuisinesItems.DEW_RUNNY_EGGS)
                .input(NMIIngredientItems.DEW)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(3)
                .build();

        this.builder(NMICuisinesItems.UDUMBARA_CAKE)
                .input(NMIIngredientItems.UDUMBARA)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.IMITATION_BEAR_PAW)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(Items.PUFFERFISH)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SCHOLARS_GINKGO)
                .input(NMIIngredientItems.GINKGO_NUT)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.ITALIAN_RISOTTO)
                .input(NMIIngredientItems.ONION)
                .input(this.getMushroom())
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(NMIIngredientItems.BUTTER)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.SCONE)
                .input(NMIIngredientItems.BUTTER)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.CREAMY_VEGETABLE_CHOWDER)
                .input(this.getMushroom())
                .input(NMIIngredientItems.ONION)
                .input(NMIIngredientItems.BUTTER)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.KAGUYA_HIME)
                .input(Items.BAMBOO)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .input(NMIIngredientItems.TRUFFLE)
                .input(NMIIngredientItems.GINKGO_NUT)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.INO_SHIKA_CHOU)
                .input(NMIIngredientItems.BOAR_MEAT)
                .input(NMIIngredientItems.VENISON)
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.STEAMED_EGG_BAMBOO_SHOOTS)
                .input(Items.BAMBOO)
                .input(this.getEgg())
                .input(this.getKelp())
                .input(this.getMushroom())
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.MOON_CAKE)
                .input(NMIIngredientItems.LUNAR_HERB)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.ORDINARY_EAT_ME_CUPCAKE)
                .input(this.getEgg())
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.SEVEN_COLORED_YOKAN)
                .input(this.getKelp())
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.DEW)
                .input(NMIIngredientItems.UDUMBARA)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.SHRIMP_STUFFED_PUMPKIN)
                .input(Items.PUMPKIN)
                .input(NMIIngredientItems.SHRIMP)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.MISERY_CHEESE_STICKS)
                .input(NMIIngredientItems.CHEESE)
                .input(NMIIngredientItems.GINKGO_NUT)
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.CEILING_LONGING_PIE)
                .input(NMIIngredientItems.TROUT)
                .input(NMIIngredientItems.FLOUR)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.KABUTO_STEAMED_CAKE)
                .input(NMIIngredientItems.FLOUR)
                .input(NMIIngredientItems.IBERICO_PORK)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.CICADA_SLOUGH)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SAKURA_PUDDING)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.ENERGY_PUDDING)
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.LEMON)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.BURN_OUT_PUDDING)
                .input(NMIIngredientItems.GRAPES)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.LEMON)
                .input(NMIIngredientItems.LEMON)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DRAGON_CARP)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .input(NMIIngredientItems.VENISON)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.LUNAR_HERB)
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HEART_THROBBING_SURPRISE)
                .input(this.getMushroom())
                .input(NMIIngredientItems.UDUMBARA)
                .input(Items.HONEY_BOTTLE)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.PURE_WHITE_LOTUS)
                .input(NMIIngredientItems.GINKGO_NUT)
                .input(NMIIngredientItems.LOTUS_SEED)
                .input(NMIIngredientItems.FLOUR)
                .input(NMIIngredientItems.BUTTER)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.PINE_NUT_CAKE)
                .input(NMIIngredientItems.STICKY_RICE)
                .input(NMIIngredientItems.PINE_NUT)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.WHITE_DEER_UNYIELDING_PINE)
                .input(NMIIngredientItems.VENISON)
                .input(NMIIngredientItems.GINKGO_NUT)
                .input(NMIIngredientItems.PINE_NUT)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.LOTUS_FISH_LAMPS)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .input(NMIIngredientItems.BINGDI_LOTUS)
                .input(NMIIngredientItems.LOTUS_SEED)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(11)
                .build();

        this.builder(NMICuisinesItems.RICE_POWDER_MEAT)
                .input(Items.BAMBOO)
                .input(NMIIngredientItems.DEW)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.BAMBOO_SPRING)
                .input(NMIIngredientItems.CUCUMBER)
                .input(this.getEgg())
                .input(NMIIngredientItems.RADISH)
                .input(NMIIngredientItems.VENISON)
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.URCHIN_STEAMED_EGG)
                .input(NMIIngredientItems.SEA_URCHIN)
                .input(this.getEgg())
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.NATURES_BEAUTY)
                .input(NMIIngredientItems.FLOWER)
                .input(NMIIngredientItems.LUNAR_HERB)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.FAINT_DREAM)
                .input(NMIIngredientItems.FLOWER)
                .input(NMIIngredientItems.UDUMBARA)
                .input(NMIIngredientItems.LUNAR_HERB)
                .input(NMIIngredientItems.DEW)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.LITTLE_SWEET_POISON)
                .input(NMIIngredientItems.UDUMBARA)
                .input(NMIIngredientItems.CREAM)
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.LONG_HAIR_PRINCESS)
                .input(Items.PUMPKIN)
                .input(NMIIngredientItems.SHRIMP)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.MAD_HATTERS_TEA_PARTY)
                .input(Items.COCOA_BEANS)
                .input(NMIIngredientItems.CREAM)
                .input(NMIIngredientItems.FLOUR)
                .input(this.getMushroom())
                .input(NMIIngredientItems.BROCCOLI)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(15)
                .build();

        this.builder(NMICuisinesItems.PEACH_FLOWER_CRYSTAL_ROLL)
                .input(NMIIngredientItems.PEACH)
                .input(NMIIngredientItems.RED_BEAN)
                .input(NMIIngredientItems.CREEPING_FIG)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.ORIGIN_OF_LIFE)
                .input(Items.COCOA_BEANS)
                .input(NMIIngredientItems.SNOW_FUNGUS)
                .input(Items.PUMPKIN)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(13)
                .build();

        this.builder(NMICuisinesItems.PLANET_MARS)
                .input(NMIIngredientItems.CREEPING_FIG)
                .input(NMIIngredientItems.GRAPES)
                .input(NMIIngredientItems.CRAB)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockVanillaTags.STEAMER)
                .time(24)
                .build();

//        this.builder(白果灵盅)
//                .input(this.getEgg())
//                .input(NMIIngredientItems.GINKGO_NUT)
//                .input(NMIIngredientItems.LUNAR_HERB)
//                .input(Items.BAMBOO)
//                .kitchenware(NMIBlockTags.STEAMER)
//                .time(5)
//                .build();
//
//        this.builder(玉色良汤)
//                .input(Items.HONEY_BOTTLE)
//                .input(NMIIngredientItems.GINKGO_NUT)
//                .input(NMIIngredientItems.DEW)
//                .kitchenware(NMIBlockTags.STEAMER)
//                .time(7)
//                .build();
    }

    private void addOtherRecipes() {
        this.builder(NMICuisinesItems.DARK_MATTER)
                .input(Items.BARRIER, Items.BARRIER, Items.BARRIER, Items.BARRIER, Items.BARRIER)
                .kitchenware(NMIBlockVanillaTags.KITCHENWARE_BLOCK)
                .time(0)
                .build();
    }
}
