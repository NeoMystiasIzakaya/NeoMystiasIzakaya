package icu.gensoukyo.neo_mystias_izakaya.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIItemUtil;
import lombok.Getter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.*;

@Getter
public class NMIRecipeMap {

    private final List<NMIRecipeHolder> recipes;
    private final Map<Identifier, NMIRecipeHolder> recipeMap;
    private final Map<Identifier, List<Identifier>> inputItemToRecipeMap;
    private final Map<Identifier, List<Identifier>> outputItemToRecipeMap;
    private final Map<TagKey<Block>, List<Identifier>> kitchenwareToRecipeMap;

    public static final Codec<NMIRecipeMap> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    NMIRecipeHolder.CODEC.listOf().fieldOf("recipes").forGetter(NMIRecipeMap::getRecipes)
            ).apply(instance, NMIRecipeMap::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIRecipeMap> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<RegistryFriendlyByteBuf, NMIRecipeHolder>list().apply(NMIRecipeHolder.STREAM_CODEC), NMIRecipeMap::getRecipes,
            NMIRecipeMap::new
    );

    private NMIRecipeMap(List<NMIRecipeHolder> recipes) {
        this.recipes = recipes;
        this.recipeMap = buildRecipeMap(recipes);
        this.inputItemToRecipeMap = buildInputItemToRecipeMap(recipes);
        this.outputItemToRecipeMap = buildOutputItemToRecipeMap(recipes);
        this.kitchenwareToRecipeMap = buildKitchenwareToRecipeMap(recipes);
    }

    private Map<Identifier, NMIRecipeHolder> buildRecipeMap(List<NMIRecipeHolder> recipes) {
        return recipes.stream().collect(java.util.stream.Collectors.toMap(NMIRecipeHolder::key, holder -> holder));
    }

    private Map<Identifier, List<Identifier>> buildInputItemToRecipeMap(List<NMIRecipeHolder> recipes) {
        Map<Identifier, Set<Identifier>> map = new HashMap<>();
        recipes.forEach(
                e -> {
                    e.recipe().input().forEach(
                            i -> i.getValues().forEach(
                                    s -> {
                                        addToMap(map, NMIItemUtil.get(s.value()), e.key());
                                    }
                            )
                    );
                }
        );
        return normalizeMap(map);
    }

    private Map<Identifier, List<Identifier>> buildOutputItemToRecipeMap(List<NMIRecipeHolder> recipes) {
        Map<Identifier, Set<Identifier>> map = new HashMap<>();
        recipes.forEach(
                e -> {
                    addToMap(map, NMIItemUtil.get(e.recipe().output().item().value()), e.key());
                }
        );

        return normalizeMap(map);
    }

    private Map<TagKey<Block>, List<Identifier>> buildKitchenwareToRecipeMap(List<NMIRecipeHolder> recipes) {
        Map<TagKey<Block>, Set<Identifier>> map = new HashMap<>();
        recipes.forEach(
                e -> {
                    TagKey<Block> kitchenware = e.recipe().kitchenware();
                    Set<Identifier> set = map.getOrDefault(kitchenware, new HashSet<>());
                    set.add(e.key());
                    map.put(kitchenware, set);
                }
        );
        Map<TagKey<Block>, List<Identifier>> normalizedMap = new HashMap<>();
        map.forEach((key, valueSet) -> normalizedMap.put(key, new ArrayList<>(valueSet)));
        return normalizedMap;
    }

    private Map<Identifier, List<Identifier>> normalizeMap(Map<Identifier, Set<Identifier>> map) {
        Map<Identifier, List<Identifier>> normalizedMap = new HashMap<>();
        map.forEach((key, valueSet) -> normalizedMap.put(key, new ArrayList<>(valueSet)));
        return normalizedMap;
    }


    private void addToMap(Map<Identifier, Set<Identifier>> inputItemToRecipeMap, Identifier itemId, Identifier recipeId) {
        Set<Identifier> set = inputItemToRecipeMap.getOrDefault(itemId, new HashSet<>());
        set.add(recipeId);
        inputItemToRecipeMap.put(itemId, set);
    }

    public static NMIRecipeMap create(List<NMIRecipeHolder> recipes) {
        return new NMIRecipeMap(recipes);
    }

    public static final NMIRecipeMap EMPTY = new NMIRecipeMap(List.of());
}
