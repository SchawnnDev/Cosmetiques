/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.MenuManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 19:15.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.menus;

import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.CosmetiqueManager.CosmetiqueType;
import fr.schawnndev.api.utils.ItemDisponibility;
import fr.schawnndev.pets.PetChangeNameEvent;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    @Getter
    private static ItemStack cosmeticItem;

    public MenuManager(){

        // Player item

        cosmeticItem = new ItemStack(Material.JUKEBOX);

        ItemMeta itemMeta = cosmeticItem.getItemMeta();
        itemMeta.setDisplayName("§5Cosmétiques");
        cosmeticItem.setItemMeta(itemMeta);

        new Gadget_SubMenu();
        new Particle_SubMenu();
        new Pet_SubMenu();
        new Main_Menu();
        new ItemDisponibility();
        new PetManager_Menu();
        new PetChangeNameEvent();

    }


    public static List<String> getNewLore(String lore){
        List<String> _lore = new ArrayList<>();
        _lore.add(lore);
        return _lore;
    }

    public static ItemStack buildItem(ItemStack itemStack, Cosmetique cosmetique, Player player, String displayName, List<String> lore, boolean hasGadget){
        ItemStack finalItemStack = itemStack;
        ItemMeta finalItemMeta = finalItemStack.getItemMeta();
        int prix = cosmetique.getPrice();
        boolean vip = cosmetique.isVip();
        CosmetiqueType cosmetiqueType = cosmetique.getCosmetiqueType();

        if(!hasGadget) {

            if (vip) {

                if (player.hasPermission("lcmaster.cosmetiques") || player.isOp() || player.hasPermission("lcmaster.*")) {                  lore.add("§7------------");
                    lore.add("§aVous possédez " + (cosmetiqueType == CosmetiqueType.PARTICLE ? "cette Particule" : cosmetiqueType == CosmetiqueType.GADGET ? "ce Gadget" : "ce Pet") + " grâce à votre grade.");
                    lore.add("§e» §aEquiper " + (cosmetiqueType == CosmetiqueType.PARTICLE ? "cette Particule." : cosmetiqueType == CosmetiqueType.GADGET ? "ce Gadget." : "ce Pet."));
                } else {
                    lore.add("§7------------");
                    lore.add("§bPrix : §6Réservé aux §eVIP");
                }
            } else {
                if (prix != -1) {
                    lore.add("§7------------");
                    lore.add("§bPrix : §6" + prix + " §bLCoins");
                }
            }
        } else {
            lore.add("§7------------");
            lore.add("§e» §aEquiper " + (cosmetiqueType == CosmetiqueType.PARTICLE ? "cette Particule." : cosmetiqueType == CosmetiqueType.GADGET ? "ce Gadget." : "ce Pet."));
        }

        finalItemMeta.setDisplayName(displayName);
        finalItemMeta.setLore(lore);
        finalItemStack.setItemMeta(finalItemMeta);

        return finalItemStack;
    }


}
