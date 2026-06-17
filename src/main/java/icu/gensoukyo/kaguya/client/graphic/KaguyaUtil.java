package icu.gensoukyo.kaguya.client.graphic;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import icu.gensoukyo.kaguya.client.graphic.state.ColoredRectangleRenderState;
import icu.gensoukyo.kaguya.client.pipeline.KaguyaPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
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


    // region inner

    private static void innerFill(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, TextureSetup textureSetup, float x0, float y0, float x1, float y1, int color1, @Nullable Integer color2) {
        submitGuiElementRenderState(guiGraphics, new ColoredRectangleRenderState(renderPipeline, textureSetup, new Matrix3x2f(guiGraphics.pose()), x0, y0, x1, y1, color1, color2 != null ? color2 : color1, guiGraphics.peekScissorStack()));
    }

    public static void submitGuiElementRenderState(GuiGraphicsExtractor guiGraphics, GuiElementRenderState renderState) {
        guiGraphics.submitGuiElementRenderState(renderState);
    }

    public static void submitPictureInPictureRenderState(GuiGraphicsExtractor guiGraphics, PictureInPictureRenderState renderState) {
        guiGraphics.submitPictureInPictureRenderState(renderState);
    }

    //endregion
}
