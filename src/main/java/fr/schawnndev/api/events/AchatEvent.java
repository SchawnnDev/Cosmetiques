/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.events.AchatEvent) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 25/05/15 09:39.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api.events;

import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Achat;
import fr.schawnndev.api.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AchatEvent implements Listener {

    public AchatEvent(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if(Manager.playersBuying.contains(player.getUniqueId()) && e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().contains("§6Achat:")) {

            Achat achat = Manager.getAchat(player.getUniqueId());

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(achat != null && achat.isOpened() && !achat.isFinishToPay()){
                if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                    switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
                        case "§aValider":
                            achat.buy();
                            break;
                        case "§cAnnuler":
                            achat.finish(false, true);
                            break;
                        default:
                            break;
                    }
                }
            }



        }
    }

}
