package icu.gensoukyo.neo_mystias_izakaya.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

@JeiPlugin
public class MystiasPlugin implements IModPlugin {
    private static final Identifier IDENTIFIER = id("jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

    }


    @Override
    public Identifier getPluginUid() {
        return IDENTIFIER;
    }
}
