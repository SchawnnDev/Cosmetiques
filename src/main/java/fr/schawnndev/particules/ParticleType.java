/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.particules.ParticleType) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 19/05/15 12:56.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.particules;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ParticleType {

    EXPLOSION_NORMAL("explode", 0),
    EXPLOSION_LARGE("largeexplode", 1),
    EXPLOSION_HUGE("hugeexplosion", 2),
    FIREWORKS_SPARK("fireworksSpark", 3),
    WATER_BUBBLE("bubble", 4),
    WATER_SPLASH("splash", 5),
    WATER_WAKE("wake", 6),
    SUSPENDED("suspended", 7),
    SUSPENDED_DEPTH("depthSuspend", 8),
    CRIT("crit", 9),
    CRIT_MAGIC("magicCrit", 10),
    SMOKE_NORMAL("smoke", 11),
    SMOKE_LARGE("largesmoke", 12),
    SPELL("spell", 13),
    SPELL_INSTANT("instantSpell", 14),
    SPELL_MOB("mobSpell", 15),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16),
    SPELL_WITCH("witchMagic", 17),
    DRIP_WATER("dripWater", 18),
    DRIP_LAVA("dripLava", 19),
    VILLAGER_ANGRY("angryVillager", 20),
    VILLAGER_HAPPY("happyVillager", 21),
    TOWN_AURA("townaura", 22),
    NOTE("note", 23),
    PORTAL("portal", 24),
    ENCHANTMENT_TABLE("enchantmenttable", 25),
    FLAME("flame", 26),
    LAVA("lava", 27),
    FOOTSTEP("footstep", 28),
    CLOUD("cloud", 29),
    REDSTONE("reddust", 30),
    SNOWBALL("snowballpoof", 31),
    SNOW_SHOVEL("snowshovel", 32),
    SLIME("slime",33),
    HEART("heart",34),
    BARRIER("barrier",35),
    WATER_DROP("droplet", 39),
    ITEM_TAKE("take",40),
    MOB_APPEARANCE("mobappearance",41);

    @Getter
    private String packetName;

    @Getter
    private int id;

    private static Map<String, ParticleType> particleNames = new HashMap<String, ParticleType>();

    static {
        for (ParticleType t : values()) {
            particleNames.put(t.getPacketName(), t);
        }
    }

    private ParticleType(String packetName, int id){
        this.packetName = packetName;
        this.id = id;
    }

    public static ParticleType getByName(String name){
        return particleNames.get(name);
    }

}
