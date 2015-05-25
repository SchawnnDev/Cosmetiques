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

import fr.schawnndev.particules.particules.ParticleCoeur;
import fr.schawnndev.particules.particules.ParticleFlames;
import fr.schawnndev.particules.particules.ParticleNotes;
import fr.schawnndev.particules.particules.ParticlePluie;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParticleManager {

    @Getter
    private static ParticleCoeur particleCoeur = new ParticleCoeur();
    @Getter
    private static ParticlePluie particlePluie = new ParticlePluie();
    @Getter
    private static ParticleNotes particleNotes = new ParticleNotes();
    @Getter
    private static ParticleFlames particleFlames = new ParticleFlames();

    public static boolean hasParticleActive(Player player){
        UUID uuid = player.getUniqueId();

        if(particleCoeur.getTasks().containsKey(uuid))
            return true;
        if(particleFlames.getTasks().containsKey(uuid))
            return true;
        if(particleNotes.getTasks().containsKey(uuid))
            return true;
        if(particlePluie.getTasks().containsKey(uuid))
            return true;

        return false;
    }

    public static boolean isParticleActive(Player player, String particle){
        UUID uuid = player.getUniqueId();

        if(particle.equalsIgnoreCase("coeur") && particleCoeur.getTasks().containsKey(uuid))
            return true;
        if(particle.equalsIgnoreCase("flames") && particleFlames.getTasks().containsKey(uuid))
            return true;
        if(particle.equalsIgnoreCase("notes") && particleNotes.getTasks().containsKey(uuid))
            return true;
        if(particle.equalsIgnoreCase("pluie") && particlePluie.getTasks().containsKey(uuid))
            return true;

        return false;
    }

    public static void removeActiveParticle(Player player){
        UUID uuid = player.getUniqueId();

        if(particleCoeur.getTasks().containsKey(uuid))
            particleCoeur.stopParticle(uuid);
        if(particleFlames.getTasks().containsKey(uuid))
            particleFlames.stopParticle(uuid);
        if(particleNotes.getTasks().containsKey(uuid))
            particleNotes.stopParticle(uuid);
        if(particlePluie.getTasks().containsKey(uuid))
            particlePluie.stopParticle(uuid);

    }


}
