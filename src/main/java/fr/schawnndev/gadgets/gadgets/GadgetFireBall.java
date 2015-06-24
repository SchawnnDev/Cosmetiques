/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetFireball) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 24/06/15 10:08.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.pets.pets.CustomFireball;
import fr.schawnndev.utils.Fireworks;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class GadgetFireBall extends Gadget implements Listener {

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.FIREBALL;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExplode(EntityExplodeEvent e) {
        if (e.getEntity() != null && e.getEntity().hasMetadata("gadget_fireball"))
            e.setCancelled(true);
    }

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Entity boule = CustomFireball.spawn(player.getLocation());
            boule.setMetadata("gadget_fireball", new FixedMetadataValue(LCCosmetiques.getInstance(), "slt"));
            boule.setVelocity(player.getLocation().getDirection().multiply(2.452567426f));
            boule.setPassenger(player);
            boule.setFireTicks(-1);
            player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1f, 2f);

            new BukkitRunnable(){
                @Override
                public void run() {
                    Fireworks.fireFirework(boule.getLocation(), Fireworks.colors[new Random().nextInt(Fireworks.colors.length)]);

                    if(boule != null && !boule.isDead())
                        boule.remove();
                }
            }.runTaskLater(LCCosmetiques.getInstance(), 20 * 8);

        }
    }
}
