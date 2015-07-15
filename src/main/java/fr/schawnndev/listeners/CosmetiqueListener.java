/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.listeners.CosmetiqueListener) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 15/07/15 17:47.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.listeners;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CosmetiqueListener implements Listener {

    public CosmetiqueListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("Particules")) {
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                for(CosmetiqueManager.Cosmetique cosmetique : CosmetiqueManager.Cosmetique.values())
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b" + cosmetique.getMysqlName())){
                        return;
                    }

            }
        }

    }

}
