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

import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.listeners.ServerListener;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.particules.aaaaaa;
import fr.schawnndev.particules.ParticuleManager;
import fr.schawnndev.particules.ParticleEffect;
import fr.schawnndev.sql.SQL;
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import org.bukkit.Bukkit;
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

        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);

        // SQL

        sql = new SQL(getConfig().getString("db.host"), getConfig().getString("db.repo"), getConfig().getString("db.user"), getConfig().getString("db.pass"));
        sql.start();

        // Items

        new ItemStackManager();

        // Menus

        new MenuManager();

        // Data

        saveDefaultConfig();

    }

    public void onDisable() {
        sql.stop();
    }

    /**
     *
     *  TEST
     *
     */




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("particule")){
            Player player = (Player)sender;

            if(args.length == 0){

                try {

                    if(SQLManager.hasBuyCosmetic(player, "coeurs")) {

                        aaaaaa particle = new aaaaaa(player.getUniqueId(), ParticleEffect.HEART);

                        particle.start();

                        ParticuleManager.addParticle(particle);
                    } else {
                        player.sendMessage("§cTu n'as pas cette particule !");
                        return true;
                    }

                } catch (Exception e){
                    player.sendMessage("§cErreur (Voir console) ! " + e.getMessage());
                    e.printStackTrace();
                    return true;
                }

            } else {
                player.sendMessage("§cTrop d'arguments: /particule <particule>");
                return true;
            }
        }

        if(label.equalsIgnoreCase("a")){
            Player player = (Player)sender;
            player.getInventory().addItem(ItemStackManager.getHead(player));
            return true;
        }

        if(label.equalsIgnoreCase("b")){
            Player player = (Player)sender;
            if(SQLManager.hasBuyCosmetic(player, "helix"))
                player.sendMessage("§cTu as !");
            else
                player.sendMessage("§cTu as pas !");
            return true;
        }




        return false;
    }

}
