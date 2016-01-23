package ipsis.buildersguides.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    /**
     * Return the block position of the n'th non-air block in the facing direction
     * Return the currPos value if no block found within maxDistance blcoks away
     */
    public static BlockPos getNthBlock(World world, BlockPos currPos, EnumFacing facing, int step) {
        return getNthBlock(world, currPos, facing, step, 64);
    }

    public static BlockPos getNthBlock(World world, BlockPos currPos, EnumFacing facing, int step, int maxDistance) {

        int count = 0;
        BlockPos found = new BlockPos(currPos);
        for (int i = 1; i <= maxDistance; i++) {
            BlockPos c = currPos.offset(facing, i);
            if (!world.isAirBlock(c)) {
                count ++;
                if (count == step) {
                    found = new BlockPos(c);
                    break;
                }
            }
        }
        return found;
    }

    public static class PlaneInfo {
        public List<BlockPos> blockList;
        public List<BlockPos> centerList;

        public PlaneInfo() {
            blockList = new ArrayList<>();
            centerList = new ArrayList<>();
        }
    }

    // TODO BlockPos has a getAllInBox that returns a list of BlockPos - can we use it???
    public static PlaneInfo getPlaneBlockList(BlockPos origin, EnumFacing axisA, EnumFacing axisB, int distA, int distB) {

        PlaneInfo planeInfo = new PlaneInfo();

        if (axisA == axisB)
            return planeInfo;

        for (int i = 0; i <= distA; i++) {
            for (int j = 0; j <= distB; j++) {
                planeInfo.blockList.add(origin.offset(axisA, i).offset(axisB, j));
            }
        }

        // Near sides
        BlockPos p1;
        p1 = origin.offset(axisA, distA);
        List<BlockPos> centersA = getCenterBlockList(origin, p1, axisA);
        p1 = origin.offset(axisB, distB);
        List<BlockPos> centersB = getCenterBlockList(origin, p1, axisB);

        for (BlockPos pA : centersA) {
            for (BlockPos pB : centersB) {
                if (axisA == EnumFacing.UP || axisA == EnumFacing.DOWN) {
                    planeInfo.centerList.add(new BlockPos(pB.getX(), pA.getY(), pB.getZ()));
                } else if (axisA == EnumFacing.NORTH || axisA == EnumFacing.SOUTH) {
                    planeInfo.centerList.add(new BlockPos(pB.getX(), pB.getY(), pA.getZ()));
                } else if (axisA == EnumFacing.EAST || axisA == EnumFacing.WEST) {
                    planeInfo.centerList.add(new BlockPos(pA.getX(), pB.getY(), pA.getZ()));
                }
            }
        }

        planeInfo.centerList.addAll(centersA);
        planeInfo.centerList.addAll(centersB);

        BlockPos p2;
        p1 = origin.offset(axisA, distA);
        p2 = origin.offset(axisA, distA).offset(axisB, distB);
        planeInfo.centerList.addAll(getCenterBlockList(p1, p2, axisB));

        p1 = origin.offset(axisB, distB);
        p2 = origin.offset(axisB, distB).offset(axisA, distA);
        planeInfo.centerList.addAll(getCenterBlockList(p1, p2, axisA));

        return planeInfo;
    }

    public static List<BlockPos> getCenterBlockList(BlockPos p1, BlockPos p2, EnumFacing facing) {

        List<BlockPos> centerList = new ArrayList<>();
        int count = numBlocksBetween(p1, p2);
        if (count == 0) {
            /* no center */
        } else if (count % 2 == 0) {
            /* even */
            count /= 2;
            centerList.add(p1.offset(facing, count));
            centerList.add(p1.offset(facing, count + 1));
        } else {
            /* odd */
            centerList.add(p1.offset(facing, (count / 2) + 1));
        }

        return centerList;
    }

    /**
     * Number of blocks between the two positions
     */
    public static int numBlocksBetween(BlockPos p1, BlockPos p2) {

        if (p1.equals(p2))
            return 0;

        /* Must be a change in only one direction */
        if ((p1.getX() == p2.getX() && p1.getY() == p2.getY()) ||
            (p1.getY() == p2.getY() && p1.getZ() == p2.getZ()) ||
            (p1.getX() == p2.getX() && p1.getZ() == p2.getZ())) {

            int d = Math.abs((p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) + (p1.getZ() - p2.getZ())) - 1;
            if (d < 0)
                d = 0;

            return d;
        }

        return 0;
    }
}
