package icu.gensoukyo.neo_mystias_izakaya.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NMIRecipeUtil {

    public static List<NMIRecipeHolder> getAvailableRecipes(List<ItemStack> input) {
        Set<Identifier> recipesIds = new HashSet<>();
        for (ItemStack stack : input) {
            recipesIds.addAll(NMIDataAccessor.getInstance().getRecipeMap().getInputItemToRecipeMap().get(NMIItemStackUtil.get(stack)));
        }
        return getRecipes(new ArrayList<>(recipesIds));
    }

    public static List<NMIRecipeHolder> getRecipesByOutput(ItemStack output) {
        List<Identifier> recipesIds = NMIDataAccessor.getInstance().getRecipeMap().getOutputItemToRecipeMap().get(NMIItemStackUtil.get(output));
        return getRecipes(recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipesByKitchenware(TagKey<Block> kitchenware) {
        List<Identifier> recipesIds = NMIDataAccessor.getInstance().getRecipeMap().getKitchenwareToRecipeMap().get(kitchenware);
        return getRecipes(recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipes(List<Identifier> recipesIds) {
        List<NMIRecipeHolder> recipes = new ArrayList<>(recipesIds.size());
        recipesIds.forEach(id -> {
            NMIRecipeHolder recipe = NMIDataAccessor.getInstance().getRecipeMap().getRecipeMap().get(id);
            if (recipe != null) {
                recipes.add(recipe);
            }
        });
        return recipes;
    }

    public static NMIRecipeHolder getRecipe(Identifier id) {
        return NMIDataAccessor.getInstance().getRecipeMap().getRecipeMap().get(id);
    }

}
