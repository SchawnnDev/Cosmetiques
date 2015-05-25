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
import fr.schawnndev.particules.Particle;
import fr.schawnndev.particules.ParticleManager;
import fr.schawnndev.sql.SQLManager;
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

import java.util.ArrayList;
import java.util.List;

public class Particle_SubMenu implements Listener {

    @Getter
    private static Inventory basicInventory;

    private static PositionConverter positionConverter = new PositionConverter();

    public Particle_SubMenu(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());

        basicInventory = Bukkit.createInventory(null, 6*9, "§6Particules");

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

        ItemStack cosmetiques = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta cosmetiquesMeta = cosmetiques.getItemMeta();
        cosmetiquesMeta.setDisplayName("§5§lParticules");
        cosmetiques.setItemMeta(cosmetiquesMeta);

        basicInventory.setItem(positionConverter.convert(1, 1), cosmetiques);
        basicInventory.setItem(positionConverter.convert(9, 1), cosmetiques);

    }

    public static void open(Player player){
        Inventory inv = getBasicInventory();
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
        magicienLore.add("§7Tu as toujours rêvé d'être un magicien ?");
        magicienLore.add("§7Alors cette particule va bien te décrire.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.ENCHANTMENT_TABLE), Cosmetique.MAGICIEN.getPrice(),
                Cosmetique.MAGICIEN.isVip(), "§5Magicien", magicienLore, cosmetics.contains("magicien")));

        List<String> pluieLore = new ArrayList<>();
        pluieLore.add("§7Je crois que tu as oublié du parapluie :P");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.WATER_BUCKET), Cosmetique.PLUIE.getPrice(),
                Cosmetique.PLUIE.isVip(), "§3Pluie", pluieLore, cosmetics.contains("pluie")));

        List<String> laveLore = new ArrayList<>();
        laveLore.add("§7C'est chaud tout ça..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.LAVA_BUCKET), Cosmetique.LAVE.getPrice(),
                Cosmetique.LAVE.isVip(), "§6Lave", laveLore, cosmetics.contains("lave")));

        List<String> notesLore = new ArrayList<>();
        notesLore.add("§7Au yééééé..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.JUKEBOX), Cosmetique.NOTES.getPrice(),
                Cosmetique.NOTES.isVip(), "§dNotes", notesLore, cosmetics.contains("notes")));

        List<String> emeraldLore = new ArrayList<>();
        emeraldLore.add("§7Au yééééé..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.EMERALD), Cosmetique.CONTENT.getPrice(),
                Cosmetique.CONTENT.isVip(), "§aContent", emeraldLore, cosmetics.contains("content")));

        List<String> fumeeLore = MenuManager.getNewLore("§7Au yééééé..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SULPHUR), Cosmetique.FUMEE.getPrice(),
                Cosmetique.FUMEE.isVip(), "§7Fumée", fumeeLore, cosmetics.contains("fumee")));

        List<String> flamesListener = MenuManager.getNewLore("§7Attention ça brûle !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.MOB_SPAWNER), Cosmetique.FLAMES.getPrice(),
                Cosmetique.FLAMES.isVip(), "§eFlames", flamesListener, cosmetics.contains("flames")));

        List<String> spiralesLore = MenuManager.getNewLore("§7Au yééééé..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GLASS_BOTTLE), Cosmetique.SPIRALES.getPrice(),
                Cosmetique.SPIRALES.isVip(), "§bSpirales", spiralesLore, cosmetics.contains("spirales")));

        List<String> redstoneLore = MenuManager.getNewLore("§7Au yééééé..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.REDSTONE), Cosmetique.REDSTONE.getPrice(),
                Cosmetique.REDSTONE.isVip(), "§4Redstone", redstoneLore, cosmetics.contains("redstone")));

        List<String> coeursLore = MenuManager.getNewLore("§7Montrez votre amour !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.RED_ROSE), Cosmetique.COEURS.getPrice(),
                Cosmetique.COEURS.isVip(), "§cCoeurs", coeursLore, cosmetics.contains("coeurs")));

        List<String> legendaryLore = MenuManager.getNewLore("§7Au yééééé..");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short)0, (byte)1), Cosmetique.LEGENDARY.getPrice(),
                Cosmetique.LEGENDARY.isVip(), "§cLege§3ndary", legendaryLore, cosmetics.contains("legendary")));

        return itemStacks;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals(basicInventory.getName())){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§cCoeurs":

                        if(!ParticleManager.isParticleActive(player, "coeurs")){

                            if(Cosmetique.COEURS.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bcoeurs§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleCoeur().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bcoeurs !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "coeurs")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bcoeurs§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleCoeur().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("coeurs", Cosmetique.COEURS.getPrice(), CosmetiqueManager.CosmetiqueType.PARTICLE, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }



                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }

                        break;
                    case "§eFlames":

                        if(!ParticleManager.isParticleActive(player, "flames")){

                            if(Cosmetique.FLAMES.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bflames§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleCoeur().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bflames !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "flames")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bflames§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleFlames().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("flames", Cosmetique.FLAMES.getPrice(), CosmetiqueManager.CosmetiqueType.PARTICLE, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }

                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }

                        break;
                    case "§dNotes":
                        if(!ParticleManager.isParticleActive(player, "notes")){

                            if(Cosmetique.NOTES.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bnotes§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleCoeur().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bnotes !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "notes")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bnotes§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleNotes().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("notes", Cosmetique.NOTES.getPrice(), CosmetiqueManager.CosmetiqueType.PARTICLE, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }

                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }
                        break;
                    case "§3Pluie":
                        if(!ParticleManager.isParticleActive(player, "pluie")){

                            if(Cosmetique.PLUIE.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bpluie§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticlePluie().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bpluie !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "pluie")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bpluie§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticlePluie().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("pluie", Cosmetique.PLUIE.getPrice(), CosmetiqueManager.CosmetiqueType.PARTICLE, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }

                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }

}
