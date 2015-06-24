/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.pets.CustomFireball) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 24/06/15 09:54.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets.pets;

import net.minecraft.server.v1_8_R1.EntityFireball;
import net.minecraft.server.v1_8_R1.MovingObjectPosition;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftFireball;
import org.bukkit.entity.Fireball;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomFireball extends EntityFireball {

    public CustomFireball(World world) {
        super(world);
    }

    public static Fireball spawn(Location location) {
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();
        CustomFireball customEntity = new CustomFireball(mcWorld);
        customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (CraftFireball) customEntity.getBukkitEntity();
    }

    @Override
    protected void a(MovingObjectPosition movingObjectPosition) {
        return;
    }

}
