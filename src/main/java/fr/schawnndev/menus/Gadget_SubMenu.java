/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.Gadget_SubMenu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 19:15.
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

public class Gadget_SubMenu implements Listener {

    private static PositionConverter positionConverter = new PositionConverter();

    public Gadget_SubMenu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    public static void open(Player player){
        Inventory inv = Bukkit.createInventory(null, 6*9, "Gadgets");

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
        cosmetiquesMeta.setDisplayName("§cGadgets");
        cosmetiques.setItemMeta(cosmetiquesMeta);

        ItemStack retour = new ItemStack(Material.ARROW);
        ItemMeta retourMeta = retour.getItemMeta();
        retourMeta.setDisplayName("§6Page précédente");
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

        List<String> doublejumpLore = new ArrayList<>();
        doublejumpLore.add("§7Sautez tel un lapin.");
        doublejumpLore.add("  ");
        doublejumpLore.add("§7Recharge : §a5 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FEATHER), CosmetiqueManager.Cosmetique.DOUBLE_JUMP, player, "§bDouble Jump", doublejumpLore, cosmetics.contains("doublejump")));

        List<String> musicLore = new ArrayList<>();
        musicLore.add("§7Faites profiter votre entourage de belles");
        musicLore.add("§7musiques de Minecraft dans un rayon de 30 blocs.");
        musicLore.add("  ");
        musicLore.add("§7Recharge : §a2 minutes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(2258), CosmetiqueManager.Cosmetique.MUSIC, player, "§2Music", musicLore, cosmetics.contains("music")));

        List<String> cakeLore = new ArrayList<>();
        cakeLore.add("§7Posez un gâteau empoisonné qui va infliger des");
        cakeLore.add("§7des dégâts d'émpoisonnements qui en mangera");
        cakeLore.add("  ");
        cakeLore.add("§7Recharge : §a45 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.CAKE), CosmetiqueManager.Cosmetique.GATEAU_EMPOISONNE, player, "§3Gâteau Empoisonné", cakeLore , cosmetics.contains("gateauempoisonne")));

        List<String> glaceLore = new ArrayList<>();
        glaceLore.add("§7Lancez une boule de neige dans les airs");
        glaceLore.add("§7qui traçera un chemin en blocs de glaces !");
        glaceLore.add("  ");
        glaceLore.add("§7Recharge : §a20 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SNOW_BALL), CosmetiqueManager.Cosmetique.GLACE, player, "§cça glisse !", glaceLore, cosmetics.contains("laisse")));

        List<String> canonLore = new ArrayList<>();
        canonLore.add("§7Invoquez aléatoirement un monstre parmis");
        canonLore.add("§7un zombie, une araignée, un cochon zombie");
        canonLore.add("§7ou encore un squelette.");
        canonLore.add("  ");
        canonLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SULPHUR), CosmetiqueManager.Cosmetique.CANON, player, "§fCanon", canonLore, cosmetics.contains("canon")));

        List<String> appleLore = new ArrayList<>();
        appleLore.add("§7Faites sortir des pommes de votre");
        appleLore.add("§7tête tel un magicien qui ferait sortir");
        appleLore.add("§7un lapin de son chapeau magique !");
        appleLore.add("  ");
        appleLore.add("§7Recharge : §a40 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GOLDEN_APPLE), CosmetiqueManager.Cosmetique.APPLE, player, "§eApple", appleLore, cosmetics.contains("apple")));

        List<String> encreLore = new ArrayList<>();
        encreLore.add("§7Aveuglez le joueur ciblé pendant §a5 secondes§7 !");
        encreLore.add("  ");
        encreLore.add("§7Recharge : §a25 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(351), CosmetiqueManager.Cosmetique.ENCRE, player, "§cEncre", encreLore, cosmetics.contains("encre")));

        List<String> canneapechesLore = new ArrayList<>();
        canneapechesLore.add("§7Téléportez-vous où vous lancez votre hameçon !");
        canneapechesLore.add("  ");
        canneapechesLore.add("§7Recharge : §a5 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FISHING_ROD), CosmetiqueManager.Cosmetique.CANNE_A_PECHE, player, "§6Canne à pêche", canneapechesLore, cosmetics.contains("canneapeche")));

        List<String> ibelieveicanflyLore = new ArrayList<>();

        ibelieveicanflyLore.add("§7Vous rêvez de vous envoyer en l'air ?");
        ibelieveicanflyLore.add("§7cette fireball est faite pour vous !");
        ibelieveicanflyLore.add("  ");
        ibelieveicanflyLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FIREBALL), CosmetiqueManager.Cosmetique.FIREBALL, player, "§2I believe I can Fly", ibelieveicanflyLore, cosmetics.contains("fireball")));

        List<String> tntLore = new ArrayList<>();

        tntLore.add("§7Lorsque vous posez la TNT sur le sol, elle explose");
        tntLore.add("§7puis propulse les joueurs dans un rayon de 30 blocs.");
        tntLore.add("  ");
        tntLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.TNT), CosmetiqueManager.Cosmetique.TNT, player, "§7TNT", tntLore, cosmetics.contains("tnt")));

        List<String> artificeLore = new ArrayList<>();
        artificeLore.add("§7Propulsez-vous dans les airs avec le feu d'artifice !");
        artificeLore.add("  ");
        artificeLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FIREWORK), CosmetiqueManager.Cosmetique.ARTIFICE, player, "§514 Juillet", artificeLore, cosmetics.contains("artifice")));

        return itemStacks;
    }

    private void proceedClick(Player player, CosmetiqueManager.Cosmetique cosmetique,  String id){
        if(GadgetManager.hasGadget(player, id)){
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
        } else {

            String name = id.toLowerCase().substring(0, 1).toUpperCase() + id.toLowerCase().substring(1);

            if (cosmetique.isVip()) {

                if (player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")) {

                    player.getInventory().setItem(4, ItemStackManager.getItemStack(cosmetique));

                    player.closeInventory();
                    player.sendMessage("§aTu viens de séléctioner le gadget §b" + name + "§a !");
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                    GadgetManager.addGadget(player, id, false);
                } else {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                    player.sendMessage("§cTu dois VIP pour utiliser le gadget §b" + name + "§c !");
                }

            } else {
                if (SQLManager.hasBuyCosmetic(player, id)) {
                    player.closeInventory();
                    player.getInventory().setItem(4, ItemStackManager.getItemStack(cosmetique));
                    player.sendMessage("§aTu viens de séléctioner le gadget §b" + name + "§a !");
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                    GadgetManager.addGadget(player, id, false);
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
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("Gadgets")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§6Page précédente":
                        Main_Menu.open(player);
                        break;

                    case "§7TNT":
                        proceedClick(player, CosmetiqueManager.Cosmetique.TNT, "tnt");
                        break;

                    case "§cEncre":
                        proceedClick(player, CosmetiqueManager.Cosmetique.ENCRE, "encre");
                        break;

                    case "§cça glisse !":
                        proceedClick(player, CosmetiqueManager.Cosmetique.GLACE, "glace");
                        break;

                    case "§fCanon":
                        proceedClick(player, CosmetiqueManager.Cosmetique.CANON, "canon");
                        break;

                    default:
                        break;
                }

            }
        }
    }

}
