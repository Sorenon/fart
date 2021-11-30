package net.sorenon.fart.testmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.sorenon.fart.FartRenderEvents;
import net.sorenon.fart.FartUtil;
import net.sorenon.fart.RenderStateShards;
import net.sorenon.fart.RenderTypeBuilder;

import java.util.function.Function;

public class TestMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(this::afterEntities);
        FartRenderEvents.BEFORE_BLOCK_OUTLINE.register(this::beforeBlockOutline);
        FartRenderEvents.AFTER_TRANSLUCENT.register(this::afterTranslucent);
        FartRenderEvents.LAST.register(this::last);
    }

    public void afterEntities(WorldRenderContext context) {
        render(context, 0, 70, 0);
        renderLine(context, 0.5f, 75, 0);
    }

    public void renderLine(WorldRenderContext context, float x, float y, float z) {
        VertexConsumer consumer = context.consumers().getBuffer(LINE_CUSTOM_ALWAYS.apply(4.0));
        MatrixStack stack = context.matrixStack();
        Vec3d camPos = context.camera().getPos();
        stack.push();

        stack.translate(-camPos.x, -camPos.y, -camPos.z);
        stack.translate(x, y, z);

        Matrix4f matrix4f = stack.peek().getPositionMatrix();

        consumer.vertex(matrix4f, 0, 0, 0).color(0f, 0f, 0f, 1f).normal(0, -1, 0).next();
        consumer.vertex(matrix4f, 0, -1, 0).color(0f, 0f, 0f, 1f).normal(0, -1, 0).next();

        consumer = context.consumers().getBuffer(LINE_CUSTOM.apply(2.0));
        consumer.vertex(matrix4f, 0, 0, 0).color(1f, 0f, 0f, 1f).normal(0, -1, 0).next();
        consumer.vertex(matrix4f, 0, -1, 0).color(0.5f, 0.5f, 0.5f, 1f).normal(0, -1, 0).next();

        stack.pop();

        stack.push();
        stack.translate(-camPos.x, -camPos.y, -camPos.z);
        stack.translate(x, y + 4, z);
        FartUtil.renderCrosshair(context.consumers(), context.matrixStack(), 1, false);
        stack.pop();
    }

    public void beforeBlockOutline(WorldRenderContext context) {
        render(context, 2, 71, 0);
        renderLine(context, 2.5f, 76, 0);
    }

    public void afterTranslucent(WorldRenderContext context) {
        render(context, 4, 72, 0);
        renderLine(context, 4.5f, 77, 0);
    }

    public void last(WorldRenderContext context) {
        //TODO make it so that the user doesn't have to do this
        FartRenderEvents.canvasFixPre(context.matrixStack());
        render(context, 6, 73, 0);
        FartRenderEvents.canvasFixPost(context.matrixStack());

        renderLine(context, 6.5f, 78, 0);
    }

    public void render(WorldRenderContext context, float x, float y, float z) {
        var stack = context.matrixStack();
        var consumers = context.consumers();
        var camPos = context.camera().getPos();

        stack.push();
        stack.translate(-camPos.x, -camPos.y, -camPos.z);
        stack.translate(x, y, z);

        assert consumers != null;
        VertexConsumer consumer = consumers.getBuffer(ENTITY_CUTOUT_ALWAYS.apply(MissingSprite.getMissingSpriteId()));
        Matrix4f modelMatrix = stack.peek().getPositionMatrix();
        Matrix3f normalMatrix = stack.peek().getNormalMatrix();
        consumer.vertex(modelMatrix, 0, 1, 0).color(255, 255, 255, 255).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();
        consumer.vertex(modelMatrix, 1, 1, 0).color(255, 255, 255, 255).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();
        consumer.vertex(modelMatrix, 1, 0, 0).color(255, 255, 255, 255).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();
        consumer.vertex(modelMatrix, 0, 0, 0).color(255, 255, 255, 255).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();

        stack.pop();
    }

    public static final Function<Identifier, RenderLayer> ENTITY_CUTOUT_ALWAYS = Util.memoize((texture) -> {
        RenderTypeBuilder builder = new RenderTypeBuilder(new Identifier("testmod", "cutout_always"), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, false);
        builder.innerBuilder.shader(RenderStateShards.shader(GameRenderer::getRenderTypeEntityTranslucentShader)).texture(RenderStateShards.texture(texture, false, false)).transparency(RenderStateShards.TRANSLUCENT_TRANSPARENCY).cull(RenderStateShards.NO_CULL).lightmap(RenderStateShards.LIGHTMAP).overlay(RenderStateShards.OVERLAY).depthTest(RenderStateShards.NO_DEPTH_TEST);
        return builder.build(false);
    });

    public static final Function<Double, RenderLayer> LINE_CUSTOM_ALWAYS = Util.memoize(aDouble -> {
        RenderTypeBuilder builder = new RenderTypeBuilder(new Identifier("testmod", "line_always"), VertexFormats.LINES, VertexFormat.DrawMode.LINES, 16, false, false);
        builder.innerBuilder
                .shader(RenderStateShards.shader(GameRenderer::getRenderTypeLinesShader))
                .lineWidth(RenderStateShards.lineWidth(aDouble))
                .layering(RenderStateShards.VIEW_OFFSET_Z_LAYERING)
                .transparency(RenderStateShards.TRANSLUCENT_TRANSPARENCY)
                .writeMaskState(RenderStateShards.COLOR_DEPTH_WRITE)
                .cull(RenderStateShards.NO_CULL)
                .depthTest(RenderStateShards.NO_DEPTH_TEST);
        return builder.build(true);
    });

    public static final Function<Double, RenderLayer> LINE_CUSTOM = Util.memoize(aDouble -> {
        RenderTypeBuilder builder = new RenderTypeBuilder(new Identifier("testmod", "line"), VertexFormats.LINES, VertexFormat.DrawMode.LINES, 16, false, false);
        builder.innerBuilder
                .shader(RenderStateShards.shader(GameRenderer::getRenderTypeLinesShader))
                .lineWidth(RenderStateShards.lineWidth(aDouble))
                .layering(RenderStateShards.VIEW_OFFSET_Z_LAYERING)
                .transparency(RenderStateShards.TRANSLUCENT_TRANSPARENCY)
                .writeMaskState(RenderStateShards.COLOR_DEPTH_WRITE)
                .cull(RenderStateShards.NO_CULL);
        return builder.build(true);
    });
}
