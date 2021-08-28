package net.sorenon.fart.testmod;


import net.fabricmc.api.ClientModInitializer;
import net.sorenon.fart.FartRenderEvents;
import net.sorenon.fart.RenderStateShards;
import net.sorenon.fart.RenderTypeBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

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
    }

    public void beforeBlockOutline(WorldRenderContext context) {
        render(context, 2, 71, 0);
    }

    public void afterTranslucent(WorldRenderContext context) {
        render(context, 4, 72, 0);
    }


    public void last(WorldRenderContext context) {
        render(context, 6, 73, 0);
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
        Matrix4f modelMatrix = stack.peek().getModel();
        Matrix3f normalMatrix = stack.peek().getNormal();
        consumer.vertex(modelMatrix, 0, 1, 0).color(255, 255, 255, 255).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();
        consumer.vertex(modelMatrix, 1, 1, 0).color(255, 255, 255, 255).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();
        consumer.vertex(modelMatrix, 1, 0, 0).color(255, 255, 255, 255).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();
        consumer.vertex(modelMatrix, 0, 0, 0).color(255, 255, 255, 255).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0, 0, -1).next();

        stack.pop();
    }

    public static final Function<Identifier, RenderLayer> ENTITY_CUTOUT_ALWAYS = Util.memoize((texture) -> {
        RenderTypeBuilder builder = new RenderTypeBuilder("cutout_always", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, false);
        builder.innerBuilder.shader(RenderStateShards.shader(GameRenderer::getRenderTypeEntityTranslucentShader)).texture(RenderStateShards.texture(texture, false, false)).transparency(RenderStateShards.TRANSLUCENT_TRANSPARENCY).cull(RenderStateShards.NO_CULL).lightmap(RenderStateShards.LIGHTMAP).overlay(RenderStateShards.OVERLAY).depthTest(RenderStateShards.NO_DEPTH_TEST);
        return builder.build(false);
    });
}
