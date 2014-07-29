package com.ipsis.buildersguides.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRangeMarker extends TileBaseMarker {

    public TileRangeMarker() {

        super(false);
    }

    @Override
    public void rotateAround(ForgeDirection axis, EntityPlayer player) {

        setFacing(getFacing().getRotation(axis));
        findTargets(player);
    }
}
