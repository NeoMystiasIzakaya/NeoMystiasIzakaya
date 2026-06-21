/*
 * Copyright 2026 Kaguya154
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.kaguya.client.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import icu.gensoukyo.kaguya.Kaguya;

import static net.minecraft.client.renderer.RenderPipelines.MATRICES_PROJECTION_SNIPPET;

public class KaguyaPipelines {

    public static RenderPipeline.Snippet POSITION_COLOR_SNIPPET =
            RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET)
                    .withVertexShader(Kaguya.id("core/position_color"))
                    .withFragmentShader(Kaguya.id("core/position_color"))
                    .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
                    .buildSnippet();

    public static RenderPipeline.Snippet POSITION_TEX_SNIPPET =
            RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET)
                    .withVertexShader(Kaguya.id("core/position_tex"))
                    .withFragmentShader(Kaguya.id("core/position_tex"))
                    .withSampler("Sampler0")
                    .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
                    .buildSnippet();

    public static RenderPipeline.Snippet POSITION_TEX_COLOR_SNIPPET =
            RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET)
                    .withVertexShader(Kaguya.id("core/position_tex_color"))
                    .withFragmentShader(Kaguya.id("core/position_tex_color"))
                    .withSampler("Sampler0")
                    .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                    .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
                    .buildSnippet();
    //GUI_TEXTURED_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[]
    // {MATRICES_PROJECTION_SNIPPET}).withVertexShader("core/position_tex_color")
    // .withFragmentShader("core/position_tex_color").withSampler("Sampler0")
    // .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
    // .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS).buildSnippet();


    public static final RenderPipeline POSITION_COLOR = RenderPipeline.builder(POSITION_COLOR_SNIPPET)
            .withLocation(Kaguya.id("pipeline/position_color"))
            .build();
    public static final RenderPipeline POSITION_TEX = RenderPipeline.builder(POSITION_TEX_SNIPPET)
            .withLocation(Kaguya.id("pipeline/position_tex"))
            .build();
    public static final RenderPipeline POSITION_TEX_COLOR = RenderPipeline.builder(POSITION_TEX_COLOR_SNIPPET)
            .withLocation(Kaguya.id("pipeline/position_tex_color"))
            .build();

}
