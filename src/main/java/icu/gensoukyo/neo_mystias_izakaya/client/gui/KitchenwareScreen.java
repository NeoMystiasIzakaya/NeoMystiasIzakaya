package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.common.network.NMIKitchenwareCookMessage;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class KitchenwareScreen extends AbstractContainerScreen<KitchenwareMenu> {
    private static final Identifier BACKGROUND = id("textures/gui/kitchenware_bg.png");
    private final AbstractKitchenwareBE kitchenwareBE;
    final int YELLOW = Color.YELLOW.getRGB();
    final int BLACK = Color.BLACK.getRGB();
    final int GREEN = Color.GREEN.getRGB();
    final int RED = Color.RED.getRGB();
    final int MIN_X = 120;
    final int MIN_Y = 10;
    final int MAX_X = 215;
    final int MAX_Y = 90;
    List<NMIRecipeHolder> possibleRecipes;

    public KitchenwareScreen(KitchenwareMenu menu, Inventory inv, Component title) {
        super(menu, inv, title, 230, 219);
        Map<Identifier, NMIRecipeHolder> recipeMap = ClientNMIDataAccessor.INSTANCE.getRecipeMap().getRecipeMap();
        this.kitchenwareBE = this.getMenu().getKitchenwareBE();
        this.possibleRecipes = NMIClientRecipeUtil.getRecipesByInputAndKitchenware(List.copyOf(kitchenwareBE.getItems()), kitchenwareBE.getKitchenwareType().KITCHENWARE_TYPE);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.renderRecipes(graphics, mouseX, mouseY, a);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int hoveredRecipeIndex = getHoveredRecipeIndex((int) event.x(), (int) event.y());
        if (hoveredRecipeIndex >= 0 && hoveredRecipeIndex < possibleRecipes.size() && kitchenwareBE.canStartCooking()) {
            NMIRecipe recipe = this.possibleRecipes.get(hoveredRecipeIndex).recipe();
            ClientPayloadSender.sendKitchenwareCookMessage(new NMIKitchenwareCookMessage(recipe.output().create(), recipe.time(), kitchenwareBE.getBlockPos()));
        }
        return super.mouseClicked(event, doubleClick);
    }

    private int getHoveredRecipeIndex(int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (pMouseX > (i + MIN_X) && pMouseX < (i + MAX_X) && pMouseY > (j + MIN_Y) && pMouseY < (j + MAX_Y)) {
            int dx = pMouseX - i;
            int dy = pMouseY - j;
            int x = dx - (dx) % 20 + i;
            int y = dy - (dy - 11) % 20 + j;
            return (x - i - 120) / 20 + (y - j - 10) / 20 * 5;
        }
        return -1;
    }

    private void renderHighlight(GuiGraphicsExtractor guiGraphics, int index, int i, int j) {
        if (index >= 0) {
            int x = i + 120 + index % 5 * 20;
            int y = j + 11 + index / 5 * 20;
            guiGraphics.fill(x, y, x + 20, y + 2, YELLOW);
            guiGraphics.fill(x, y, x + 2, y + 20, YELLOW);
            guiGraphics.fill(x + 18, y, x + 20, y + 20, YELLOW);
            guiGraphics.fill(x, y + 18, x + 20, y + 20, YELLOW);
        }
    }

    protected void renderRecipes(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float a) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        int hoveredIndex = getHoveredRecipeIndex(pMouseX, pMouseY);
        renderHighlight(guiGraphics, hoveredIndex, i, j);

        for (int k = 0; k < possibleRecipes.size(); k++) {
            guiGraphics.item(possibleRecipes.get(k).recipe().output().create(), i + 122 + k % 5 * 20, j + 13 + k / 5 * 20);
        }

        int hoveredRecipeIndex = getHoveredRecipeIndex(pMouseX, pMouseY);
        if (hoveredRecipeIndex >= 0 && hoveredRecipeIndex < possibleRecipes.size()) {
            NMIRecipeHolder recipeHolder = possibleRecipes.get(hoveredRecipeIndex);
            NMIRecipe recipe = recipeHolder.recipe();
            Item cuisineItem = recipe.output().item().value();
            guiGraphics.text(font, Component.translatable(cuisineItem.getDescriptionId()), i + 15, j + 10, BLACK, false);
            guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.time").append(": " + recipe.time()), i + 15, j + 20, BLACK, false);

            FormattedCharSequence description = Component.translatable(cuisineItem.getDescriptionId() + ".desc").getVisualOrderText();
            StringBuilder builder = new StringBuilder();
            description.accept((index, style, charPoint) -> {
                builder.append(Character.toChars(charPoint));
                return true;
            });
            String substring = builder.subSequence(0, 10).toString();

            guiGraphics.text(font, Component.literal(substring + "..."), i + 15, j + 30, BLACK, false);
            ItemTagList itemTagList = ClientNMIDataAccessor.INSTANCE.getTagItemListMap().getItemToTagMap().get(recipeHolder.key());
            if (itemTagList != null) {
                int currentX = 0;
                int lineHeight = font.lineHeight;
                int startY = j + 40;
                int maxWidth = 100;
                for (int i1 = 0; i1 < itemTagList.positiveTags().size(); i1++) {
                    Identifier identifier = itemTagList.positiveTags().get(i1);
                    MutableComponent tag = Component.translatable(identifier.toLanguageKey("tag"));
                    FormattedCharSequence visualOrderText = tag.getVisualOrderText();
                    int fontWidth = font.width(visualOrderText);
                    if (currentX + fontWidth > maxWidth) {
                        currentX = 0;
                        startY += lineHeight;
                    }
                    guiGraphics.text(font, visualOrderText, i + 15 + currentX, startY, BLACK, false);
                    currentX += (fontWidth + 4);
                }
            }
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
