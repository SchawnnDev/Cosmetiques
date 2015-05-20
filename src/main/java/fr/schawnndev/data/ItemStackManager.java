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
import fr.schawnndev.CosmetiqueManager;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemStackManager {

    @Getter
    private static Map<CosmetiqueManager.Cosmetique, ItemStack> playerItems = new HashMap<>();

    public static ItemStack getHead(Player player){
   //     Item head = new Item("head", -1, false, Material.SKULL, (short) 3, null);

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

        SkullMeta im = (SkullMeta) itemStack.getItemMeta();

        im.setDisplayName("§6Informations du compte");
        im.setLore(Arrays.asList(("§7Pseudo : §a" + player.getName() + " (" + ChatColor.translateAlternateColorCodes('&', LCPerms.api.getGroup(player)) + "§a)"), ("§7Solde LCCOins : §6" + LCMaster.api.getCoins(player.getName()) + " ⛃ §a(x " + LCMaster.api.getMultiplicator(player) + "§a)")));
        //      head.addLore("§7Pseudo : §a" + player.getName() + " (" + "§cFondateur" + "§a)");
  //      head.addLore("§7Solde LCCOins : §6" + "221020" + " ⛃ §a(x " + "9" + "§a)");
        itemStack.setItemMeta(im);

        return itemStack;
    }

}
