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
import fr.schawnndev.data.IndesirableStringDictionnary;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetListener;
import fr.schawnndev.listeners.ServerListener;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.pets.PetListener;
import fr.schawnndev.pets.pets.PetEntityType;
import fr.schawnndev.reduction.Reduction;
import fr.schawnndev.reduction.ReductionManager;
import fr.schawnndev.sql.SQL;
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class LCCosmetiques extends JavaPlugin{

    @Getter
    private static LCCosmetiques instance;
    @Getter
    private static SQL sql;

    public void onEnable() {

        // Instances

        instance = this;

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

        // Dictionnary

        IndesirableStringDictionnary.init();

        // Reductions

        ReductionManager.enable();

    }

    public void onDisable() {

        // SQL

        sql.stop();

        // Pets

        PetEntityType.unregisterEntities();

        // Reductions

        ReductionManager.disable();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("reduction")){

            Player player = (Player)sender;

            if(player.isOp() || player.hasPermission("lcmaster.*")){

                if(args.length == 0){

                    player.sendMessage("§7---- §bReduction Help §7----");
                    player.sendMessage("§fListe des cosmétiques: §6/reduction listcosmetiques");
                    player.sendMessage("§fListe des reductions: §6/reduction list");
                    player.sendMessage("§fAjouter une reduction: §6/reduction add <cosmetique> <%>");
                    player.sendMessage("§fSupprimer une reduction: §6/reduction remove <cosmetique>");

                    return true;
                } else if(args.length == 1){

                    if(args[0].equalsIgnoreCase("list")){

                        for(Reduction reduction : ReductionManager.getReductions())
                            player.sendMessage("§f- §aReduction §6" + reduction.getCosmetique().getMysqlName() + "§a : §6-" + reduction.getReduction() + "% §aNouveau prix: §6" + reduction.getPriceAfterReduction());

                        return true;
                    } else if (args[0].equalsIgnoreCase("listcosmetiques")){

                        for(CosmetiqueManager.Cosmetique cosmetique : CosmetiqueManager.Cosmetique.values())
                            player.sendMessage("§f- §a" + cosmetique.getMysqlName());

                        return true;
                    }

                } else if (args.length == 2) {

                    if (args[0].equalsIgnoreCase("remove")){

                        try {
                            CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.getByMySQLName(args[1]);

                            if(ReductionManager.hasReduction(cosmetique)){
                                ReductionManager.removeReduction(cosmetique);
                                player.sendMessage("§aTu as bien supprimé la réduction §f" + args[1] + " §a!");
                                System.out.println(player.getName() + " a supprime la reduction " + args[1] + " a " + new Date().toLocaleString());
                                return true;
                            } else {
                                player.sendMessage("§cCette cosmetique n'a pas de réduction !");
                                return true;
                            }

                        } catch (Exception e){
                            player.sendMessage("§cERREUR : " + e.getMessage());
                            return true;
                        }

                    }

                } else if(args.length == 3){

                    if(args[0].equalsIgnoreCase("add")){

                        int reduction;
                        CosmetiqueManager.Cosmetique cosmetique;

                        try {
                            cosmetique = CosmetiqueManager.Cosmetique.getByMySQLName(args[1]);
                        } catch (Exception e){
                            player.sendMessage("§cERREUR : " + e.getMessage());
                            return true;
                        }

                        try {
                            reduction = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e){
                            player.sendMessage("§cERREUR : " + e.getMessage() + " §c(§fLa réduction doit être un nombre§c)");
                            return true;
                        }

                        if(reduction < 1 || reduction > 99){
                            player.sendMessage("§cLe % de réduction doit être entre 1 et 99 !");
                            return true;
                        }


                        if(!ReductionManager.hasReduction(cosmetique)){
                            ReductionManager.addReduction(cosmetique, reduction);
                            player.sendMessage("§aTu as bien ajouté la réduction §f" + args[1] + "§a|§f (-" + reduction + "%) §a!");
                            System.out.println(player.getName() + " a ajoute la reduction " + args[1] + " a " + new Date().toLocaleString());
                            return true;
                        } else {
                            player.sendMessage("§cCette cosmetique a déjà une réduction !");
                            return true;
                        }


                    }


                } else {
                    player.sendMessage("§fUnknown command. Type "+ '"' + "/help" + '"' + " for help.");
                    return true;
                }

            } else {
                player.sendMessage("§fUnknown command. Type "+ '"' + "/help" + '"' + " for help.");
                return true;
            }

        }


        return false;
    }
}
