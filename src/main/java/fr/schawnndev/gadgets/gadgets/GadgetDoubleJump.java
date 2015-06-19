/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetDoubleJump) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 19/06/15 19:15.
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
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GadgetDoubleJump extends Gadget implements Listener {

    public GadgetDoubleJump(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    private static List<UUID> players = new ArrayList<>();

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.DOUBLE_JUMP;

    @Override
    public void start(UUID uuid) {
        // Useless..
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if(player.isOnGround() && players.contains(player.getUniqueId())){
            player.setAllowFlight(true);
            players.remove(player.getUniqueId());
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(players.contains(e.getPlayer().getUniqueId()))
            players.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent e) {

        final Player player = e.getPlayer();

        if (players.contains(player.getUniqueId())) {
            e.setCancelled(true);
            return;
        }

        if (e.isFlying() && e.getPlayer().getGameMode() != GameMode.CREATIVE && GadgetManager.hasGadget(e.getPlayer(), "doublejump")) {

            if (!GadgetManager.isInCooldown(player, CosmetiqueManager.Cosmetique.DOUBLE_JUMP)) {

                e.setCancelled(true);

                player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 1.0f, 2.0f);

                for (Player p : Bukkit.getOnlinePlayers())
                    if (p != player && p.getLocation().distanceSquared(player.getLocation()) <= 15)
                        p.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 1.0f, 2.0f);

                e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.600041255d).setY(0.900352d));


                players.add(player.getUniqueId());
                player.setAllowFlight(false);

                GadgetManager.addCooldown(new Cooldown(player, CosmetiqueManager.Cosmetique.DOUBLE_JUMP, 5, true));

            } else {
                player.sendMessage(GadgetManager.getString(player, CosmetiqueManager.Cosmetique.ARTIFICE));
            }
        }
    }
}
