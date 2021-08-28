package net.sorenon.fart;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;

public class RenderTypeBuilder {

    private final String name;
    private final VertexFormat format;
    private final VertexFormat.DrawMode drawMode;
    private final int expectedBufferSize;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;

    public final RenderLayer.MultiPhaseParameters.Builder innerBuilder;

    public RenderTypeBuilder(Identifier name, VertexFormat format, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean affectsCrumbling, boolean sortOnUpload) {
        this.name = "fart_" + name.toString();
        this.format = format;
        this.drawMode = drawMode;
        this.expectedBufferSize = expectedBufferSize;
        this.affectsCrumbling = affectsCrumbling;
        this.sortOnUpload = sortOnUpload;
        this.innerBuilder = RenderLayer.MultiPhaseParameters.builder();
    }

    public RenderLayer.MultiPhase build(boolean affectsOutline) {
        return createInner(name, format, drawMode, expectedBufferSize, affectsCrumbling, sortOnUpload, innerBuilder.build(affectsOutline));
    }

    public static RenderLayer.MultiPhaseParameters.Builder makeInnerBuilder() {
        return RenderLayer.MultiPhaseParameters.builder();
    }

    public static RenderLayer.MultiPhase createInner(String name, VertexFormat format, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean affectsCrumbling, boolean sortOnUpload, RenderLayer.MultiPhaseParameters compositeState) {
        return RenderLayer.of(name, format, drawMode, expectedBufferSize, affectsCrumbling, sortOnUpload, compositeState);
    }
}
