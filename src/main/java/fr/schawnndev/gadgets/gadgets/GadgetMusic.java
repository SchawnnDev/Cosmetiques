/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetMusic) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/06/15 11:52.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.math.Randoms;
import fr.schawnndev.particules.ParticleEffect;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GadgetMusic extends Gadget implements Listener {

    public GadgetMusic(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.MUSIC;

    @Override
    public void start(UUID uuid){
        // useless
    }

    public boolean setJukebox(final Location location){
        location.add(0d, 1d, 0d);

        if(location.getBlock().isEmpty()){
            final Map.Entry<Integer, Integer> disc = getRandomDisc(new Random().nextInt(12));
            final long start = System.currentTimeMillis();
            final Entity item = location.getWorld().dropItem(location.clone().add(0d, 2d, 0d), new ItemStack(Material.getMaterial(disc.getKey())));
            item.setMetadata("gadget_music", new FixedMetadataValue(LCCosmetiques.getInstance(), "slt"));
            location.getBlock().setType(Material.JUKEBOX);
            location.getBlock().setMetadata("gadget_music", new FixedMetadataValue(LCCosmetiques.getInstance(), "slt"));
            location.getWorld().playEffect(location, Effect.RECORD_PLAY, disc.getKey());


            new BukkitRunnable() {

                @Override
                public void run() {
                    if((((System.currentTimeMillis() - start) / 1000) * 20) < disc.getValue()){
                        for(int i = 0; i < 4; i++)
                            ParticleEffect.NOTE.display(new ParticleEffect.NoteColor(Randoms.randomRangeInt(0, 24)), location.clone().add(0d, .5d, 0d), 128);
                    } else if ((((System.currentTimeMillis() - start) / 1000) * 20) > (disc.getValue() + 20)) {
                        location.getBlock().setType(Material.AIR);

                        if(item != null && !item.isDead())
                            item.remove();

                        cancel();
                        return;
                    }
                }

            }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 5l);

            return true;
        } else {
            return false;
        }

    }

    protected Map.Entry<Integer, Integer> getRandomDisc(final int random){
        int disc;
        int length;

        if(random == 1) { // 13
            disc = 2256;
            length = (60 * 2 + 58) * 20;
        } else if (random == 2) { // Cat
            disc = 2257;
            length = (60 * 3 + 5) * 20;
        } else if (random == 3) { // Blocks
            disc = 2258;
            length = (60 * 5 + 45) * 20;
        } else if (random == 4) { // Chirp
            disc = 2259;
            length = (60 * 3 + 5) * 20;
        } else if (random == 5) { // Far
            disc = 2260;
            length = (60 * 2 + 54 * 20);
        } else if (random == 6) { // Mall
            disc = 2261;
            length = (60 * 30 + 17) * 20;
        } else if (random == 7) { // Mellohi
            disc = 2262;
            length = (60 + 36) * 20;
        } else if (random == 8) { // Stal
            disc = 2263;
            length = (60 * 2 + 30) * 20;
        } else if (random == 9) { // Strad
            disc = 2264;
            length = (60 * 3 + 8) * 20;
        } else if (random == 10) { // Ward
            disc = 2265;
            length = (60 * 4 + 11) * 20;
        } else if (random == 11) { // 11
            disc = 2266;
            length = (60 + 11) * 20;
        } else { // Wait
            disc = 2267;
            length = (60 * 3 + 58) * 20;
        }

        return new AbstractMap.SimpleEntry<>(disc, length);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e){
        if(e.getItem() != null && e.getItem().hasMetadata("gadget_music"))
            e.setCancelled(true);
    }

}