package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.RecipeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CuisineListWidget extends ObjectSelectionList<CuisineListWidget.CuisineEntry>{
    private final RecipeScreen parent;
    public CuisineListWidget(RecipeScreen parent,Minecraft minecraft, int width, int height, int y, int itemHeight) {
        super(minecraft, width, height, y, itemHeight);
        this.parent = parent;
    }

    public void refreshList() {
        this.clearEntries();
    }


    public class CuisineEntry extends ObjectSelectionList.Entry<CuisineListWidget.CuisineEntry> {
        private final ItemStack cuisine;
        public CuisineEntry(ItemStack cuisine) {
            this.cuisine = cuisine;
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            return super.mouseClicked(event, doubleClick);
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void extractContent(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1, boolean b, float v) {
            Font font = Minecraft.getInstance().font;
        }
    }
}
