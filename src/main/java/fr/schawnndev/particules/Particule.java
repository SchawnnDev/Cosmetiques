/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.Particule) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 21/05/15 21:57.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules;

import java.util.UUID;

public abstract class Particule {

    public abstract void startParticle(UUID uuid);

    public abstract void stopParticle(UUID uuid);

}
