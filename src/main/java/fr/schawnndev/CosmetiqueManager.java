/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.CosmetiqueManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 18:41.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev;

import fr.schawnndev.data.ItemStackManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CosmetiqueManager {

    public static void setCurrentCosmetique(Player player, Cosmetique cosmetique, boolean withMessage){
        player.getInventory().setItem(4, ItemStackManager.getItemStack(cosmetique));

        if(withMessage)
            player.sendMessage("§aTu viens d'activer §b"+ cosmetique.toString());
    }

    public static Cosmetique getCosmetiqueFromString(String cosmetique){
        for(Cosmetique c : Cosmetique.values())
            if(c.toString().equalsIgnoreCase(cosmetique))
                return c;
        return Cosmetique.AUCUN;
    }

    public static ItemStack getPlayerItem(Cosmetique cosmetique){
        return ItemStackManager.getPlayerItems().get(cosmetique);
    }

    public static boolean isParticle(String cosmetique){
        cosmetique.toUpperCase();

        for(Cosmetique c : Cosmetique.values())
            if(c.getCosmetiqueType() == CosmetiqueType.PARTICLE && c.toString().toUpperCase().equals(cosmetique))
                return true;

        return  false;
    }

    public enum CosmetiqueType {
        GADGET("current_active_gadget"),
        PET("current_active_pet"),
        PARTICLE("current_active_particle");

        @Getter
        private String MySQLTable;

        private CosmetiqueType(String MySQLTable){
            this.MySQLTable = MySQLTable;
        }

    }

    public enum Cosmetique {

        AUCUN(CosmetiqueType.GADGET, false, 0, new ItemStack(Material.AIR)),

        // Gadgets

        DOUBLE_JUMP(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.FEATHER)),
        MUSIC(CosmetiqueType.GADGET, false, 1350, new ItemStack(2258)),
        GATEAU_EMPOISONNE(CosmetiqueType.GADGET, false, 1400, new ItemStack(Material.CAKE)),
        LAISSE(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.LEASH)),
        CANON(CosmetiqueType.GADGET, false, 2500, new ItemStack(Material.SULPHUR)),
        APPLE(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.GOLDEN_APPLE)),
        ENCRE(CosmetiqueType.GADGET, false, 2800, new ItemStack(Material.INK_SACK)),
        CANNE_A_PECHE(CosmetiqueType.GADGET, false, 1800, new ItemStack(Material.FISHING_ROD)),
        FIREBALL(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.FIREBALL)),
        TNT(CosmetiqueType.GADGET, false, 1100, new ItemStack(Material.TNT)),
        ARTIFICE(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.FIREWORK)),

        // Particules

        MAGICIEN(CosmetiqueType.PARTICLE, false, 900, new ItemStack(Material.ENCHANTMENT_TABLE)),
        PLUIE(CosmetiqueType.PARTICLE, false, 1200, new ItemStack(Material.WATER_BUCKET)),
        COEURS(CosmetiqueType.PARTICLE, false, 1500, new ItemStack(Material.RED_ROSE)),
        LAVE(CosmetiqueType.PARTICLE, false, 600, new ItemStack(Material.LAVA_BUCKET)),
        CONTENT(CosmetiqueType.PARTICLE, false, 900, new ItemStack(Material.EMERALD)),
        FUMEE(CosmetiqueType.PARTICLE, false, 1800, new ItemStack(Material.SULPHUR)),
        NOTES(CosmetiqueType.PARTICLE, false, 1600, new ItemStack(Material.NOTE_BLOCK)),
        FLAMES(CosmetiqueType.PARTICLE, false, 2000, new ItemStack(Material.MOB_SPAWNER)),
        SPIRALES(CosmetiqueType.PARTICLE, false, 1400, new ItemStack(Material.POTION)),
        REDSTONE(CosmetiqueType.PARTICLE, false, 1000, new ItemStack(Material.REDSTONE)),
        LEGENDARY(CosmetiqueType.PARTICLE, false, 987654321, new ItemStack(Material.GOLDEN_APPLE, 1, (short)0, (byte)1)),

        // Pets

        LOUP(CosmetiqueType.PET, true, 0, new ItemStack(Material.BONE)),
        LAPIN(CosmetiqueType.PET, false, 1400, new ItemStack(Material.RABBIT_FOOT)),
        MOUTON(CosmetiqueType.PET, false, 800, new ItemStack(Material.WOOL)),
        POULET(CosmetiqueType.PET, true, 0, new ItemStack(Material.FEATHER)),
        ZOMBIE(CosmetiqueType.PET, true, 0, new ItemStack(Material.ROTTEN_FLESH)),
        CREEPER(CosmetiqueType.PET, false, 1200, new ItemStack(Material.SULPHUR)),
        SQUELETTE(CosmetiqueType.PET, false, 1500, new ItemStack(Material.BOW)),
        VACHE(CosmetiqueType.PET, false, 600, new ItemStack(Material.LEATHER)),
        PIGMAN(CosmetiqueType.PET, false, 1400, new ItemStack(Material.GOLD_NUGGET)),
        CHEVAL(CosmetiqueType.PET, false, 2600, new ItemStack(Material.IRON_BARDING)),
        VACHE_CHAMPIGNON(CosmetiqueType.PET, true, 0, new ItemStack(Material.RED_MUSHROOM));

        @Getter
        private CosmetiqueType cosmetiqueType;

        @Getter
        private boolean vip;

        @Getter
        private int price;

        @Getter
        private ItemStack itemStack;

        private Cosmetique(CosmetiqueType cosmetiqueType, boolean vip, int price, ItemStack itemStack){
            this.cosmetiqueType = cosmetiqueType;
            this.vip=vip;
            this.price=price;
            this.itemStack=itemStack;
        }

    }


}
