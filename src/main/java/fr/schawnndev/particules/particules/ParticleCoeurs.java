/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticleCoeur) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:54.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules.particules;

import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.particules.Particle;
import fr.schawnndev.particules.ParticleEffect;
import fr.schawnndev.particules.ParticleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleCoeurs extends Particle {

    @Getter
    public Cosmetique cosmetique = Cosmetique.COEURS;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(final UUID uuid) {

        ParticleManager.getActiveParticles().put(uuid, "coeurs");

        tasks.put(uuid,

                Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

                    float c = 0;
                    float a = 0;
                    boolean r = false;
                    Player player = Bukkit.getPlayer(uuid);

                    @Override
                    public void run() {
                        c += 0.2;
                        a += 0.4;
                        if (c > 8 && !r)
                            r = true;
                        else if (r && c > 0)
                            c -= 0.4;
                        else
                            r = false;

                        if (player != null && player.isOnline()) {

                            Location loc = player.getLocation();

                            float x = (float) (loc.getX() + Math.cos(a));
                            float y = (float) (loc.getY() + c/4);
                            float z = (float) (loc.getZ() + Math.sin(a));

                            ParticleEffect.HEART.display(0f, 0f, 0f, 0.01f, 1, new Location(loc.getWorld(), x, y, z), 128);

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