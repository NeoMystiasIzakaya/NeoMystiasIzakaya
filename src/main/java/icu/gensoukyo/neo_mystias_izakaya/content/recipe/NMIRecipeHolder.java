/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record NMIRecipeHolder(Identifier key, NMIRecipe recipe) {

    public static final Codec<NMIRecipeHolder> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(NMIRecipeHolder::key),
                    NMIRecipe.CODEC.fieldOf("recipe").forGetter(NMIRecipeHolder::recipe)
            ).apply(instance, NMIRecipeHolder::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIRecipeHolder> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, NMIRecipeHolder::key,
            NMIRecipe.STREAM_CODEC, NMIRecipeHolder::recipe,
            NMIRecipeHolder::new
    );
     @Override
     public String toString() {
          return "NMIRecipeHolder{" +
                  "item=" + key +
                  ", recipe=" + recipe +
                  '}';
     }
}
