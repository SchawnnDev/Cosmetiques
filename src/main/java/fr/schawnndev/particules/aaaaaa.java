/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.Particule) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 19/05/15 12:55.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules;

import fr.schawnndev.LCCosmetiques;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class aaaaaa {

    @Getter
    public UUID player;

    private ParticleEffect particleEffect;
    private int shed_id;
    private float compteur;
    private double pi;
    public boolean isStarted;

    public aaaaaa(UUID player, ParticleEffect particleEffect){
        this.player = player;
        this.shed_id = 0;
        this.particleEffect = particleEffect;
        this.compteur = 0f;
        this.pi = 3.14159265358979323846;
        this.isStarted = false;
    }

    public void start(){

        // Ne pas démarrer plusieurs fois pour un même joueur

        if(isStarted)
            return;

        isStarted = true;

        // Task, particle

        shed_id = Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

            @Override
            public void run() {
                compteur += 0.1f;

                // Location actuelle du joueur

                if(Bukkit.getPlayer(player) == null){
                    stop();
                    return;
                }

                Player p = Bukkit.getPlayer(player);

                Location playerLoc = p.getLocation();

                // 1er cercle

                particleEffect.display(0f, 0f, 0f, 0.01f, 1, new Location(playerLoc.getWorld(), (playerLoc.getX() - Math.sin(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur)), (playerLoc.getY() + 1 - Math.sin(compteur)), (playerLoc.getZ() + Math.cos(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur))), 128);
                particleEffect.display(0f, 0f, 0f, 0.01f, 1, new Location(playerLoc.getWorld(), (playerLoc.getX() + Math.sin(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur)), (playerLoc.getY() + 1 + Math.sin(compteur)), (playerLoc.getZ() - Math.cos(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur))), 128);

                // 2eme cercle

                particleEffect.display(0f, 0f, 0f, 0.01f, 1, new Location(playerLoc.getWorld(), (playerLoc.getX() - Math.sin(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur)), (playerLoc.getY() + 1 + Math.sin(compteur)), (playerLoc.getZ() + Math.cos(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur))), 128);
                particleEffect.display(0f, 0f, 0f, 0.01f, 1, new Location(playerLoc.getWorld(), (playerLoc.getX() + Math.sin(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur)), (playerLoc.getY() + 1 - Math.sin(compteur)), (playerLoc.getZ() - Math.cos(3.14159265358979323846 * playerLoc.getYaw() / 180 + compteur))), 128);

            }

        }, 0l, 3l).getTaskId();
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(shed_id);
    }

}
