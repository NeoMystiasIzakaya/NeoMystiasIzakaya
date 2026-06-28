/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import icu.gensoukyo.kaguya.client.graphic.KaguyaUtil;
import icu.gensoukyo.kaguya.util.KaguyaFormatUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import icu.gensoukyo.neo_mystias_izakaya.common.util.holder.ItemStackHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CupBoardItemResourceListWidget extends ObjectSelectionList<CupBoardItemResourceListWidget.Entry> {

    private final int listWidth;
    private final int itemPreRow;
    private final Supplier<List<ItemResourceWithCount>> itemResourceWithCountAssessor;
    private final Consumer<ItemResourceWithCount> onItemClick;

    public CupBoardItemResourceListWidget(Minecraft minecraft, int width, int height, int x,int y, Supplier<List<ItemResourceWithCount>> itemResourceWithCountAssessor, Consumer<ItemResourceWithCount> onItemClick) {
        super(minecraft, width, height, y, 20);
        setPosition(x,y);
        this.listWidth = width;
        this.itemPreRow = (width-10)/20;
        this.itemResourceWithCountAssessor = itemResourceWithCountAssessor;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getRowWidth() {
        return this.listWidth - 12;
    }

    @Override
    protected int scrollBarX() {
        return getRowLeft()+scrollbarWidth();
    }

    @Override
    protected void extractListBackground(GuiGraphicsExtractor graphics) {
    }

    @Override
    protected void extractListSeparators(GuiGraphicsExtractor graphics) {
    }

    @Override
    protected void extractSelection(GuiGraphicsExtractor graphics, CupBoardItemResourceListWidget.Entry entry, int outlineColor) {
    }

    public void refresh(){
        this.clearEntries();
        List<ItemResourceWithCount> itemResourceWithCounts = itemResourceWithCountAssessor.get();
        for (int i = 0; i < itemResourceWithCounts.size(); i+=itemPreRow) {
            List<ItemResourceWithCount> row = itemResourceWithCounts.subList(i, Math.min(i + itemPreRow, itemResourceWithCounts.size()));
            addEntry(new Entry(onItemClick,row));
        }
    }

    @Override
    protected void extractScrollbar(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int scrollerHeight = this.scrollerHeight();
        int scrollerY = this.scrollBarY();
        if (this.scrollable()) {
            KaguyaUtil.fillXYWH(graphics, scrollBarX(), scrollerY, this.scrollbarWidth(), scrollerHeight, 0x40947B58);
            if (this.isOverScrollbar(mouseX, mouseY)) {
                graphics.requestCursor(this.scrolling ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND);
            }
        }
    }

    public static class Entry extends ObjectSelectionList.Entry<Entry> {

        protected final ItemStackHolder itemStack = new ItemStackHolder();
        protected final Consumer<ItemResourceWithCount> resourceConsumer;
        private final List<ItemResourceWithCount> itemResourceWithCountList;

        public Entry(Consumer<ItemResourceWithCount> resourceConsumer, List<ItemResourceWithCount> itemResourceWithCountList) {
            this.resourceConsumer = resourceConsumer;
            this.itemResourceWithCountList = itemResourceWithCountList;
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            if (event.button() == InputConstants.MOUSE_BUTTON_LEFT) {
                int index = ((int)(event.x()-10 - getX()) / 20);
                if (index >= itemResourceWithCountList.size()) return false;
                resourceConsumer.accept(itemResourceWithCountList.get(index));
                return true;
            }
            return false;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void extractContent(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, boolean b, float v) {
            Font font = Minecraft.getInstance().font;
            for (int i = 0; i < itemResourceWithCountList.size(); i++) {
                guiGraphics.item(this.itemResourceWithCountList.get(i).itemResource().toStack(), this.getContentX()+12+i*20, this.getY()+2);
                String formatCount = KaguyaFormatUtil.formatCount(itemResourceWithCountList.get(i).count());
                guiGraphics.text(font, formatCount,this.getContentX()+i*20+10+20-font.width(formatCount)-1,this.getY()+20-font.lineHeight-1,0xFFFFFFFF);
            }
        }

    }
}
