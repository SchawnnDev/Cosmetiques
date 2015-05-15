/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.utils.GlassColor) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 15/05/15 17:28.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api.utils;

import lombok.Getter;

public enum GlassColor {

    WHITE((byte) 0),

    ORANGE((byte) 1),

    MAGENTA((byte) 2),

    LIGHT_BLUE((byte) 3),

    YELLOW((byte) 4),

    LIME((byte) 5),

    PINK((byte) 6),

    GRAY((byte) 7),

    LIGHT_GRAY((byte) 8),

    CYAN((byte) 9),

    PURPLE((byte) 10),

    BLUE((byte) 11),

    BROWN((byte) 12),

    GREEN((byte) 13),

    RED((byte) 14),

    BLACK((byte) 15);

    @Getter
    private byte data;

    private GlassColor(byte data){
        this.data = data;
    }
}
