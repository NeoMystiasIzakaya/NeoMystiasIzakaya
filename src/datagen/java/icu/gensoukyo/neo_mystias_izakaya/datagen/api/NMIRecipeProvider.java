package icu.gensoukyo.neo_mystias_izakaya.datagen.api;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class NMIRecipeProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final Map<Identifier, NMIRecipe> recipeMap = new TreeMap<>();

    protected abstract void addRecipes();

    protected NMIRecipeProvider(PackOutput output, String modid) {
        this.modid = modid;
        this.output = output;
    }

    protected void addRecipe(String key, NMIRecipe recipe) {
        this.recipeMap.put(Identifier.fromNamespaceAndPath(modid, key), recipe);
    }

    protected void addRecipe(Identifier key, NMIRecipe recipe) {
        this.recipeMap.put(key, recipe);
    }

    protected Builder builder(ItemLike item) {
        return new Builder(Identifier.fromNamespaceAndPath(modid, BuiltInRegistries.ITEM.getKey(item.asItem()).getPath()), recipeMap).output(new ItemStackTemplate(item.asItem()));
    }

    protected Builder builder(String key) {
        return new Builder(Identifier.fromNamespaceAndPath(modid, key), recipeMap);
    }

    protected Builder builder(Identifier key) {
        return new Builder(key, recipeMap);
    }


    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addRecipes();
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
        private List<Ingredient> input = new ArrayList<>();
        private ItemStackTemplate output;
        private TagKey<Block> kitchenware;
        private int time;

        protected Builder(Identifier key, Map<Identifier, NMIRecipe> recipeMap) {
            this.key = key;
            this.recipeMap = recipeMap;
        }

        private void fillNull() {
            if (this.input == null) {
                this.input = new ArrayList<>();
            }
        }

        public Builder input(ItemLike... items) {
            this.fillNull();
            for (ItemLike item : items) {
                this.input.add(Ingredient.of(item));
            }
            return this;
        }

        public Builder input(List<Ingredient> ingredients) {
            this.fillNull();
            this.input.addAll(ingredients);
            return this;
        }

        public Builder output(ItemStackTemplate output) {
            this.output = output;
            return this;
        }

        public Builder output(ItemLike output) {
            this.output = new ItemStackTemplate(output.asItem());
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
            if (this.input.isEmpty()) {
                throw new RuntimeException("The input in the recipe cannot be empty. by key: %s".formatted(this.key));
            }
            if (this.output == null) {
                throw new RuntimeException("The output in the recipe cannot be null. by key: %s".formatted(this.key));
            }
            if (this.kitchenware == null) {
                throw new RuntimeException("The kitchenware in the recipe cannot be null. by key: %s".formatted(this.key));
            }
            NMIRecipe recipe = new NMIRecipe(input, output, kitchenware, time);
            recipeMap.put(key, recipe);
        }

    }
}
