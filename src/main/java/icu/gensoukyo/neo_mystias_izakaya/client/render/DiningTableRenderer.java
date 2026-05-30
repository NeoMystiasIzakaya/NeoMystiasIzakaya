/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import icu.gensoukyo.neo_mystias_izakaya.client.render.state.DiningTableRenderState;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class DiningTableRenderer implements BlockEntityRenderer<DiningTableBlockEntity, DiningTableRenderState> {

    private final ItemModelResolver itemModelResolver;

    public DiningTableRenderer(BlockEntityRendererProvider.Context context) {
        itemModelResolver = context.itemModelResolver();
    }

    @Override
    public DiningTableRenderState createRenderState() {
        return new DiningTableRenderState();
    }

    @Override
    public void extractRenderState(DiningTableBlockEntity blockEntity, DiningTableRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        ItemStack cuisine = blockEntity.getCuisine();
        ItemStack beverage = blockEntity.getBeverage();
        ItemStackRenderState cuisineRenderState = new ItemStackRenderState();
        ItemStackRenderState beverageRenderState = new ItemStackRenderState();
        itemModelResolver.updateForTopItem(cuisineRenderState, cuisine, ItemDisplayContext.NONE, blockEntity.getLevel(), null, (int) blockEntity.getBlockPos().asLong());
        itemModelResolver.updateForTopItem(beverageRenderState, beverage, ItemDisplayContext.NONE, blockEntity.getLevel(), null, (int) blockEntity.getBlockPos().asLong());
        state.cuisineRenderState = cuisineRenderState;
        state.beverageRenderState = beverageRenderState;
        state.index = blockEntity.getTableIndex();
    }

    @Override
    public void submit(DiningTableRenderState diningTableRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        Font font = Minecraft.getInstance().font;
        poseStack.pushPose();
        poseStack.scale(0.5F,0.5F,0.5F);
        poseStack.translate(0.5D, 2.5D, 1D);
        diningTableRenderState.beverageRenderState.submit(poseStack, submitNodeCollector, diningTableRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.translate(1D, 0D, 0D);
        diningTableRenderState.cuisineRenderState.submit(poseStack, submitNodeCollector, diningTableRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.translate(-0.5D,2D,0D);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.scale(0.1F,0.1F,0.1F);
        MutableComponent literal = Component.literal(String.valueOf(diningTableRenderState.index));
        submitNodeCollector.submitText(poseStack, 0,0, literal.getVisualOrderText(), false, Font.DisplayMode.NORMAL, diningTableRenderState.lightCoords,0xFFFFFFFF, 0,0);
        poseStack.mulPose(Axis.YN.rotationDegrees(180));
        poseStack.translate(-font.width(literal), 0.0D, 0.0D);
        submitNodeCollector.submitText(poseStack, 0,0, literal.getVisualOrderText(), false, Font.DisplayMode.NORMAL, diningTableRenderState.lightCoords,0xFFFFFFFF, 0,0);
        poseStack.popPose();
    }
}
