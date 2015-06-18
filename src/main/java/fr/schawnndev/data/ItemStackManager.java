/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.data.ItemStackManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 19:22.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.data;

import com.icroque.lcperms.LCPerms;
import fr.lyneteam.lcmaster.LCMaster;
import fr.schawnndev.CosmetiqueManager.*;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemStackManager {

    @Getter
    private static Map<Cosmetique, ItemStack> playerItems = new HashMap<>();

    public ItemStackManager(){
        playerItems.put(Cosmetique.AUCUN, new ItemStack(Material.AIR));
        playerItems.put(Cosmetique.DOUBLE_JUMP, buildItemStack(Material.FEATHER, "§bDouble Jump"));
        playerItems.put(Cosmetique.MUSIC, buildItemStack(Material.getMaterial(2258), "§2Music"));
        playerItems.put(Cosmetique.GATEAU_EMPOISONNE, buildItemStack(Material.CAKE, "§3Gâteau Empoisonné"));
        playerItems.put(Cosmetique.GLACE, buildItemStack(Material.SNOW_BALL, "§cça glisse !"));
        playerItems.put(Cosmetique.CANON, buildItemStack(Material.SULPHUR, "§fCanon"));
        playerItems.put(Cosmetique.APPLE, buildItemStack(Material.GOLDEN_APPLE, "§eApple"));
        playerItems.put(Cosmetique.ENCRE, buildItemStack(Material.INK_SACK, "§cEncre"));
        playerItems.put(Cosmetique.CANNE_A_PECHE, buildItemStack(Material.FISHING_ROD, "§6Canne à pêche"));
        playerItems.put(Cosmetique.FIREBALL, buildItemStack(Material.FIREBALL, "§2I believe I can Fly"));
        playerItems.put(Cosmetique.TNT, buildItemStack(Material.TNT, "§7TNT"));
        playerItems.put(Cosmetique.ARTIFICE, buildItemStack(Material.FIREWORK, "§514 juillet"));
    }

    public static ItemStack getHead(Player player){
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

        SkullMeta im = (SkullMeta) itemStack.getItemMeta();
        im.setOwner(player.getName());
        im.setDisplayName("§6Mon compte");
        im.setLore(Arrays.asList("§7Solde LCoins : §6" + LCMaster.api.getCoins(player.getName()) + " ⛃"));
        itemStack.setItemMeta(im);

        return itemStack;
    }

    public static ItemStack buildItemStack(Material material, String name){

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack getItemStackByName(String cosmetic){

        Cosmetique cosmetique = Cosmetique.AUCUN;

        if(cosmetic.equalsIgnoreCase("canneapeche")) {
            cosmetique = Cosmetique.CANNE_A_PECHE;
        } else if (cosmetic.equalsIgnoreCase("gateauempoisonne")){
            cosmetique = Cosmetique.GATEAU_EMPOISONNE;
        } else {

            try {
                cosmetique = Cosmetique.valueOf(cosmetic.toUpperCase());
            } catch (Exception e) {
            }

        }

        return (playerItems.containsKey(cosmetique) ? playerItems.get(cosmetique) : new ItemStack(Material.AIR));
    }

    public static ItemStack getItemStack(Cosmetique cosmetique){
        return (playerItems.containsKey(cosmetique) ? playerItems.get(cosmetique) : new ItemStack(Material.AIR));
    }

}
