package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class KitchenwareScreen extends AbstractContainerScreen<KitchenwareMenu> {
    private static final Identifier BACKGROUND = id("textures/gui/kitchenware_bg.png");
    final int YELLOW = Color.YELLOW.getRGB();
    final int BLACK = Color.BLACK.getRGB();
    final int MIN_X = 120;
    final int MIN_Y = 10;
    final int MAX_X = 215;
    final int MAX_Y = 90;
    List<NMIRecipeHolder> possibleRecipes;

    public KitchenwareScreen(KitchenwareMenu menu, Inventory inv, Component title) {
        super(menu, inv, title, 230, 219);
        Map<Identifier, NMIRecipeHolder> recipeMap = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipeMap();
        AbstractKitchenwareBE kitchenwareBE = this.getMenu().getKitchenwareBE();
        this.possibleRecipes = NMIClientRecipeUtil.getRecipesByInputAndKitchenware(List.copyOf(kitchenwareBE.getItems()), kitchenwareBE.getKitchenwareType().KITCHENWARE_TYPE);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.renderRecipes(graphics, mouseX, mouseY, a);
    }

    protected void renderRecipes(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float a) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int index = 0;

        if (pMouseX > (i + MIN_X) && pMouseX < (i + MAX_X) && pMouseY > (j + MIN_Y) && pMouseY < (j + MAX_Y)) {
            int dx = pMouseX - i;
            int dy = pMouseY - j;
            int x = dx - (dx) % 20 + i;
            int y = dy - (dy - 11) % 20 + j;
            guiGraphics.fill(x, y, x + 20, y + 2, YELLOW);
            guiGraphics.fill(x, y, x + 2, y + 20, YELLOW);
            guiGraphics.fill(x + 18, y, x + 20, y + 20, YELLOW);
            guiGraphics.fill(x, y + 18, x + 20, y + 20, YELLOW);
            index = (x - i - 120) / 20 + (y - j - 10) / 20 * 5;
        }

        for (int k = 0; k < possibleRecipes.size(); k++) {
            guiGraphics.item(possibleRecipes.get(k).recipe().output().create(), i + 122 + k % 5 * 20, j + 13 + k / 5 * 20);
        }
    }

