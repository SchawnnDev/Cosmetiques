/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.MenuManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 19:15.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.menus;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuManager {

    @Getter
    private static ItemStack cosmeticItem;

    public MenuManager(){

        // Player item

        cosmeticItem = new ItemStack(Material.JUKEBOX);

        ItemMeta itemMeta = cosmeticItem.getItemMeta();
        itemMeta.setDisplayName("§f=== §5Cosmétiques §f===  ");
        cosmeticItem.setItemMeta(itemMeta);

    }

}
