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
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.math.PositionConverter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Main_Menu implements Listener {

    private static PositionConverter positionConverter = new PositionConverter();

    public Main_Menu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    public static void open(Player player){
        Inventory inv = Bukkit.createInventory(null, 6*9, "             §6§oCosmétiques");

        ItemStack glassStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0, GlassColor.WHITE.getData());
        ItemMeta glassMeta = glassStack.getItemMeta();
        glassMeta.setDisplayName("§7-");
        glassStack.setItemMeta(glassMeta);

        inv.setItem(positionConverter.convert(3, 1), glassStack);
        inv.setItem(positionConverter.convert(7, 1), glassStack);
        inv.setItem(positionConverter.convert(2, 2), glassStack);
        inv.setItem(positionConverter.convert(3, 2), glassStack);
        inv.setItem(positionConverter.convert(7, 2), glassStack);
        inv.setItem(positionConverter.convert(8, 2), glassStack);
        inv.setItem(positionConverter.convert(2, 3), glassStack);
        inv.setItem(positionConverter.convert(8, 3), glassStack);
        inv.setItem(positionConverter.convert(2, 4), glassStack);
        inv.setItem(positionConverter.convert(8, 4), glassStack);
        inv.setItem(positionConverter.convert(2, 5), glassStack);
        inv.setItem(positionConverter.convert(3, 5), glassStack);
        inv.setItem(positionConverter.convert(7, 5), glassStack);
        inv.setItem(positionConverter.convert(8, 5), glassStack);
        inv.setItem(positionConverter.convert(2, 3), glassStack);
        inv.setItem(positionConverter.convert(8, 3), glassStack);
        inv.setItem(positionConverter.convert(3, 6), glassStack);
        inv.setItem(positionConverter.convert(7, 6), glassStack);

        /**
         *  Item: particules
         */

        ItemStack particules = new ItemStack(Material.MELON_SEEDS);
        ItemMeta particulesMeta = particules.getItemMeta();
        particulesMeta.setDisplayName("§6Particules");
        particules.setItemMeta(particulesMeta);

        inv.setItem(positionConverter.convert(5, 3), particules);

        ItemStack colorant = new ItemStack(Material.INK_SACK, 1, (short)0, (byte)8); // 8 = gris & 10 = vert
        ItemMeta colorantMeta = particules.getItemMeta();
        colorantMeta.setDisplayName("§cDésactivé");
        colorant.setItemMeta(colorantMeta);

        inv.setItem(positionConverter.convert(5, 4), colorant);

        /**
         *  Item: gadgets
         */

        ItemStack gadgets = new ItemStack(356);
        ItemMeta gadgetsMeta = gadgets.getItemMeta();
        gadgetsMeta.setDisplayName("§cGadgets");
        gadgets.setItemMeta(gadgetsMeta);

        inv.setItem(positionConverter.convert(7, 3), gadgets);

        inv.setItem(positionConverter.convert(7, 4), colorant);

        /**
         *  Item: animaux
         */

        ItemStack animaux = new ItemStack(Material.DIAMOND_BARDING);
        ItemMeta animauxMeta = animaux.getItemMeta();
        animauxMeta.setDisplayName("§3Pets");
        animaux.setItemMeta(animauxMeta);

        inv.setItem(positionConverter.convert(3, 3), animaux);

        inv.setItem(positionConverter.convert(3, 4), colorant);

        /**
         *  Item: head
         */

        inv.setItem(positionConverter.convert(5, 1), ItemStackManager.getHead(player));

        /**
         *  Achats
         */


        if(Manager.playersBuying.contains(player.getUniqueId()))
            Manager.playersBuying.remove(player.getUniqueId());

        if(Manager.achats.contains(player.getUniqueId()))
            Manager.achats.remove(player.getUniqueId());

        /**
         *  Open
         */

        player.openInventory(inv);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
            if(e.getItem() != null && e.getItem().getType() == Material.JUKEBOX && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equals("§f=== §5Cosmétiques §f===  "))
                open(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("             §6§oCosmétiques")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§cGadgets":
                        Gadget_SubMenu.open(player);
                        break;
                    case "§3Pets":
                        player.closeInventory();
                        player.sendMessage("§cEn dev'");
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        break;
                    case "§6Particules":
                        Particle_SubMenu.open(player);
                        break;
                    default:
                        break;
                }

            }
        }
    }


}
