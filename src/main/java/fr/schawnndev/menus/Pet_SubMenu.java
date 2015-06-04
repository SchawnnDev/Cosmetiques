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
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.api.utils.ItemDisponibility;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.math.PositionConverter;
import fr.schawnndev.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

        List<String> doublejumpLore = new ArrayList<>();
        doublejumpLore.add("§7Double Jump sur les §aHubs§7.");
        doublejumpLore.add("  ");
        doublejumpLore.add("§7Recharge : §a5 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FEATHER), CosmetiqueManager.Cosmetique.DOUBLE_JUMP.getPrice(),
                CosmetiqueManager.Cosmetique.DOUBLE_JUMP.isVip(), "§bDouble Jump", doublejumpLore, cosmetics.contains("doublejump"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> musicLore = new ArrayList<>();
        musicLore.add("§7Faites profiter votre entourage de belles");
        musicLore.add("§7musiques de Minecraft dans un rayon de 30 blocs.");
        musicLore.add("  ");
        musicLore.add("§7Recharge : §a2 minutes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(2258), CosmetiqueManager.Cosmetique.MUSIC.getPrice(),
                CosmetiqueManager.Cosmetique.MUSIC.isVip(), "§2Music", musicLore, cosmetics.contains("music"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> cakeLore = new ArrayList<>();
        cakeLore.add("§7Posez un gâteau empoisonné qui va infliger des");
        cakeLore.add("§7des dégâts d'émpoisonnements qui en mangera");
        cakeLore.add("  ");
        cakeLore.add("§7Recharge : §a45 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.CAKE), CosmetiqueManager.Cosmetique.GATEAU_EMPOISONNE.getPrice(),
                CosmetiqueManager.Cosmetique.GATEAU_EMPOISONNE.isVip(), "§3Gâteau Empoisonné",cakeLore , cosmetics.contains("gateauempoisonne"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> laisseLore = new ArrayList<>();
        laisseLore.add("§7Attrapez les joueurs avec un simple clic");
        laisseLore.add("§7et baladez les sur toute la map !");
        laisseLore.add("  ");
        laisseLore.add("§7Recharge : §a10 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.LEASH), CosmetiqueManager.Cosmetique.LAISSE.getPrice(),
                CosmetiqueManager.Cosmetique.LAISSE.isVip(), "§4Attrape moi, si tu peux !", laisseLore, cosmetics.contains("laisse"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> canonLore = new ArrayList<>();
        canonLore.add("§7Invoquez aléatoirement un monstre parmis");
        canonLore.add("§7un zombie, une araignée, un cochon zombie");
        canonLore.add("§7ou encore un squelette.");
        canonLore.add("  ");
        canonLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SULPHUR), CosmetiqueManager.Cosmetique.CANON.getPrice(),
                CosmetiqueManager.Cosmetique.CANON.isVip(), "§fCanon", canonLore, cosmetics.contains("canon"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> appleLore = new ArrayList<>();
        appleLore.add("§7Oh yééééééé !");
        appleLore.add("  ");
        appleLore.add("§7Recharge : §a40 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GOLDEN_APPLE), CosmetiqueManager.Cosmetique.APPLE.getPrice(),
                CosmetiqueManager.Cosmetique.APPLE.isVip(), "§eApple", appleLore, cosmetics.contains("apple"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> encreLore = new ArrayList<>();
        encreLore.add("§7Aveuglez le joueur ciblé pendant §a5 secondes§7 !");
        encreLore.add("  ");
        encreLore.add("§7Recharge : §a10 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(351), CosmetiqueManager.Cosmetique.ENCRE.getPrice(),
                CosmetiqueManager.Cosmetique.ENCRE.isVip(), "§cEncre", encreLore, cosmetics.contains("encre"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> canneapechesLore = new ArrayList<>();
        canneapechesLore.add("§7Téléportez-vous où vous lancez votre hameçon !");
        canneapechesLore.add("  ");
        canneapechesLore.add("§7Recharge : §a5 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FISHING_ROD), CosmetiqueManager.Cosmetique.CANNE_A_PECHE.getPrice(),
                CosmetiqueManager.Cosmetique.CANNE_A_PECHE.isVip(), "§6Canne à pêche", canneapechesLore, cosmetics.contains("canneapeche"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> ibelieveicanflyLore = new ArrayList<>();

        ibelieveicanflyLore.add("§7Vous rêvez de vous envoyer en l'air ?");
        ibelieveicanflyLore.add("§7cette fireball est faite pour vous !");
        ibelieveicanflyLore.add("  ");
        ibelieveicanflyLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FIREBALL), CosmetiqueManager.Cosmetique.FIREBALL.getPrice(),
                CosmetiqueManager.Cosmetique.FIREBALL.isVip(), "§2I believe I can Fly", ibelieveicanflyLore, cosmetics.contains("fireball"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> tntLore = new ArrayList<>();

        tntLore.add("§7Lorsque vous posez la TNT sur le sol, elle explose");
        tntLore.add("§7puis propulse les joueurs dans un rayon de 30 blocs.");
        tntLore.add("  ");
        tntLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.TNT), CosmetiqueManager.Cosmetique.TNT.getPrice(),
                CosmetiqueManager.Cosmetique.TNT.isVip(), "§7TNT", tntLore, cosmetics.contains("tnt"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> artificeLore = new ArrayList<>();
        artificeLore.add("§7Propulsez-vous dans les airs avec le feu d'artifice !");
        artificeLore.add("  ");
        artificeLore.add("§7Recharge : §a15 secondes§7.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.FIREWORK), CosmetiqueManager.Cosmetique.ARTIFICE.getPrice(),
                CosmetiqueManager.Cosmetique.ARTIFICE.isVip(), "§514 Juillet", artificeLore, cosmetics.contains("artifice"), CosmetiqueManager.CosmetiqueType.GADGET));

        return itemStacks;
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

                    default:
                        break;
                }

            }
        }
    }

}
