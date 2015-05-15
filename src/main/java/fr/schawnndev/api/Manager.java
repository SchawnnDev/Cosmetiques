/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.Manager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 15/05/15 17:21.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Manager {

    public static ItemStack getHead(Player player){
        ItemStack head = new ItemStack(8 /* Head ItemStack */);

        //TODO: MySQL

        return head;
    }

}
