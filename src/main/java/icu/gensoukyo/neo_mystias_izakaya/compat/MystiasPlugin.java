package icu.gensoukyo.neo_mystias_izakaya.compat;

import icu.gensoukyo.neo_mystias_izakaya.compat.recipe.*;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIRecipeUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;

import java.util.List;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

@JeiPlugin
public class MystiasPlugin implements IModPlugin {
    private static final Identifier IDENTIFIER = id("jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var gui = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BoilingPotRecipe(gui));
        registration.addRecipeCategories(new GrillRecipe(gui));
        registration.addRecipeCategories(new FryingPanRecipe(gui));
        registration.addRecipeCategories(new SteamerRecipe(gui));
        registration.addRecipeCategories(new CuttingBoardRecipe(gui));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<NMIRecipe> recipes;
        recipes = NMIRecipeUtil.client().getRecipes(NMIBlockTags.BOILING_POT);
        registration.addRecipes(BoilingPotRecipe.BOILING_POT, recipes);

        recipes = NMIRecipeUtil.client().getRecipes(NMIBlockTags.GRILL);
        registration.addRecipes(GrillRecipe.GRILL, recipes);

        recipes = NMIRecipeUtil.client().getRecipes(NMIBlockTags.FRYING_PAN);
        registration.addRecipes(FryingPanRecipe.FRYING_PAN, recipes);

        recipes = NMIRecipeUtil.client().getRecipes(NMIBlockTags.STEAMER);
        registration.addRecipes(SteamerRecipe.STEAMER, recipes);

        recipes = NMIRecipeUtil.client().getRecipes(NMIBlockTags.CUTTING_BOARD);
        registration.addRecipes(CuttingBoardRecipe.CUTTING_BOARD, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(BoilingPotRecipe.BOILING_POT, NMIMainItems.BOILING_POT.toStack());
        registration.addCraftingStation(GrillRecipe.GRILL, NMIMainItems.GRILL.toStack());
        registration.addCraftingStation(FryingPanRecipe.FRYING_PAN, NMIMainItems.FRYING_PAN.toStack());
        registration.addCraftingStation(SteamerRecipe.STEAMER, NMIMainItems.STEAMER.toStack());
        registration.addCraftingStation(CuttingBoardRecipe.CUTTING_BOARD, NMIMainItems.CUTTING_BOARD.toStack());
    }


    @Override
    public Identifier getPluginUid() {
        return IDENTIFIER;
    }
}
