/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.Pet) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 05/06/15 10:59.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets;

import fr.schawnndev.CosmetiqueManager.Cosmetique;
import fr.schawnndev.pets.pets.PetPoulet;
import net.minecraft.server.v1_8_R1.EntityCreature;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *  Pet interface (Custom Pets)
 */

public interface Pet {

    /**
     *  Get the Owner of the Pet
     * @return The Owner of the Pet
     */

    public Player getOwner();

    /**
     *  Get the Pet name
     * @return The name of the Pet
     */

    public String getName();

    /**
     *  Set the Pet name
     * @param name The name of the Pet
     */

    public void setName(String name);

    /**
     *  Teleport the Pet to the Owner
     */

    public void teleportToOwner();

    /**
     * Teleport the Pet to a custom location
     * @param location The location where the Pet must be teleported
     */

    public void teleportToLoc(Location location);

    /**
     *  If the Pet is on the head from the Owner
     */

    public boolean isHat();

    /**
     *  Set the Pet on the head from the Owner
     */

    public void setHat();

    /**
     *  If the Owner is riding on the Pet
     */

    public boolean isRiding();

    /**
     *  Set the Owner ride on the Pet
     */

    public void setRide();

    /**
     *  Pet follows the Owner
     */

    public void follow();

    /**
     *  Pet follow the Owner "forever"
     */

    public void startFollow();

    /**
     *  Pet doesn't follow the Owner anymore
     */

    public void stopFollow();

    /**
     *  Remove the Pet from the Server
     */

    public void remove();

    /**
     *  Spawn the Pet
     * @param location The Location where the Pet does spawn.
     * @param owner The Owner of the Pet
     * @return The Custom Pet of the Owner
     */

    public PetPoulet spawn(Location location, Player owner);

    /**
     *  Get the type of the Pet
     *  @return The Cosmetique of the Pet
     */

    public Cosmetique getCosmetiqueType();

    /**
     *  Get the Bukkit Entity of Pet
     * @return The Bukkit Entity of Pet
     */

    public EntityCreature getCBukkitEntity();

    /**
     *  Get the Minecraft Entity of Pet
     * @return The Minecraft Entity of Pet
     */

    public Entity getMCEntity();

}
