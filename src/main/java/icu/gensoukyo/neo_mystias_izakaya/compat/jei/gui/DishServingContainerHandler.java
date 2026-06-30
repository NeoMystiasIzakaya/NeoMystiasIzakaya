/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jei.gui;

import com.google.common.collect.ImmutableList;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.DishServingScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public class DishServingContainerHandler implements IGuiContainerHandler<DishServingScreen> {
    @Override
    public List<Rect2i> getGuiExtraAreas(DishServingScreen containerScreen) {
        int y = containerScreen.getTopPos();

        int x1 = containerScreen.getLeftPos()+containerScreen.getImageWidth()+4;
        int width = Math.min(130,containerScreen.getMinecraft().getWindow().getGuiScaledWidth()-x1-10);
        Rect2i rect1 = new Rect2i(x1, y, width, containerScreen.getImageHeight());

        int x2 = Math.max(containerScreen.getLeftPos()-130,10);
        Rect2i rect2 = new Rect2i(x2, y, containerScreen.getLeftPos()-x2-4,containerScreen.getImageHeight());


        return ImmutableList.of(rect1,rect2);
    }
}
