package icu.gensoukyo.neo_mystias_izakaya.datagen.api;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemList;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class NMIRecipeProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final Map<Identifier, NMIRecipe> recipeMap = new TreeMap<>();

    protected abstract void addRecipes();

    protected NMIRecipeProvider(String modid, PackOutput output) {
        this.modid = modid;
        this.output = output;
    }

    protected void addRecipe(String key, NMIRecipe recipe) {
        this.recipeMap.put(Identifier.fromNamespaceAndPath(modid, key), recipe);
    }

    protected void addRecipe(Identifier key, NMIRecipe recipe) {
        this.recipeMap.put(key, recipe);
    }

    protected Builder builder(String key) {
        return new Builder(Identifier.fromNamespaceAndPath(modid, key), recipeMap);
    }

    protected Builder builder(Identifier key) {
        return new Builder(key, recipeMap);
    }


    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("recipe"));
        return DataProvider.saveAll(cachedOutput, NMIRecipe.CODEC, provider, recipeMap);
    }

    @Override
    public String getName() {
        return "neo_mystias_izakaya:recipe:" + modid;
    }

    public static class Builder {
        private final Identifier key;
        private final Map<Identifier, NMIRecipe> recipeMap;
        private List<Ingredient> input;
        private ItemStackTemplate output;
        private TagKey<Block> kitchenware;
        private int time;

        protected Builder(Identifier key, Map<Identifier, NMIRecipe> recipeMap) {
            this.key = key;
            this.recipeMap = recipeMap;
        }

        public Builder input(List<Ingredient> input) {
            this.input = input;
            return this;
        }

        public Builder output(ItemStackTemplate output) {
            this.output = output;
            return this;
        }

        public Builder kitchenware(TagKey<Block> kitchenware) {
            this.kitchenware = kitchenware;
            return this;
        }

        public Builder time(int time) {
            this.time = time;
            return this;
        }

        public void build() {
            NMIRecipe recipe = new NMIRecipe(input, output, kitchenware, time);
            recipeMap.put(key, recipe);
        }

    }
}