//    protected void renderMealItem(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, int i, int j) {
//        if (this.menu instanceof AbstractCookMenu cookMenu) {
//            boolean isMenuItemEqual = menuItems.equals(menu.getIngredientList());
//            ItemStack target = cookMenu.cookerTE.getItem(6);
//            if (target.isEmpty()) {
//                int index = 0;
//                //渲染选中方框
//                if (pMouseX > (i + minX) && pMouseX < (i + maxX) && pMouseY > (j + minY) && pMouseY < (j + maxY)) {
//                    int dx = pMouseX - i;
//                    int dy = pMouseY - j;
//                    int x = dx - (dx) % 20 + i;
//                    int y = dy - (dy - 11) % 20 + j;
//                    guiGraphics.fill(x, y, x + 20, y + 2, YELLOW);
//                    guiGraphics.fill(x, y, x + 2, y + 20, YELLOW);
//                    guiGraphics.fill(x + 18, y, x + 20, y + 20, YELLOW);
//                    guiGraphics.fill(x, y + 18, x + 20, y + 20, YELLOW);
//                    index = (x - i - 120) / 20 + (y - j - 10) / 20 * 5;
//                }
//                //渲染可制作菜肴列表
//                if (!isMenuItemEqual) {
//                    possibleMeals = UtilMethod.getItems(menu.getItems(), MealList.getInstance().getMealList(), menu.cookerType);
//                }
//                menuItems = new ArrayList<>(menu.getIngredientList());
//                if (!possibleMeals.isEmpty()) {
//                    for (int k = 0; k < possibleMeals.size(); k++) {
//                        guiGraphics.renderItem(possibleMeals.get(k).result, i + 122 + k % 5 * 20, j + 13 + k / 5 * 20);
//                    }
//                    if (index < possibleMeals.size()) {
//                        targetItemNew = possibleMeals.get(index);
//                    }
//                } else {
//                    targetItemNew = MealRecipe.EMPTY;
//                }
//            } else {
//                targetItemNew = MealList.getInstance().getRecipeMap().get(target.getItem());
//            }
//            if (targetItemNew.result.getItem() instanceof CookedMealItem cookedMealItem) {
//                if (!targetItemNew.equals(targetItemOld) || !isMenuItemEqual) {
//                    negativeStrings = cookedMealItem.negativeTag.stream()
//                            .map(foodTagEnum -> Component.translatable("mystia_izakaya." + foodTagEnum.name()).getString())
//                            .collect(Collectors.toCollection(ArrayList::new));
//                    positiveStings = UtilMethod.getPositiveStings(cookMenu, targetItemNew);
//                }
//                targetItemOld = targetItemNew;
//
//                guiGraphics.drawString(font, Component.translatable(cookedMealItem.getDescriptionId()), i + 15, j + 10, BLACK, false);
//                guiGraphics.drawString(font, Component.translatable("gui.mystia_izakaya.level").append(": " + cookedMealItem.level), i + 15, j + 20, BLACK, false);
//                guiGraphics.drawString(font, Component.translatable("gui.mystia_izakaya.cooking_time").append(": " + cookedMealItem.cookingTime), i + 15, j + 30, BLACK, false);
//                guiGraphics.drawString(font, Component.translatable("gui.mystia_izakaya.tags").append(":"), i + 15, j + 40, BLACK, false);
//
//                int stringLength = 0;
//                int stringHeight = 0;
//                for (int k = 0; k < positiveStings.size(); k++) {
//                    //guiGraphics.fill(i + 15 + stringLength * 10 - 2, j + 50 + stringHeight * 10 - 1,i + 15 + stringLength * 10 + positiveStings.get(k).length() * 9 + 1, j + 50 + stringHeight * 10 + 9,positiveOutColor);
//                    //guiGraphics.fill(i + 15 + stringLength * 10 - 1, j + 50 + stringHeight * 10,i + 15 + stringLength * 10 + positiveStings.get(k).length() * 9, j + 50 + stringHeight * 10 + 8,positiveInColor);
//                    guiGraphics.drawString(font, positiveStings.get(k), i + 15 + stringLength * 10, j + 50 + stringHeight * 10, positiveOutColor, false);
//                    stringLength += positiveStings.get(k).length();
//                    if (positiveStings.size() > k + 1) {
//                        if (stringLength + positiveStings.get(k + 1).length() > 10) {
//                            stringLength = 0;
//                            stringHeight++;
//                        }
//                    }
//                }
//                if (!negativeStrings.isEmpty()) {
//                    if (stringLength + negativeStrings.getFirst().length() > 10) {
//                        stringLength = 0;
//                        stringHeight++;
//                    }
//                    for (int k = 0; k < negativeStrings.size(); k++) {
//                        guiGraphics.drawString(font, negativeStrings.get(k), i + 15 + stringLength * 10, j + 50 + stringHeight * 10, Color.RED.getRGB(), false);
//                        stringLength += negativeStrings.get(k).length();
//                        if (negativeStrings.size() > k + 1) {
//                            if (stringLength + negativeStrings.get(k + 1).length() > 10) {
//                                stringLength = 0;
//                                stringHeight++;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void updateRecipes() {
        AbstractKitchenwareBE kitchenwareBE = this.menu.getKitchenwareBE();
        this.possibleRecipes = NMIClientRecipeUtil.getRecipesByInputAndKitchenware(List.copyOf(kitchenwareBE.getItems()), kitchenwareBE.getKitchenwareType().KITCHENWARE_TYPE);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0.0F, 0.0F, 256, 256, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    }
}
