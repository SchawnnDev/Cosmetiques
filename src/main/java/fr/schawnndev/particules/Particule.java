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
import fr.schawnndev.utils.PacketManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Particule {

    public Player player;
    private ParticleType particleType;
    private int shed_id;
    private float compteur;
    private double pi;
    public boolean isStarted;

    public Particule(Player player, ParticleType particleType){
        this.player = player;
        this.shed_id = 0;
        this.particleType = particleType;
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

                Location playerLoc = player.getLocation();

                final double X = playerLoc.getX();
                final double Y = playerLoc.getY();
                final double Z = playerLoc.getZ();
                final float YAW = playerLoc.getYaw();

                // Pour montrer les particules à tout le monde

                for (Player observer : Bukkit.getOnlinePlayers()) {

                    // 1er cercle

                    PacketManager.sendPacket(observer, PacketManager.getPacket(particleType.getPacketName(), (float) (X - Math.sin(pi * (YAW) / 180 + compteur)), (float) (Y + 1 - Math.sin(compteur)), (float) (Z + Math.cos(pi * (YAW) / 180 + compteur)), 0F, 0F, 0F, 0F, 1));
                    PacketManager.sendPacket(observer, PacketManager.getPacket(particleType.getPacketName(), (float) (X + Math.sin(pi * (YAW) / 180 + compteur)), (float) (Y + 1 + Math.sin(compteur)), (float) (Z - Math.cos(pi * (YAW) / 180 + compteur)), 0F, 0F, 0F, 0F, 1));

                    // 2eme cercle

                    PacketManager.sendPacket(observer, PacketManager.getPacket(particleType.getPacketName(), (float) (X - Math.sin(pi * (YAW) / 180 + compteur)), (float) (Y + 1 + Math.sin(compteur)), (float) (Z + Math.cos(pi * (YAW) / 180 + compteur)), 0F, 0F, 0F, 0F, 1));
                    PacketManager.sendPacket(observer, PacketManager.getPacket(particleType.getPacketName(), (float) (X + Math.sin(pi * (YAW) / 180 + compteur)), (float) (Y + 1 - Math.sin(compteur)), (float) (Z - Math.cos(pi * (YAW) / 180 + compteur)), 0F, 0F, 0F, 0F, 1));
                }

            }

        }, 0l, 3l).getTaskId();
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(shed_id);
    }

}
