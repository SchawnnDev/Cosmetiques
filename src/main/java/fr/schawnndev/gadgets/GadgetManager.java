/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.GadgetManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 31/05/15 00:51.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.gadgets.GadgetEncre;
import fr.schawnndev.gadgets.gadgets.GadgetFireBall;
import fr.schawnndev.gadgets.gadgets.GadgetGlace;
import fr.schawnndev.gadgets.gadgets.GadgetTNT;
import fr.schawnndev.sql.SQLManager;
import fr.schawnndev.utils.Cooldown;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.KeyStore;
import java.util.*;

public class GadgetManager {

    @Getter
    private static GadgetFireBall gadgetFireBall = new GadgetFireBall();

    @Getter
    private static GadgetTNT gadgetTNT = new GadgetTNT();

    @Getter
    private static GadgetEncre gadgetEncre = new GadgetEncre();

    @Getter
    private static GadgetGlace gadgetGlace = new GadgetGlace();

    @Getter
    private static Map<UUID, String> activeGadgets = new HashMap<>();

    @Getter
    private static List<Cooldown> cooldowns = new ArrayList<>();

    public static boolean hasGadget(Player player, String gadget){
        final UUID uuid = player.getUniqueId();

        return activeGadgets.containsKey(uuid) && activeGadgets.get(uuid).equalsIgnoreCase(gadget);
    }

    public static boolean hasCooldown(Player player, CosmetiqueManager.Cosmetique cosmetique){
        for(Cooldown c : cooldowns)
            if(c.getCosmetique() == cosmetique && c.getPlayer().equals(player.getUniqueId()))
                return true;
        return false;
    }

    public static boolean isInCooldown(Player player, CosmetiqueManager.Cosmetique cosmetique){
        if(!hasCooldown(player, cosmetique)) return false;
        Cooldown cooldown = getCooldown(player, cosmetique);

        return cooldown.isStarted();
    }

    public static String getString(Player player, CosmetiqueManager.Cosmetique cosmetique){
        return getCooldown(player, cosmetique).getMessage();
    }

    public static Cooldown getCooldown(Player player, CosmetiqueManager.Cosmetique cosmetique){
        for(Cooldown c : cooldowns)
            if(c.getCosmetique() == cosmetique && c.getPlayer().equals(player.getUniqueId()))
                return c;

        return null;
    }

    public static void addCooldown(Cooldown cooldown){
        cooldowns.add(cooldown);
    }

    public static void removeCooldown(Cooldown cooldown){
        cooldowns.remove(cooldown);
    }

    public static String getGadget(Player player){
        final UUID uuid = player.getUniqueId();

        if(activeGadgets.containsKey(uuid))
            return activeGadgets.get(uuid);

        return "aucun";
    }

    public static boolean hasGadgetActive(Player player){
        final UUID uuid = player.getUniqueId();

        return activeGadgets.containsKey(uuid) && !activeGadgets.get(uuid).equalsIgnoreCase("aucun");
    }

    public static void addGadget(Player player, String gadget, boolean withSQL){
        activeGadgets.put(player.getUniqueId(), gadget);

        if(withSQL)
            SQLManager.setActiveCosmetic(player, gadget, CosmetiqueManager.CosmetiqueType.GADGET);
    }


}
