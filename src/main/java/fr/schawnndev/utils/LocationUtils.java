/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.utils.LocationUtils) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 04/06/15 19:04.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static String locationToString(Location location, boolean withYawAndPitch){
        String loc = "";

        loc += location.getWorld().getName() + "|"; // WORLD
        loc += location.getX() + "|"; // X
        loc += location.getY() + "|"; // Y
        loc += location.getZ() + (withYawAndPitch ? "|" : ""); // Z

        if(withYawAndPitch){
            loc += location.getYaw() + "|"; // Yaw
            loc += location.getPitch(); // Pitch

        }

        return loc;
    }

    public static Location stringToLocation(String location, boolean withYawAndPitch){

        if(location == null) return null;

        String[] locs = location.split("|");

        World world = Bukkit.getWorld(locs[0]);
        double x = Double.parseDouble(locs[1]);
        double y = Double.parseDouble(locs[2]);
        double z = Double.parseDouble(locs[3]);

        return withYawAndPitch ? new Location(world, x, y, z, Float.parseFloat(locs[4]), Float.parseFloat(locs[5])) : new Location(world, x, y, z);
    }



}
