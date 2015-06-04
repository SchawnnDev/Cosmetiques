/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetTNT) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 04/06/15 18:39.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.utils.LocationUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class GadgetTNT extends Gadget implements Listener {

    public GadgetTNT(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.TNT;

    public static List<UUID> cooldowns = new ArrayList<>();

    @Override
    public void start(UUID uuid){
        // useless
    }

    public void proceedExplode(Location location){

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getLocation().distance(location) <= 15){
                p.setVelocity(p.getLocation().getDirection().multiply(-2.13).setY(1.9903));
                p.playSound(p.getLocation(), Sound.WOLF_HURT, 1f, 1f);
            }
        }
    }

    public void setBomb(Location location){
        location.add(0d, 1d, 0d);

        TNTPrimed tntPrimed = location.getWorld().spawn(location, TNTPrimed.class);
        tntPrimed.setMetadata("gadget_tnt", new FixedMetadataValue(LCCosmetiques.getInstance(), "hello"));

    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        if(e.getEntity() instanceof TNTPrimed){
            if(e.getEntity().hasMetadata("gadget_tnt")) {
                e.setCancelled(true);

                proceedExplode(e.getEntity().getLocation());

            }
        }
    }

}
