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

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class GadgetListener implements Listener {

    public GadgetListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null){ // Gadgets with click on blocks

            if(e.getItem() != null && e.getItem().hasItemMeta()
                    && e.getItem().getItemMeta().getDisplayName() != null) {

                if (e.getItem().getType() == Material.TNT && e.getItem().getItemMeta().getDisplayName().equals("§7TNT") && GadgetManager.hasGadget(e.getPlayer(), "tnt")) {

                    /**
                     *  TNT
                     */

                    if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.TNT)) {
                        player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.TNT));
                        return;
                    } else {
                        GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.TNT, 15, true));
                        final UUID uuid = player.getUniqueId();

                        GadgetManager.getGadgetTNT().setBomb(e.getClickedBlock().getLocation());

                        for (Player p : Bukkit.getOnlinePlayers())
                            if (p.getLocation().distance(e.getClickedBlock().getLocation()) <= 15)
                                p.playSound(p.getLocation(), Sound.FUSE, 4f, 4f);

                        return;
                    }
                }
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) { // Gadgets with click on air

            if(e.getItem() != null && e.getItem().hasItemMeta()
                    && e.getItem().getItemMeta().getDisplayName() != null){

                /**
                 *  Fireball
                 */


                if (e.getItem().getType() == Material.FIREBALL && e.getItem().getItemMeta().getDisplayName().equals("§2I believe I can Fly")) {
                    final UUID uuid = player.getUniqueId();

                    GadgetManager.getGadgetFireBall().start(uuid);

                }

                /**
                 *  Encre
                 */

                if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getItem().getType() == Material.INK_SACK && e.getItem().getItemMeta().getDisplayName().equals("§cEncre") && GadgetManager.hasGadget(e.getPlayer(), "encre")) {

                    if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.ENCRE)) {
                        player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.ENCRE));
                        return;
                    } else {
                        GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.ENCRE, 25, true));
                        final UUID uuid = player.getUniqueId();

                        GadgetManager.getGadgetEncre().start(uuid);

                        return;
                    }
                }

                /**
                 *  Glace
                 */

                if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getItem().getType() == Material.SNOW_BALL && e.getItem().getItemMeta().getDisplayName().equals("§cça glisse !") && GadgetManager.hasGadget(e.getPlayer(), "glace")) {

                    e.setCancelled(true);

                    player.getInventory().setItem(4, ItemStackManager.getItemStackByName("glace"));

                    if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.GLACE)) {
                        player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.GLACE));
                        return;
                    } else {
                        GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.GLACE, 40, true));
                        final UUID uuid = player.getUniqueId();

                        GadgetManager.getGadgetGlace().start(uuid);

                        return;
                    }
                }

                /**
                 *  Canon
                 */

                if (e.getAction() == Action.RIGHT_CLICK_AIR && e.getItem().getType() == Material.SULPHUR && e.getItem().getItemMeta().getDisplayName().equals("§fCanon") && GadgetManager.hasGadget(e.getPlayer(), "canon")) {

                    if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.CANON)) {
                        player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.CANON));
                        return;
                    } else {
                        GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.CANON, 15, true));
                        final UUID uuid = player.getUniqueId();

                        GadgetManager.getGadgetCanon().start(uuid);

                        return;
                    }
                }



            }
        }
    }
}
