package net.sorenon.fart;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

//TODO everything

public class FartRenderEvents {
    private static final boolean CANVAS = FabricLoader.getInstance().isModLoaded("canvas");

    @Environment(EnvType.CLIENT)
    @FunctionalInterface
    public interface FartRenderEvent {
        void run(WorldRenderContext context);
    }

    public static final Event<FartRenderEvent> BEFORE_BLOCK_OUTLINE = EventFactory.createArrayBacked(FartRenderEvent.class, context -> {
    }, callbacks -> context -> {
        for (final FartRenderEvent callback : callbacks) {
            callback.run(context);
        }
    });

    public static final Event<FartRenderEvent> AFTER_TRANSLUCENT = EventFactory.createArrayBacked(FartRenderEvent.class, context -> {
    }, callbacks -> context -> {
        for (final FartRenderEvent callback : callbacks) {
            callback.run(context);
        }
    });

    public static final Event<FartRenderEvent> LAST = EventFactory.createArrayBacked(FartRenderEvent.class, context -> {
    }, callbacks -> context -> {
        for (final FartRenderEvent callback : callbacks) {
            callback.run(context);
        }
    });

    public static void canvasFixPre(MatrixStack stack) {
        if (CANVAS) {
            stack.push();
            stack.loadIdentity();
        }
    }

    public static void canvasFixPost(MatrixStack stack) {
        if (CANVAS) {
            stack.pop();
        }
    }

    static {
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, hitResult) -> {
            canvasFixPre(context.matrixStack());
            BEFORE_BLOCK_OUTLINE.invoker().run(context);
            canvasFixPost(context.matrixStack());
            return true;
        });
        WorldRenderEvents.AFTER_TRANSLUCENT.register((context) -> {
            canvasFixPre(context.matrixStack());
            GlStateManager._disableDepthTest();
            AFTER_TRANSLUCENT.invoker().run(context);
            ((VertexConsumerProvider.Immediate) context.consumers()).draw();
            canvasFixPost(context.matrixStack());
        });
        WorldRenderEvents.LAST.register((context) -> {
//            canvasFixPre(context.matrixStack());
            MatrixStack poseStack = RenderSystem.getModelViewStack();
            poseStack.push();
            poseStack.loadIdentity();
            RenderSystem.applyModelViewMatrix();
            GlStateManager._disableDepthTest();
            LAST.invoker().run(context);
            ((VertexConsumerProvider.Immediate) context.consumers()).draw();
            poseStack.pop();
            RenderSystem.applyModelViewMatrix();
//            canvasFixPost(context.matrixStack());
        });
    }
}
