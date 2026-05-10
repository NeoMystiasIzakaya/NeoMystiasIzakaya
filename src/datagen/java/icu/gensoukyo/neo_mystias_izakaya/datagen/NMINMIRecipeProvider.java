/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.datagen.api.NMIRecipeProvider;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockTags;
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

    private void addBoilingPotRecipes() {
        this.builder(NMICuisinesItems.SEAFOOD_MISO_SOUP)
                .input(this.getKelp())
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.MISO_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.POWER_SOUP)
                .input(this.getKelp())
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HUNTERS_CASSEROLE)
                .input(Items.POTATO)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PORK_BOWL)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.BEEF_BOWL)
                .input(Items.BEEF)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.SHIRAYUKI)
                .input(Items.PUFFERFISH)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.TOFU_STEW)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.HODGEPODGE)
                .input(this.getKelp())
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.DAIMYOS_FEAST)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.TONKOTSU_RAMEN)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.FUJIS_LAVA)
                .input(Items.BEEF)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PEACH_TAPIOCA)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.REAL_SEAFOOD_MISO_SOUP)
                .input(NMIIngredientItems.SALMON)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.BOILED_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.GOLDEN_RIBS_SOUP)
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.SICHUAN_BOILED_FISH)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DUMPLINGS)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.TANGYUAN)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.IMITATION_SHIRIKODAMA)
                .input(NMIIngredientItems.VENISON)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SECRET_SAVORY_MUSHROOM_HOTPOT)
                .input(NMIIngredientItems.TRUFFLE)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.MUSHROOM_MAIDENS_TIP_TAP_POT)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.CREAM_OF_MUSHROOM_SOUP)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.BUDDHA_JUMPS_OVER_THE_WALL)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.AGONY_ODEN)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.LIONS_HEAD)
                .input(Items.BEEF)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.BUDDHAS_DELIGHT)
                .input(NMIIngredientItems.UDUMBARA)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.EIGHT_TRIGRAM_FISH_MAWS)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.TIANSHIS_STEWED_MUSHROOMS)
                .input(NMIIngredientItems.CHESTNUT)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PALACE_OF_THE_HAN)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.BAMBOO_MEAT_POT)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.PLUM_TEA_RICE)
                .input(NMIIngredientItems.PLUM)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(4)
                .build();

        this.builder(NMICuisinesItems.MUSHROOM_HERB_ROAD)
                .input(NMIIngredientItems.RED_TOON)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(6)
                .build();


        this.builder(NMICuisinesItems.MIASMA_GARDEN)
                .input(Items.PUFFERFISH)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.TWO_FLAVOR_BEEF_HOTPOT)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.URCHIN_RAINDROP_CAKE)
                .input(NMIIngredientItems.SEA_URCHIN)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HEART_WARMING_CONGEE)
                .input(NMIIngredientItems.SNOW_FUNGUS)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SUPREME_SEAFOOD_NOODLES)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .kitchenware(NMIBlockTags.BOILING_POT)
                .time(10)
                .build();

    }

    private void addCuttingBoardRecipes() {
        this.builder(NMICuisinesItems.FRESH_TOFU)
                .input(NMIIngredientItems.RADISH)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.RICE_BALL)
                .input(this.getKelp())
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.PORK_RICE_BALL)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.PINK_RICE_BALL)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.FALLING_BLOSSOMS)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.VEGETABLE_SALAD)
                .input(Items.POTATO)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SASHIMI_PLATTER)
                .input(NMIIngredientItems.SALMON)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SECRET_DRIED_FISH_CRISPS)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CARVED_ROSE_SALAD)
                .input(NMIIngredientItems.RADISH)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.PEACH_SHRIMP_SALAD)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.LUNAR_DANGO)
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.MOCHI)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.PEACH_YATSUHASHI)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.LUNAR_LOVER_BISCUITS)
                .input(NMIIngredientItems.BUTTER)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.FLOWING_SOMEN)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.CUBIC_KEDAMA_ICE_CREAM)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CUBIC_KEDAMA_VOLCANIC_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.SCARLET_DEVIL_CAKE)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.UNCONSCIOUS_YOUKAI_MOUSSE)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PICKLES)
                .input(NMIIngredientItems.CUCUMBER)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.SEA_URCHIN_SASHIMI)
                .input(NMIIngredientItems.SEA_URCHIN)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.NIGIRI_SUSHI)
                .input(NMIIngredientItems.SALMON)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GLOOMY_FRUIT_PIE)
                .input(NMIIngredientItems.LEMON)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CRISPY_SPIRALS)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.OEDO_BOAT_FEAST)
                .input(NMIIngredientItems.SALMON)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(24)
                .build();

        this.builder(NMICuisinesItems.NEKO_MANMA)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.YASHOUMA_DANGO)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.STAR_LOTUS_SHIP)
                .input(Items.PUMPKIN)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(13)
                .build();

        this.builder(NMICuisinesItems.HONEYED_CHESTNUT)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.AGAINST_THE_WORLD)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.RED_BEAN_DAIFUKU)
                .input(NMIIngredientItems.RED_BEAN)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.DRUNK_SHRIMP_IN_BAMBOO)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.KITTENS_WATER_PLAY)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.MOONLIGHT_OVER_THE_LOTUS_POND)
                .input(NMIIngredientItems.GRAPES)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.DRAGONSONG_PEACH)
                .input(Items.COCOA_BEANS)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.MOLECULAR_EGG)
                .input(Items.COCOA_BEANS)
                .kitchenware(NMIBlockTags.CUTTING_BOARD)
                .time(7)
                .build();

    }

    private void addFryingPanRecipes() {
        this.builder(NMICuisinesItems.PORK_STIR_FRY)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.KABAYAKI_LAMPREYS)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.POTATO_CROQUETTES)
                .input(Items.POTATO)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.FRIED_LAMPREYS)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.FRIED_CICADA_SLOUGHS)
                .input(NMIIngredientItems.CICADA_SLOUGH)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DEEP_FRIED_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.FRIED_PORK_CUTLET)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.CLASSIC_STEAK)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.BEEF_WELLINGTON)
                .input(NMIIngredientItems.WAGYU_BEEF)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.EGGS_BENEDICT)
                .input(this.getEgg())
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.PANCAKES_WITH_SYRUP)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.SALMON_STEAK)
                .input(NMIIngredientItems.SALMON)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.PORK_BAMBOO_SHOOTS_STIR_FRY)
                .input(NMIIngredientItems.BAMBOO_SHOOT)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.STINKY_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.RAINBOW_PAN_FRIED_PORK_BUNS)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.MAPO_TOFU)
                .input(NMIIngredientItems.TOFU)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.DEEP_FRIED_SHRIMP_TEMPURA)
                .input(NMIIngredientItems.SHRIMP)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GOLDEN_CRISPY_FISH_CAKES)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.CREAMY_CRAB)
                .input(NMIIngredientItems.CREAM)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.OKONOMIYAKI)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.TAKOYAKI)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.PORK_MUSHROOM_STIR_FRY)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GIANT_TAMAGOYAKI)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SALMON_TEMPURA)
                .input(NMIIngredientItems.SALMON)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CHEESE_OMELETTE)
                .input(this.getEgg())
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.CAUTION_HELLISH_SPICE)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.UNZAN_COTTON_CANDY)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.CANDIED_SWEET_POTATO)
                .input(NMIIngredientItems.SWEET_POTATO)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.GOLDEN_TWO_SHROOM_WRAP)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.TEMPURA_PLATTER)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.TOMATO_FRIES)
                .input(NMIIngredientItems.TOMATO)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.PEACH_BRAISED_PORK)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DORAYAKI)
                .input(NMIIngredientItems.RED_BEAN)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.TOON_PANCAKE)
                .input(NMIIngredientItems.RED_TOON)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(6)
                .build();
        this.builder(NMICuisinesItems.EEL_BOWL_WITH_EGG)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.HOT_PEPPER_SOUP)
                .input(NMIIngredientItems.CHILI)
                .kitchenware(NMIBlockTags.FRYING_PAN)
                .time(8)
                .build();

    }

    private void addGrillRecipes() {
        this.builder(NMICuisinesItems.PORK_TROUT_KEBAB)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.GRILLED_LAMPREY)
                .input(NMIIngredientItems.LAMPREY)
                .kitchenware(NMIBlockTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.ENERGY_SKEWER)
                .input(Items.BEEF)
                .kitchenware(NMIBlockTags.GRILL)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.NITEN_ICHIRYU)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockTags.GRILL)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.ROASTED_MUSHROOM)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.GRILL)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.CANTONESE_CHAR_SIU)
                .input(Items.PORKCHOP)
                .kitchenware(NMIBlockTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.IMMORTAL_TURKEY)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.GRILL)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HOURAI_BRANCH)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.GRILL)
                .time(13)
                .build();

        this.builder(NMICuisinesItems.ALL_MEAT_FEAST)
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockTags.GRILL)
                .time(14)
                .build();

        this.builder(NMICuisinesItems.BISCAY_BISCUITS)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.GRILL)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.SMOKED_BUCCANEER)
                .input(Items.BEEF)
                .kitchenware(NMIBlockTags.GRILL)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.INSTANT_DEATH)
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockTags.GRILL)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.BAKED_SWEET_POTATO)
                .input(NMIIngredientItems.SWEET_POTATO)
                .kitchenware(NMIBlockTags.GRILL)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.SCRUMPTIOUS_STORM)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockTags.GRILL)
                .time(18)
                .build();

        this.builder(NMICuisinesItems.KITTEN_CANELE)
                .input(Items.COCOA_BEANS)
                .kitchenware(NMIBlockTags.GRILL)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.KITTEN_PIZZA)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.GRILL)
                .time(10)
                .build();

    }

    private void addSteamerRecipes() {
        this.builder(NMICuisinesItems.SCONE)
                .input(NMIIngredientItems.BUTTER)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.CREAMY_VEGETABLE_CHOWDER)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.DEW_RUNNY_EGGS)
                .input(NMIIngredientItems.DEW)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(3)
                .build();

        this.builder(NMICuisinesItems.UDUMBARA_CAKE)
                .input(NMIIngredientItems.UDUMBARA)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.IMITATION_BEAR_PAW)
                .input(NMIIngredientItems.IBERICO_PORK)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SCHOLARS_GINKGO)
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.ITALIAN_RISOTTO)
                .input(NMIIngredientItems.ONION)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.KAGUYA_HIME)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.INO_SHIKA_CHOU)
                .input(NMIIngredientItems.BOAR_MEAT)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.STEAMED_EGG_BAMBOO_SHOOTS)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.MOON_CAKE)
                .input(NMIIngredientItems.LUNAR_HERB)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.ORDINARY_EAT_ME_CUPCAKE)
                .input(this.getEgg())
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.SEVEN_COLORED_YOKAN)
                .input(this.getKelp())
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.SHRIMP_STUFFED_PUMPKIN)
                .input(Items.PUMPKIN)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.MISERY_CHEESE_STICKS)
                .input(NMIIngredientItems.CHEESE)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.CEILING_LONGING_PIE)
                .input(NMIIngredientItems.TROUT)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.KABUTO_STEAMED_CAKE)
                .input(NMIIngredientItems.FLOUR)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.SAKURA_PUDDING)
                .input(Items.HONEY_BOTTLE)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(6)
                .build();

        this.builder(NMICuisinesItems.ENERGY_PUDDING)
                .input(NMIIngredientItems.GRAPES)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.BURN_OUT_PUDDING)
                .input(NMIIngredientItems.GRAPES)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.DRAGON_CARP)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.HEART_THROBBING_SURPRISE)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.PURE_WHITE_LOTUS)
                .input(NMIIngredientItems.GINKGO_NUT)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.PINE_NUT_CAKE)
                .input(NMIIngredientItems.STICKY_RICE)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.WHITE_DEER_UNYIELDING_PINE)
                .input(NMIIngredientItems.VENISON)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();
        this.builder(NMICuisinesItems.LOTUS_FISH_LAMPS)
                .input(NMIIngredientItems.PREMIUM_TUNA)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(11)
                .build();

        this.builder(NMICuisinesItems.RICE_POWDER_MEAT)
                .input(Items.BAMBOO)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.BAMBOO_SPRING)
                .input(NMIIngredientItems.CUCUMBER)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(14)
                .build();
        this.builder(NMICuisinesItems.URCHIN_STEAMED_EGG)
                .input(NMIIngredientItems.SEA_URCHIN)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(7)
                .build();

        this.builder(NMICuisinesItems.NATURES_BEAUTY)
                .input(NMIIngredientItems.FLOWER)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(9)
                .build();

        this.builder(NMICuisinesItems.FAINT_DREAM)
                .input(NMIIngredientItems.FLOWER)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(12)
                .build();

        this.builder(NMICuisinesItems.LITTLE_SWEET_POISON)
                .input(NMIIngredientItems.UDUMBARA)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(10)
                .build();

        this.builder(NMICuisinesItems.LONG_HAIR_PRINCESS)
                .input(Items.PUMPKIN)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(5)
                .build();

        this.builder(NMICuisinesItems.MAD_HATTERS_TEA_PARTY)
                .input(Items.COCOA_BEANS)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(15)
                .build();

        this.builder(NMICuisinesItems.PEACH_FLOWER_CRYSTAL_ROLL)
                .input(NMIIngredientItems.PEACH)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(8)
                .build();

        this.builder(NMICuisinesItems.ORIGIN_OF_LIFE)
                .input(Items.COCOA_BEANS)
                .kitchenware(NMIBlockTags.STEAMER)
                .time(13)
                .build();

        this.builder(NMICuisinesItems.PLANET_MARS)
                .input(this.getMushroom())
                .kitchenware(NMIBlockTags.STEAMER)
                .time(24)
                .build();

    }

    private void addOtherRecipes() {
        this.builder(NMICuisinesItems.DARK_MATTER)
                .input(Items.BARRIER, Items.BARRIER, Items.BARRIER, Items.BARRIER, Items.BARRIER)
                .kitchenware(NMIBlockTags.KITCHENWARE_BLOCK)
                .time(0)
                .build();
//        this.builder(山泉双色果盘)
//                .input(NMIIngredientItems.PEACH)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(3)
//                .build();
//
//        this.builder(鱼饼)
//                .input(NMIIngredientItems.TROUT)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(4)
//                .build();
//
//        this.builder(白果灵盅)
//                .input(this.getEgg())
//                .kitchenware(NMIBlockTags.STEAMER)
//                .time(5)
//                .build();
//
//        this.builder(恶魔的甜甜圈)
//                .input(NMIIngredientItems.FLOUR)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(4)
//                .build();
//
//        this.builder(正义执行雕塑)
//                .input(Items.COCOA_BEANS)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(5)
//                .build();
//
//        this.builder(超位·业火烧烤宴)
//                .input(Items.BEEF)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(5)
//                .build();
//
//        this.builder(甜心三明治)
//                .input(NMIIngredientItems.TUNA)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(3)
//                .build();
//
//        this.builder(毛茸茸蘑菇汤)
//                .input(棉花糖)
//                .kitchenware(NMIBlockTags.BOILING_POT)
//                .time(6)
//                .build();
//
//        this.builder(血色下午茶)
//                .input(NMIIngredientItems.WAGYU_BEEF)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(5)
//                .build();
//
//        this.builder(月见饼)
//                .input(NMIIngredientItems.LUNAR_HERB)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(4)
//                .build();
//
//        this.builder(玉色良汤)
//                .input(蜂蜜)
//                .kitchenware(NMIBlockTags.STEAMER)
//                .time(7)
//                .build();
//
//        this.builder(星月桃子糕)
//                .input(NMIIngredientItems.PEACH)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(4)
//                .build();
//
//        this.builder(樱绯星屑缀玉烩)
//                .input(NMIIngredientItems.CHILI)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(20)
//                .build();
//
//        this.builder(黯月魔境慕斯)
//                .input(this.getEgg())
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(8)
//                .build();
//
//        this.builder(烬色魔纹千层酥)
//                .input(NMIIngredientItems.FLOUR)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(8)
//                .build();
//
//        this.builder(肉汁奶酪薯条)
//                .input(Items.POTATO)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(5)
//                .build();
//
//        this.builder(俄罗斯酸辣汤)
//                .input(NMIIngredientItems.ONION)
//                .kitchenware(NMIBlockTags.BOILING_POT)
//                .time(10)
//                .build();
//
//        this.builder(奶油甜煎饼卷)
//                .input(NMIIngredientItems.FLOUR)
//                .kitchenware(NMIBlockTags.FRYING_PAN)
//                .time(13)
//                .build();
//
//        this.builder(烤玉米)
//                .input(玉米)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(6)
//                .build();
//
//        this.builder(俄罗斯冻汤)
//                .input(大葱)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(10)
//                .build();
//
//        this.builder(西班牙冷汤)
//                .input(大蒜)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(13)
//                .build();
//
//        this.builder(板栗饼)
//                .input(NMIIngredientItems.BUTTER)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(6)
//                .build();
//
//        this.builder(胜春朝)
//                .input(NMIIngredientItems.GRAPES)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(10)
//                .build();
//
//        this.builder(秋神的工作)
//                .input(蜂蜜)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(16)
//                .build();
//
//        this.builder(南瓜派)
//                .input(Items.PUMPKIN)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(5)
//                .build();
//
//        this.builder(芝士玉米焗红薯)
//                .input(玉米)
//                .kitchenware(NMIBlockTags.GRILL)
//                .time(7)
//                .build();
//
//        this.builder(惊吓万圣夜)
//                .input(Items.PUMPKIN)
//                .kitchenware(NMIBlockTags.CUTTING_BOARD)
//                .time(12)
//                .build();
    }
}
