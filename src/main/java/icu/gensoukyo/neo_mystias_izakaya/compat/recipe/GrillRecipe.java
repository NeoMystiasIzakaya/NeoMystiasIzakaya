package icu.gensoukyo.neo_mystias_izakaya.compat.recipe;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class GrillRecipe extends AbstractNMIRecipe {
    public static final IRecipeType<NMIRecipe> GRILL = IRecipeType.create(NeoMystiasIzakaya.MODID, "grill", NMIRecipe.class);

    public GrillRecipe(IGuiHelper guiHelper) {
        super(guiHelper, guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, NMIMainItems.GRILL.toStack()));
    }

    @Override
    public IRecipeType<NMIRecipe> getRecipeType() {
        return GRILL;
    }
}
