/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticlePluie) is part of LCCosmetiques.
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
import fr.schawnndev.math.Randoms;
import fr.schawnndev.particules.ParticleEffect;
import fr.schawnndev.particules.Particle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticlePluie extends Particle {

    @Getter
    public Cosmetique cosmetique = Cosmetique.PLUIE;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(final UUID uuid) {

        tasks.put(uuid,

            Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

                Player player = Bukkit.getPlayer(uuid);

                @Override
                public void run() {
                    if (player != null && player.isOnline()) {
                        Location l = player.getLocation();
                        l.setY(player.getLocation().getY() + 3.7d);

                        ParticleEffect.WATER_DROP.display(0f, 0f, 0f, 0f, 1, l.add(Randoms.randomRangeFloat(-0.4F, 0.4F), 0.0D, Randoms.randomRangeFloat(-0.5F, 0.5F)), 128);
                        ParticleEffect.CLOUD.display(0f, 0f, 0f, 0f, 3, l.add(Randoms.randomRangeFloat(-0.5F, 0.5F), 0.0D, Randoms.randomRangeFloat(-0.5F, 0.5F)), 128);

                        l.subtract(l);

                        if (!player.isInsideVehicle())
                            ParticleEffect.WATER_SPLASH.display(0f, 0f, 0f, 0f, 4, player.getLocation().add(0.0D, 1.0D, 0.0D), 128);

                    } else {
                        stopParticle(uuid);
                    }
                }

            }, 0l, 2l).getTaskId());
    }

    @Override
    public void stopParticle(UUID uuid) {
        if(tasks.containsKey(uuid))
            Bukkit.getScheduler().cancelTask(tasks.get(uuid));
    }
}
