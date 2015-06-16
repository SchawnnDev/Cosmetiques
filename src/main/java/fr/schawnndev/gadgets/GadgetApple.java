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
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
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

            final List<Entity> entities = new ArrayList<>();

            new BukkitRunnable() {

                @Override
                public void run() {

                    if(((System.currentTimeMillis() - start)/1000) < 8) {

                        for (int i = 0; i <= 7; i++) {

                            ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
                            ItemMeta appleMeta = apple.getItemMeta();
                            appleMeta.setDisplayName("hey" + (random.nextFloat() * i));
                            apple.setItemMeta(appleMeta);

                            Item item = player.getWorld().dropItem(player.getLocation().clone().add(0d, 2d, 0d), apple);
                            item.setMetadata("gadget_apple", new FixedMetadataValue(LCCosmetiques.getInstance(), "hello"));
                            item.setVelocity(new Vector(Randoms.randomRangeFloat(-0.5f, 0.5f), Randoms.randomRangeFloat(0.2f, 0.5f), Randoms.randomRangeFloat(-0.5f, 0.5f)));
                            entities.add(item);
                        }

                    } else if (((System.currentTimeMillis() - start)/1000) < 12){

                        for(Entity entity : entities)
                            if(entity != null && entity.isValid())
                                entity.remove();

                        entities.clear();
                        cancel();
                        return;
                    }


                }

            }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 15l);
        }
    }

}
