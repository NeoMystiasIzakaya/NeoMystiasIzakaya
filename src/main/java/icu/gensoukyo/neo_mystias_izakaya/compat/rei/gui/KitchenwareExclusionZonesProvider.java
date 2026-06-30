/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.rei.gui;

import com.google.common.collect.ImmutableList;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZonesProvider;

import java.util.Collection;

public class KitchenwareExclusionZonesProvider implements ExclusionZonesProvider<KitchenwareScreen> {

    @Override
    public Collection<Rectangle> provide(KitchenwareScreen containerScreen) {
        // 在 GUI 右侧增加 25px 额外区域
        int x1 = containerScreen.getLeftPos() + containerScreen.getImageWidth();
        int y1 = containerScreen.getTopPos();
        Rectangle rect1 = new Rectangle(x1, y1, 25, containerScreen.getImageHeight());

        int x2 = Math.max(containerScreen.getLeftPos()-130,10);
        int y2 = containerScreen.getTopPos();
        Rectangle rect2 = new Rectangle(x2, y2, containerScreen.getLeftPos()-x2-10,containerScreen.getImageHeight());

        return ImmutableList.of(rect1,rect2);
    }
}
