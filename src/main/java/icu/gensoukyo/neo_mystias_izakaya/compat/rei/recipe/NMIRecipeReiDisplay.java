/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.rei.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.Kitchenware;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import lombok.Getter;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

@Getter
public class NMIRecipeReiDisplay extends BasicDisplay {

    public static final MapCodec<NMIRecipeReiDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(ins -> ins.group(
            NMIRecipe.CODEC.fieldOf("recipe").forGetter(o -> o.recipe),
            Identifier.CODEC.fieldOf("location").forGetter(o -> o.location)
    ).apply(ins, NMIRecipeReiDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIRecipeReiDisplay> STREAM_CODEC = StreamCodec.composite(
            NMIRecipe.STREAM_CODEC, NMIRecipeReiDisplay::getRecipe,
            Identifier.STREAM_CODEC, NMIRecipeReiDisplay::getLocation,
            NMIRecipeReiDisplay::new
    );

    private final CategoryIdentifier<?> category;

    private final Identifier location;
    private final NMIRecipe recipe;

    public NMIRecipeReiDisplay(NMIRecipe recipe, Identifier identifier){
        super(EntryIngredients.ofIngredients(recipe.input()),
                Collections.singletonList(EntryIngredients.of(recipe.output().create())));
        this.recipe = recipe;
        this.location = identifier;
        this.category =  CategoryIdentifier.of(identifier);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return category;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return DisplaySerializer.of(MAP_CODEC,STREAM_CODEC);
    }

    public static class BoilingPot extends NMIRecipeReiDisplay {
        public BoilingPot(NMIRecipe recipe) {
            super(recipe, Kitchenware.BOILING_POT_ID);
        }
    }
    public static class CuttingBoard extends NMIRecipeReiDisplay {
        public CuttingBoard(NMIRecipe recipe) {
            super(recipe, Kitchenware.CUTTING_BOARD_ID);
        }
    }
    public static class FryingPan extends NMIRecipeReiDisplay {
        public FryingPan(NMIRecipe recipe) {
            super(recipe, Kitchenware.FRYING_PAN_ID);
        }
    }
    public static class Steamer extends NMIRecipeReiDisplay {
        public Steamer(NMIRecipe recipe) {
            super(recipe, Kitchenware.STEAMER_ID);
        }
    }
    public static class Grill extends NMIRecipeReiDisplay {
        public Grill(NMIRecipe recipe) {
            super(recipe, Kitchenware.GRILL_ID);
        }
    }
}