/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetGlace) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 09/06/15 21:10.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.utils.ResetBlock;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GadgetGlace extends Gadget implements Listener {

    public GadgetGlace(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.GLACE;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Snowball)
            if(e.getDamager().hasMetadata("gadget_glace"))
                e.setCancelled(true);
    }

    public boolean isAlreadyResetBlock(Location location, List<ResetBlock> resetBlockList){

        for(ResetBlock resetBlock : resetBlockList)
            if(resetBlock.getLocation().equals(location))
                return true;

        return false;
    }

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Random random = new Random();

            final List<ResetBlock> blockList = new ArrayList<>();

            final Entity boule = player.throwSnowball();
            boule.setMetadata("gadget_glace", new FixedMetadataValue(LCCosmetiques.getInstance(), "gadget_glace"));

            new BukkitRunnable() {

                @Override
                public void run() {

                    if(boule.isDead() || !boule.isValid()){

                        new BukkitRunnable() {

                            @Override
                            public void run() {

                                for(int i = 0; i < blockList.size(); i++)
                                    blockList.get(i).reset();

                            }

                        }.runTaskLater(LCCosmetiques.getInstance(), 20*10);

                        cancel();
                        return;
                    }

                    int i = random.nextInt(2);

                    Material material = i == 1 ? Material.PACKED_ICE : Material.ICE;

                    Block blockLoc = boule.getLocation().clone().subtract(0d, 1d, 0d).getBlock();

                    if(blockLoc.getType() != Material.SIGN && blockLoc.getType() != Material.STONE_BUTTON
                            && blockLoc.getType() != Material.WOOD_BUTTON && blockLoc.getType() != Material.WHEAT
                            && blockLoc.getType() != Material.LEVER && blockLoc.getType() != Material.TORCH
                            && blockLoc.getType() != Material.REDSTONE_TORCH_OFF && blockLoc.getType() != Material.REDSTONE_TORCH_ON){
                        if(!isAlreadyResetBlock(blockLoc.getLocation(), blockList)) {
                            blockList.add(new ResetBlock(blockLoc.getLocation(), blockLoc.getType(), blockLoc.getData()));
                            blockLoc.setType(material);
                        }
                    }
                }

            }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 1l);
        }
    }

}
