/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.listeners.ServerListener) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 17:26.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.listeners;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Manager;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.particules.ParticleManager;
import fr.schawnndev.pets.PetChangeNameEvent;
import fr.schawnndev.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ServerListener implements Listener {

    public ServerListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        final Player player = e.getPlayer();

        if(SQLManager.hasActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.PARTICLE)){

            String particle = SQLManager.getActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.PARTICLE);

            ParticleManager.activeParticleByName(player, particle);

        }

        boolean _hasActiveGadget = false;
        String _gadget = null;

        if(SQLManager.hasActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.GADGET)) {
            _gadget = SQLManager.getActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.GADGET);
            _hasActiveGadget = true;
        }

        final String gadget = _gadget;
        final boolean hasActiveGadget = _hasActiveGadget;

        new BukkitRunnable(){
            @Override
            public void run() {

                player.getInventory().setItem(8, MenuManager.getCosmeticItem());

                if(hasActiveGadget) {
                    player.getInventory().setItem(4, ItemStackManager.getItemStackByName(gadget));
                    GadgetManager.addGadget(player, gadget, false);
                }
            }
        }.runTaskLater(LCCosmetiques.getInstance(), 1L);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        // Active particle sql

        if(ParticleManager.getActiveParticles().containsKey(e.getPlayer().getUniqueId())) {
            SQLManager.setActiveCosmetic(e.getPlayer(), ParticleManager.getActiveParticles().get(e.getPlayer().getUniqueId()), CosmetiqueManager.CosmetiqueType.PARTICLE);
            ParticleManager.getActiveParticles().remove(e.getPlayer().getUniqueId());
        } else {
            SQLManager.setActiveCosmetic(e.getPlayer(), "aucun", CosmetiqueManager.CosmetiqueType.PARTICLE);
        }

        // Active gadget sql

        if(GadgetManager.hasGadgetActive(e.getPlayer())) {
            SQLManager.setActiveCosmetic(e.getPlayer(), GadgetManager.getGadget(e.getPlayer()), CosmetiqueManager.CosmetiqueType.GADGET);
            GadgetManager.getActiveGadgets().remove(e.getPlayer().getUniqueId());
        } else {
            SQLManager.setActiveCosmetic(e.getPlayer(), "aucun", CosmetiqueManager.CosmetiqueType.GADGET);
        }

        // Achats

        if(Manager.playersBuying.contains(e.getPlayer().getUniqueId()))
            Manager.playersBuying.remove(e.getPlayer().getUniqueId());

        if(Manager.hasAchat(e.getPlayer().getUniqueId()))
            Manager.achats.remove(Manager.getAchat(e.getPlayer().getUniqueId()));

        // PetChangeName

        if(PetChangeNameEvent.isChangingPetName(e.getPlayer()))
            PetChangeNameEvent.removePlayerChangingPetName(e.getPlayer());

    }

}
