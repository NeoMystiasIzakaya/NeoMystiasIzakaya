package icu.gensoukyo.neo_mystias_izakaya.util;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NMIRecipeUtil {

    private final NMIDataAccessor accessor;

    public NMIRecipeUtil(NMIDataAccessor accessor) {
        this.accessor = accessor;
    }

    private static final NMIRecipeUtil SERVER = new NMIRecipeUtil(NMIDataAccessor.server());
    private static final NMIRecipeUtil CLIENT = new NMIRecipeUtil(NMIDataAccessor.client());

    public static NMIRecipeUtil server() {
        return SERVER;
    }

    public static NMIRecipeUtil client() {
        return CLIENT;
    }

    public List<NMIRecipeHolder> getAvailableRecipes(List<ItemStack> input) {
        return getAvailableRecipes(accessor, input);
    }

    public List<NMIRecipeHolder> getRecipesByOutput(ItemStack output) {
        return getRecipesByOutput(accessor, output);
    }

    public List<NMIRecipeHolder> getRecipesByKitchenware(TagKey<Block> kitchenware) {
        return getRecipesByKitchenware(accessor, kitchenware);
    }

    public List<NMIRecipeHolder> getRecipes(List<Identifier> recipesIds) {
        return getRecipes(accessor, recipesIds);
    }

    public NMIRecipeHolder getRecipe(Identifier id) {
        return getRecipe(accessor, id);
    }

    public static List<NMIRecipeHolder> getAvailableRecipes(NMIDataAccessor accessor, List<ItemStack> input) {
        Set<Identifier> recipesIds = new HashSet<>();
        for (ItemStack stack : input) {
            List<Identifier> identifiers = accessor.getRecipeMap().getInputItemToRecipeMap().get(NMIItemStackUtil.get(stack));
            if (identifiers != null) {
                recipesIds.addAll(identifiers);
            }
        }
        return getRecipes(accessor,new ArrayList<>(recipesIds));
    }

    public static List<NMIRecipeHolder> getRecipesByOutput(NMIDataAccessor accessor,ItemStack output) {
        List<Identifier> recipesIds = accessor.getRecipeMap().getOutputItemToRecipeMap().get(NMIItemStackUtil.get(output));
        return getRecipes(accessor,recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipesByKitchenware(NMIDataAccessor accessor,TagKey<Block> kitchenware) {
        List<Identifier> recipesIds = accessor.getRecipeMap().getKitchenwareToRecipeMap().get(kitchenware);
        return getRecipes(accessor,recipesIds);
    }

    public static List<NMIRecipeHolder> getRecipes(NMIDataAccessor accessor,List<Identifier> recipesIds) {
        List<NMIRecipeHolder> recipes = new ArrayList<>(recipesIds.size());
        recipesIds.forEach(id -> {
            NMIRecipeHolder recipe = accessor.getRecipeMap().getRecipeMap().get(id);
            if (recipe != null) {
                recipes.add(recipe);
            }
        });
        return recipes;
    }

    public List<NMIRecipe> getRecipes(TagKey<Block> kitchenware) {
        ArrayList<NMIRecipe> recipes = new ArrayList<>();
        accessor.getRecipeMap().getRecipeMap().values().forEach(recipe -> {
            if (recipe.recipe().kitchenware().equals(kitchenware)) {
                recipes.add(recipe.recipe());
            }
        });

        return recipes;
    }

    public static NMIRecipeHolder getRecipe(NMIDataAccessor accessor,Identifier id) {
        return accessor.getRecipeMap().getRecipeMap().get(id);
    }

}
