/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetCanon) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 10/06/15 12:25.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.particules.ParticleEffect;
import fr.schawnndev.utils.Fireworks;
import fr.schawnndev.utils.ResetBlock;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GadgetCanon extends Gadget implements Listener {

    public GadgetCanon(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.GLACE;

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        if(e.getRightClicked().hasMetadata("gadget_canon")){
            e.setCancelled(true);
        }
    }

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Entity mob = player.getWorld().spawnEntity(player.getLocation(), getRandomEntity());
            mob.setMetadata("gadget_canon", new FixedMetadataValue(LCCosmetiques.getInstance(), "gadget_canon"));
            mob.setVelocity(player.getLocation().getDirection().multiply(3.1854700045f));

            new BukkitRunnable() {

                @Override
                public void run() {

                    if(mob.isOnGround()){
                        proceedExplosion(mob);
                        cancel();
                        return;
                    }

                    ParticleEffect.CLOUD.display(0.5F, 0.5F, 0.5F, 0F, 10, mob.getLocation(), 300);


                }

            }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 2l);
        }
    }

    private void proceedExplosion(final Entity mob){

        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers())
                    if(player.getLocation().distance(mob.getLocation()) <= 30)
                        player.playSound(mob.getLocation(), Sound.EXPLODE, 1f, 5f);
                Fireworks.fireFirework(mob.getLocation(), Color.BLUE);
                mob.remove();

            }

        }.runTaskLater(LCCosmetiques.getInstance(), 4 * 20);


    }

    private EntityType getRandomEntity(){
        final int i = new Random().nextInt(10);

        if(i == 1)
            return EntityType.COW;
        else if(i == 2)
            return EntityType.HORSE;
        else if (i == 3)
            return EntityType.WOLF;
        else if (i == 4)
            return EntityType.VILLAGER;
        else if (i == 5)
            return EntityType.PIG;
        else if (i == 6)
            return EntityType.SHEEP;
        else if (i == 7)
            return EntityType.IRON_GOLEM;
        else if (i == 8)
            return EntityType.RABBIT;
        else if (i == 9)
            return EntityType.OCELOT;
        else if (i == 10)
            return EntityType.MUSHROOM_COW;
        else
            return EntityType.PIG;
    }

}