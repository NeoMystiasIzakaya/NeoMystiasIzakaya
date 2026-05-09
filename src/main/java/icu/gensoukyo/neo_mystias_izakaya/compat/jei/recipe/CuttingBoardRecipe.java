package icu.gensoukyo.neo_mystias_izakaya.compat.jei.recipe;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;

public class CuttingBoardRecipe extends AbstractNMIRecipe {
    public static final IRecipeType<NMIRecipe> CUTTING_BOARD = IRecipeType.create(NeoMystiasIzakaya.MODID, "cutting_board", NMIRecipe.class);

    public CuttingBoardRecipe(IGuiHelper guiHelper) {
        super(guiHelper, guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, NMIMainItems.CUTTING_BOARD.toStack()));
    }

    @Override
    public IRecipeType<NMIRecipe> getRecipeType() {
        return CUTTING_BOARD;
    }
}
