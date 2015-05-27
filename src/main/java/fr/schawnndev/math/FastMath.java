/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.math.FastMath) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 27/05/15 18:52.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.math;

public class FastMath {

    /**
     *  Special float sin/cos
     */

    private static final class Sin
    {
        static final float[] table = new float[16384];

        static
        {
            for (int i = 0; i < 16384; i++) {
                table[i] = ((float)Math.sin((i + 0.5F) / 16384.0F * 6.283186F));
            }
            for (int i = 0; i < 360; i += 90) {
                table[((int)(i * 45.511112F) & 0x3FFF)] = ((float)Math.sin(i * 0.01745329F));
            }
        }
    }

    public static final float sin(float radians)
    {
        return Sin.table[((int)(radians * 2607.5945F) & 0x3FFF)];
    }

    public static final float cos(float radians)
    {
        return Sin.table[((int)((radians + 1.570796F) * 2607.5945F) & 0x3FFF)];
    }

    public static final float sinDeg(float degrees)
    {
        return Sin.table[((int)(degrees * 45.511112F) & 0x3FFF)];
    }

    public static final float cosDeg(float degrees)
    {
        return Sin.table[((int)((degrees + 90.0F) * 45.511112F) & 0x3FFF)];
    }

}
