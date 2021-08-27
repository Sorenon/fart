package net.fabricmc.example;

import com.mojang.blaze3d.shaders.Shader;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class RenderStateShards {
    public static final RenderStateShard.TransparencyStateShard NO_TRANSPARENCY = RenderStateShard.NO_TRANSPARENCY;
    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = RenderStateShard.ADDITIVE_TRANSPARENCY;
    public static final RenderStateShard.TransparencyStateShard LIGHTNING_TRANSPARENCY = RenderStateShard.LIGHTNING_TRANSPARENCY;
    public static final RenderStateShard.TransparencyStateShard GLINT_TRANSPARENCY = RenderStateShard.GLINT_TRANSPARENCY;
    public static final RenderStateShard.TransparencyStateShard CRUMBLING_TRANSPARENCY = RenderStateShard.CRUMBLING_TRANSPARENCY;
    public static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = RenderStateShard.TRANSLUCENT_TRANSPARENCY;
    public static final RenderStateShard.TextureStateShard BLOCK_SHEET_MIPPED = RenderStateShard.BLOCK_SHEET_MIPPED;
    public static final RenderStateShard.TextureStateShard BLOCK_SHEET = RenderStateShard.BLOCK_SHEET;
    public static final RenderStateShard.EmptyTextureStateShard NO_TEXTURE = RenderStateShard.NO_TEXTURE;
    public static final RenderStateShard.TexturingStateShard DEFAULT_TEXTURING = RenderStateShard.DEFAULT_TEXTURING;
    public static final RenderStateShard.TexturingStateShard GLINT_TEXTURING = RenderStateShard.GLINT_TEXTURING;
    public static final RenderStateShard.TexturingStateShard ENTITY_GLINT_TEXTURING = RenderStateShard.ENTITY_GLINT_TEXTURING;
    public static final RenderStateShard.LightmapStateShard LIGHTMAP = RenderStateShard.LIGHTMAP;
    public static final RenderStateShard.LightmapStateShard NO_LIGHTMAP = RenderStateShard.NO_LIGHTMAP;
    public static final RenderStateShard.OverlayStateShard OVERLAY = RenderStateShard.OVERLAY;
    public static final RenderStateShard.OverlayStateShard NO_OVERLAY = RenderStateShard.NO_OVERLAY;
    public static final RenderStateShard.CullStateShard CULL = RenderStateShard.CULL;
    public static final RenderStateShard.CullStateShard NO_CULL = RenderStateShard.NO_CULL;
    public static final RenderStateShard.DepthTestStateShard NO_DEPTH_TEST = RenderStateShard.NO_DEPTH_TEST;
    public static final RenderStateShard.DepthTestStateShard EQUAL_DEPTH_TEST = RenderStateShard.EQUAL_DEPTH_TEST;
    public static final RenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST = RenderStateShard.LEQUAL_DEPTH_TEST;
    public static final RenderStateShard.WriteMaskStateShard COLOR_DEPTH_WRITE = RenderStateShard.COLOR_DEPTH_WRITE;
    public static final RenderStateShard.WriteMaskStateShard COLOR_WRITE = RenderStateShard.COLOR_WRITE;
    public static final RenderStateShard.WriteMaskStateShard DEPTH_WRITE = RenderStateShard.DEPTH_WRITE;
    public static final RenderStateShard.LayeringStateShard NO_LAYERING = RenderStateShard.NO_LAYERING;
    public static final RenderStateShard.LayeringStateShard POLYGON_OFFSET_LAYERING = RenderStateShard.POLYGON_OFFSET_LAYERING;
    public static final RenderStateShard.LayeringStateShard VIEW_OFFSET_Z_LAYERING = RenderStateShard.VIEW_OFFSET_Z_LAYERING;
    public static final RenderStateShard.OutputStateShard MAIN_TARGET = RenderStateShard.MAIN_TARGET;
    public static final RenderStateShard.OutputStateShard OUTLINE_TARGET = RenderStateShard.OUTLINE_TARGET;
    public static final RenderStateShard.OutputStateShard TRANSLUCENT_TARGET = RenderStateShard.TRANSLUCENT_TARGET;
    public static final RenderStateShard.OutputStateShard PARTICLES_TARGET = RenderStateShard.PARTICLES_TARGET;
    public static final RenderStateShard.OutputStateShard WEATHER_TARGET = RenderStateShard.WEATHER_TARGET;
    public static final RenderStateShard.OutputStateShard CLOUDS_TARGET = RenderStateShard.CLOUDS_TARGET;
    public static final RenderStateShard.OutputStateShard ITEM_ENTITY_TARGET = RenderStateShard.ITEM_ENTITY_TARGET;
    public static final RenderStateShard.LineStateShard DEFAULT_LINE = RenderStateShard.DEFAULT_LINE;

    public static RenderStateShard.ShaderStateShard shader(Supplier<ShaderInstance> supplier) {
        return new RenderStateShard.ShaderStateShard(supplier);
    }

    public static RenderStateShard.TextureStateShard texture(ResourceLocation resourceLocation, boolean blur, boolean mipmap) {
        return new RenderStateShard.TextureStateShard(resourceLocation, blur, mipmap);
    }
}
