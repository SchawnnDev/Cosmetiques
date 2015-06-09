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

        AUCUN(CosmetiqueType.GADGET, false, 0, new ItemStack(Material.AIR), "aucun"),

        // Gadgets

        DOUBLE_JUMP(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.FEATHER), "doublejump"),
        MUSIC(CosmetiqueType.GADGET, false, 1350, new ItemStack(2258), "music"),
        GATEAU_EMPOISONNE(CosmetiqueType.GADGET, false, 1400, new ItemStack(Material.CAKE), "gateauempoisonne"),
        GLACE(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.ICE), "glace"),
        CANON(CosmetiqueType.GADGET, false, 2500, new ItemStack(Material.SULPHUR), "canon"),
        APPLE(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.GOLDEN_APPLE), "apple"),
        ENCRE(CosmetiqueType.GADGET, false, 2800, new ItemStack(Material.INK_SACK), "encre"),
        CANNE_A_PECHE(CosmetiqueType.GADGET, false, 1800, new ItemStack(Material.FISHING_ROD), "canneapeche"),
        FIREBALL(CosmetiqueType.GADGET, true, 0, new ItemStack(Material.FIREBALL), "fireball"),
        TNT(CosmetiqueType.GADGET, false, 1100, new ItemStack(Material.TNT), "tnt"),
        ARTIFICE(CosmetiqueType.GADGET, false, 900, new ItemStack(Material.FIREWORK), "artifice"),

        // Particules

        MAGICIEN(CosmetiqueType.PARTICLE, false, 900, new ItemStack(Material.ENCHANTMENT_TABLE), "magicien"),
        PLUIE(CosmetiqueType.PARTICLE, false, 1200, new ItemStack(Material.WATER_BUCKET), "pluie"),
        COEURS(CosmetiqueType.PARTICLE, true, 0, new ItemStack(Material.RED_ROSE), "coeurs"),
        LAVE(CosmetiqueType.PARTICLE, false, 600, new ItemStack(Material.LAVA_BUCKET), "lave"),
        CONTENT(CosmetiqueType.PARTICLE, false, 900, new ItemStack(Material.EMERALD), "content"),
        FUMEE(CosmetiqueType.PARTICLE, false, 1800, new ItemStack(Material.SULPHUR), "fumee"),
        NOTES(CosmetiqueType.PARTICLE, false, 1600, new ItemStack(Material.NOTE_BLOCK), "notes"),
        FLAMES(CosmetiqueType.PARTICLE, false, 2000, new ItemStack(Material.MOB_SPAWNER), "flames"),
        SPIRALES(CosmetiqueType.PARTICLE, true, 0, new ItemStack(Material.POTION), "spirales"),
        REDSTONE(CosmetiqueType.PARTICLE, false, 1000, new ItemStack(Material.REDSTONE), "redstone"),
        LEGENDARY(CosmetiqueType.PARTICLE, false, 987654321, new ItemStack(Material.GOLDEN_APPLE, 1, (short)0, (byte)1), "legendary"),

        // Pets

        LOUP(CosmetiqueType.PET, true, 0, new ItemStack(Material.BONE), "loup"),
        LAPIN(CosmetiqueType.PET, false, 1400, new ItemStack(Material.RABBIT_FOOT), "lapin"),
        MOUTON(CosmetiqueType.PET, false, 800, new ItemStack(Material.WOOL), "mouton"),
        POULET(CosmetiqueType.PET, true, 0, new ItemStack(Material.FEATHER), "poulet"),
        ZOMBIE(CosmetiqueType.PET, true, 0, new ItemStack(Material.ROTTEN_FLESH), "zombie"),
        CREEPER(CosmetiqueType.PET, false, 1200, new ItemStack(Material.SULPHUR), "creeper"),
        SQUELETTE(CosmetiqueType.PET, false, 1500, new ItemStack(Material.BOW), "squelette"),
        VACHE(CosmetiqueType.PET, false, 600, new ItemStack(Material.LEATHER), "vache"),
        PIGMAN(CosmetiqueType.PET, false, 1400, new ItemStack(Material.GOLD_NUGGET), "pigman"),
        CHEVAL(CosmetiqueType.PET, false, 2600, new ItemStack(Material.IRON_BARDING), "cheval"),
        VACHE_CHAMPIGNON(CosmetiqueType.PET, true, 0, new ItemStack(Material.RED_MUSHROOM), "vachechampignon");

        @Getter
        private CosmetiqueType cosmetiqueType;

        @Getter
        private boolean vip;

        @Getter
        private int price;

        @Getter
        private ItemStack itemStack;

        @Getter
        private String mysqlName;

        private static final Map<Cosmetique, String> MYSQL_NAMES = new HashMap<>();
        private static final Map<Cosmetique, String> ENUM_NAMES = new HashMap<>();

        static {
            for(Cosmetique c : values()) {
                MYSQL_NAMES.put(c, c.getMysqlName());
                ENUM_NAMES.put(c, c.toString().toLowerCase());
            }
        }

        private Cosmetique(CosmetiqueType cosmetiqueType, boolean vip, int price, ItemStack itemStack, String mysqlName){
            this.cosmetiqueType = cosmetiqueType;
            this.vip=vip;
            this.price=price;
            this.itemStack=itemStack;
            this.mysqlName = mysqlName;
        }

        public static final Cosmetique getByMySQLName(String name){
            for(Map.Entry<Cosmetique, String> s : MYSQL_NAMES.entrySet())
                if(s.getValue().equalsIgnoreCase(name))
                    return  s.getKey();
            return AUCUN;
        }

        public static final Cosmetique getByName(String name){
            for(Map.Entry<Cosmetique, String> s : ENUM_NAMES.entrySet())
                if(s.getValue().equalsIgnoreCase(name))
                    return s.getKey();
            return AUCUN;
        }

    }


}
