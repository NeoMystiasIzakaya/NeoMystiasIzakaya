package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class CuisineListWidget extends ObjectSelectionList<CuisineListWidget.CuisineEntry>{
    private final RecipeScreen parent;
    private final int listWidth;
    public CuisineListWidget(RecipeScreen parent,Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
        this.parent = parent;
        this.listWidth = width;
    }

    @Override
    public int getRowWidth() {
        return this.listWidth - 12;
    }

    @Override
    protected int scrollBarX() {
        return (super.scrollBarX() - 8);
    }

    @Override
    protected void extractListBackground(GuiGraphicsExtractor graphics) {

    }

    @Override
    protected void extractListSeparators(GuiGraphicsExtractor graphics) {

    }

    public void refreshList() {
        this.clearEntries();
        parent.buildImageList(this::addEntry, cuisine -> new CuisineEntry(cuisine, this.parent));
    }

    public static class CuisineEntry extends ObjectSelectionList.Entry<CuisineListWidget.CuisineEntry> {
        @Getter
        private final NMIRecipeHolder cuisine;
        private final RecipeScreen parent;
        public CuisineEntry(NMIRecipeHolder cuisine, RecipeScreen parent) {
            this.cuisine = cuisine;
            this.parent = parent;
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            this.parent.setSelected(this);
            return super.mouseClicked(event, doubleClick);
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            Font font = Minecraft.getInstance().font;
            Item value = cuisine.recipe().output().item().value();
            graphics.text(font, Component.translatable(value.getDescriptionId()), getX() + 18, getY() + 4, 0xFFFFFFFF);
            graphics.item(value.getDefaultInstance(), getX(), getY());
        }
    }
}
