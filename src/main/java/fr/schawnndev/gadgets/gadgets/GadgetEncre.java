/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetEncre) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 09/06/15 20:13.
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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GadgetEncre extends Gadget implements Listener {

    public GadgetEncre(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.ENCRE;

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(e.getItem() != null && e.getItem().getItemStack() != null)
            if(e.getItem().getItemStack().getType() == Material.INK_SACK)
                if(e.getItem().hasMetadata("gadget_encre"))
                    e.setCancelled(true);
    }

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Location location = player.getLocation();

            final Entity ink = location.getWorld().dropItem(player.getEyeLocation().clone().subtract(0d, 0.2d, 0d), new ItemStack(Material.INK_SACK));

            ink.setMetadata("gadget_encre", new FixedMetadataValue(LCCosmetiques.getInstance(), "slt"));
            ink.setVelocity(player.getLocation().getDirection().multiply(2.000415f));

            new BukkitRunnable() {

                @Override
                public void run() {

                    if(ink.isOnGround()){
                        ink.remove();
                        cancel();
                        return;
                    }

                    for(Entity e : ink.getNearbyEntities(1d, 1d, 1d)){
                        if(e instanceof Player && e != player){
                            ink.remove();
                            player.sendMessage("§aVous avez fait perdre la vue à §b<player> §a!".replace("<player>", e.getName()));
                            e.sendMessage("§aVous avez été aveuglé par §b<player> &a!".replace("<player>", player.getName()));
                            ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 5));
                            cancel();
                            return;
                        }

                    }
                }

            }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 2l);
        }
    }

}

