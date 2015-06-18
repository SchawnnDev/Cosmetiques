/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetGateau) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 18/06/15 20:02.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Cake;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GadgetGateauEmpoisonne extends Gadget implements Listener {

    public GadgetGateauEmpoisonne(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.GATEAU_EMPOISONNE;

    private List<UUID> playerEatedCake = new ArrayList<>();

    @Override
    public void start(UUID uuid){
        // useless
    }

    public boolean setCake(Location location){
        location.add(0d, 1d, 0d);

        if(location.getBlock().isEmpty()){
            location.getBlock().setType(Material.CAKE_BLOCK);
            location.getBlock().setMetadata("gadget_gateauempoisonne", new FixedMetadataValue(LCCosmetiques.getInstance(), "slt"));
            return true;
        } else {
            return false;
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CAKE_BLOCK
                && e.getClickedBlock().hasMetadata("gadget_gateauempoisonne")){

            e.setCancelled(true);

            final Player player = e.getPlayer();

            if(playerEatedCake.contains(player.getUniqueId())){
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                player.sendMessage("§7Tu en as déjà mangé ! Il faut digérer un peu..");
                return;
            }

            playerEatedCake.add(player.getUniqueId());

            new BukkitRunnable(){
                @Override
                public void run() {
                    playerEatedCake.remove(player.getUniqueId());
                }
            }.runTaskLater(LCCosmetiques.getInstance(), 20*5);

            Cake c = (Cake) e.getClickedBlock().getState().getData();

            if (c.getSlicesRemaining() - 1 < 0) {
                e.getClickedBlock().setType(Material.AIR);
            } else {
                c.setSlicesRemaining(c.getSlicesRemaining() - 1);
            }
            e.getClickedBlock().setData(c.getData());

            player.playSound(player.getLocation(), Sound.EAT, 1.0f, 2.0f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40 * 5, 9));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40 * 5, 1));

            new BukkitRunnable(){
                @Override
                public void run() {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40*4 + 20, 0));
                    player.playSound(player.getLocation(), Sound.GHAST_SCREAM, 4.0f, 4.0f);
                }
            }.runTaskLater(LCCosmetiques.getInstance(), 30l);

        }

    }

}