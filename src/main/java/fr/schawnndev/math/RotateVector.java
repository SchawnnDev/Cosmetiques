/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.math.RotateVector) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 27/05/15 21:36.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.math;

import org.bukkit.util.Vector;

public class RotateVector {

    public static void rotateAroundX(Vector v, double a) {
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static void rotateAroundY(Vector v, double a) {
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static void rotateAroundZ(Vector v, double a) {
        double cos = Math.cos(a);
        double sin = Math.sin(a);
        double x = v.getX() * cos - v.getY() * sin;
        double y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }

    public static void rotateVector(Vector v, double aX, double aY, double aZ) {
        rotateAroundX(v, aX);
        rotateAroundY(v, aY);
        rotateAroundZ(v, aZ);
        return v;
    }


}
