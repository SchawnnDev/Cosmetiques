/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.Main_Menu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 19:15.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.menus;

import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Manager;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.math.PositionConverter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Main_Menu implements Listener {

    @Getter
    private static Inventory basicInventory;

    private static PositionConverter positionConverter = new PositionConverter();

    public Main_Menu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());

        basicInventory = Bukkit.createInventory(null, 6*9, "§6Cosmétiques");

        ItemStack glassStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0, GlassColor.WHITE.getData());
        ItemMeta glassMeta = glassStack.getItemMeta();
        glassMeta.setDisplayName("§7-");
        glassStack.setItemMeta(glassMeta);

        basicInventory.setItem(positionConverter.convert(3, 1), glassStack);
        basicInventory.setItem(positionConverter.convert(7, 1), glassStack);
        basicInventory.setItem(positionConverter.convert(2, 2), glassStack);
        basicInventory.setItem(positionConverter.convert(3, 2), glassStack);
        basicInventory.setItem(positionConverter.convert(7, 2), glassStack);
        basicInventory.setItem(positionConverter.convert(8, 2), glassStack);
        basicInventory.setItem(positionConverter.convert(2, 3), glassStack);
        basicInventory.setItem(positionConverter.convert(8, 3), glassStack);
        basicInventory.setItem(positionConverter.convert(2, 4), glassStack);
        basicInventory.setItem(positionConverter.convert(8, 4), glassStack);
        basicInventory.setItem(positionConverter.convert(2, 5), glassStack);
        basicInventory.setItem(positionConverter.convert(3, 5), glassStack);
        basicInventory.setItem(positionConverter.convert(7, 5), glassStack);
        basicInventory.setItem(positionConverter.convert(8, 5), glassStack);
        basicInventory.setItem(positionConverter.convert(2, 3), glassStack);
        basicInventory.setItem(positionConverter.convert(8, 3), glassStack);
        basicInventory.setItem(positionConverter.convert(3, 6), glassStack);
        basicInventory.setItem(positionConverter.convert(7, 6), glassStack);

        ItemStack cosmetiques = new ItemStack(Material.JUKEBOX);
        ItemMeta cosmetiquesMeta = cosmetiques.getItemMeta();
        cosmetiquesMeta.setDisplayName("§5§lCosmétiques");
        cosmetiques.setItemMeta(cosmetiquesMeta);

        basicInventory.setItem(positionConverter.convert(1, 1), cosmetiques);
        basicInventory.setItem(positionConverter.convert(9, 1), cosmetiques);

        /**
         *  Item: particules
         */

        ItemStack particules = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta particulesMeta = particules.getItemMeta();
        particulesMeta.setDisplayName("§6§lParticules");
        particules.setItemMeta(particulesMeta);

        basicInventory.setItem(positionConverter.convert(5, 3), particules);

        ItemStack colorant = new ItemStack(Material.INK_SACK, 1, (short)0, (byte)8); // 8 = gris & 10 = vert
        ItemMeta colorantMeta = particules.getItemMeta();
        colorantMeta.setDisplayName("§cDésactivé");
        colorant.setItemMeta(colorantMeta);

        basicInventory.setItem(positionConverter.convert(5, 4), colorant);

        /**
         *  Item: gadgets
         */

        ItemStack gadgets = new ItemStack(Material.REDSTONE);
        ItemMeta gadgetsMeta = gadgets.getItemMeta();
        gadgetsMeta.setDisplayName("§c§lGadgets");
        gadgets.setItemMeta(gadgetsMeta);

        basicInventory.setItem(positionConverter.convert(7, 3), gadgets);

        basicInventory.setItem(positionConverter.convert(7, 4), colorant);

        /**
         *  Item: animaux
         */

        ItemStack animaux = new ItemStack(Material.DIAMOND_BARDING);
        ItemMeta animauxMeta = animaux.getItemMeta();
        animauxMeta.setDisplayName("§3§lAnimaux");
        animaux.setItemMeta(animauxMeta);

        basicInventory.setItem(positionConverter.convert(3, 3), animaux);

        basicInventory.setItem(positionConverter.convert(3, 4), colorant);

    }

    public static void open(Player player){

        if(Manager.playersBuying.contains(player.getUniqueId()))
            Manager.playersBuying.remove(player.getUniqueId());

        if(Manager.achats.contains(player.getUniqueId()))
            Manager.achats.remove(player.getUniqueId());

        player.openInventory(basicInventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals(basicInventory.getName())){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§c§lGadgets":
                        player.closeInventory();
                        player.sendMessage("§cEn dev'");
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        break;
                    case "§3§lAnimaux":
                        player.closeInventory();
                        player.sendMessage("§cEn dev'");
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        break;
                    case "§6§lParticules":
                        Particle_SubMenu.open(player);
                        break;
                    default:
                        break;
                }

            }
        }
    }


}
