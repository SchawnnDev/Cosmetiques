/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.Pet_SubMenu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 31/05/15 00:47.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.menus;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Achat;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.api.utils.ItemDisponibility;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.math.PositionConverter;
import fr.schawnndev.pets.PetManager;
import fr.schawnndev.sql.SQLManager;
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

import java.util.ArrayList;
import java.util.List;

public class Pet_SubMenu implements Listener {

    private static PositionConverter positionConverter = new PositionConverter();

    public Pet_SubMenu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    public static void open(Player player){
        Inventory inv = Bukkit.createInventory(null, 6*9, "               §6§oPets");

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

        ItemStack cosmetiques = new ItemStack(356);
        ItemMeta cosmetiquesMeta = cosmetiques.getItemMeta();
        cosmetiquesMeta.setDisplayName("§cPets");
        cosmetiques.setItemMeta(cosmetiquesMeta);

        ItemStack retour = new ItemStack(Material.ARROW);
        ItemMeta retourMeta = retour.getItemMeta();
        retourMeta.setDisplayName("§7<===");
        retourMeta.setLore(MenuManager.getNewLore("§6Page précédente"));
        retour.setItemMeta(retourMeta);

        inv.setItem(positionConverter.convert(1, 1), cosmetiques);
        inv.setItem(positionConverter.convert(2, 6), retour);
        inv.setItem(positionConverter.convert(9, 1), cosmetiques);
        inv.setItem(positionConverter.convert(5, 1), ItemStackManager.getHead(player));

        List<ItemStack> items = generateItemStacks(player);

        int[] positions = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

        for(int i = 0; i < items.size(); i++){

            ItemStack item = items.get(i);

            int count = 0;

            while (positions[count] != 1)
                count++;

            positions[count] = 0;

            ItemDisponibility.InventoryPosition inventory = (ItemDisponibility.InventoryPosition) ItemDisponibility.getInventoryPositions().toArray()[count];

            inv.setItem(positionConverter.convert(inventory.getColonne(), inventory.getLigne()), item);

        }

        player.openInventory(inv);
    }

