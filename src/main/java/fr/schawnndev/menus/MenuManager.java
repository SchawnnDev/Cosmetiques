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

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.api.utils.ItemDisponibility;
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
        itemMeta.setDisplayName("§f=== §5Cosmétiques §f===  ");
        cosmeticItem.setItemMeta(itemMeta);

        new Gadget_SubMenu();
        new Particle_SubMenu();
        new Main_Menu();
        new ItemDisponibility();

    }


    public static List<String> getNewLore(String lore){
        List<String> l = new ArrayList<>();
        l.add(lore);
        return l;
    }

    public static ItemStack buildItem(ItemStack itemStack, int prix, boolean vip, String displayName, List<String> lore, boolean hasGadget, CosmetiqueManager.CosmetiqueType cosmetiqueType){
        ItemStack finalItemStack = itemStack;
        ItemMeta finalItemMeta = finalItemStack.getItemMeta();

        if(!hasGadget) {

            if (vip) {
                lore.add("§7------------");
                lore.add("§bPrix : §6Réservé aux §eVIP");
            } else {
                if (prix != -1) {
                    lore.add("§7------------");
                    lore.add("§bPrix : §6" + prix + " §bLCoins");
                }
            }
        } else {
            lore.add("§7------------");
            lore.add("§aVous possédez " + (cosmetiqueType == CosmetiqueManager.CosmetiqueType.PARTICLE ? "cette particule." : cosmetiqueType == CosmetiqueManager.CosmetiqueType.GADGET ? "ce gadget." : "ce pet."));
        }

        finalItemMeta.setDisplayName(displayName);
        finalItemMeta.setLore(lore);
        finalItemStack.setItemMeta(finalItemMeta);

        return finalItemStack;
    }


}
