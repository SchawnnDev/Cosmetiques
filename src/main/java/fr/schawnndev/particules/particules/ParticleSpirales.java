/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticleSpirales) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:55.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules.particules;

import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.math.FastMath;
import fr.schawnndev.math.Randoms;
import fr.schawnndev.math.RotateVector;
import fr.schawnndev.particules.Particle;
import fr.schawnndev.particules.ParticleEffect;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleSpirales extends Particle {

    @Getter
    public Cosmetique cosmetique = Cosmetique.SPIRALES;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(final UUID uuid) {

        tasks.put(uuid,

                Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

                    Player player = Bukkit.getPlayer(uuid);
                    private int p = 150;
                    private int pPerIteration = 12;
                    private float s = 1.0F;
                    private float xFactor = 1.0F;
                    private float yFactor = 0.6F;
                    private float zFactor = 1.0F;
                    private float yOffset = 0.6F;
                    private double xRotation;
                    private double yRotation;
                    private double zRotation = 0.0D;
                    private int step;

                    @Override
                    public void run() {
                        if (player != null && player.isOnline()) {

                            Location l = player.getLocation();
                            Vector v = new Vector();

                            for (int i = 0; i < pPerIteration; i++) {
                                step += 1;

                                float t = 3.14f / p * step;
                                float r = FastMath.sin(t * 2.718f * pPerIteration / p) * s;
                                float s = r * 3.1415F * t;

                                v.setX(xFactor * r * -Math.cos(s));
                                v.setZ(zFactor * r * -Math.sin(s));
                                v.setY(yFactor + yOffset - 1.0F);

                                RotateVector.rotateVector(v, xRotation, yRotation, zRotation);
                                ParticleEffect.SPELL_WITCH.display(0f, 0f, 0f, 0f, 1, l.add(v), 128);
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