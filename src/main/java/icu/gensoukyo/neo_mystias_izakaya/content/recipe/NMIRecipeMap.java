package icu.gensoukyo.neo_mystias_izakaya.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.util.NMIItemUtil;
import lombok.Getter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Getter
public class NMIRecipeMap {

    private final List<NMIRecipeHolder> recipes;
    private final Map<Identifier, NMIRecipeHolder> recipeMap;
    private final Map<Identifier, List<Identifier>> inputItemToRecipeMap;
    private final Map<Identifier, List<Identifier>> outputItemToRecipeMap;
    private final Map<Identifier, List<Identifier>> kitchenwareToRecipeMap;

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
        Map<Identifier, List<Identifier>> map = new TreeMap<>();
        recipes.forEach(
                e->{
                    e.recipe().input().forEach(
                            i->i.getValues().forEach(
                                    s->{
                                        addToMap(map, NMIItemUtil.get(s.value()), e.key());
                                    }
                            )
                    );
                }
        );
        return map;
    }

    private Map<Identifier, List<Identifier>> buildOutputItemToRecipeMap(List<NMIRecipeHolder> recipes) {
        Map<Identifier, List<Identifier>> map = new TreeMap<>();
        recipes.forEach(
                e->{
                    addToMap(map, NMIItemUtil.get(e.recipe().output().getItem()), e.key());
                }
        );
        return map;
    }

    private Map<Identifier, List<Identifier>> buildKitchenwareToRecipeMap(List<NMIRecipeHolder> recipes) {
        Map<Identifier, List<Identifier>> map = new TreeMap<>();
        recipes.forEach(
                e->{
                    e.recipe().kitchenware().getValues().forEach(
                            s->addToMap(map, NMIItemUtil.get(s.value()), e.key())
                    );
                }
        );
        return map;
    }



    private void addToMap(Map<Identifier, List<Identifier>> inputItemToRecipeMap, Identifier itemId, Identifier recipeId) {
        List<Identifier> list = inputItemToRecipeMap.getOrDefault(itemId, new ArrayList<>());
        list.add(recipeId);
        inputItemToRecipeMap.put(itemId, list);
    }

    public static NMIRecipeMap create(List<NMIRecipeHolder> recipes) {
        return new NMIRecipeMap(recipes);
    }

    public static final NMIRecipeMap EMPTY = new NMIRecipeMap(List.of());
}
