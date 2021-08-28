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
    public interface ZhuLi {
        void doTheThing(WorldRenderContext context);
    }

    public static final Event<ZhuLi> BEFORE_BLOCK_OUTLINE = EventFactory.createArrayBacked(ZhuLi.class, context -> {
    }, callbacks -> context -> {
        for (final ZhuLi callback : callbacks) {
            callback.doTheThing(context);
        }
    });

    public static final Event<ZhuLi> AFTER_TRANSLUCENT = EventFactory.createArrayBacked(ZhuLi.class, context -> {
    }, callbacks -> context -> {
        for (final ZhuLi callback : callbacks) {
            callback.doTheThing(context);
        }
    });

    public static final Event<ZhuLi> LAST = EventFactory.createArrayBacked(ZhuLi.class, context -> {
    }, callbacks -> context -> {
        for (final ZhuLi callback : callbacks) {
            callback.doTheThing(context);
        }
    });

    private static void canvasFixPre(MatrixStack stack) {
        if (CANVAS) {
            stack.push();
            stack.loadIdentity();
        }
    }

    private static void canvasFixPost(MatrixStack stack) {
        if (CANVAS) {
            stack.pop();
        }
    }

    static {
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, hitResult) -> {
            canvasFixPre(context.matrixStack());
            BEFORE_BLOCK_OUTLINE.invoker().doTheThing(context);
            canvasFixPost(context.matrixStack());
            return true;
        });
        WorldRenderEvents.AFTER_TRANSLUCENT.register((context) -> {
            canvasFixPre(context.matrixStack());
            GlStateManager._disableDepthTest();
            AFTER_TRANSLUCENT.invoker().doTheThing(context);
            ((VertexConsumerProvider.Immediate) context.consumers()).draw();
            canvasFixPost(context.matrixStack());
        });
        WorldRenderEvents.LAST.register((context) -> {
            canvasFixPre(context.matrixStack());
            MatrixStack poseStack = RenderSystem.getModelViewStack();
            poseStack.push();
            poseStack.loadIdentity();
            RenderSystem.applyModelViewMatrix();
            GlStateManager._disableDepthTest();
            LAST.invoker().doTheThing(context);
            ((VertexConsumerProvider.Immediate) context.consumers()).draw();
            poseStack.pop();
            canvasFixPost(context.matrixStack());
        });
    }
}
