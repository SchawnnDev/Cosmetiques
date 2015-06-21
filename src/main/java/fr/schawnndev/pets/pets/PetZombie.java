/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.pets.PetZombie) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 08/06/15 13:01.
 *  *
 *  * Helped by [http://past.is/6xpTx].
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
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.UUID;

public class PetZombie extends EntityZombie implements Pet {

    private Plugin plugin;
    private Cosmetique cosmetique;
    private UUID owner;
    private BukkitTask task; // != null
    private boolean hat;
    private boolean riding;

    public PetZombie(UUID owner, Cosmetique cosmetique) {
        super(((CraftWorld) Bukkit.getPlayer(owner).getWorld()).getHandle());

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

    public Player getPetOwner() {
        return Bukkit.getPlayer(this.owner);
    }

    @Override
    public void setName(String name) {
        setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        setCustomNameVisible(true);
    }

    @Override
    public void teleportToOwner() {
        getBukkitEntity().teleport(getPetOwner());
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
    public void setRide(boolean arg) {

        if(arg){
            if (getPetOwner().getPassenger() != null && getPetOwner().getPassenger().equals(getMCEntity()))
                getMCEntity().leaveVehicle();

            getMCEntity().teleport(getPetOwner());
            getMCEntity().setPassenger(getPetOwner());

            riding = true;

        } else {
            getPetOwner().leaveVehicle();

            riding = false;
        }

    }

    @Override
    public void setHat(boolean arg) {

        if(arg){
            if (getPetOwner().getPassenger() == null) {
                if (getMCEntity().getPassenger() != null && getMCEntity().getPassenger().equals(getPetOwner()))
                    getPetOwner().leaveVehicle();

                getBukkitEntity().teleport(getPetOwner());
                getPetOwner().setPassenger(getBukkitEntity());

                hat = true;
            }
        } else {
            if (PetManager.isAPet(getPetOwner().getPassenger())) {
                getPetOwner().getPassenger().leaveVehicle();

                hat = false;
            }
        }

    }

    @Override
    public void follow() {
        Location ownerLocation = getPetOwner().getLocation();
        PathEntity pathEntity = navigation.a(ownerLocation.getX(), ownerLocation.getY(), ownerLocation.getZ());
        navigation.a(pathEntity, 1.3f);
    }

    @Override
    public void startFollow() {
        stopFollow();

        task = new BukkitRunnable() {

            @Override
            public void run() {

                if(getPetOwner() == null || getMCEntity() == null) {
                    cancel();
                } else {

                    Location entityLocation = getMCEntity().getLocation();

                    if (entityLocation.getWorld().equals(getPetOwner().getWorld()))
                        if (entityLocation.distanceSquared(getPetOwner().getLocation()) < (20 * 3))
                            if (entityLocation.distanceSquared(getPetOwner().getLocation()) > (3 * 3))
                                follow();
                            else
                                navigation.n();
                        else if (getPetOwner().isOnGround())
                            teleportToOwner();

                }

            }

        }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 20l);

    }


    @Override
    public void stopFollow() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public PetZombie spawn(Location location, Player owner) {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return this;
    }

    @Override
    public void remove() {
        stopFollow();

        getBukkitEntity().remove();
    }

    @Override
    public boolean a(Material material) {
        return super.a(material);
    }

    @Override
    protected void dropDeathLoot(boolean flag, int i) {}

    @Override
    protected void dropEquipment(boolean flag, int i) {}

    public void g(float sideMot, float forMot) {
        if (passenger == null || !(passenger instanceof EntityHuman)) {
            super.g(sideMot, forMot);
            S = 0.5f;
            return;
        }

        lastYaw = yaw = passenger.yaw;
        pitch = passenger.pitch * 0.5f;

        setYawPitch(this.yaw, pitch);
        aI = (this.aG = yaw);

        S = 1.0f;

        sideMot = ((EntityLiving) passenger).aX * 0.5f;
        forMot = ((EntityLiving) passenger).aY;

        sideMot *= 0.75f;

        float speed = 0.25f;
        j(speed);
        super.g(sideMot, forMot);

        Field jump = null;
        try {
            jump = EntityLiving.class.getDeclaredField("aW");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        jump.setAccessible(true);

        if (jump != null && onGround) {
            try {
                if (jump.getBoolean(passenger)) {
                    double jumpHeight = 0.5d;
                    motY = jumpHeight;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public EntityCreature getCBukkitEntity() {
        return this;
    }
}