    public static List<ItemStack> generateItemStacks(Player player){
        List<ItemStack> itemStacks = new ArrayList<>();
        List<String> cosmetics = SQLManager.getCosmetics(player);

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.BONE), CosmetiqueManager.Cosmetique.LOUP.getPrice(),
                CosmetiqueManager.Cosmetique.LOUP.isVip(), "§eLoup", MenuManager.getNewLore("§7Un compagnon fidèle et minon comme tout."), cosmetics.contains("loup"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.RABBIT_FOOT), CosmetiqueManager.Cosmetique.LAPIN.getPrice(),
                CosmetiqueManager.Cosmetique.LAPIN.isVip(), "§eLapin", MenuManager.getNewLore("§7Un lapin trop mimi !"), cosmetics.contains("lapin"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.WOOL), CosmetiqueManager.Cosmetique.MOUTON.getPrice(),
                CosmetiqueManager.Cosmetique.MOUTON.isVip(), "§eMouton", MenuManager.getNewLore("§7Mmmêêêh !") , cosmetics.contains("mouton"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FEATHER), CosmetiqueManager.Cosmetique.POULET.getPrice(),
                CosmetiqueManager.Cosmetique.POULET.isVip(), "§ePoulet", MenuManager.getNewLore("§7Kott.. Kottkottquelette... !"), cosmetics.contains("poulet"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.ROTTEN_FLESH), CosmetiqueManager.Cosmetique.ZOMBIE.getPrice(),
                CosmetiqueManager.Cosmetique.ZOMBIE.isVip(), "§eZombie", MenuManager.getNewLore("§7Attention, ça mord !"), cosmetics.contains("zombie"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SULPHUR), CosmetiqueManager.Cosmetique.CREEPER.getPrice(),
                CosmetiqueManager.Cosmetique.CREEPER.isVip(), "§eCreeper", MenuManager.getNewLore("§7Faites attention, ça peut exploser !"), cosmetics.contains("creeper"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.BOW), CosmetiqueManager.Cosmetique.SQUELETTE.getPrice(),
                CosmetiqueManager.Cosmetique.SQUELETTE.isVip(), "§eSquelette", MenuManager.getNewLore("§7Ayez une visée de sniper !"), cosmetics.contains("squelette"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.LEATHER), CosmetiqueManager.Cosmetique.VACHE.getPrice(),
                CosmetiqueManager.Cosmetique.VACHE.isVip(), "§eVache", MenuManager.getNewLore("§7Meuh !"), cosmetics.contains("vache"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GOLD_NUGGET), CosmetiqueManager.Cosmetique.PIGMAN.getPrice(),
                CosmetiqueManager.Cosmetique.PIGMAN.isVip(), "§ePigman", MenuManager.getNewLore("§7Une bête atroce !"), cosmetics.contains("pigman"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.IRON_BARDING), CosmetiqueManager.Cosmetique.CHEVAL.getPrice(),
                CosmetiqueManager.Cosmetique.CHEVAL.isVip(), "§eCheval", MenuManager.getNewLore("§7Devenez un vrai aventurier."), cosmetics.contains("cheval"), CosmetiqueManager.CosmetiqueType.PET));

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.RED_MUSHROOM), CosmetiqueManager.Cosmetique.VACHE_CHAMPIGNON.getPrice(),
                CosmetiqueManager.Cosmetique.VACHE_CHAMPIGNON.isVip(), "§eVache Champignon", MenuManager.getNewLore("§7Des gros champignons partout D:"), cosmetics.contains("vache_champignon"), CosmetiqueManager.CosmetiqueType.PET));

        return itemStacks;
    }

    private void proceedClick(Player player, CosmetiqueManager.Cosmetique cosmetique,  String id){

        String name = cosmetique == CosmetiqueManager.Cosmetique.VACHE_CHAMPIGNON ? "Vache Champignon" : id.toLowerCase().substring(0, 1).toUpperCase() + id.toLowerCase().substring(1);

        if(PetManager.hasActivePet(player) && PetManager.getPet(player).getCosmetiqueType() == cosmetique){
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
        } else {

            if (cosmetique.isVip()) {

                if (player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")) {
                    player.closeInventory();
                    player.sendMessage("§aTu viens de séléctionner le pet §b" + name + "§a !");
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                    PetManager.addPlayerPet(player, cosmetique);
                } else {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                    player.sendMessage("§cTu dois VIP pour avoir le pet §b" + name + "§c !");
                }

            } else {
                if (SQLManager.hasBuyCosmetic(player, id)) {
                    player.closeInventory();
                    player.sendMessage("§aTu viens de séléctionner le pet §b" + name + "§a !");
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                    PetManager.addPlayerPet(player, cosmetique);
                } else {
                    Achat achat = new Achat(id, cosmetique, player);
                    achat.generate();
                    achat.proceedOpening();
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("               §6§oPets")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){

                    case "§7<===":
                        Main_Menu.open(player);
                        break;

                    case "§eVache":
                        proceedClick(player, CosmetiqueManager.Cosmetique.VACHE, "vache");
                        break;

                    case "§eCheval":
                        proceedClick(player, CosmetiqueManager.Cosmetique.CHEVAL, "cheval");
                        break;

                    case "§eVache Champignon":
                        proceedClick(player, CosmetiqueManager.Cosmetique.VACHE_CHAMPIGNON, "vachechampignon");
                        break;

                    case "§ePoulet":
                        proceedClick(player, CosmetiqueManager.Cosmetique.POULET, "poulet");
                        break;

                    case "§eZombie":
                        proceedClick(player, CosmetiqueManager.Cosmetique.ZOMBIE, "zombie");
                        break;

                    case "§eCreeper":
                        proceedClick(player, CosmetiqueManager.Cosmetique.CREEPER, "creeper");
                        break;

                    case "§ePigman":
                        proceedClick(player, CosmetiqueManager.Cosmetique.PIGMAN, "pigman");
                        break;

                    case "§eSquelette":
                        proceedClick(player, CosmetiqueManager.Cosmetique.SQUELETTE, "squelette");
                        break;

                    case "§eLapin":
                        proceedClick(player, CosmetiqueManager.Cosmetique.LAPIN, "lapin");
                        break;

                    case "§eLoup":
                        proceedClick(player, CosmetiqueManager.Cosmetique.LOUP, "loup");
                        break;

                    case "§eMouton":
                        proceedClick(player, CosmetiqueManager.Cosmetique.MOUTON, "mouton");
                        break;

                    default:
                        break;
                }

            }
        }
    }

}
