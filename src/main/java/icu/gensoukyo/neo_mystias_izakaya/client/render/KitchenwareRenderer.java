package icu.gensoukyo.neo_mystias_izakaya.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import icu.gensoukyo.neo_mystias_izakaya.client.render.state.KitchenwareRenderState;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class KitchenwareRenderer<T extends AbstractKitchenwareBE> implements BlockEntityRenderer<T, KitchenwareRenderState> {
    private final ItemModelResolver itemModelResolver;

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
        if (!targetItem.isEmpty()) {
            itemModelResolver.updateForTopItem(state.targetItemModel, targetItem, ItemDisplayContext.NONE, Minecraft.getInstance().level, null, (int) blockEntity.getBlockPos().asLong());
            state.isRender = true;
        }

    }

    @Override
    public void submit(KitchenwareRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (state.isRender) {
            poseStack.pushPose();
            poseStack.translate(0.5D, 1D, 0.5D);
            state.targetItemModel.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
