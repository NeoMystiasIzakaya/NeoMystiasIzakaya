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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class KitchenwareScreen extends AbstractContainerScreen<KitchenwareMenu> {
    private static final Identifier BACKGROUND = id("textures/gui/kitchenware_bg.png");
    final int YELLOW = Color.YELLOW.getRGB();
    final int BLACK = Color.BLACK.getRGB();
    final int GREEN = Color.GREEN.getRGB();
    final int RED = Color.RED.getRGB();
    final int MIN_X = 120;
    final int MIN_Y = 10;
    final int MAX_X = 215;
    final int MAX_Y = 90;
    private final AbstractKitchenwareBE kitchenwareBE;
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
        boolean isLit = kitchenwareBE.getBlockState().getValue(BlockStateProperties.LIT);
        if (isLit) {
            renderProgress(graphics, mouseX, mouseY);
            return;
        }
        if (kitchenwareBE.getResultItem().isEmpty()) {
            this.renderRecipes(graphics, mouseX, mouseY);
        } else {
            this.renderResult(graphics, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int hoveredRecipeIndex = getHoveredRecipeIndex((int) event.x(), (int) event.y());
        if (hoveredRecipeIndex >= 0 && hoveredRecipeIndex < possibleRecipes.size() && kitchenwareBE.canStartCooking()) {
            NMIRecipe recipe = this.possibleRecipes.get(hoveredRecipeIndex).recipe();
            ClientPayloadSender.sendKitchenwareCookMessage(new NMIKitchenwareCookMessage(recipe, recipe.time(), kitchenwareBE.getBlockPos()));
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

    protected void renderRecipes(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
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
            renderCuisineInfo(guiGraphics, recipeHolder, i, j);
        }
    }

    protected void renderResult(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {

    }

    protected void renderProgress(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        float cookTime = this.menu.getData().get(0);
        float totalTime = this.menu.getData().get(1);
        float process = (totalTime - cookTime) / 20;
        if (cookTime > 0) {
            guiGraphics.text(font, Component.translatable("gui.neo_mystias_izakaya.progress").append(Component.literal(" : " + String.format("%.2f / %.2f s", process, (totalTime / 20)))), i + 120, j + 10, BLACK, false);
            guiGraphics.text(font, Component.literal(String.format("%.2f", (1 - cookTime / totalTime) * 100) +" %"), i + 120, j + 25, BLACK, false);
        }
    }

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

    private void renderCuisineInfo(GuiGraphicsExtractor guiGraphics, NMIRecipeHolder recipeHolder, int i, int j) {
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
            TagRenderState state = new TagRenderState(0, j + 40);
            state = renderTags(itemTagList.positiveTags(), GREEN, state, guiGraphics, i + 15);
            state = renderTags(itemTagList.negativeTags(), RED, state, guiGraphics, i + 15);
        }
    }

    private TagRenderState renderTags(List<Identifier> tags, int color, TagRenderState state, GuiGraphicsExtractor guiGraphics, int baseX) {
        int currentX = state.currentX();
        int startY = state.startY();
        int lineHeight = font.lineHeight;
        int maxWidth = 100;

        for (Identifier identifier : tags) {
            MutableComponent tag = Component.translatable(identifier.toLanguageKey("tag"));
            FormattedCharSequence visualOrderText = tag.getVisualOrderText();
            int fontWidth = font.width(visualOrderText);
            if (currentX + fontWidth > maxWidth) {
                currentX = 0;
                startY += lineHeight;
            }
            guiGraphics.text(font, visualOrderText, baseX + currentX, startY, color, false);
            currentX += (fontWidth + 4);
        }
        return new TagRenderState(currentX, startY);
    }

    private record TagRenderState(int currentX, int startY) {
    }
}
