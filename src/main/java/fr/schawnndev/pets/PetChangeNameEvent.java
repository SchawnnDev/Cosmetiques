/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.PetChangeNameEvent) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 07/06/15 17:07.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets;

import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.data.IndesirableStringDictionnary;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PetChangeNameEvent implements Listener {

    public PetChangeNameEvent(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    private static List<UUID> playerChangePetName = new ArrayList<>();

    protected static void sendChangeMessageTutoriel(final Player player) {

        new BukkitRunnable() {

            int timer = 0;

            @Override
            public void run() {

                if (!isChangingPetName(player)) {
                    cancel();
                    return;
                }

                if (timer == 0) {
                    player.sendMessage("§6§nPour changer le nom de votre pet :");
                } else if (timer == 1) {
                    player.sendMessage("    ");
                    player.sendMessage("§6Vous devez écrire le nom voulu dans le §atchat§6.");
                } else if (timer == 2) {
                    player.sendMessage("    ");
                    player.sendMessage("§6Vous pouvez ajouter des §acouleurs §6!");
                } else if (timer == 3){
                    player.sendMessage("    ");
                    player.sendMessage("§6Il vous suffit d'ajouter un §a& §6plus le §anombre§6 de la couleur !");
                    player.sendMessage("§6Les couleurs sont visibles ici: §ahttp://bit.ly/1QfKInv");
                } else if(timer == 4){
                    player.sendMessage("    ");
                    player.sendMessage("§6Vous pouvez écrire le §anom §6de votre pet dans le tchat §6!");
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 2f);
                } else if (timer == 5){
                    cancel();
                }

                timer++;

            }

        }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 20L);
    }

    public static void addPlayerChangingPetName(Player player){

        if(!isChangingPetName(player)) {

            sendChangeMessageTutoriel(player);

            playerChangePetName.add(player.getUniqueId());

        }

    }

    public static void removePlayerChangingPetName(Player player){
        playerChangePetName.remove(player.getUniqueId());
    }

    public static void processChangingPetName(Player player, String _name) {

        String name = ChatColor.translateAlternateColorCodes('&', _name);

        if (name.length() > 25) {

            player.sendMessage("§cVeuillez mettre un nom inférieur à 25 caractères.");

            return;

        } else if (IndesirableStringDictionnary.containsIndesirable(name)){

            player.sendMessage("§cLe pseudo de votre pet contient un mot indésirable.");

            return;

        } else {

            if (PetManager.hasActivePet(player))
                PetManager.getPet(player).setName(ChatColor.translateAlternateColorCodes('&', name));

            player.sendMessage("§aLe nouveau nom de votre pet est: " + ChatColor.translateAlternateColorCodes('&', name));

            removePlayerChangingPetName(player);

        }

    }

    public static boolean isChangingPetName(Player player){
        return playerChangePetName.contains(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e){

        Player player = e.getPlayer();

        if(isChangingPetName(player)){
            e.setCancelled(true);
            processChangingPetName(player, e.getMessage());
        }

    }

}
