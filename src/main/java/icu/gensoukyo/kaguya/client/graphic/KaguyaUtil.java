/*
 * Copyright 2026 Kaguya154
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.kaguya.client.graphic;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import icu.gensoukyo.kaguya.client.graphic.state.BlitRenderState;
import icu.gensoukyo.kaguya.client.graphic.state.ColoredRectangleRenderState;
import icu.gensoukyo.kaguya.client.pipeline.KaguyaPipelines;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2f;
import org.jspecify.annotations.Nullable;

public final class KaguyaUtil {

    // region fill

    public static void fillXYXY(GuiGraphicsExtractor guiGraphics, float x0, float y0, float x1, float y1, int color1) {
        fillXYXY(guiGraphics, KaguyaPipelines.POSITION_COLOR, x0, y0, x1, y1, color1);
    }

    public static void fillXYXY(GuiGraphicsExtractor guiGraphics, RenderPipeline pipeline, float x0, float y0, float x1, float y1, int color) {
        if (x0 < x1) {
            float tmp = x0;
            x0 = x1;
            x1 = tmp;
        }

        if (y0 < y1) {
            float tmp = y0;
            y0 = y1;
            y1 = tmp;
        }

        innerFill(guiGraphics, pipeline, TextureSetup.noTexture(), x0, y0, x1, y1, color, null);
    }

    public static void fillXYXY(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, TextureSetup textureSetup, float x0, float y0, float x1, float y1) {
        innerFill(guiGraphics, renderPipeline, textureSetup, x0, y0, x1, y1, -1, null);
    }

    public static void fillXYWH(GuiGraphicsExtractor guiGraphics, float x, float y, float w, float h, int color) {
        fillXYWH(guiGraphics, KaguyaPipelines.POSITION_COLOR, x, y, w, h, color);
    }

    public static void fillXYWH(GuiGraphicsExtractor guiGraphics, RenderPipeline pipeline, float x, float y, float w, float h, int color) {
        if (w < 0) {
            x = x + w;
            w = -w;
        }
        if (h < 0) {
            y = y + h;
            h = -h;
        }

        innerFill(guiGraphics, pipeline, TextureSetup.noTexture(), x, y, x + w, y + h, color, null);
    }

    public static void fillXYWH(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, TextureSetup textureSetup, float x, float y, float w, float h) {
        innerFill(guiGraphics, renderPipeline, textureSetup, x, y, x + w, y + h, -1, null);
    }


    // endregion

    // region fill centered

    public static void fillCentered(GuiGraphicsExtractor guiGraphics, float x, float y, float w, float h, int color) {
        fillXYWH(guiGraphics, x - w / 2, y - h / 2, w, h, color);
    }

    public static void fillCentered(GuiGraphicsExtractor guiGraphics, RenderPipeline pipeline, float x, float y, float w, float h, int color) {
        fillXYWH(guiGraphics, pipeline, x - w / 2, y - h / 2, w, h, color);
    }

    public static void fillCentered(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, TextureSetup textureSetup, float x, float y, float w, float h) {
        fillXYWH(guiGraphics, renderPipeline, textureSetup, x - w / 2, y - h / 2, w, h);
    }

    //endregion

    //region blit

    public static void blit(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, Identifier texture, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight, int color) {
        blit(guiGraphics, renderPipeline, texture, x, y, u, v, width, height, width, height, textureWidth, textureHeight, color);
    }

    public static void blit(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, Identifier texture, float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        blit(guiGraphics, renderPipeline, texture, x, y, u, v, width, height, width, height, textureWidth, textureHeight);
    }

    public static void blit(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, Identifier texture, float x, float y, float u, float v, float width, float height, float srcWidth, float srcHeight, float textureWidth, float textureHeight) {
        blit(guiGraphics, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight, -1);
    }

    public static void blit(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, Identifier texture, float x, float y, float u, float v, float width, float height, float srcWidth, float srcHeight, float textureWidth, float textureHeight, int color) {
        innerBlit(guiGraphics, renderPipeline, texture, x, x + width, y, y + height, (u + 0.0F) / textureWidth, (v + 0.0F) / textureHeight, (u + srcWidth) / textureWidth, (v + srcHeight) / textureHeight, color);
    }

    public static void blitXYXYUVUV(GuiGraphicsExtractor guiGraphics, Identifier location, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, location, x0, x1, y0, y1, u0, v0, u1, v1, -1);
    }

    public static void blitXYWHUVUV(GuiGraphicsExtractor guiGraphics, Identifier location, float x, float y, float width, float height, float u0, float v0, float u1, float v1) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, location, x, x + width, y, y + height, u0, v0, u1, v1, -1);
    }

    public static void blitXYXYUVWH(GuiGraphicsExtractor guiGraphics, Identifier location, float x0, float y0, float x1, float y1, float u, float v, float uWidth, float vHeight) {
        blitXYXYUVWH(guiGraphics, location, x0, y0, x1, y1, u, v, uWidth, vHeight, 256, 256);
    }

    public static void blitXYXYUVWH(GuiGraphicsExtractor guiGraphics, Identifier location, float x0, float y0, float x1, float y1, float u, float v, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, location, x0, x1, y0, y1, (u + 0.0F) / textureWidth, (v + 0.0F) / textureHeight, (u + uWidth) / textureWidth, (v + vHeight) / textureHeight, -1);
    }

    public static void blitXYWHUVWH(GuiGraphicsExtractor guiGraphics, Identifier location, float x, float y, float width, float height, float u, float v, float uWidth, float vHeight) {
        blitXYWHUVWH(guiGraphics, location, x, y, width, height, u, v, uWidth, vHeight, 256, 256);
    }

    public static void blitXYWHUVWH(GuiGraphicsExtractor guiGraphics, Identifier location, float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, location, x, x + width, y, y + height, (u + 0.0F) / textureWidth, (v + 0.0F) / textureHeight, (u + uWidth) / textureWidth, (v + vHeight) / textureHeight, -1);
    }

    public static void blitXYXYUVUV(GuiGraphicsExtractor guiGraphics, GpuTextureView textureView, GpuSampler sampler, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, textureView, sampler, x0, y0, x1, y1, u0, v0, u1, v1, -1);
    }

    public static void blitXYWHUVUV(GuiGraphicsExtractor guiGraphics, GpuTextureView textureView, GpuSampler sampler, float x, float y, float width, float height, float u0, float v0, float u1, float v1) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, textureView, sampler, x, y, x + width, y + height, u0, v0, u1, v1, -1);
    }

    public static void blitXYXYUVWH(GuiGraphicsExtractor guiGraphics, GpuTextureView textureView, GpuSampler sampler, float x0, float y0, float x1, float y1, float u, float v, float uWidth, float vHeight) {
        blitXYXYUVWH(guiGraphics, textureView, sampler, x0, y0, x1, y1, u, v, uWidth, vHeight, 256, 256);
    }

    public static void blitXYXYUVWH(GuiGraphicsExtractor guiGraphics, GpuTextureView textureView, GpuSampler sampler, float x0, float y0, float x1, float y1, float u, float v, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, textureView, sampler, x0, y0, x1, y1, (u + 0.0F) / textureWidth, (v + 0.0F) / textureHeight, (u + uWidth) / textureWidth, (v + vHeight) / textureHeight, -1);
    }

    public static void blitXYWHUVWH(GuiGraphicsExtractor guiGraphics, GpuTextureView textureView, GpuSampler sampler, float x, float y, float width, float height, float u, float v, float uWidth, float vHeight) {
        blitXYWHUVWH(guiGraphics, textureView, sampler, x, y, width, height, u, v, uWidth, vHeight, 256, 256);
    }

    public static void blitXYWHUVWH(GuiGraphicsExtractor guiGraphics, GpuTextureView textureView, GpuSampler sampler, float x, float y, float width, float height, float u, float v, float uWidth, float vHeight, float textureWidth, float textureHeight) {
        innerBlit(guiGraphics, KaguyaPipelines.POSITION_TEX_COLOR, textureView, sampler, x, y, x + width, y + height, (u + 0.0F) / textureWidth, (v + 0.0F) / textureHeight, (u + uWidth) / textureWidth, (v + vHeight) / textureHeight, -1);
    }

    // endregion

    // region inner

    private static void innerFill(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, TextureSetup textureSetup, float x0, float y0, float x1, float y1, int color1, @Nullable Integer color2) {
        submitGuiElementRenderState(guiGraphics, new ColoredRectangleRenderState(renderPipeline, textureSetup, new Matrix3x2f(guiGraphics.pose()), x0, y0, x1, y1, color1, color2 != null ? color2 : color1, guiGraphics.peekScissorStack()));
    }

    private static void innerBlit(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, Identifier location, float x0, float x1, float y0, float y1, float u0, float v0, float u1, float v1, int color) {
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(location);
        innerBlit(guiGraphics, renderPipeline, texture.getTextureView(), texture.getSampler(), x0, y0, x1, y1, u0, v0, u1, v1, color);
    }

    private static void innerBlit(GuiGraphicsExtractor guiGraphics, RenderPipeline pipeline, GpuTextureView textureView, GpuSampler sampler, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int color) {
        submitGuiElementRenderState(guiGraphics, new BlitRenderState(pipeline, TextureSetup.singleTexture(textureView, sampler), new Matrix3x2f(guiGraphics.pose()), x0, y0, x1, y1, u0, u1, v0, v1, color, guiGraphics.peekScissorStack()));
    }

    public static void submitGuiElementRenderState(GuiGraphicsExtractor guiGraphics, GuiElementRenderState renderState) {
        guiGraphics.submitGuiElementRenderState(renderState);
    }

    public static void submitPictureInPictureRenderState(GuiGraphicsExtractor guiGraphics, PictureInPictureRenderState renderState) {
        guiGraphics.submitPictureInPictureRenderState(renderState);
    }

    //endregion
}
