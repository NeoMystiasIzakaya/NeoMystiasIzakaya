package icu.gensoukyo.neo_mystias_izakaya.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public record NMIRecipe(List<Ingredient> input, ItemStack output, Ingredient kitchenware, int time) {

    public static final Codec<NMIRecipe> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("input").forGetter(NMIRecipe::input),
                    ItemStack.CODEC.fieldOf("output").forGetter(NMIRecipe::output),
                    Ingredient.CODEC.fieldOf("kitchenware").forGetter(NMIRecipe::kitchenware),
                    Codec.INT.fieldOf("time").forGetter(NMIRecipe::time)
            ).apply(instance, NMIRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<RegistryFriendlyByteBuf, Ingredient>list().apply(Ingredient.CONTENTS_STREAM_CODEC), NMIRecipe::input,
            ItemStack.STREAM_CODEC, NMIRecipe::output,
            Ingredient.CONTENTS_STREAM_CODEC, NMIRecipe::kitchenware,
            ByteBufCodecs.INT, NMIRecipe::time,
            NMIRecipe::new
    );
}
