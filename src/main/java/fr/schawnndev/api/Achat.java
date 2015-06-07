/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.Achat) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 23/05/15 18:51.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api;

import fr.lyneteam.lcmaster.LCMaster;
import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.CosmetiqueManager.*;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.math.PositionConverter;
import fr.schawnndev.particules.ParticleManager;
import fr.schawnndev.particules.particules.ParticleMagicien;
import fr.schawnndev.pets.PetManager;
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Achat {

    @Getter
    private int price;

    @Getter
    private String id;

    @Getter
    private Cosmetique cosmetique;

    @Getter
    private Player player;

    @Getter
    private boolean generated;

    @Getter
    private boolean allowToBuy;

    @Getter
    private boolean isOpened;

    @Getter
    private boolean finishToPay;

    @Getter
    private String inventoryName;

    private Inventory inventory;

    public Achat(String id, Cosmetique cosmetique, Player player){
        this.id = id;
        this.price = cosmetique.getPrice();
        this.player = player;
        this.generated = false;
        this.isOpened = false;
        this.allowToBuy = true;
        this.inventoryName = "§6Achat: §c" + id.toLowerCase().substring(0, 1).toUpperCase() + id.toLowerCase().substring(1);
        this.cosmetique = cosmetique;
        System.out.println("Nouvel achat de " + player.getName() + " | id: " + id + " | date: " + new Date().toLocaleString());
        Manager.achats.add(this);
    }

    public void generate(){
        inventory = Bukkit.createInventory(null, InventoryType.DISPENSER, inventoryName);

        PositionConverter converter = new PositionConverter();

        ItemStack woolGreen = new ItemStack(Material.WOOL, 1, (short) 0, (byte)5);
        ItemMeta woolGreenMeta = woolGreen.getItemMeta();
        woolGreenMeta.setDisplayName("§aValider");
        woolGreen.setItemMeta(woolGreenMeta);

        inventory.setItem(3, woolGreen);

        ItemStack woolRed = new ItemStack(Material.WOOL, 1, (short)0, (byte) 14);
        ItemMeta woolRedMeta = woolRed.getItemMeta();
        woolRedMeta.setDisplayName("§cAnnuler");
        woolRed.setItemMeta(woolRedMeta);

        inventory.setItem(5, woolRed);

        ItemStack itemStack = cosmetique.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String cosmetiqueTypeString = cosmetique.getCosmetiqueType().toString().toLowerCase().substring(0, 1).toUpperCase() + cosmetique.getCosmetiqueType().toString().toLowerCase().substring(1);
        itemMeta.setDisplayName("§6" + cosmetiqueTypeString + ": §a" + id.toLowerCase().substring(0, 1).toUpperCase() + id.toLowerCase().substring(1));
        itemMeta.setLore(Arrays.asList("§7------------","§bPrix: §6" + price + " §bLCoins"));
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(4, itemStack);

        generated = true;
    }

    public void proceedOpening(){
        float money = LCMaster.api.getCoins(player.getName());

        if(money >= (float) price) {
            allowToBuy = true;
            open();
        } else {
            finish(true, false);
        }
    }

    public void open(){
        if(!isGenerated()) return;
        if(!isAllowToBuy()) return;

        player.openInventory(inventory);
        Manager.playersBuying.add(player.getUniqueId());

        isOpened = true;
    }

    public void buy(){
        if(!isOpened()) return;

        float money = LCMaster.api.getCoins(player.getName());

        if(money >= (float) price && !SQLManager.hasBuyCosmetic(player, id)) {

            SQLManager.addCosmetic(player, id);

            money -= price;

            LCMaster.api.setCoins(player.getName(), money);

            finish(false, false);
            player.sendMessage("§aTu viens d'acheter §b" + id);
            System.out.println("Achat confirme de " + player.getName() + " | id: " + id + " | date: " + new Date().toLocaleString());

            if(cosmetique.getCosmetiqueType() == CosmetiqueType.PARTICLE)
                ParticleManager.activeParticleByName(player, id);
            else if (cosmetique.getCosmetiqueType() == CosmetiqueType.GADGET){
                player.getInventory().setItem(4, ItemStackManager.getItemStack(cosmetique));
                GadgetManager.addGadget(player, id, false);
            } else {
                PetManager.addPlayerPet(player, cosmetique);
            }

        }

        finishToPay = true;
    }

    public void finish(boolean hasNotEnoughtCoins, boolean annuler){
        if(Manager.playersBuying.contains(player.getUniqueId()))
            Manager.playersBuying.remove(player.getUniqueId());

        if(Manager.achats.contains(this))
            Manager.achats.remove(this);

        player.closeInventory();

        if(annuler) {
            player.sendMessage("§cTu as annulé l'achat de " + id);
            System.out.println("Achat annule (annule par joueur) de " + player.getName() + " | id: " + id + " | date: " + new Date().toLocaleString());
        }

        if(hasNotEnoughtCoins) {
            player.sendMessage("§cTu n'as pas assez de LCCoins.");
            System.out.println("Achat annule (pas assez de coins) de " + player.getName() + " | id: " + id + " | date: " + new Date().toLocaleString());
        }
    }

}
