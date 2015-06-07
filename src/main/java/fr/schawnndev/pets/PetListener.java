/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.PetListener) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 05/06/15 11:01.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets;

import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.menus.PetManager_Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PetListener implements Listener {

    public PetListener(){ Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance()); }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if (PetManager.isAPet(entity))
            e.setCancelled(true);

    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        Entity entity = e.getEntity();

        if (PetManager.isAPet(entity))
            e.setCancelled(true);

    }

    @EventHandler
    public void onDrop(ItemSpawnEvent e){
        if(e.getEntity() != null && e.getEntity() instanceof Item && e.getEntity().getItemStack() != null && e.getEntity().getItemStack().getType() == Material.EGG)
            e.setCancelled(true);
    }

    @EventHandler
    public void onFire(EntityCombustEvent e) {
        Entity entity = e.getEntity();

        if (PetManager.isAPet(entity))
            e.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        Player owner = e.getPlayer();
        Entity entity = e.getRightClicked();


        if(PetManager.isAPet(entity))
            if (PetManager.hasActivePet(owner))
                if (PetManager.getPet(owner).getMCEntity().equals(PetManager.getEntityPet(entity)))
                    PetManager_Menu.open(owner);

    }

}
