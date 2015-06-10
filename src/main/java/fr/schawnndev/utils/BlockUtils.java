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
import java.util.HashSet;
import java.util.Set;

public class BlockUtils {

    public static Set<Byte> blockPassSet = new HashSet<>();
    public static Set<Byte> blockAirFoliageSet = new HashSet<>();
    public static Set<Byte> fullSolid = new HashSet<>();
    public static Set<Byte> blockUseSet = new HashSet<>();

    public static boolean solid(Block block) {
        return block != null && solid(block.getTypeId());
    }

    public static boolean solid(int block) {
        return solid((byte) block);
    }

    public static boolean solid(byte block) {
        if (blockPassSet.isEmpty()) {
            blockPassSet.add((byte) 0);
            blockPassSet.add((byte) 6);
            blockPassSet.add((byte) 8);
            blockPassSet.add((byte) 9);
            blockPassSet.add((byte) 10);
            blockPassSet.add((byte) 11);
            blockPassSet.add((byte) 26);
            blockPassSet.add((byte) 27);
            blockPassSet.add((byte) 28);
            blockPassSet.add((byte) 30);
            blockPassSet.add((byte) 31);
            blockPassSet.add((byte) 32);
            blockPassSet.add((byte) 37);
            blockPassSet.add((byte) 38);
            blockPassSet.add((byte) 39);
            blockPassSet.add((byte) 40);
            blockPassSet.add((byte) 50);
            blockPassSet.add((byte) 51);
            blockPassSet.add((byte) 55);
            blockPassSet.add((byte) 59);
            blockPassSet.add((byte) 63);
            blockPassSet.add((byte) 64);
            blockPassSet.add((byte) 65);
            blockPassSet.add((byte) 66);
            blockPassSet.add((byte) 68);
            blockPassSet.add((byte) 69);
            blockPassSet.add((byte) 70);
            blockPassSet.add((byte) 71);
            blockPassSet.add((byte) 72);
            blockPassSet.add((byte) 75);
            blockPassSet.add((byte) 76);
            blockPassSet.add((byte) 77);
            blockPassSet.add((byte) 78);
            blockPassSet.add((byte) 83);
            blockPassSet.add((byte) 90);
            blockPassSet.add((byte) 92);
            blockPassSet.add((byte) 93);
            blockPassSet.add((byte) 94);
            blockPassSet.add((byte) 96);
            blockPassSet.add((byte) 101);
            blockPassSet.add((byte) 102);
            blockPassSet.add((byte) 104);
            blockPassSet.add((byte) 105);
            blockPassSet.add((byte) 106);
            blockPassSet.add((byte) 107);
            blockPassSet.add((byte) 111);
            blockPassSet.add((byte) 115);
            blockPassSet.add((byte) 116);
            blockPassSet.add((byte) 117);
            blockPassSet.add((byte) 118);
            blockPassSet.add((byte) 119);
            blockPassSet.add((byte) 120);
            blockPassSet.add((byte) -85);
        }
        return !blockPassSet.contains(Byte.valueOf(block));
    }

    public static boolean airFoliage(Block block) {
        if (block == null) {
            return false;
        }
        return airFoliage(block.getTypeId());
    }

    public static boolean airFoliage(int block) {
        return airFoliage((byte) block);
    }

    public static boolean airFoliage(byte block) {
        if (blockAirFoliageSet.isEmpty()) {
            blockAirFoliageSet.add((byte) 0);
            blockAirFoliageSet.add((byte) 6);
            blockAirFoliageSet.add((byte) 31);
            blockAirFoliageSet.add((byte) 32);
            blockAirFoliageSet.add((byte) 37);
            blockAirFoliageSet.add((byte) 38);
            blockAirFoliageSet.add((byte) 39);
            blockAirFoliageSet.add((byte) 40);
            blockAirFoliageSet.add((byte) 51);
            blockAirFoliageSet.add((byte) 59);
            blockAirFoliageSet.add((byte) 104);
            blockAirFoliageSet.add((byte) 105);
            blockAirFoliageSet.add((byte) 115);
            blockAirFoliageSet.add((byte) -115);
            blockAirFoliageSet.add((byte) -114);
        }
        return blockAirFoliageSet.contains(Byte.valueOf(block));
    }

    public static boolean fullSolid(Block block) {
        if (block == null) {
            return false;
        }
        return fullSolid(block.getTypeId());
    }

    public static boolean fullSolid(int block) {
        return fullSolid((byte) block);
    }

