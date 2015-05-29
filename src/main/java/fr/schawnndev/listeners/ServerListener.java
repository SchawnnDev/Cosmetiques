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
import fr.schawnndev.particules.ParticleManager;
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

        if(SQLManager.hasActiveCosmetic(e.getPlayer(), CosmetiqueManager.CosmetiqueType.PARTICLE)){

            String particle = SQLManager.getActiveCosmetic(e.getPlayer(), CosmetiqueManager.CosmetiqueType.PARTICLE);

            ParticleManager.activeParticleByName(e.getPlayer(), particle);

        }


        new BukkitRunnable(){
            @Override
            public void run() {
                player.getInventory().setItem(8, MenuManager.getCosmeticItem());
            }
        }.runTaskLater(LCCosmetiques.getInstance(), 3l);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        if(ParticleManager.getActiveParticles().containsKey(e.getPlayer().getUniqueId())) {
            SQLManager.setActiveCosmetic(e.getPlayer(), ParticleManager.getActiveParticles().get(e.getPlayer().getUniqueId()), CosmetiqueManager.CosmetiqueType.PARTICLE);
            ParticleManager.getActiveParticles().remove(e.getPlayer().getUniqueId());
        } else {
            SQLManager.setActiveCosmetic(e.getPlayer(), "aucun", CosmetiqueManager.CosmetiqueType.PARTICLE);
        }

        if(Manager.playersBuying.contains(e.getPlayer().getUniqueId()))
            Manager.playersBuying.remove(e.getPlayer().getUniqueId());

        if(Manager.hasAchat(e.getPlayer().getUniqueId()))
            Manager.achats.remove(Manager.getAchat(e.getPlayer().getUniqueId()));

    }

}
