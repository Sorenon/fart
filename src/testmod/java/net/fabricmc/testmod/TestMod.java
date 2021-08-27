package net.fabricmc.testmod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.example.RenderStateShards;
import net.fabricmc.example.RenderTypeBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public class TestMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(this::afterEntities);
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(this::beforeBlockOutline);
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::afterTranslucent);
        WorldRenderEvents.LAST.register(this::last);
    }

    public void afterEntities(WorldRenderContext context) {
        render(context, 0, 70, 0);
    }

    public boolean beforeBlockOutline(WorldRenderContext context, HitResult hitResult) {
        render(context, 2, 71, 0);
        return true;
    }

    public void afterTranslucent(WorldRenderContext context) {
        render(context, 4, 72, 0);
        GlStateManager._disableDepthTest();
        ((MultiBufferSource.BufferSource)context.consumers()).endBatch();
    }


    public void last(WorldRenderContext context) {
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.setIdentity();
        RenderSystem.applyModelViewMatrix();

        render(context, 6, 73, 0);
        GlStateManager._disableDepthTest();
        ((MultiBufferSource.BufferSource)context.consumers()).endBatch();

        poseStack.popPose();
    }

    public void render(WorldRenderContext context, float x, float y, float z) {
        PoseStack stack = context.matrixStack();
        MultiBufferSource consumers = context.consumers();
        Vec3 camPos = context.camera().getPosition();

        stack.pushPose();
        stack.translate(-camPos.x, -camPos.y, -camPos.z);
        stack.translate(x, y, z);

        VertexConsumer consumer = consumers.getBuffer(ENTITY_CUTOUT_ALWAYS.apply(MissingTextureAtlasSprite.getLocation()));
        Matrix4f modelMatrix = stack.last().pose();
        Matrix3f normalMatrix = stack.last().normal();
        consumer.vertex(modelMatrix, 0, 1, 0).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, -1).endVertex();
        consumer.vertex(modelMatrix, 1, 1, 0).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, -1).endVertex();
        consumer.vertex(modelMatrix, 1, 0, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, -1).endVertex();
        consumer.vertex(modelMatrix, 0, 0, 0).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, -1).endVertex();

        stack.popPose();
    }

    public static final Function<ResourceLocation, RenderType> ENTITY_CUTOUT_ALWAYS = Util.memoize((texture) -> {
        RenderTypeBuilder builder = new RenderTypeBuilder("cutout_always", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false);
        builder.innerBuilder.setShaderState(RenderStateShards.shader(GameRenderer::getRendertypeEntityTranslucentShader)).setTextureState(RenderStateShards.texture(texture, false, false)).setTransparencyState(RenderStateShards.TRANSLUCENT_TRANSPARENCY).setCullState(RenderStateShards.NO_CULL).setLightmapState(RenderStateShard.LIGHTMAP).setOverlayState(RenderStateShard.OVERLAY).setDepthTestState(RenderStateShard.NO_DEPTH_TEST);
        return builder.build(false);
    });
}
