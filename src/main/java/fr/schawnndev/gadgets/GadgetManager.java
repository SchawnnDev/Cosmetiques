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
import fr.schawnndev.gadgets.gadgets.GadgetFireBall;
import fr.schawnndev.gadgets.gadgets.GadgetTNT;
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GadgetManager {

    @Getter
    private static GadgetFireBall gadgetFireBall = new GadgetFireBall();

    @Getter
    private static GadgetTNT gadgetTNT = new GadgetTNT();

    @Getter
    private static Map<UUID, String> activeGadgets = new HashMap<>();

    public static boolean hasGadget(Player player, String gadget){
        final UUID uuid = player.getUniqueId();

        return activeGadgets.containsKey(uuid) && activeGadgets.get(uuid).equalsIgnoreCase(gadget);

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
