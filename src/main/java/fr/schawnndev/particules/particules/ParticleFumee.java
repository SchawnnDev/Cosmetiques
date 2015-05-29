/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticleFumee) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:54.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules.particules;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.particules.Particle;
import fr.schawnndev.particules.ParticleEffect;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleFumee extends Particle {

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.FUMEE;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(final UUID uuid) {

        tasks.put(uuid,

                Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

                    double c = 0.0;
                    Player player = Bukkit.getPlayer(uuid);

                    @Override
                    public void run() {

                        if (player != null && player.isOnline()) {
                            Location loc = player.getLocation();

                            if (c == 360) c = 0;

                            for (double a = c; a < 360 + c; a += 60)
                                ParticleEffect.SMOKE_LARGE.display(0f, 0f, 0f, 0f, 1, new Location(loc.getWorld(), (float) (loc.getX() + Math.cos(Math.toRadians(a))), (float) loc.getY(), (float) (loc.getZ() + Math.sin(Math.toRadians(a)))), 128);

                            c += 1.5;

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