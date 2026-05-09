package icu.gensoukyo.neo_mystias_izakaya.compat.recipe;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class BoilingPotRecipe extends AbstractNMIRecipe {
    public static final IRecipeType<NMIRecipe> BOILING_POT = IRecipeType.create(NeoMystiasIzakaya.MODID, "boiling_pot", NMIRecipe.class);
    public BoilingPotRecipe(IGuiHelper guiHelper) {
        super(guiHelper, guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, NMIMainItems.BOILING_POT.toStack()));
    }

    @Override
    public IRecipeType<NMIRecipe> getRecipeType() {
        return BOILING_POT;
    }
}
