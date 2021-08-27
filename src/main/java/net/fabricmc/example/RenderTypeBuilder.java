package net.fabricmc.example;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

public class RenderTypeBuilder {

    private final String name;
    private final VertexFormat format;
    private final VertexFormat.Mode drawMode;
    private final int expectedBufferSize;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;

    public final RenderType.CompositeState.CompositeStateBuilder innerBuilder;

    public RenderTypeBuilder(String name, VertexFormat format, VertexFormat.Mode drawMode, int expectedBufferSize, boolean affectsCrumbling, boolean sortOnUpload) {
        this.name = name;
        this.format = format;
        this.drawMode = drawMode;
        this.expectedBufferSize = expectedBufferSize;
        this.affectsCrumbling = affectsCrumbling;
        this.sortOnUpload = sortOnUpload;
        this.innerBuilder = RenderType.CompositeState.builder();
    }

    public RenderType.CompositeRenderType build(boolean affectsOutline) {
        return RenderType.create(name, format, drawMode, expectedBufferSize, affectsCrumbling, sortOnUpload, innerBuilder.createCompositeState(affectsOutline));
    }

    public static RenderType.CompositeState.CompositeStateBuilder builder() {
        return RenderType.CompositeState.builder();
    }

    public static RenderType.CompositeRenderType create(String name, VertexFormat format, VertexFormat.Mode drawMode, int expectedBufferSize, boolean affectsCrumbling, boolean sortOnUpload, RenderType.CompositeState compositeState) {
        return RenderType.create(name, format, drawMode, expectedBufferSize, affectsCrumbling, sortOnUpload, compositeState);
    }
}
