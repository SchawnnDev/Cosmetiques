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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

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
    public void onFire(EntityCombustEvent e) {
        Entity entity = e.getEntity();

        if (PetManager.isAPet(entity))
            e.setCancelled(true);

    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        Player owner = e.getPlayer();

        if (!owner.isSneaking())
            PetManager.setHat(owner);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        Player owner = e.getPlayer();
        Entity entity = e.getRightClicked();

        if(PetManager.hasActivePet(owner))
            if (PetManager.getPet(owner).equals(PetManager.getEntityPet(entity)))
                    PetManager_Menu.open(owner);

    }

}
