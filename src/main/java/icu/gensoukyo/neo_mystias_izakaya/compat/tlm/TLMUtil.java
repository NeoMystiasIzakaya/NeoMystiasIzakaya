/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.api.client.render.MaidRenderState;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.github.tartaricacid.touhoulittlemaid.util.EntityCacheUtil.clearMaidDataResidue;

public class TLMUtil {
    public static final Cache<String, EntityMaid> SCREEN_CACHE = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).build();

    public static void renderEntityPart(String modelID, GuiGraphicsExtractor graphics, int mouseX, int mouseY, int middleX, int middleY, float renderItemScale) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;
        EntityMaid entity = null;
        try {
            entity = SCREEN_CACHE.get(modelID, () -> new EntityMaid(world));
        } catch (ExecutionException e) {
            NeoMystiasIzakaya.LOGGER.error("Error while getting entity maid", e);
        }

        if (entity instanceof EntityMaid maid) {
            clearMaidDataResidue(maid, true);
            maid.setModelId(modelID);
            maid.renderState = MaidRenderState.GARAGE_KIT;
            maid.tickCount = 0;
            int centerX = (middleX - 256 / 2) / 2;
            int yOffset = (int) (45 * (renderItemScale - 1));

            InventoryScreen.extractEntityInInventoryFollowsMouse(
                    graphics,
                    centerX - 100,
                    middleY - 100,
                    centerX + 100,
                    middleY + 200 - yOffset,
                    (int) (45 * renderItemScale),
                    0.1F,
                    mouseX,
                    mouseY,
                    entity);
        }
    }


}
