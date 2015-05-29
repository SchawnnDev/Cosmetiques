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
import fr.schawnndev.math.PositionConverter;
import fr.schawnndev.particules.ParticleManager;
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
        Inventory inv = Bukkit.createInventory(null, 6*9, "               §6§oGadgets");

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

        List<String> notesLore = new ArrayList<>();
        notesLore.add("§7Exprimez vos talents de danseur grâce à");
        notesLore.add("§7ces petites particules en forme de note.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.JUKEBOX), CosmetiqueManager.Cosmetique.NOTES.getPrice(),
                CosmetiqueManager.Cosmetique.NOTES.isVip(), "§dNotes", notesLore, cosmetics.contains("notes"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> emeraldLore = new ArrayList<>();
        emeraldLore.add("§7Laissez parler votre humeur.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.EMERALD), CosmetiqueManager.Cosmetique.CONTENT.getPrice(),
                CosmetiqueManager.Cosmetique.CONTENT.isVip(), "§aContent", emeraldLore, cosmetics.contains("content"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> fumeeLore = MenuManager.getNewLore("§7Attention ça brûle !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.SULPHUR), CosmetiqueManager.Cosmetique.FUMEE.getPrice(),
                CosmetiqueManager.Cosmetique.FUMEE.isVip(), "§7Fumée", fumeeLore, cosmetics.contains("fumee"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> flamesLore = new ArrayList<>();
        flamesLore.add("§7Les flammes vous encerclent telle une barrière");
        flamesLore.add("§7protectrice.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.MOB_SPAWNER), CosmetiqueManager.Cosmetique.FLAMES.getPrice(),
                CosmetiqueManager.Cosmetique.FLAMES.isVip(), "§eFlames", flamesLore, cosmetics.contains("flames"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> spiralesLore = MenuManager.getNewLore("§7Faites apparaître votre aura de puissance.");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.POTION), CosmetiqueManager.Cosmetique.SPIRALES.getPrice(),
                CosmetiqueManager.Cosmetique.SPIRALES.isVip(), "§bSpirales", spiralesLore, cosmetics.contains("spirales"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> redstoneLore = MenuManager.getNewLore("§7Vous ressemblez à aypierre...");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.REDSTONE), CosmetiqueManager.Cosmetique.REDSTONE.getPrice(),
                CosmetiqueManager.Cosmetique.REDSTONE.isVip(), "§4Redstone", redstoneLore, cosmetics.contains("redstone"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> coeursLore = MenuManager.getNewLore("§7Laissez parler vos sentiments !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.RED_ROSE), CosmetiqueManager.Cosmetique.COEURS.getPrice(),
                CosmetiqueManager.Cosmetique.COEURS.isVip(), "§cCoeurs", coeursLore, cosmetics.contains("coeurs"), CosmetiqueManager.CosmetiqueType.GADGET));

        List<String> legendaryLore = new ArrayList<>();
        legendaryLore.add("§7Seuls les riches auront la chance de pouvoir");
        legendaryLore.add("§7se payer cette particule si secrète !");

        itemStacks.add(MenuManager.buildItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short)0, (byte)1), CosmetiqueManager.Cosmetique.LEGENDARY.getPrice(),
                CosmetiqueManager.Cosmetique.LEGENDARY.isVip(), "§cLege§3ndary", legendaryLore, cosmetics.contains("legendary"), CosmetiqueManager.CosmetiqueType.GADGET));

        return itemStacks;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("               §6§oGadgets")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§7<===":
                        Main_Menu.open(player);
                        break;

                    case "§7Fumée":

                        if(!ParticleManager.isParticleActive(player, "fumee")){

                            if(CosmetiqueManager.Cosmetique.FUMEE.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bfumée§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleFumee().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bfumée !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "fumee")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bfumée§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleFumee().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("fumee", CosmetiqueManager.Cosmetique.FUMEE.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }

                        break;
                    case "§5Magicien":

                        if(!ParticleManager.isParticleActive(player, "magicien")){

                            if(CosmetiqueManager.Cosmetique.MAGICIEN.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bmagicien§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleMagicien().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bmagicien !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "magicien")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bmagicien§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleMagicien().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("magicien", CosmetiqueManager.Cosmetique.MAGICIEN.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }

                        break;
                    case "§4Redstone":

                        if(!ParticleManager.isParticleActive(player, "redstone")){

                            if(CosmetiqueManager.Cosmetique.REDSTONE.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bredstone§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleRedstone().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bredstone !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "redstone")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bredstone§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleRedstone().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("redstone", CosmetiqueManager.Cosmetique.COEURS.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }

                        break;
                    case "§cCoeurs":

                        if(!ParticleManager.isParticleActive(player, "coeurs")){

                            if(CosmetiqueManager.Cosmetique.COEURS.isVip()){

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
                                    Achat achat = new Achat("coeurs", CosmetiqueManager.Cosmetique.COEURS.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
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

                            if(CosmetiqueManager.Cosmetique.FLAMES.isVip()){

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
                                    Achat achat = new Achat("flames", CosmetiqueManager.Cosmetique.FLAMES.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }

                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }

                        break;
                    case "§aContent":

                        if(!ParticleManager.isParticleActive(player, "content")){

                            if(CosmetiqueManager.Cosmetique.CONTENT.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bcontent§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleContent().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bcontent !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "content")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bcontent§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleContent().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("content", CosmetiqueManager.Cosmetique.CONTENT.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
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

                            if(CosmetiqueManager.Cosmetique.NOTES.isVip()){

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
                                    Achat achat = new Achat("notes", CosmetiqueManager.Cosmetique.NOTES.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
                                    achat.generate();
                                    achat.proceedOpening();
                                }
                            }

                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                        }
                        break;
                    case "§bSpirales":

                        if(!ParticleManager.isParticleActive(player, "spirales")){

                            if(CosmetiqueManager.Cosmetique.SPIRALES.isVip()){

                                if(player.hasPermission("lccosmetiques.vip") || player.isOp() || player.hasPermission("lccosmetiques.*")){
                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bspirales§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleSpirales().startParticle(player.getUniqueId());
                                } else {
                                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                                    player.sendMessage("§cTu dois être VIP pour utiliser §bspirales !");
                                }

                            } else {
                                if(SQLManager.hasBuyCosmetic(player, "spirales")){

                                    if(ParticleManager.hasParticleActive(player)){
                                        ParticleManager.removeActiveParticle(player);
                                    }

                                    player.closeInventory();
                                    player.sendMessage("§aTu viens d'activer la particule §bspirales§a !");
                                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, 1f);
                                    ParticleManager.getParticleSpirales().startParticle(player.getUniqueId());
                                } else {
                                    Achat achat = new Achat("spirales", CosmetiqueManager.Cosmetique.SPIRALES.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
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

                            if(CosmetiqueManager.Cosmetique.PLUIE.isVip()){

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
                                    Achat achat = new Achat("pluie", CosmetiqueManager.Cosmetique.PLUIE.getPrice(), CosmetiqueManager.CosmetiqueType.GADGET, player);
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