    public static boolean fullSolid(byte block) {
        if (fullSolid.isEmpty()) {
            fullSolid.add((byte) 1);
            fullSolid.add((byte) 2);
            fullSolid.add((byte) 3);
            fullSolid.add((byte) 4);
            fullSolid.add((byte) 5);
            fullSolid.add((byte) 7);
            fullSolid.add((byte) 12);
            fullSolid.add((byte) 13);
            fullSolid.add((byte) 14);
            fullSolid.add((byte) 15);
            fullSolid.add((byte) 16);
            fullSolid.add((byte) 17);
            fullSolid.add((byte) 19);
            fullSolid.add((byte) 20);
            fullSolid.add((byte) 21);
            fullSolid.add((byte) 22);
            fullSolid.add((byte) 23);
            fullSolid.add((byte) 24);
            fullSolid.add((byte) 25);
            fullSolid.add((byte) 29);
            fullSolid.add((byte) 33);
            fullSolid.add((byte) 35);
            fullSolid.add((byte) 41);
            fullSolid.add((byte) 42);
            fullSolid.add((byte) 43);
            fullSolid.add((byte) 44);
            fullSolid.add((byte) 45);
            fullSolid.add((byte) 46);
            fullSolid.add((byte) 47);
            fullSolid.add((byte) 48);
            fullSolid.add((byte) 49);
            fullSolid.add((byte) 56);
            fullSolid.add((byte) 57);
            fullSolid.add((byte) 58);
            fullSolid.add((byte) 60);
            fullSolid.add((byte) 61);
            fullSolid.add((byte) 62);
            fullSolid.add((byte) 73);
            fullSolid.add((byte) 74);
            fullSolid.add((byte) 79);
            fullSolid.add((byte) 80);
            fullSolid.add((byte) 82);
            fullSolid.add((byte) 84);
            fullSolid.add((byte) 86);
            fullSolid.add((byte) 87);
            fullSolid.add((byte) 88);
            fullSolid.add((byte) 89);
            fullSolid.add((byte) 91);
            fullSolid.add((byte) 95);
            fullSolid.add((byte) 97);
            fullSolid.add((byte) 98);
            fullSolid.add((byte) 99);
            fullSolid.add((byte) 100);
            fullSolid.add((byte) 103);
            fullSolid.add((byte) 110);
            fullSolid.add((byte) 112);
            fullSolid.add((byte) 121);
            fullSolid.add((byte) 123);
            fullSolid.add((byte) 124);
            fullSolid.add((byte) 125);
            fullSolid.add((byte) 126);
            fullSolid.add((byte) -127);
            fullSolid.add((byte) -123);
            fullSolid.add((byte) -119);
            fullSolid.add((byte) -118);
            fullSolid.add((byte) -104);
            fullSolid.add((byte) -103);
            fullSolid.add((byte) -101);
            fullSolid.add((byte) -98);
        }
        return fullSolid.contains(Byte.valueOf(block));
    }

    public static boolean usable(Block block) {
        if (block == null) {
            return false;
        }
        return usable(block.getTypeId());
    }

    public static boolean usable(int block) {
        return usable((byte) block);
    }

    public static boolean usable(byte block) {
        if (blockUseSet.isEmpty()) {
            blockUseSet.add((byte) 23);
            blockUseSet.add((byte) 26);
            blockUseSet.add((byte) 33);
            blockUseSet.add((byte) 47);
            blockUseSet.add((byte) 54);
            blockUseSet.add((byte) 58);
            blockUseSet.add((byte) 61);
            blockUseSet.add((byte) 62);
            blockUseSet.add((byte) 64);
            blockUseSet.add((byte) 69);
            blockUseSet.add((byte) 71);
            blockUseSet.add((byte) 77);
            blockUseSet.add((byte) 93);
            blockUseSet.add((byte) 94);
            blockUseSet.add((byte) 96);
            blockUseSet.add((byte) 107);
            blockUseSet.add((byte) 116);
            blockUseSet.add((byte) 117);
            blockUseSet.add((byte) -126);
            blockUseSet.add((byte) -111);
            blockUseSet.add((byte) -110);
            blockUseSet.add((byte) -102);
            blockUseSet.add((byte) -98);
        }
        return blockUseSet.contains(Byte.valueOf(block));
    }

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
                        double offset = offset(loc, curBlock.getLocation().add(0.5D, 0.5D, 0.5D));
                        if (offset <= dR) {
                            blockList.put(curBlock, 1.0D - offset / dR);
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
                        blockList.put(curBlock, 1.0D - offset / dR);
                    }
                }
            }
        }
        return blockList;
    }
}
