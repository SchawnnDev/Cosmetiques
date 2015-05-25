/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.listeners.ServerListener) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 17:26.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.listeners;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Manager;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerListener implements Listener {

    public ServerListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        final Player player = e.getPlayer();

        CosmetiqueManager.setCosmetique(e.getPlayer(), CosmetiqueManager.getCosmetiqueFromString(SQLManager.getActiveCosmetic(e.getPlayer())));

        new BukkitRunnable(){
            @Override
            public void run() {
                if(CosmetiqueManager.getCosmetique(player) != CosmetiqueManager.Cosmetique.AUCUN)
                    player.getInventory().setItem(4, CosmetiqueManager.getPlayerItem(CosmetiqueManager.getCosmetique(player)));
                player.getInventory().setItem(8, MenuManager.getCosmeticItem());
            }
        }.runTaskLater(LCCosmetiques.getInstance(), 20L);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        SQLManager.setActiveCosmetic(e.getPlayer(), CosmetiqueManager.getCosmetiqueString(e.getPlayer()));
        CosmetiqueManager.removeCosmetique(e.getPlayer());

        if(Manager.playersBuying.contains(e.getPlayer().getUniqueId()))
            Manager.playersBuying.remove(e.getPlayer().getUniqueId());
        if(Manager.hasAchat(e.getPlayer().getUniqueId()))
            Manager.achats.remove(Manager.getAchat(e.getPlayer().getUniqueId()));

    }

}
