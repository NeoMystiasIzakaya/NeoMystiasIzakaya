package icu.gensoukyo.neo_mystias_izakaya.compat;

import icu.gensoukyo.neo_mystias_izakaya.compat.recipe.BoilingPotRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIRecipeUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeAccess;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

@JeiPlugin
public class MystiasPlugin implements IModPlugin {
    private static final Identifier IDENTIFIER = id("jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BoilingPotRecipe(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<NMIRecipe> recipes = NMIRecipeUtil.client().getRecipes(NMIBlockTags.BOILING_POT);
        registration.addRecipes(BoilingPotRecipe.BOILING_POT, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(BoilingPotRecipe.BOILING_POT, NMIMainItems.BOILING_POT.toStack());
    }


    @Override
    public Identifier getPluginUid() {
        return IDENTIFIER;
    }
}
