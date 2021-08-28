package net.sorenon.fart;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;

public class RenderTypeBuilder {

    private final String name;
    private final VertexFormat format;
    private final VertexFormat.DrawMode drawMode;
    private final int expectedBufferSize;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;

    public final RenderLayer.MultiPhaseParameters.Builder innerBuilder;

    public RenderTypeBuilder(String name, VertexFormat format, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean affectsCrumbling, boolean sortOnUpload) {
        this.name = name;
        this.format = format;
        this.drawMode = drawMode;
        this.expectedBufferSize = expectedBufferSize;
        this.affectsCrumbling = affectsCrumbling;
        this.sortOnUpload = sortOnUpload;
        this.innerBuilder = RenderLayer.MultiPhaseParameters.builder();
    }

    public RenderLayer.MultiPhase build(boolean affectsOutline) {
        return create(name, format, drawMode, expectedBufferSize, affectsCrumbling, sortOnUpload, innerBuilder.build(affectsOutline));
    }

    public static RenderLayer.MultiPhaseParameters.Builder makeBuilder() {
        return RenderLayer.MultiPhaseParameters.builder();
    }

    public static RenderLayer.MultiPhase create(String name, VertexFormat format, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean affectsCrumbling, boolean sortOnUpload, RenderLayer.MultiPhaseParameters compositeState) {
        return RenderLayer.of(name, format, drawMode, expectedBufferSize, affectsCrumbling, sortOnUpload, compositeState);
    }
}
