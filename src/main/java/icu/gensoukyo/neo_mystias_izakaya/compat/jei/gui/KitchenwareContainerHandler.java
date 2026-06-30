/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.jei.gui;

import com.google.common.collect.ImmutableList;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public class KitchenwareContainerHandler implements IGuiContainerHandler<KitchenwareScreen> {
    @Override
    public List<Rect2i> getGuiExtraAreas(KitchenwareScreen containerScreen) {
        // 在 GUI 右侧增加 25px 额外区域
        int x1 = containerScreen.getLeftPos() + containerScreen.getImageWidth();
        int y1 = containerScreen.getTopPos();
        Rect2i rect1 = new Rect2i(x1, y1, 25, containerScreen.getImageHeight());

        int x2 = Math.max(containerScreen.getLeftPos()-130,10);
        int y2 = containerScreen.getTopPos();
        Rect2i rect2 = new Rect2i(x2, y2, containerScreen.getLeftPos()-x2-10,containerScreen.getImageHeight());


        return ImmutableList.of(rect1,rect2);
    }
}
