/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.render.state.KitchenwareRenderState;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class KitchenwareRenderer<T extends KitchenwareBlockEntity> implements BlockEntityRenderer<T, KitchenwareRenderState> {
    private final ItemModelResolver itemModelResolver;
    private static final Identifier CONFIRM_SPRITE = NeoMystiasIzakaya.id("textures/gui/confirm.png");
    public static final RenderType RENDER_TYPE = RenderTypes.entityCutout(CONFIRM_SPRITE);

    public KitchenwareRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public KitchenwareRenderState createRenderState() {
        return new KitchenwareRenderState();
    }

    @Override
    public void extractRenderState(T blockEntity, KitchenwareRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        ItemStack targetItem = blockEntity.getTargetItem();
        ItemStack resultItem = blockEntity.getResultItem();
        if (!targetItem.isEmpty()) {
            itemModelResolver.updateForTopItem(state.targetItemModel, targetItem, ItemDisplayContext.NONE, Minecraft.getInstance().level, null, (int) blockEntity.getBlockPos().asLong());
            state.isRender = KitchenwareRenderState.RenderResult.TARGET;
        }

        if (!resultItem.isEmpty()) {
            itemModelResolver.updateForTopItem(state.resultItemModel, resultItem, ItemDisplayContext.NONE, Minecraft.getInstance().level, null, (int) blockEntity.getBlockPos().asLong());
            state.isRender = KitchenwareRenderState.RenderResult.RESULT;
        }

    }

    @Override
    public void submit(KitchenwareRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        switch (state.isRender) {
            case NONE -> {

            }
            case TARGET -> {
                poseStack.pushPose();
                poseStack.translate(0.5D, 1D, 0.5D);
                state.targetItemModel.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
            case RESULT -> {
                poseStack.pushPose();
                poseStack.translate(0.5D, 1D, 0.5D);
                state.resultItemModel.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
                submitNodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (this::renderQuad));
            }
        }
    }

    public void renderQuad(PoseStack.Pose pose, VertexConsumer buffer) {
        float half = 0.5F;
        // 四个顶点按逆时针顺序构成四边形 (从左上开始)
        pose.translate(0.5F, 0.8F, 0.55F);
        vertex(buffer, pose, -half, 0.0D, 0.0D, 1.0D, true);  // 左上
        vertex(buffer, pose,  half, 0.0D, 1.0D, 1.0D, true);  // 右上
        vertex(buffer, pose,  half, half, 1.0D, 0.0D, true);  // 右下
        vertex(buffer, pose, -half, half, 0.0D, 0.0D, true);  // 左下

        pose.translate(0,0,-0.1F);

        vertex(buffer, pose, -half, 0.0D, 0.0D, 1.0D, false);  // 左上
        vertex(buffer, pose,  half, 0.0D, 1.0D, 1.0D, false);  // 右上
        vertex(buffer, pose,  half, half, 1.0D, 0.0D, false);  // 右下
        vertex(buffer, pose, -half, half, 0.0D, 0.0D, false);  // 左下
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, double x, double y, double texU, double texV, boolean face) {
        float normal = face ? 1 : -1;
        buffer.addVertex(pose, (float) x, (float) y, 0.0F)
                .setColor(255, 255, 255, 128)
                .setUv((float) texU, (float) texV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightCoordsUtil.FULL_BRIGHT)
                .setNormal(pose, 0.0F, normal, 0.0F);
    }
}
