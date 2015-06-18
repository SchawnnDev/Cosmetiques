/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetPeche) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 17/06/15 22:13.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.utils.Cooldown;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import java.util.UUID;

public class GadgetPeche extends Gadget implements Listener {

    public GadgetPeche(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.CANNE_A_PECHE;

    @EventHandler
    public void onDamage(PlayerFishEvent e){
        if(e.getPlayer() != null && e.getHook() != null && e.getState() == PlayerFishEvent.State.IN_GROUND && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null
                && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§6Canne à pêche") && GadgetManager.hasGadget(e.getPlayer(), "canneapeche")){
            Player player = e.getPlayer();

            if (GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.CANNE_A_PECHE)) {
                player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.CANNE_A_PECHE));
                return;
            } else {
                GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.CANNE_A_PECHE, 5, true));
                player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                player.setVelocity(player.getVelocity().multiply(0.54f).setY(0.84f));
                player.getItemInHand().setDurability((short)-1);
                return;
            }
        }
    }


    @Override
    public void start(UUID uuid) {
        // Useless
    }

}
