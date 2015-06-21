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
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.utils.Fireworks;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class GadgetArtifice extends Gadget implements Listener {

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.ARTIFICE;

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            Player player = Bukkit.getPlayer(uuid);

            final int power = 3; // All the power in you :DD

            Firework f = Fireworks.longFireFirework(player.getLocation(), power);
            f.setPassenger(player);

            Fireworks.longFireFirework(player.getLocation().clone().add(1d, 0d, 1d), power);
            Fireworks.longFireFirework(player.getLocation().clone().add(-1d, 0d, 1d), power);
            Fireworks.longFireFirework(player.getLocation().clone().add(1d, 0d, 0d), power);
            Fireworks.longFireFirework(player.getLocation().clone().add(1d, 0d, -1d), power);


        }
    }

}
