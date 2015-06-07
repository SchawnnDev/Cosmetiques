/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.LCCosmetiques) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 15:53.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev;

import fr.schawnndev.api.events.AchatEvent;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetListener;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.listeners.ServerListener;
import fr.schawnndev.menus.Main_Menu;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.pets.Pet;
import fr.schawnndev.pets.PetListener;
import fr.schawnndev.pets.PetManager;
import fr.schawnndev.pets.pets.PetEntityType;
import fr.schawnndev.sql.SQL;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class LCCosmetiques extends JavaPlugin{

    @Getter
    private static LCCosmetiques instance;
    @Getter
    private static SQL sql;

    public void onEnable() {

        // Instances

        instance = this;

        // API Inutile pour le moment

        // new ItemDisponibility();

        // Listeners

        new ServerListener();
        new AchatEvent();
        new GadgetListener();
        new PetListener();

        // SQL

        sql = new SQL(getConfig().getString("db.host"), getConfig().getString("db.repo"), getConfig().getString("db.user"), getConfig().getString("db.pass"));
        sql.start();

        // Items

        new ItemStackManager();

        // Menus

        new MenuManager();

        // Data

        saveDefaultConfig();

        new ItemStackManager();

        // Entities

        PetEntityType.registerEntities();

    }

    public void onDisable() {

        sql.stop();
        PetEntityType.unregisterEntities();

    }

    /**
     *
     *  TEST
     *
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("a")){
            Player player = (Player)sender;
            player.sendMessage("§aadded pet");
            PetManager.addPlayerPet(player, CosmetiqueManager.Cosmetique.POULET);
            return true;
        }

        if(label.equalsIgnoreCase("b")){
            Player player = (Player)sender;

            if(args.length == 0){
                player.sendMessage("§cpas assez d'args !");
                return true;
            } else if (args.length == 1){
                Pet pet = PetManager.getPet(player);
                pet.setName(args[0]);
                player.sendMessage("§achanged pet name to : " + ChatColor.translateAlternateColorCodes('&', args[0]));
                return true;
            } else {
                player.sendMessage("§ctrop d'args !");
                return true;
            }

        }

        if(label.equalsIgnoreCase("c")){
            Player player = (Player)sender;
            player.sendMessage("§aset pet to ride");
            PetManager.setRide(player);
            return true;
        }

        if(label.equalsIgnoreCase("d")){
            Player player = (Player)sender;
            player.sendMessage("§apet to hat");
            PetManager.setHat(player);
            return true;
        }


        return false;
    }

}
