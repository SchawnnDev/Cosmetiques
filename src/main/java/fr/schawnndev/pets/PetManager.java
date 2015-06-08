/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (PetManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 31/05/15 00:52.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets;

import fr.schawnndev.CosmetiqueManager.*;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.pets.pets.*;
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.*;

import java.util.*;

public class PetManager {

    @Getter
    private static Map<UUID, Pet> activePets = new HashMap<>();

    @Getter
    private static Map<UUID, String> petNames = new HashMap<>();

    public static boolean isAPet(Entity entity){
        return ((CraftEntity) entity).getHandle() instanceof Pet;
    }

    public static boolean _isAPet(Entity entity){
        return activePets.values().contains(entity);
    }

    public static boolean hasActivePet(Player owner){
        return activePets.containsKey(owner.getUniqueId());
    }

    public static boolean hasPetName(Player owner){
        return petNames.containsKey(owner.getUniqueId());
    }

    public static String getPetName(Player owner){
        return petNames.get(owner.getUniqueId());
    }

    /*

    public static String serializeActivePet(Pet pet){
        return pet.getCosmetiqueType() + "|" + pet.getName();
    }

    public static String getActivePetName(Player owner){
        String[] infos = SQLManager.getActiveCosmetic(owner, CosmetiqueType.PET).split("|");
        return infos[1];
    }

    public static Cosmetique getActivePetCosmetique(Player owner){
        String[] infos = SQLManager.getActiveCosmetic(owner, CosmetiqueType.PET).split("|");
        return Cosmetique.valueOf(infos[0]);
    }

    */

    public static void addPlayerPet(Player owner, Cosmetique cosmetique){
        if (hasActivePet(owner))
            if (getPet(owner) != null)
                removePet(owner);


        Pet pet = spawn(cosmetique, owner);
        activePets.put(owner.getUniqueId(), pet);

        initializePet(owner, pet);
    }

    public static void initializePet(Player owner, Pet pet) {

        if(hasPetName(owner))
            pet.setName(getPetName(owner));

        LivingEntity entity = (LivingEntity) pet.getMCEntity();
        entity.setMaxHealth(4d);
        pet.getCBukkitEntity().setHealth(4f);

    }
    public static Pet spawn(Cosmetique cosmetique, Player owner){

        Pet pet = null;
        Location ownerLocation = owner.getLocation();

        switch (cosmetique){

            case VACHE_CHAMPIGNON:
                pet = new PetVacheChampignon(owner.getUniqueId(), cosmetique);
                break;

            case VACHE:
                pet = new PetVache(owner.getUniqueId(), cosmetique);
                break;

            case CHEVAL:
                pet = new PetCheval(owner.getUniqueId(), cosmetique);
                break;

            case CREEPER:
                pet = new PetCreeper(owner.getUniqueId(), cosmetique);
                break;

            case PIGMAN:
                pet = new PetPigman(owner.getUniqueId(), cosmetique);
                break;

            case LAPIN:
                pet = new PetLapin(owner.getUniqueId(), cosmetique);
                break;

            case ZOMBIE:
                pet = new PetZombie(owner.getUniqueId(), cosmetique);
                break;

            case SQUELETTE:
                pet = new PetSquelette(owner.getUniqueId(), cosmetique);
                break;

            case POULET:
                pet = new PetPoulet(owner.getUniqueId(), cosmetique);
                break;

            case MOUTON:
                pet = new PetMouton(owner.getUniqueId(), cosmetique);
                break;

            case LOUP:
                pet = new PetLoup(owner.getUniqueId(), cosmetique);
                break;

        }

        pet.spawn(ownerLocation, owner);

        return pet;
    }

    public static Pet getPet(Player owner){
        return activePets.get(owner.getUniqueId());
    }

    public static Entity getEntityPet(Entity entity){
        for(Pet p : activePets.values())
            if(p.getMCEntity().equals(entity))
                return p.getMCEntity();

        return null;
    }

    public static void removePet(Player owner) {
        if (hasActivePet(owner) && getPet(owner) != null)
                getPet(owner).remove();

        activePets.remove(owner.getUniqueId());
    }

    public static void setHat(Player owner, boolean arg) {
        if (hasActivePet(owner) && getPet(owner) != null)
            getPet(owner).setHat(arg);
    }

    public static void setRide(Player owner, boolean arg) {
        if (hasActivePet(owner) && getPet(owner) != null)
            getPet(owner).setRide(arg);
    }


}