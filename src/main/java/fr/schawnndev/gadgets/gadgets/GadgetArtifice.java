/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetArtifice) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 16/06/15 17:45.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Random;
import java.util.UUID;

public class GadgetArtifice extends Gadget implements Listener {

    public GadgetArtifice(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.ARTIFICE;

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Random random = new Random();


        }
    }

}
