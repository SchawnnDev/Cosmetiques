/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.GadgetListener) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 03/06/15 21:22.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets;

import fr.schawnndev.LCCosmetiques;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class GadgetListener implements Listener {

    public GadgetListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null){ // Gadgets with click on blocks

            if(e.getItem() != null && e.getItem().hasItemMeta()
                    && e.getItem().getItemMeta().getDisplayName() != null) {

                if (e.getItem().getType() == Material.TNT && e.getItem().getItemMeta().getDisplayName().equals("§7TNT") && GadgetManager.hasGadget(e.getPlayer(), "tnt")) {
                    final UUID uuid = e.getPlayer().getUniqueId();

                    GadgetManager.getGadgetTNT().setBomb(e.getClickedBlock().getLocation());

                    for(Player player : Bukkit.getOnlinePlayers())
                        if(player.getLocation().distance(e.getClickedBlock().getLocation()) <= 15)
                            player.playSound(player.getLocation(), Sound.FUSE, 4f, 4f);

                }

            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) { // Gadgets with click on air

            if(e.getItem() != null && e.getItem().hasItemMeta()
                    && e.getItem().getItemMeta().getDisplayName() != null){

                if (e.getItem().getType() == Material.FIREBALL && e.getItem().getItemMeta().getDisplayName().equals("§2I believe I can Fly")) {
                    final UUID uuid = e.getPlayer().getUniqueId();

                    GadgetManager.getGadgetFireBall().start(uuid);

                }



            }
        }
    }
}
