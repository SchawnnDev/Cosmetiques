/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.particules.ParticleLegendary) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:55.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules.particules;

import fr.schawnndev.particules.Particule;
import fr.schawnndev.CosmetiqueManager.Cosmetique;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleLegendary extends Particule {

    @Getter
    public Cosmetique cosmetique = Cosmetique.LEGENDARY;

    @Getter
    private static Map<UUID, Integer> tasks = new HashMap<>();

    @Override
    public void startParticle(UUID uuid) {

    }

    @Override
    public void stopParticle(UUID uuid) {

    }

}
