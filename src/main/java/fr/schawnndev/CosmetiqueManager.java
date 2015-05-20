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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CosmetiqueManager {

    private static Map<UUID, Cosmetique> cosmetiques = new HashMap<>();

    public static void setCosmetique(Player player, Cosmetique cosmetique){
        cosmetiques.put(player.getUniqueId(), cosmetique);
    }

    public static void removeCosmetique(Player player){
        if(cosmetiques.containsKey(player.getUniqueId()))
            cosmetiques.remove(player.getUniqueId());
    }

    public static Cosmetique getCosmetique(Player player){
        return cosmetiques.get(player.getUniqueId());
    }

    public static String getCosmetiqueString(Player player){
        return cosmetiques.get(player.getUniqueId()).toString().toLowerCase();
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

    public enum CosmetiqueType {
        GADGET,
        PET,
        PARTICLE;
    }

    public enum Cosmetique {

        AUCUN(CosmetiqueType.GADGET, false, 0),

        // Gadgets

        DOUBLE_JUMP(CosmetiqueType.GADGET, true, 0),
        MUSIC(CosmetiqueType.GADGET, false, 1350),
        GATEAU_EMPOISONNE(CosmetiqueType.GADGET, false, 1400),
        LAISSE(CosmetiqueType.GADGET, true, 0),
        CANON(CosmetiqueType.GADGET, false, 2500),
        APPLE(CosmetiqueType.GADGET, true, 0),
        ENCRE(CosmetiqueType.GADGET, false, 2800),
        CANNE_A_PECHE(CosmetiqueType.GADGET, false, 1800),
        FIREBALL(CosmetiqueType.GADGET, true, 0),
        TNT(CosmetiqueType.GADGET, false, 1100),
        ARTIFICE(CosmetiqueType.GADGET, true, 0),

        // Particules

        MAGICIEN(CosmetiqueType.PARTICLE, false, 900),
        PLUIE(CosmetiqueType.PARTICLE, false, 1200),
        COEUR(CosmetiqueType.PARTICLE, false, 1500),
        LAVE(CosmetiqueType.PARTICLE, false, 600),
        CONTENT(CosmetiqueType.PARTICLE, false, 900),
        FUMEE(CosmetiqueType.PARTICLE, false, 1800),
        NOTES(CosmetiqueType.PARTICLE, false, 1600),
        FLAMES(CosmetiqueType.PARTICLE, false, 2000),
        SPIRALES(CosmetiqueType.PARTICLE, false, 1400),
        REDSTONE(CosmetiqueType.PARTICLE, false, 1000),
        LEGENDARY(CosmetiqueType.PARTICLE, false, 987654321)

        // Pets (en dev)

        ;

        @Getter
        private CosmetiqueType cosmetiqueType;

        private Cosmetique(CosmetiqueType cosmetiqueType, boolean vip, int price){
            this.cosmetiqueType = cosmetiqueType;
        }

    }


}
