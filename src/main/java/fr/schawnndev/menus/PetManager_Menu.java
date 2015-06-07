/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.PetManager_Menu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 07/06/15 00:26.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.menus;

import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.pets.Pet;
import fr.schawnndev.pets.PetChangeNameEvent;
import fr.schawnndev.pets.PetManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PetManager_Menu implements Listener {

    public PetManager_Menu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    public static void open(Player player){

        Inventory inv = Bukkit.createInventory(null, 9, "§6Gerez votre pet");

        boolean isHat = false;
        boolean isRide = false;
        boolean hasName = false;
        String name = null;

        if(PetManager.hasActivePet(player)){

            Pet pet = PetManager.getPet(player);

            isHat = pet.isHat();
            isRide = pet.isRiding() & player.getPassenger() != null;

            if(pet.getName() != null && !pet.getName().contains("entity.Pet")) {
                hasName = true;
                name = pet.getName();
            }

        }

        ItemStack hat = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta hatMeta = hat.getItemMeta();
        hatMeta.setDisplayName("§6Mettez votre pet sur votre tête");
        hatMeta.setLore(Arrays.asList((isHat ? "§7Votre pet est sur votre tête." : "§7Votre pet n'est pas sur votre tête."), "§f--------------", (isHat ? "§7Cliquez pour enlever votre pet de votre tête." : "§7Cliquez pour mettre votre pet sur votre tête.")));
        hat.setItemMeta(hatMeta);

        ItemStack move = new ItemStack(Material.SADDLE);
        ItemMeta moveMeta = move.getItemMeta();
        moveMeta.setDisplayName("§6Naviguez votre pet");
        moveMeta.setLore(Arrays.asList((isRide ? "§7Vous naviguez votre pet." : "§7Vous ne naviguez pas votre pet."), "§f--------------",  (isRide ? "§7Cliquez pour dessendre de votre pet." : "§7Cliquez pour monter votre pet.")));
        move.setItemMeta(moveMeta);

        ItemStack nom = new ItemStack(Material.PAPER);
        ItemMeta nomMeta = nom.getItemMeta();
        nomMeta.setDisplayName("§6Changez le nom de votre pet");
        nomMeta.setLore(Arrays.asList((hasName ? ("§7Nom actuel : §3" + name) : "§7Nom actuel : §caucun"), "§f--------------", "§7Cliquez pour changer le nom de votre pet."));
        nom.setItemMeta(nomMeta);

        inv.setItem(2, hat);
        inv.setItem(4, move);
        inv.setItem(6,  nom);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("§6Gerez votre pet")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§6Mettez votre pet sur votre tête":
                        if(PetManager.hasActivePet(player))
                            PetManager.setHat(player, !PetManager.getPet(player).isHat());

                        player.closeInventory();
                        break;
                    case "§6Naviguez votre pet":
                        if(PetManager.hasActivePet(player))
                            PetManager.setRide(player, !PetManager.getPet(player).isRiding());

                        player.closeInventory();
                        break;
                    case "§6Changez le nom de votre pet":
                        PetChangeNameEvent.addPlayerChangingPetName(player);

                        player.closeInventory();
                        break;
                    default:
                        break;
                }

            }
        }
    }



}
