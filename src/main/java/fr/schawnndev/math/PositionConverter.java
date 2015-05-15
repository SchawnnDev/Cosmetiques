/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.math.PositionConverter) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 15/05/15 16:49.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.math;

import org.bukkit.Location;

import java.util.Random;

public class PositionConverter {

    /**
     * @param colonne Position sur 9 dans l'inventaire (ligne horizontale)
     * @param ligne Postion sur + 6 dans l'inventaire (ligne verticale)
     * @return Le nombre où doit être l'item
     * @author SchawnnDev
     */

    public int convert(int colonne, int ligne) {
        return (ligne == 1) ? (colonne - 1) : (((ligne == 1) ? 0 : ((ligne - 1) * 9) + colonne) - 1);
    }

    /**
     * @param loc Location du block qui veut savoir le centre
     * @return Location du millieu du block
     */

    public Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
                getRelativeCoord(loc.getBlockX()),
                getRelativeCoord(loc.getBlockY()),
                getRelativeCoord(loc.getBlockZ()));
    }

    private double getRelativeCoord(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }

    /**
     * @param l Location <b>Location début</b>
     * @return Location <b>Random</b>
     */

    public Location getRandomDirection(Location l) {
        return new Location(l.getWorld(), l.getX(), l.getY(), l.getZ(), l.getYaw(), (float) ((new Random().nextBoolean()) ? l.getPitch() + Randoms.randomRange(0.0, 30.0) : l.getPitch() - Randoms.randomRange(0.0, 30.0)));
    }

}
