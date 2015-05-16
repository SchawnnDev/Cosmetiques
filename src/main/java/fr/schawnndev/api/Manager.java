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

import fr.schawnndev.api.events.ClickEvent;
import fr.schawnndev.api.interfaces.Click;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Manager {

    public static Item getHead(Player player){

        Item head = new Item("head", -1, false, Material.SKULL, (short) 3, null, new Click() {
            @Override
            public void onClick(ClickEvent e) {
                e.setCancelled(true);
            }
        });


        SkullMeta im = (SkullMeta) head.getRawItemStack().getItemMeta();
        im.setOwner(player.getName());
        head.setItemMeta(im);

        //TODO: MySQL

        return head;
    }

}
