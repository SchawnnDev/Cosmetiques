/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.entities.PetPouletEntity) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 06/06/15 17:47.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets.pets;

import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.pets.Pet;
import fr.schawnndev.pets.PetManager;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.UUID;

public class PetPoulet extends EntityChicken implements Pet {

    private Plugin plugin;
    private Cosmetique cosmetique;
    private UUID owner;
    private Integer task; // != null
    private boolean hat;
    private boolean riding;

    public PetPoulet(UUID owner, Cosmetique cosmetique) {
        super((World) Bukkit.getPlayer(owner).getWorld());

        this.plugin = LCCosmetiques.getInstance();
        this.owner = owner;
        this.cosmetique = cosmetique;
        this.hat = false;
        this.riding = false;

        startFollow();
    }

    @Override
    public boolean isRiding() {
        return riding;
    }

    @Override
    public boolean isHat(){
        return hat;
    }

    @Override
    public Player getOwner() {
        return Bukkit.getPlayer(this.owner);
    }

    @Override
    public void setName(String name) {
        setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        setCustomNameVisible(true);
    }

    @Override
    public void teleportToOwner() {
        getBukkitEntity().teleport(getOwner());
    }

    @Override
    public void teleportToLoc(Location loc) {
        getBukkitEntity().teleport(loc);
    }

    @Override
    public org.bukkit.entity.Entity getMCEntity() {
        return getBukkitEntity();
    }

    @Override
    public Cosmetique getCosmetiqueType() {
        return cosmetique;
    }

    @Override
    public void setRide() {

        if(getMCEntity().getPassenger() != null)

            getOwner().leaveVehicle();

        else {

            if (getOwner().getPassenger() != null && getOwner().getPassenger().equals(getMCEntity()))
                getMCEntity().leaveVehicle();

            getMCEntity().teleport(getOwner());
            getMCEntity().setPassenger(getOwner());

        }

    }

    @Override
    public void setHat() {
        if (getOwner().getPassenger() == null) {
            if (getMCEntity().getPassenger() != null && getMCEntity().getPassenger().equals(getOwner())) {
                getOwner().leaveVehicle();
            }
            this.getBukkitEntity().teleport(getOwner());
            getOwner().setPassenger(this.getBukkitEntity());
        } else
            if (PetManager.isAPet(getOwner().getPassenger()))
                getOwner().getPassenger().leaveVehicle();


    }

    @Override
    public void follow() {
        Location ownerLocation = getOwner().getLocation();
        PathEntity pathEntity = navigation.a(ownerLocation.getX(), ownerLocation.getY(), ownerLocation.getZ());
        navigation.a(pathEntity, 1.3f);
    }

    @Override
    public void startFollow() {
        stopFollow();

        task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

            @Override
            public void run() {

                Location entityLocation = getMCEntity().getLocation();

                if (entityLocation.getWorld().equals(getOwner().getWorld()))
                    if (entityLocation.distance(getOwner().getLocation()) < 20)
                        if (entityLocation.distance(getOwner().getLocation()) > 3)
                            follow();
                        else
                            navigation.n();
                    else if (getOwner().isOnGround())
                        teleportToOwner();

            }

        }, 0l, 20l).getTaskId();

    }

    @Override
    public void stopFollow() {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task);
            task = null;
        }
    }

    @Override
    public PetPoulet spawn(Location location, Player owner) {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return this;
    }

    @Override
    public void remove() {
        getOwner().getWorld().playSound(getBukkitEntity().getLocation(), Sound.WOLF_WHINE, 1f, 1f);

        stopFollow();

        getBukkitEntity().remove();
    }

    @Override
    public void g(float sideMot, float forMot) {
        if (passenger == null || !(passenger instanceof EntityHuman)) {
            super.g(sideMot, forMot);
            S = 0.5f;    // Make sure the entity can walk over half slabs, instead of jumping
            return;
        }

        lastYaw = yaw = passenger.yaw;
        pitch = passenger.pitch * 0.5f;

        // Set the entity's pitch, yaw, head rotation etc.
        setYawPitch(this.yaw, pitch); //[url]https://github.com/Bukkit/mc-dev/blob/master/net/minecraft/server/Entity.java#L163-L166[/url]
        aI = (this.aG = yaw);

        S = 1.0f;    // The custom entity will now automatically climb up 1 high blocks

        sideMot = ((EntityLiving) passenger).aX * 0.5f;
        forMot = ((EntityLiving) passenger).aY;

		/*
        if (forMot <= 0.0F) {
			forMot *= 0.25F;    // Make backwards slower
		}
		*/
        sideMot *= 0.75F;    // Also make sideways slower

        float speed = 0.25F;    // 0.2 is the default entity speed. I made it slightly faster so that riding is better than walking
        j(speed);    // Apply the speed
        super.g(sideMot, forMot);    // Apply the motion to the entity

        Field jump = null;
        try {
            jump = EntityLiving.class.getDeclaredField("aW");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        jump.setAccessible(true);

        if (jump != null && onGround) {    // Wouldn't want it jumping while on the ground would we?
            try {
                if (jump.getBoolean(this.passenger)) {
                    double jumpHeight = 0.5d;
                    motY = jumpHeight;    // Used all the time in NMS for entity jumping
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public net.minecraft.server.v1_8_R1.EntityCreature getCBukkitEntity() {
        return this;
    }
}
