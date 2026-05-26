/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.render.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class DiningTableRenderState extends BlockEntityRenderState {
    public ItemStackRenderState cuisineRenderState;
    public ItemStackRenderState beverageRenderState;
}
