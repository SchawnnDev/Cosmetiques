/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.utils.ResetBlock) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 09/06/15 21:34.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ResetBlock {

    @Getter
    private Location location;

    @Getter
    private Material material;

    @Getter
    private byte data;

    /**
     * @param location The location of the ResetBlock
     * @param material The start material before the set
     * @param data The start data before the set
     */

    public ResetBlock(Location location, Material material, byte data){
        this.location = location;
        this.material = material;
        this.data = data;
    }

    /**
     * Reset the block to the local material and data
     */

    public void reset(){
        Block block = location.getBlock();
        block.setType(material);
        block.setData(data);
    }

}
