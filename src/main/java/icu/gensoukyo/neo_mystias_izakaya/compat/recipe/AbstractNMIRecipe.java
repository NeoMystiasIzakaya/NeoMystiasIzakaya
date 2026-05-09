package icu.gensoukyo.neo_mystias_izakaya.compat.recipe;

import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.List;

public abstract class AbstractNMIRecipe implements IRecipeCategory<NMIRecipe> {
    private final IDrawable icon;
    private final IDrawable slotDraw;
    private final Component localizedName;

    public AbstractNMIRecipe(IGuiHelper guiHelper, IDrawable icon) {
        this.slotDraw = guiHelper.getSlotDrawable();
        this.icon = icon;
        this.localizedName = Component.translatable("neo_mystias_izakaya");
    }

    @Override
    public int getWidth() {
        return 160;
    }

    @Override
    public int getHeight() {
        return 48;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NMIRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> input = recipe.input();
        ItemStackTemplate output = recipe.output();
        for (int i = 0; i < input.size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, i * 24 + 4, 5).addItemStacks(getItemList(input.get(i))).setBackground(slotDraw, -1, -1);
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 140, 5).add(output.create()).setBackground(slotDraw, -1, -1);
    }

    @Override
    public void draw(NMIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor graphics, double mouseX, double mouseY) {
        TagKey<Block> kitchenware = recipe.kitchenware();
        ItemStack defaultInstance = BuiltInRegistries.BLOCK.getTagOrEmpty(kitchenware).iterator().next().value().asItem().getDefaultInstance();
        Font font = Minecraft.getInstance().font;
        float time = recipe.time();
        int darkGray = 0x555555;
        graphics.text(font, String.format("%.1f min", time), 16, 25, darkGray, false);
        graphics.item(defaultInstance, 64, 25);
    }

    private List<ItemStack> getItemList(Ingredient ingredient) {
        return ingredient.items().map(itemHolder -> itemHolder.value().getDefaultInstance()).toList();
    }
}
