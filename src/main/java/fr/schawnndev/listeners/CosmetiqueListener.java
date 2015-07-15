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
import fr.schawnndev.reduction.ReductionManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CosmetiqueListener implements Listener {

    private static Map<UUID, CosmetiqueManager.Cosmetique> playerReduction = new HashMap<>();

    public CosmetiqueListener() {
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("§aCosmetiques List")) {
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                for (CosmetiqueManager.Cosmetique cosmetique : CosmetiqueManager.Cosmetique.values())
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b" + cosmetique.getMysqlName())) {
                        playerReduction.put(player.getUniqueId(), cosmetique);
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
                        player.sendMessage("§bVeuillez marquer une valeur entre 1 et 99 dans le chat!");
                        return;
                    }

            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {

        Player player = e.getPlayer();

        if (playerReduction.containsKey(player.getUniqueId())) {
            e.setCancelled(true);

            CosmetiqueManager.Cosmetique cosmetique = playerReduction.get(player.getUniqueId());
            int reduction;

            try {
                reduction = Integer.parseInt(e.getMessage());
            } catch (NumberFormatException ex) {
                player.sendMessage("§cERREUR : " + ex.getMessage() + " §c(§fLa réduction doit être un nombre§c)");
                return;
            }

            if (reduction < 1 || reduction > 99) {
                player.sendMessage("§cLe % de réduction doit être entre 1 et 99 !");
                return;
            }

            if (cosmetique.isVip()) {
                player.sendMessage("§cUne réduction ne peux être appliquée à une cosmétique VIP !");
                return;
            }

            if (!ReductionManager.hasReduction(cosmetique)) {
                ReductionManager.addReduction(cosmetique, reduction);
                player.sendMessage("§aTu as bien ajouté la réduction §f" + cosmetique.getMysqlName() + " §a|§f (-" + reduction + "%) §a!");
                System.out.println(player.getName() + " a ajoute la reduction " + cosmetique.getMysqlName() + " a " + new Date().toLocaleString());
            } else {
                player.sendMessage("§cCette cosmetique a déjà une réduction !");
            }
        }
    }
}
