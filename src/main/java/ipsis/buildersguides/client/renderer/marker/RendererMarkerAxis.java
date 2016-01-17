package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.EnumFacing;

public class RendererMarkerAxis extends RendererMarker {
    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RendererMarker.RENDER_ALPHA);

            for (EnumFacing f : EnumFacing.values()) {
                RenderUtils.drawLine(
                        0.0F, 0.0F, 0.0F,
                        0.0F + (f.getFrontOffsetX() * 64.0F),
                        0.0F + (f.getFrontOffsetY() * 64.0F),
                        0.0F + (f.getFrontOffsetZ() * 64.0F));
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
