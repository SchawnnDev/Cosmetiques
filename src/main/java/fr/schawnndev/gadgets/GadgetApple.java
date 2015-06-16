/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.GadgetApple) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 16/06/15 13:55.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.math.Randoms;
import fr.schawnndev.utils.ResetBlock;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class GadgetApple extends Gadget implements Listener {

    public GadgetApple(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.APPLE;

    @EventHandler
    public void onDamage(PlayerPickupItemEvent e){
        if(e.getItem() != null && e.getItem().getItemStack() != null)
            if(e.getItem().getItemStack().getType() == Material.GOLDEN_APPLE)
            if(e.getItem().hasMetadata("gadget_apple"))
                e.setCancelled(true);
    }


    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Random random = new Random();

            final long start = System.currentTimeMillis();

            new BukkitRunnable() {

                @Override
                public void run() {

                    if(((System.currentTimeMillis() - start)/1000) > 8){
                        cancel();
                        return;
                    }

                    final int r = random.nextInt(8);

                    for(int i = 0; i <= r; i++){

                        Entity item = player.getWorld().dropItem(player.getLocation().clone().add(0d, 3d,0d), new ItemStack(Material.GOLDEN_APPLE));
                        item.setMetadata("gadget_apple", new FixedMetadataValue(LCCosmetiques.getInstance(), "hello"));
                        item.setCustomName("hey"+(random.nextFloat() * r));
                        item.setVelocity(new Vector(Randoms.randomRangeFloat(-0.5f, 0.5f), Randoms.randomRangeFloat(0.2f, 0.5f), Randoms.randomRangeFloat(-0.5f, 0.5f)));

                    }



                }

            }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 15l);
        }
    }

}
