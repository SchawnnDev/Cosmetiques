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
import fr.schawnndev.listeners.CosmetiqueListener;
import fr.schawnndev.listeners.ServerListener;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.pets.PetListener;
import fr.schawnndev.pets.pets.PetEntityType;
import fr.schawnndev.reduction.Reduction;
import fr.schawnndev.reduction.ReductionManager;
import fr.schawnndev.sql.SQL;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

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
        new CosmetiqueListener();

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

                        if(ReductionManager.getReductions().size() < 1){
                            player.sendMessage("§cIl n'y a pas de réductions !");
                            return true;
                        }

                        for(Reduction reduction : ReductionManager.getReductions())
                            player.sendMessage("§f- §aReduction §6" + reduction.getCosmetique().getMysqlName() + "§a : §6-" + reduction.getReduction() + "% §aNouveau prix: §6" + reduction.getPriceAfterReduction());

                        return true;
                    } else if (args[0].equalsIgnoreCase("listcosmetiques")){
                        CosmetiqueManager.open(player);
                        return true;
                    } else if (args[0].equalsIgnoreCase("remove")){
                        player.sendMessage("§cIl manque un argument: §6/reduction remove <cosmetique>");
                        return true;
                    } else if (args[0].equalsIgnoreCase("add")){
                        player.sendMessage("§cIl manque deux arguments: §6/reduction add <cosmetique> <%>");
                        return true;
                    }

                } else if (args.length == 2) {

                    if (args[0].equalsIgnoreCase("remove")){

                        if(args[1].equalsIgnoreCase("pets") || args[1].equalsIgnoreCase("gadgets") || args[1].equalsIgnoreCase("particules")){

                            CosmetiqueManager.CosmetiqueType cosmetiqueType = CosmetiqueManager.CosmetiqueType.valueOf(args[1].replace("particules", "particle").replace("gadgets", "gadget").replace("pets", "pet").toUpperCase());
                            List<Map.Entry<CosmetiqueManager.Cosmetique, String>> entryList = new ArrayList<>();

                            for (CosmetiqueManager.Cosmetique c : CosmetiqueManager.Cosmetique.values()) {
                                if (c.getCosmetiqueType() == cosmetiqueType) {
                                    if (!c.isVip()) {

                                        if (ReductionManager.hasReduction(c)) {
                                            ReductionManager.removeReduction(c);
                                            System.out.println(player.getName() + " a supprime la reduction " + args[1] + " a " + new Date().toLocaleString());
                                            entryList.add(new AbstractMap.SimpleEntry<>(c, "bon"));
                                        } else {
                                            entryList.add(new AbstractMap.SimpleEntry<>(c, "deja"));
                                        }

                                    } else {
                                        entryList.add(new AbstractMap.SimpleEntry<>(c, "vip"));
                                    }
                                }
                            }

                            for(Map.Entry<CosmetiqueManager.Cosmetique, String> entry : entryList){

                                if(entry.getValue().equalsIgnoreCase("bon")) {

                                    player.sendMessage("§f- §6Removed §c" + entry.getKey().getMysqlName());

                                } else if(entry.getValue().equalsIgnoreCase("vip")) {

                                    player.sendMessage("§f- §6Not Removed §c" + entry.getKey().getMysqlName() + " §6Cause: §cVIP");

                                } else if(entry.getValue().equalsIgnoreCase("deja")) {

                                    player.sendMessage("§f- §6Not Removed §c" + entry.getKey().getMysqlName() + " §6Cause: §cDEJA");

                                }

                            }

                        } else {

                            try {
                                CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.getByMySQLName(args[1]);

                                if (ReductionManager.hasReduction(cosmetique)) {
                                    ReductionManager.removeReduction(cosmetique);
                                    player.sendMessage("§aTu as bien supprimé la réduction §f" + args[1] + " §a!");
                                    System.out.println(player.getName() + " a supprime la reduction " + args[1] + " a " + new Date().toLocaleString());
                                    return true;
                                } else {
                                    player.sendMessage("§cCette cosmetique n'a pas de réduction !");
                                    return true;
                                }

                            } catch (Exception e) {
                                player.sendMessage("§cERREUR : " + e.getMessage());
                                return true;
                            }

                        }

                    } else if (args[0].equalsIgnoreCase("add")){
                        player.sendMessage("§cIl manque un argument: §6/reduction add " + args[1] + " <%>");
                        return true;
                    }

                } else if(args.length == 3){

                    if(args[0].equalsIgnoreCase("add")){

                        int reduction;
                        CosmetiqueManager.Cosmetique cosmetique = null;
                        boolean category = false;
                        String category_name = null;

                        if(args[1].equalsIgnoreCase("pets") || args[1].equalsIgnoreCase("gadgets") || args[1].equalsIgnoreCase("particules")){
                            category_name = args[1];
                            category = true;
                        } else {
                            try {
                                cosmetique = CosmetiqueManager.Cosmetique.getByMySQLName(args[1]);
                            } catch (Exception e){
                                player.sendMessage("§cERREUR : " + e.getMessage());
                                return true;
                            }
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

                        if(!category) {

                            if (!ReductionManager.hasReduction(cosmetique)) {
                                ReductionManager.addReduction(cosmetique, reduction);
                                player.sendMessage("§aTu as bien ajouté la réduction §f" + args[1] + " §a|§f (-" + reduction + "%) §a!");
                                System.out.println(player.getName() + " a ajoute la reduction " + args[1] + " a " + new Date().toLocaleString());
                                return true;
                            } else {
                                player.sendMessage("§cCette cosmetique a déjà une réduction !");
                                return true;
                            }

                        } else {

                            CosmetiqueManager.CosmetiqueType cosmetiqueType = CosmetiqueManager.CosmetiqueType.valueOf(category_name.replace("particules", "particle").replace("gadgets", "gadget").replace("pets", "pet").toUpperCase());
                            List<Map.Entry<CosmetiqueManager.Cosmetique, String>> entryList = new ArrayList<>();

                            for (CosmetiqueManager.Cosmetique c : CosmetiqueManager.Cosmetique.values()) {
                                if (c.getCosmetiqueType() == cosmetiqueType) {
                                    if (!c.isVip()) {

                                        if (!ReductionManager.hasReduction(c)) {
                                            ReductionManager.addReduction(c, reduction);
                                            System.out.println(player.getName() + " a ajoute la reduction " + args[1] + " a " + new Date().toLocaleString());
                                            entryList.add(new AbstractMap.SimpleEntry<>(c, "bon"));
                                        } else {
                                            entryList.add(new AbstractMap.SimpleEntry<>(c, "deja"));
                                        }


                                    } else {
                                        entryList.add(new AbstractMap.SimpleEntry<>(c, "vip"));
                                    }
                                }
                            }

                            for(Map.Entry<CosmetiqueManager.Cosmetique, String> entry : entryList){

                                if(entry.getValue().equalsIgnoreCase("bon")) {

                                    player.sendMessage("§f- §6Added §c" + entry.getKey().getMysqlName() + " §6with §c-" + reduction + "% ");

                                } else if(entry.getValue().equalsIgnoreCase("vip")) {

                                    player.sendMessage("§f- §6Not Added §c" + entry.getKey().getMysqlName() + " §6with §c-" + reduction + "% §6Cause: §cVIP");

                                } else if(entry.getValue().equalsIgnoreCase("deja")) {

                                    player.sendMessage("§f- §6Not Added §c" + entry.getKey().getMysqlName() + " §6with §c-" + reduction + "% §6Cause: §cDEJA");

                                }

                            }

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
