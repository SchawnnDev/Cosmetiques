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
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftFirework;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import sun.java2d.cmm.ColorTransform;

import java.util.*;

public class Fireworks {

    private static final Color[] colors = { Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.YELLOW,
                                            Color.TEAL, Color.WHITE, Color.SILVER, Color.RED, Color.PURPLE, Color.NAVY,
                                            Color.GREEN, Color.OLIVE, Color.MAROON, Color.GRAY, Color.LIME, Color.ORANGE };

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

    public static Firework longFireFirework(Location loc, int power) {
        final Firework firework = loc.getWorld().spawn(loc, Firework.class);
        final Random random = new Random();
        FireworkMeta meta = firework.getFireworkMeta();
        List<FireworkEffect> effects = new ArrayList<>();
        effects.add(FireworkEffect.builder().with(Type.BALL_LARGE).withColor(colors[random.nextInt(colors.length)])
                .withColor(colors[random.nextInt(colors.length)]).withFade(colors[random.nextInt(colors.length)]).build());
        meta.clearEffects();
        meta.addEffects(effects);
        meta.setPower(power);
        firework.setFireworkMeta(meta);

        return firework;
    }

}
