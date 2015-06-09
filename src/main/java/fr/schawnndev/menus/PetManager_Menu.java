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
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.math.PositionConverter;
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

    private static PositionConverter positionConverter = new PositionConverter();

    public PetManager_Menu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    public static void open(Player player){

        Inventory inv = Bukkit.createInventory(null, 3*9, "§6Gérez votre pet");

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

        ItemStack glassStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0, GlassColor.WHITE.getData());
        ItemMeta glassMeta = glassStack.getItemMeta();
        glassMeta.setDisplayName("§7-");
        glassStack.setItemMeta(glassMeta);

        inv.setItem(positionConverter.convert(1, 1), glassStack);
        inv.setItem(positionConverter.convert(4, 1), glassStack);
        inv.setItem(positionConverter.convert(6, 1), glassStack);
        inv.setItem(positionConverter.convert(9, 1), glassStack);
        inv.setItem(positionConverter.convert(2, 2), glassStack);
        inv.setItem(positionConverter.convert(8, 2), glassStack);
        inv.setItem(positionConverter.convert(1, 3), glassStack);
        inv.setItem(positionConverter.convert(4, 3), glassStack);
        inv.setItem(positionConverter.convert(6, 3), glassStack);
        inv.setItem(positionConverter.convert(9, 3), glassStack);

        ItemStack hat = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta hatMeta = hat.getItemMeta();
        hatMeta.setDisplayName("§bHat");
        hatMeta.setLore(Arrays.asList("§7Vous permet de mettre votre", "§7Pet sur votre tête.", "§7----------------", ("§7Etat: " + (isHat ? "§aActivé" : "§cDésactivé"))));
        hat.setItemMeta(hatMeta);

        ItemStack move = new ItemStack(Material.SADDLE);
        ItemMeta moveMeta = move.getItemMeta();
        moveMeta.setDisplayName("§6Ride");
        moveMeta.setLore(Arrays.asList("§7Vous permet de monter sur", "§7votre Pet et de le naviguer !", "§7----------------", ("§7Etat: " + (isRide ? "§aActivé" : "§cDésactivé"))));
        move.setItemMeta(moveMeta);

        ItemStack nom = new ItemStack(Material.PAPER);
        ItemMeta nomMeta = nom.getItemMeta();
        nomMeta.setDisplayName("§cRename");
        nomMeta.setLore(Arrays.asList("§7Vous permet de donner", "§7un nom à votre Pet.", "§7--------------", (hasName ? ("§7Nom actuel : §3" + name) : "§7Nom actuel : §cAucun")));
        nom.setItemMeta(nomMeta);

        inv.setItem(positionConverter.convert(3, 2), hat);
        inv.setItem(positionConverter.convert(5, 2), move);
        inv.setItem(positionConverter.convert(7, 2), nom);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("§6Gérez votre pet")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§bHat":
                        if(PetManager.hasActivePet(player))
                            PetManager.setHat(player, !PetManager.getPet(player).isHat());

                        player.closeInventory();
                        break;
                    case "§6Ride":
                        if(PetManager.hasActivePet(player))
                            PetManager.setRide(player, !PetManager.getPet(player).isRiding());

                        player.closeInventory();
                        break;
                    case "§cRename":
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
