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
import fr.schawnndev.pets.PetManager;
import fr.schawnndev.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerListener implements Listener {

    public ServerListener(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Deprecated
    private boolean hasActiveCosmetic(Player player, CosmetiqueManager.CosmetiqueType cosmetiqueType, Map<CosmetiqueManager.CosmetiqueType, String> cosmetiqueTypeStringMap){

        if(!cosmetiqueTypeStringMap.containsKey(cosmetiqueType))
            return false;

        if(cosmetiqueTypeStringMap.get(cosmetiqueType).equalsIgnoreCase("aucun"))
            return false;

        return true;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        final Player player = e.getPlayer();

        final float start = System.currentTimeMillis();

        final Map<CosmetiqueManager.CosmetiqueType, String> cosmetiqueTypeStringMap = new HashMap<>();

        for(CosmetiqueManager.CosmetiqueType c : CosmetiqueManager.CosmetiqueType.values())
            cosmetiqueTypeStringMap.put(c, SQLManager.getActiveCosmetic(player, c));


        if(hasActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.PET, cosmetiqueTypeStringMap)){

            String petName = SQLManager.getActivePetName(player);

            if(petName.equalsIgnoreCase("aucun")){

                PetManager.addPlayerPet(player, CosmetiqueManager.Cosmetique.getByMySQLName(cosmetiqueTypeStringMap.get(CosmetiqueManager.CosmetiqueType.PET)));

            } else {

                PetManager.addCustomPlayerPet(player, CosmetiqueManager.Cosmetique.getByMySQLName(cosmetiqueTypeStringMap.get(CosmetiqueManager.CosmetiqueType.PET)), ChatColor.translateAlternateColorCodes('&', petName));
            }

        }

        if(hasActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.PARTICLE, cosmetiqueTypeStringMap)){

            ParticleManager.activeParticleByName(player, cosmetiqueTypeStringMap.get(CosmetiqueManager.CosmetiqueType.PARTICLE));

        }

        boolean _hasActiveGadget = false;
        String _gadget = null;

        if(hasActiveCosmetic(player, CosmetiqueManager.CosmetiqueType.GADGET, cosmetiqueTypeStringMap)) {
            _gadget = cosmetiqueTypeStringMap.get(CosmetiqueManager.CosmetiqueType.GADGET);
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

        // Active pet sql

        if(PetManager.hasActivePet(e.getPlayer())){
            SQLManager.setActiveCosmetic(e.getPlayer(), PetManager.getPet(e.getPlayer()).getCosmetiqueType().getMysqlName(), CosmetiqueManager.CosmetiqueType.PET);

            if(PetManager.getPet(e.getPlayer()).getName().contains("entity.Pet"))
                SQLManager.setActivePetName(e.getPlayer(), "aucun");
            else
                SQLManager.setActivePetName(e.getPlayer(), PetManager.getPet(e.getPlayer()).getName());

            PetManager.removePet(e.getPlayer());
        } else {
            SQLManager.setActiveCosmetic(e.getPlayer(), "aucun", CosmetiqueManager.CosmetiqueType.PET);
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
