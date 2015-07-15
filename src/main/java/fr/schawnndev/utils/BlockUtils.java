/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.utils.BlockUtils) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 10/06/15 13:50.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class BlockUtils {

    public static double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static HashMap<Block, Double> getInRadius(Location loc, double dR, boolean getHightest) {
        return getInRadius(loc, dR, 999.0D);
    }

    public static HashMap<Block, Double> getInRadius(Location loc, double dR, double heightLimit) {
        HashMap<Block, Double> blockList = new HashMap<>();
        int iR = (int) dR + 1;
        for (int x = -iR; x <= iR; x++) {
            for (int z = -iR; z <= iR; z++) {
                for (int y = -iR; y <= iR; y++) {
                    if (Math.abs(y) <= heightLimit) {
                        Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y), (int) (loc.getZ() + z));
                        double offset = offset(loc, curBlock.getLocation().add(0.5d, 0.5d, 0.5d));
                        if (offset <= dR) {
                            blockList.put(curBlock, 1.0d - offset / dR);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    public static HashMap<Block, Double> getInRadius(Block block, double dR) {
        HashMap<Block, Double> blockList = new HashMap<>();
        int iR = (int) dR + 1;
        for (int x = -iR; x <= iR; x++) {
            for (int z = -iR; z <= iR; z++) {
                for (int y = -iR; y <= iR; y++) {
                    Block curBlock = block.getRelative(x, y, z);

                    double offset = offset(block.getLocation(), curBlock.getLocation());
                    if (offset <= dR) {
                        blockList.put(curBlock, 1.0d - offset / dR);
                    }
                }
            }
        }
        return blockList;
    }
}
