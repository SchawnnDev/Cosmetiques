/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.menus.Particle_SubMenu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 19:16.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.menus;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Achat;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.api.utils.ItemDisponibility;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.math.PositionConverter;
import fr.schawnndev.particules.ParticleManager;
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

public class Particle_SubMenu implements Listener {

    private static PositionConverter positionConverter = new PositionConverter();

    public Particle_SubMenu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    public static void open(Player player){
        Inventory inv = Bukkit.createInventory(null, 6*9, "Particules");

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

        ItemStack cosmetiques = new ItemStack(Material.MELON_SEEDS);
        ItemMeta cosmetiquesMeta = cosmetiques.getItemMeta();
        cosmetiquesMeta.setDisplayName("§6Particules");
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

        List<String> magicienLore = new ArrayList<>();
        magicienLore.add("§7Entourez-vous de petites particules d'enchantements");
        magicienLore.add("§7mystérieuses et sympathiques.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.ENCHANTMENT_TABLE), Cosmetique.MAGICIEN, player, "§5Magicien", magicienLore, cosmetics.contains("magicien")));

        List<String> pluieLore = MenuManager.getNewLore("§7Il me semble que vous avez oublié votre parapluie.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.WATER_BUCKET), Cosmetique.PLUIE, player, "§3Pluie", pluieLore, cosmetics.contains("pluie")));

        List<String> laveLore = new ArrayList<>();
        laveLore.add("§7Très pratique en hiver, cette");
        laveLore.add("particule remplacera votre chauffage.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.LAVA_BUCKET), Cosmetique.LAVE, player, "§6Lave", laveLore, cosmetics.contains("lave")));

        List<String> notesLore = new ArrayList<>();
        notesLore.add("§7Exprimez vos talents de danseur grâce à");
        notesLore.add("§7ces petites particules en forme de note.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.NOTE_BLOCK), Cosmetique.NOTES, player, "§dNotes", notesLore, cosmetics.contains("notes")));

        List<String> emeraldLore = new ArrayList<>();
        emeraldLore.add("§7Laissez parler votre humeur.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.EMERALD), Cosmetique.CONTENT, player, "§aContent", emeraldLore, cosmetics.contains("content")));

        List<String> fumeeLore = MenuManager.getNewLore("§7Attention ça brûle !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SULPHUR), Cosmetique.FUMEE, player, "§7Fumée", fumeeLore, cosmetics.contains("fumee")));

        List<String> flamesLore = new ArrayList<>();
        flamesLore.add("§7Les flammes vous encerclent,");
        flamesLore.add("§7telle une barrière protectrice.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.MOB_SPAWNER), Cosmetique.FLAMES, player, "§eFlames", flamesLore, cosmetics.contains("flames")));

        List<String> spiralesLore = MenuManager.getNewLore("§7Faites apparaître votre aura de puissance.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.POTION), Cosmetique.SPIRALES, player, "§bSpirales", spiralesLore, cosmetics.contains("spirales")));

        List<String> redstoneLore = MenuManager.getNewLore("§7Attention à ne pas faire court-circuiter les serveurs !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.REDSTONE), Cosmetique.REDSTONE, player, "§4Redstone", redstoneLore, cosmetics.contains("redstone")));

        List<String> coeursLore = MenuManager.getNewLore("§7Laissez parler vos sentiments !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.RED_ROSE), Cosmetique.COEURS, player, "§cCoeurs", coeursLore, cosmetics.contains("coeurs")));

        List<String> legendaryLore = new ArrayList<>();
        legendaryLore.add("§7Seuls les riches auront la chance de pouvoir");
        legendaryLore.add("§7se payer cette particule si secrète !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short)0, (byte)1), Cosmetique.LEGENDARY, player, "§c§lLegendary", legendaryLore, cosmetics.contains("legendary")));

        return itemStacks;
    }

    /**
     * @param player The player to proceed click
     * @param cosmetique The cosmetique (Particle) to proceed click
     * @param id The mysql name (id) to proceed click
     */

    private void proceedClick(Player player, CosmetiqueManager.Cosmetique cosmetique,  String id){

        if(!ParticleManager.isParticleActive(player, id)){

            String name = id.toLowerCase().substring(0, 1).toUpperCase() + id.toLowerCase().substring(1);

            if(cosmetique.isVip()){

                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                    if(ParticleManager.hasParticleActive(player)){
                        ParticleManager.removeActiveParticle(player);
                    }

                    player.closeInventory();
                    player.sendMessage("§aTu viens d'activer la particule §b" + name + "§a !");
                    ParticleManager.activeParticleByName(player, id);
                } else {

                    player.sendMessage("§cVous devez être §eVIP §cpour utiliser cette Particule.");
                    player.sendMessage("§b§lCliquez-ici §r§bpour le devenir : §chttp://store.legendcraft.fr");

                }

            } else {
                if(SQLManager.hasBuyCosmetic(player, id)){

                    if(ParticleManager.hasParticleActive(player)){
                        ParticleManager.removeActiveParticle(player);
                    }

                    player.closeInventory();
                    player.sendMessage("§aTu viens d'activer la particule §b" + name + "§a !");
                    ParticleManager.activeParticleByName(player, id);
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
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("Particules")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§6Page précédente":
                        Main_Menu.open(player);
                        break;

                    case "§6Lave":

                        proceedClick(player, Cosmetique.LAVE, "lave");

                        break;
                    case "§7Fumée":

                        proceedClick(player, Cosmetique.FUMEE, "fumee");

                        break;
                    case "§5Magicien":

                        proceedClick(player, Cosmetique.MAGICIEN, "magicien");

                        break;
                    case "§4Redstone":

                        proceedClick(player, Cosmetique.REDSTONE, "redstone");

                        break;
                    case "§cCoeurs":

                        proceedClick(player, Cosmetique.COEURS, "coeurs");

                        break;
                    case "§eFlames":

                        proceedClick(player, Cosmetique.FLAMES, "flames");

                        break;
                    case "§aContent":

                        proceedClick(player, Cosmetique.CONTENT, "content");

                        break;
                    case "§dNotes":

                        proceedClick(player, Cosmetique.NOTES, "notes");

                        break;
                    case "§bSpirales":

                        proceedClick(player, Cosmetique.SPIRALES, "spirales");

                        break;
                    case "§3Pluie":

                        proceedClick(player, Cosmetique.PLUIE, "pluie");

                        break;
                    default:
                        break;
                }

            }
        }
    }

}
