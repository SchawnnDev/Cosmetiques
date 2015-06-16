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
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GadgetListener implements Listener {

    public GadgetListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(PlayerInteractEvent e) {

        final Player player = e.getPlayer();

        /**
         *  14 juillet
         */


        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && e.getItem().getType() == Material.FIREWORK && e.getItem().getItemMeta().getDisplayName().equals("§514 juillet") && GadgetManager.hasGadget(e.getPlayer(), "artifice")) {

            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);

            player.getInventory().setItem(4, new ItemStack(Material.AIR));

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().setItem(4, ItemStackManager.getItemStack(CosmetiqueManager.Cosmetique.ARTIFICE));
                    player.updateInventory();
                }

            }.runTaskLater(LCCosmetiques.getInstance(), 2l);

            if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.ARTIFICE)) {
                player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.ARTIFICE));
                return;
            } else {
                GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.ARTIFICE, 15, true));
                final UUID uuid = player.getUniqueId();

                GadgetManager.getGadgetArtifice().start(uuid);

                return;
            }
        }

        /**
         *  Apple
         */

        if (e.getItem() != null && e.getItem().hasItemMeta()
                && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getType() == Material.GOLDEN_APPLE && e.getItem().getItemMeta().getDisplayName().equals("§eApple") && GadgetManager.hasGadget(e.getPlayer(), "apple")) {

            e.setCancelled(true);
            player.getInventory().setItem(4, new ItemStack(Material.AIR));

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().setItem(4, ItemStackManager.getItemStack(CosmetiqueManager.Cosmetique.APPLE));
                    player.updateInventory();
                }

            }.runTaskLater(LCCosmetiques.getInstance(), 2l);

            if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.APPLE)) {
                player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.APPLE));
                return;
            } else {
                GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.APPLE, 40, true));
                final UUID uuid = player.getUniqueId();

                GadgetManager.getGadgetApple().start(uuid);

                return;
            }
        }

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
                    e.setUseItemInHand(Event.Result.DENY);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.getInventory().setItem(4, ItemStackManager.getItemStack(CosmetiqueManager.Cosmetique.GLACE));
                            player.updateInventory();
                        }

                    }.runTaskLater(LCCosmetiques.getInstance(), 2l);

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
