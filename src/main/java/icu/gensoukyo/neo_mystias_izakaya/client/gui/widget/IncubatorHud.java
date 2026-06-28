/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.common.resource.ItemResourceWithCount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IncubatorHud extends AbstractContainerWidget {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static IncubatorHud CurrentActive;
    private final Consumer<ItemResourceWithCount> onItemClick;

    private List<ItemResourceWithCount> itemResourceWithCountList = new ArrayList<>();

    private ItemResourceListWidget itemResourceListWidget;

    public IncubatorHud(int x, int y, int width, int height, Consumer<ItemResourceWithCount> onItemClick) {
        super(x, y, width, height, Component.empty(),AbstractScrollArea.defaultSettings(10));
        this.onItemClick = onItemClick;
        CurrentActive = this;
        this.itemResourceListWidget = new ItemResourceListWidget(Minecraft.getInstance(),this.getWidth(),this.getHeight(),getX(),getY(), ArrayList::new,(_)->{});
    }

    public void refresh(List<ItemResourceWithCount> itemResourceWithCountList){
        this.itemResourceWithCountList = new ArrayList<>();
        this.itemResourceWithCountList.addAll(itemResourceWithCountList);
        refresh();
    }
    public void update(ItemResource itemResource){
        for (int i = 0; i < itemResourceWithCountList.size(); i++) {
            if (itemResourceWithCountList.get(i).itemResource().equals(itemResource) && itemResourceWithCountList.get(i).count() != Long.MAX_VALUE){
                itemResourceWithCountList.set(i,new ItemResourceWithCount(itemResource,itemResourceWithCountList.get(i).count()-1));
                refresh();
                return;
            }
        }
    }

    public void refresh(){
        this.itemResourceListWidget = new ItemResourceListWidget(Minecraft.getInstance(),this.getWidth(),this.getHeight(),getX(),getY(),()->itemResourceWithCountList,onItemClick);
        this.itemResourceListWidget.refresh();
        CurrentActive = this;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float v) {
        if (itemResourceListWidget != null) {
            itemResourceListWidget.extractRenderState(guiGraphics, mouseX, mouseY, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    protected int contentHeight() {
        return getHeight();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return itemResourceListWidget.mouseScrolled(mouseX,mouseY,scrollX,scrollY);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of(itemResourceListWidget);
    }
}
