/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticleFlames) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:55.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules.particules;

import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.particules.ParticleEffect;
import fr.schawnndev.particules.Particle;
import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.particules.ParticleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleFlames extends Particle {

    @Getter
    public Cosmetique cosmetique = Cosmetique.FLAMES;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(final UUID uuid) {

        ParticleManager.getActiveParticles().put(uuid, "flames");

        tasks.put(uuid,

                Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

                    Player player = Bukkit.getPlayer(uuid);
                    double i = 0.0d;

                    @Override
                    public void run() {

                        if (player != null && player.isOnline()) {

                            double s = 0.1570796326794897d;
                            double rad = 1.0D;

                            Location l = player.getLocation();
                            double angle = s * i;
                            double angle2 = s * i;
                            angle2 -= angle2 * 2.0d;

                            double X = l.getX() - rad * Math.cos(angle);
                            double Z = l.getZ() - rad * Math.sin(angle);
                            double Y2 = l.getY() + 1.0d + rad * Math.cos(angle);
                            double X2 = l.getX() + rad * Math.cos(angle2);
                            double Z2 = l.getZ() + rad * Math.sin(angle2);
                            Location p1 = new Location(l.getWorld(), X, Y2, Z);
                            Location p2 = new Location(l.getWorld(), X2, Y2, Z2);

                            ParticleEffect.FLAME.display(0f, 0f, 0f, 0f, 2, p1, 128);
                            ParticleEffect.FLAME.display(0f, 0f, 0f, 0f, 2, p2, 128);

                            i += 1.0D;
                        } else {
                            stopParticle(uuid);
                        }

                    }

                }, 0l, 2l).getTaskId());
    }

    @Override
    public void stopParticle(UUID uuid) {
        if (tasks.containsKey(uuid)) {
            Bukkit.getScheduler().cancelTask(tasks.get(uuid));
            tasks.remove(uuid);
        }
    }
}