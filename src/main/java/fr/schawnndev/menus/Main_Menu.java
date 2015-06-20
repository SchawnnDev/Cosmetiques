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

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.api.Manager;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.math.PositionConverter;
import fr.schawnndev.particules.ParticleManager;
import fr.schawnndev.pets.PetManager;
import fr.schawnndev.sql.SQLManager;
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
        Inventory inv = Bukkit.createInventory(null, 6*9, "Cosmétiques");

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

        //

        byte couleur_particules = ParticleManager.getActiveParticles().containsKey(player.getUniqueId()) ? (byte)8 : (byte)10;
        ItemStack colorant_particules = new ItemStack(Material.INK_SACK, 1, (short)0, couleur_particules);
        ItemMeta colorant_particulesMeta = colorant_particules.getItemMeta();
        colorant_particulesMeta.setDisplayName(couleur_particules == 10 ? "§aChoisir une Particule" : "§cDésactiver votre Particule");
        colorant_particules.setItemMeta(colorant_particulesMeta);

        inv.setItem(positionConverter.convert(5, 4), colorant_particules);

        /**
         *  Item: gadgets
         */

        ItemStack gadgets = new ItemStack(356);
        ItemMeta gadgetsMeta = gadgets.getItemMeta();
        gadgetsMeta.setDisplayName("§cGadgets");
        gadgets.setItemMeta(gadgetsMeta);

        inv.setItem(positionConverter.convert(7, 3), gadgets);

        //

        byte couleur_gadgets = GadgetManager.hasGadgetActive(player) ? (byte)8 : (byte)10;
        ItemStack colorant_gadgets = new ItemStack(Material.INK_SACK, 1, (short)0, couleur_gadgets);
        ItemMeta colorant_gadgetsMeta = colorant_gadgets.getItemMeta();
        colorant_gadgetsMeta.setDisplayName(couleur_gadgets == 10 ? "§aChoisir un Gadget" : "§cDésactiver votre Gadget");
        colorant_gadgets.setItemMeta(colorant_gadgetsMeta);

        inv.setItem(positionConverter.convert(7, 4), colorant_gadgets);

        /**
         *  Item: animaux
         */

        ItemStack animaux = new ItemStack(Material.DIAMOND_BARDING);
        ItemMeta animauxMeta = animaux.getItemMeta();
        animauxMeta.setDisplayName("§3Pets");
        animaux.setItemMeta(animauxMeta);

        inv.setItem(positionConverter.convert(3, 3), animaux);

        //

        byte couleur_pets = PetManager.hasActivePet(player) ? (byte)8 : (byte)10;
        ItemStack colorant_pets = new ItemStack(Material.INK_SACK, 1, (short)0, couleur_pets);
        ItemMeta colorant_petsMeta = colorant_pets.getItemMeta();
        colorant_petsMeta.setDisplayName(couleur_pets == 10 ? "§aChoisir un Pet" : "§cDésactiver votre Pet");
        colorant_pets.setItemMeta(colorant_petsMeta);

        inv.setItem(positionConverter.convert(3, 4), colorant_pets);

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

        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
            if(e.getItem() != null && e.getItem().getType() == Material.JUKEBOX && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName() != null && e.getItem().getItemMeta().getDisplayName().equals("§5Cosmétiques"))
                open(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("Cosmétiques")){
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            e.setCursor(new ItemStack(Material.AIR));

            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                switch (e.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§cGadgets":
                        Gadget_SubMenu.open(player);
                        break;
                    case "§3Pets":
                        Pet_SubMenu.open(player);
                        break;
                    case "§6Particules":
                        Particle_SubMenu.open(player);
                        break;
                    case "§cDésactiver votre Particule":
                        ParticleManager.removeActiveParticle(player);
                        open(player);
                        break;
                    case "§aChoisir une Particule":
                        Particle_SubMenu.open(player);
                        break;
                    case "§cDésactiver votre Gadget":
                        GadgetManager.addGadget(player, "aucun", false);
                        player.getInventory().setItem(4, new ItemStack(Material.AIR));
                        open(player);
                        break;
                    case "§aChoisir un Gadget":
                        Gadget_SubMenu.open(player);
                        break;
                    case "§cDésactiver votre Pet":
                        if(PetManager.hasActivePet(player))
                            PetManager.removePet(player);
                        open(player);
                        break;
                    case "§aChoisir un Pet":
                        Pet_SubMenu.open(player);
                        break;
                    default:
                        break;
                }

            }
        }
    }


}
