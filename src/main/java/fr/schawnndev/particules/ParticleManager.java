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

import fr.schawnndev.particules.particules.*;
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
    @Getter
    private static ParticleSpirales particleSpirales = new ParticleSpirales();
    @Getter
    private static ParticleContent particleContent = new ParticleContent();
    @Getter
    private static ParticleRedstone particleRedstone = new ParticleRedstone();
    @Getter
    private static ParticleMagicien particleMagicien = new ParticleMagicien();

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
        if(particleSpirales.getTasks().containsKey(uuid))
            return true;
        if(particleContent.getTasks().containsKey(uuid))
            return true;
        if(particleRedstone.getTasks().containsKey(uuid))
            return true;
        if(particleMagicien.getTasks().containsKey(uuid))
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
        if(particle.equalsIgnoreCase("spirales") && particleSpirales.getTasks().containsKey(uuid))
            return true;
        if(particle.equalsIgnoreCase("content") && particleContent.getTasks().containsKey(uuid))
            return true;
        if(particle.equalsIgnoreCase("redstone") && particleRedstone.getTasks().containsKey(uuid))
            return true;
        if(particle.equalsIgnoreCase("magicien") && particleMagicien.getTasks().containsKey(uuid))
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
        if(particleSpirales.getTasks().containsKey(uuid))
            particleSpirales.stopParticle(uuid);
        if(particleContent.getTasks().containsKey(uuid))
            particleContent.stopParticle(uuid);
        if(particleRedstone.getTasks().containsKey(uuid))
            particleRedstone.stopParticle(uuid);
        if(particleMagicien.getTasks().containsKey(uuid))
            particleMagicien.stopParticle(uuid);
    }


}
