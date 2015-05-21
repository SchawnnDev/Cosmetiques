/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.ParticuleManage) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 20/05/15 17:25.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ParticuleManager {

    private static List<aaaaaa> particles = new ArrayList<>();

    public static boolean hasParticle(Player player){
        for(aaaaaa p : particles)
            if(p.getPlayer() == player.getUniqueId())
                return true;
        return false;
    }

    public static void removeParticle(aaaaaa particule){
        if(particles.contains(particule))
            particles.remove(particule);
    }

    public static void addParticle(aaaaaa particule){
        if(!particles.contains(particule))
            particles.remove(particule);
    }

    public static void removeParticle(Player player){
        if(!hasParticle(player)) return;

        aaaaaa particule = getParticule(player);

        if(particles.contains(particule))
            particles.remove(particule);
    }

    public static aaaaaa getParticule(Player player){
        for(aaaaaa p : particles)
            if(p.getPlayer() == player.getUniqueId())
                return p;

        return null;
    }


}
