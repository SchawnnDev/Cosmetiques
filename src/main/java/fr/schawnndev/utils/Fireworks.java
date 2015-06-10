/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.utils.Fireworks) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 10/06/15 13:22.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.utils;

import fr.schawnndev.LCCosmetiques;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Fireworks {

    public static void fireFirework(Location loc, Color color) {
        final Firework firework = loc.getWorld().spawn(loc, Firework.class);
        final Random random = new Random();
        FireworkMeta meta = firework.getFireworkMeta();
        List<FireworkEffect> effects = new ArrayList<>();
        effects.add(FireworkEffect.builder().flicker(random.nextBoolean()).with(Type.values()[random.nextInt(Type.values().length)]).trail(random.nextBoolean()).withColor(color).withFade(color).build());
        meta.clearEffects();
        meta.addEffects(effects);

        firework.setFireworkMeta(meta);

            new BukkitRunnable() {
                @Override
                public void run() {
                    firework.detonate();
                }
            }.runTaskLater(LCCosmetiques.getInstance(), 1l);
    }
}
