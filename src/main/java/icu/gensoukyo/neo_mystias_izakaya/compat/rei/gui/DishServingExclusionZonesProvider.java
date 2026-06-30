/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.rei.gui;

import com.google.common.collect.ImmutableList;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.DishServingScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZonesProvider;

import java.util.Collection;

public class DishServingExclusionZonesProvider implements ExclusionZonesProvider<DishServingScreen> {

    @Override
    public Collection<Rectangle> provide(DishServingScreen containerScreen) {
        int y = containerScreen.getTopPos();

        int x1 = containerScreen.getLeftPos()+containerScreen.getImageWidth()+4;
        int width = Math.min(130,containerScreen.getMinecraft().getWindow().getGuiScaledWidth()-x1-10);
        Rectangle rect1 = new Rectangle(x1, y, width, containerScreen.getImageHeight());

        int x2 = Math.max(containerScreen.getLeftPos()-130,10);
        Rectangle rect2 = new Rectangle(x2, y, containerScreen.getLeftPos()-x2-4,containerScreen.getImageHeight());


        return ImmutableList.of(rect1,rect2);
    }
}
