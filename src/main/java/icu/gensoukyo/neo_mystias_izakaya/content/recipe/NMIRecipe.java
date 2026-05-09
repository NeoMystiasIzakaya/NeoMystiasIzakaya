/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record NMIRecipe(List<Ingredient> input, ItemStackTemplate output, TagKey<Block> kitchenware, int time) {

    public static final Codec<NMIRecipe> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("input").forGetter(NMIRecipe::input),
                    ItemStackTemplate.CODEC.fieldOf("output").forGetter(NMIRecipe::output),
                    TagKey.codec(Registries.BLOCK).fieldOf("kitchenware").forGetter(NMIRecipe::kitchenware),
                    Codec.INT.fieldOf("time").forGetter(NMIRecipe::time)
            ).apply(instance, NMIRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<RegistryFriendlyByteBuf, Ingredient>list().apply(Ingredient.CONTENTS_STREAM_CODEC), NMIRecipe::input,
            ItemStackTemplate.STREAM_CODEC, NMIRecipe::output,
            TagKey.streamCodec(Registries.BLOCK), NMIRecipe::kitchenware,
            ByteBufCodecs.INT, NMIRecipe::time,
            NMIRecipe::new
    );
}
