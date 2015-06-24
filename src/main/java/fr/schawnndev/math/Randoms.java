/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.math.Randoms) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 15/05/15 16:51.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.math;

import java.util.Random;

public class Randoms {

    final static Random r = new Random();

    /**
     * @param min Nombre minimum (double)
     * @param max Nombre maximum (double)
     * @return Un random entre min et max
     * @author SchawnnDev
     */

    public static double randomRange(double min, double max) {
        return min + r.nextDouble() * (max - min);
    }

    /**
     * @param min Nombre minimum (float)
     * @param max Nombre maximum (float)
     * @return Un random entre min et max
     * @author SchawnnDev
     */

    public static float randomRangeFloat(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }

    /**
     * @param min Nombre minimum (integer)
     * @param max Nombre maximum (integer)
     * @return Un random entre min et max
     * @author SchawnnDev
     */

    public static int randomRangeInt(int min, int max) {
        return min + r.nextInt(max - min);
    }

    /**
     * @param min Nombre minimum (integer)
     * @param max Nombre maximum (integer)
     * @return Un random entre min et max
     * @author SchawnnDev
     */

    public static byte randomRangeByte(int min, int max) {
        return (byte) randomRangeInt(min, max);
    }

}
