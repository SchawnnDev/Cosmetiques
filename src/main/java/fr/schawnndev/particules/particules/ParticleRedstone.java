/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticleRedstone) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:55.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules.particules;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.math.FastMath;
import fr.schawnndev.math.RotateVector;
import fr.schawnndev.particules.Particle;
import fr.schawnndev.particules.ParticleEffect;
import fr.schawnndev.particules.ParticleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleRedstone extends Particle {

    @Getter
    public Cosmetique cosmetique = Cosmetique.REDSTONE;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(final UUID uuid) {

        ParticleManager.getActiveParticles().put(uuid, "redstone");

        tasks.put(uuid,

                Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

                    Player player = Bukkit.getPlayer(uuid);
                    private double xRotation;
                    private double yRotation;
                    private double zRotation = 0.0D;
                    private int step;

                    @Override
                    public void run() {
                        if (player != null && player.isOnline()) {

                            Location l = player.getLocation();
                            Vector v = new Vector();

                            for (int i = 0; i < 12; i++) {
                                step += 1;

                                float t = PI / 150 * step;
                                float r = FastMath.sin(t * 2.718f * 12 / 150);
                                float s = r * PI * t;

                                v.setX(1.0f * r * -FastMath.cos(s));
                                v.setZ(1.0f * r * -FastMath.sin(s));
                                v.setY(0.2f);

                                RotateVector.rotateVector(v, xRotation, yRotation, zRotation);
                                ParticleEffect.REDSTONE.display(0f, 0f, 0f, 0f, 1, l.add(v), 128);
                                l.subtract(v);
                            }

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