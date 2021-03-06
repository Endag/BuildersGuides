package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Laser Marker
 *
 * No longer needed as the Axis one with disabled faces replaces it
 */
public class MarkerLaser extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return false;
        /* return t == MarkerType.LASER; */
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return te.hasValidV(f);
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            te.setV(side, 0);
        } else {
            te.setV(side, 1);
        }
        BlockUtils.markBlockForUpdate(worldIn, te.getPos());
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        // NOOP
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearClientData();

        for (EnumFacing f : EnumFacing.VALUES) {
            if (te.isFaceEnabled(f)) {
                for (int i = 1; i <= 64; i++) {
                    te.addToBlockList(new BlockPos(te.getPos().add(f.getFrontOffsetX() * i, f.getFrontOffsetY() * i, f.getFrontOffsetZ() * i)));
                }
            }
        }
    }
}
