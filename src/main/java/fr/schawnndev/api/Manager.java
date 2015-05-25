/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.Manager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 15/05/15 17:21.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api;

import fr.schawnndev.api.events.ClickEvent;
import fr.schawnndev.api.interfaces.Click;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Manager {

    public static List<Achat> achats = new ArrayList<>();

    public static List<UUID> playersBuying = new ArrayList<>();

    public static Achat getAchat(UUID uuid){
        for(Achat a : achats)
            if(a.getPlayer().getUniqueId() == uuid)
                return a;
        return null;
    }

    public static boolean hasAchat(UUID uuid){
        for(Achat a : achats)
            if(a.getPlayer().getUniqueId() == uuid)
                return true;
        return false;
    }

}
