package net.sorenon.fart;

import java.util.function.Supplier;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.Shader;
import net.minecraft.util.Identifier;

public class RenderStateShards {
    public static final RenderPhase.Transparency NO_TRANSPARENCY = RenderPhase.NO_TRANSPARENCY;
    public static final RenderPhase.Transparency ADDITIVE_TRANSPARENCY = RenderPhase.ADDITIVE_TRANSPARENCY;
    public static final RenderPhase.Transparency LIGHTNING_TRANSPARENCY = RenderPhase.LIGHTNING_TRANSPARENCY;
    public static final RenderPhase.Transparency GLINT_TRANSPARENCY = RenderPhase.GLINT_TRANSPARENCY;
    public static final RenderPhase.Transparency CRUMBLING_TRANSPARENCY = RenderPhase.CRUMBLING_TRANSPARENCY;
    public static final RenderPhase.Transparency TRANSLUCENT_TRANSPARENCY = RenderPhase.TRANSLUCENT_TRANSPARENCY;

    public static final RenderPhase.Texture BLOCK_SHEET_MIPPED = RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE;
    public static final RenderPhase.Texture BLOCK_SHEET = RenderPhase.BLOCK_ATLAS_TEXTURE;
    public static final RenderPhase.TextureBase NO_TEXTURE = RenderPhase.NO_TEXTURE;
    public static final RenderPhase.Texturing DEFAULT_TEXTURING = RenderPhase.DEFAULT_TEXTURING;
    public static final RenderPhase.Texturing GLINT_TEXTURING = RenderPhase.GLINT_TEXTURING;
    public static final RenderPhase.Texturing ENTITY_GLINT_TEXTURING = RenderPhase.ENTITY_GLINT_TEXTURING;
    public static final RenderPhase.Lightmap LIGHTMAP = RenderPhase.ENABLE_LIGHTMAP;
    public static final RenderPhase.Lightmap NO_LIGHTMAP = RenderPhase.DISABLE_LIGHTMAP;
    public static final RenderPhase.Overlay OVERLAY = RenderPhase.ENABLE_OVERLAY_COLOR;
    public static final RenderPhase.Overlay NO_OVERLAY = RenderPhase.DISABLE_OVERLAY_COLOR;

    public static final RenderPhase.Cull CULL = RenderPhase.ENABLE_CULLING;
    public static final RenderPhase.Cull NO_CULL = RenderPhase.DISABLE_CULLING;
    public static final RenderPhase.DepthTest NO_DEPTH_TEST = RenderPhase.ALWAYS_DEPTH_TEST;
    public static final RenderPhase.DepthTest EQUAL_DEPTH_TEST = RenderPhase.EQUAL_DEPTH_TEST;
    public static final RenderPhase.DepthTest LEQUAL_DEPTH_TEST = RenderPhase.LEQUAL_DEPTH_TEST;
    public static final RenderPhase.WriteMaskState COLOR_DEPTH_WRITE = RenderPhase.ALL_MASK;
    public static final RenderPhase.WriteMaskState COLOR_WRITE = RenderPhase.COLOR_MASK;
    public static final RenderPhase.WriteMaskState DEPTH_WRITE = RenderPhase.DEPTH_MASK;
    public static final RenderPhase.Layering NO_LAYERING = RenderPhase.NO_LAYERING;
    public static final RenderPhase.Layering POLYGON_OFFSET_LAYERING = RenderPhase.POLYGON_OFFSET_LAYERING;
    public static final RenderPhase.Layering VIEW_OFFSET_Z_LAYERING = RenderPhase.VIEW_OFFSET_Z_LAYERING;
    public static final RenderPhase.Target MAIN_TARGET = RenderPhase.MAIN_TARGET;
    public static final RenderPhase.Target OUTLINE_TARGET = RenderPhase.OUTLINE_TARGET;
    public static final RenderPhase.Target TRANSLUCENT_TARGET = RenderPhase.TRANSLUCENT_TARGET;
    public static final RenderPhase.Target PARTICLES_TARGET = RenderPhase.PARTICLES_TARGET;
    public static final RenderPhase.Target WEATHER_TARGET = RenderPhase.WEATHER_TARGET;
    public static final RenderPhase.Target CLOUDS_TARGET = RenderPhase.CLOUDS_TARGET;
    public static final RenderPhase.Target ITEM_ENTITY_TARGET = RenderPhase.ITEM_TARGET;
    public static final RenderPhase.LineWidth DEFAULT_LINE = RenderPhase.FULL_LINE_WIDTH;

    public static RenderPhase.Shader shader(Supplier<Shader> supplier) {
        return new RenderPhase.Shader(supplier);
    }

    public static RenderPhase.Texture texture(Identifier resourceLocation, boolean blur, boolean mipmap) {
        return new RenderPhase.Texture(resourceLocation, blur, mipmap);
    }
}
