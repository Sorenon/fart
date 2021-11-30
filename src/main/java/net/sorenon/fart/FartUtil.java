package net.sorenon.fart;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FartUtil {

    public static void renderCrosshair(VertexConsumerProvider consumerProvider, MatrixStack poseStack, float size, boolean depthTest) {
        renderCrosshair(consumerProvider, poseStack, size, depthTest, true, true, true);
    }

    public static void renderCrosshair(VertexConsumerProvider consumerProvider, MatrixStack poseStack, float size, boolean depthTest, boolean drawX, boolean drawY, boolean drawZ) {
        Matrix4f model = poseStack.peek().getPositionMatrix();
        Matrix3f normal = poseStack.peek().getNormalMatrix() ;

        VertexConsumer consumer = consumerProvider.getBuffer(LINE_COLOR_ONLY.apply(4d, depthTest));
        if (drawX) {
            consumer.vertex(model, 0.0f, 0.0f, 0.0f).color(0, 0, 0, 255).normal(normal, 1.0F, 0.0F, 0.0F).next();
            consumer.vertex(model, size, 0.0f, 0.0f).color(0, 0, 0, 255).normal(normal, 1.0F, 0.0F, 0.0F).next();
        }

        if (drawY) {
            consumer.vertex(model, 0.0f, 0.0f, 0.0f).color(0, 0, 0, 255).normal(normal, 0.0F, 1.0F, 0.0F).next();
            consumer.vertex(model, 0.0f, size, 0.0f).color(0, 0, 0, 255).normal(normal, 0.0F, 1.0F, 0.0F).next();
        }

        if (drawZ) {
            consumer.vertex(model, 0.0f, 0.0f, 0.0f).color(0, 0, 0, 255).normal(normal, 0.0F, 0.0F, 1.0F).next();
            consumer.vertex(model, 0.0f, 0.0f, size).color(0, 0, 0, 255).normal(normal, 0.0F, 0.0F, 1.0F).next();
        }

        consumer = consumerProvider.getBuffer(LINE.apply(2d, depthTest));

        if (drawX) {
            consumer.vertex(model, 0.0f, 0.0f, 0.0f).color(255, 0, 0, 255).normal(normal, 1.0F, 0.0F, 0.0F).next();
            consumer.vertex(model, size, 0.0f, 0.0f).color(255, 0, 0, 255).normal(normal, 1.0F, 0.0F, 0.0F).next();
        }

        if (drawY) {
            consumer.vertex(model, 0.0f, 0.0f, 0.0f).color(0, 255, 0, 255).normal(normal, 0.0F, 1.0F, 0.0F).next();
            consumer.vertex(model, 0.0f, size, 0.0f).color(0, 255, 0, 255).normal(normal, 0.0F, 1.0F, 0.0F).next();
        }

        if (drawZ) {
            consumer.vertex(model, 0.0f, 0.0f, 0.0f).color(127, 127, 255, 255).normal(normal, 0.0F, 0.0F, 1.0F).next();
            consumer.vertex(model, 0.0f, 0.0f, size).color(127, 127, 255, 255).normal(normal, 0.0F, 0.0F, 1.0F).next();
        }
    }

    public static final BiFunction<Double, Boolean, RenderLayer> LINE = Util.memoize((lineWidth, depthTest) -> {
        RenderPhase.DepthTest depthTest1 = RenderStateShards.NO_DEPTH_TEST;
        if (depthTest) {
            depthTest1 = RenderStateShards.LEQUAL_DEPTH_TEST;
        }

        RenderTypeBuilder builder = new RenderTypeBuilder(new Identifier("fart", "line"), VertexFormats.LINES, VertexFormat.DrawMode.LINES, 16, false, false);
        builder.innerBuilder
                .shader(RenderStateShards.shader(GameRenderer::getRenderTypeLinesShader))
                .lineWidth(RenderStateShards.lineWidth(lineWidth))
                .layering(RenderStateShards.VIEW_OFFSET_Z_LAYERING)
                .transparency(RenderStateShards.TRANSLUCENT_TRANSPARENCY)
                .writeMaskState(RenderStateShards.COLOR_DEPTH_WRITE)
                .cull(RenderStateShards.NO_CULL)
                .depthTest(depthTest1);
        return builder.build(true);
    });

    public static final BiFunction<Double, Boolean, RenderLayer> LINE_COLOR_ONLY = Util.memoize((lineWidth, depthTest) -> {
        RenderPhase.DepthTest depthTest1 = RenderStateShards.NO_DEPTH_TEST;
        if (depthTest) {
            depthTest1 = RenderStateShards.LEQUAL_DEPTH_TEST;
        }

        RenderTypeBuilder builder = new RenderTypeBuilder(new Identifier("fart", "line_color_only"), VertexFormats.LINES, VertexFormat.DrawMode.LINES, 16, false, false);
        builder.innerBuilder
                .shader(RenderStateShards.shader(GameRenderer::getRenderTypeLinesShader))
                .lineWidth(RenderStateShards.lineWidth(lineWidth))
                .layering(RenderStateShards.VIEW_OFFSET_Z_LAYERING)
                .transparency(RenderStateShards.TRANSLUCENT_TRANSPARENCY)
                .writeMaskState(RenderStateShards.COLOR_WRITE)
                .cull(RenderStateShards.NO_CULL)
                .depthTest(depthTest1);
        return builder.build(true);
    });
}
