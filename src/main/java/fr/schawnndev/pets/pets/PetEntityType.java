/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.pets.entities.CustomPetEntityType) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 06/06/15 17:38.
 *  *
 *  * Helped by [http://past.is/6xpTx].
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.pets.pets;

import lombok.Getter;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum PetEntityType {

    POULET("PetChicken", 93, EntityType.CHICKEN, EntityChicken.class, PetPoulet.class),
    VACHE_CHAMPIGNON("PetVacheChampignon", 96, EntityType.MUSHROOM_COW, EntityMushroomCow.class, PetVacheChampignon.class),
    VACHE("PetVache", 92, EntityType.MUSHROOM_COW, EntityMushroomCow.class, PetVache.class),
    ZOMBIE("PetZombie", 54, EntityType.ZOMBIE, EntityZombie.class, PetZombie.class),
    LAPIN("PetLapin", 101, EntityType.RABBIT, EntityRabbit.class, PetLapin.class),
    LOUP("PetLoup", 95, EntityType.WOLF, EntityWolf.class, PetLoup.class),
    MOUTON("PetMouton", 91, EntityType.SHEEP, EntitySheep.class, PetMouton.class),
    CREEPER("PetCreeper", 50, EntityType.CREEPER, EntityCreeper.class, PetCreeper.class),
    SQUELETTE("PetSkeleton", 51, EntityType.SKELETON, EntitySkeleton.class, PetSquelette.class),
    PIGMAN("PetPigman", 57, EntityType.PIG_ZOMBIE, EntityPigZombie.class, PetPigman.class),
    CHEVAL("PetCheval", 100, EntityType.HORSE, EntityHorse.class, PetCheval.class);


    @Getter
    private String name;
    @Getter
    private int iD;
    @Getter
    private EntityType entityType;
    @Getter
    private Class<? extends EntityInsentient> NMSClass;
    @Getter
    private Class<? extends EntityInsentient> customClass;

    private PetEntityType(String name, int id, EntityType entityType, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.iD = id;
        this.entityType = entityType;
        this.NMSClass = nmsClass;
        this.customClass = customClass;
    }

    @SuppressWarnings("unchecked")
    public static void registerEntities() {
        for (PetEntityType entity : values()) {
            try {
                List<Map<?, ?>> dataMaps = new ArrayList<>();
                for (Field f : EntityTypes.class.getDeclaredFields()) {
                    if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                        f.setAccessible(true);
                        dataMaps.add((Map<?, ?>) f.get(null));
                    }
                }
                ((Map<Class<? extends EntityInsentient>, String>) dataMaps.get(1)).put(entity.getCustomClass(), entity.getName());
                ((Map<Class<? extends EntityInsentient>, Integer>) dataMaps.get(3)).put(entity.getCustomClass(), entity.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static void unregisterEntities() {
        for (PetEntityType entity : values()) {
            try {
                ((Map) getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ((Map) getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (PetEntityType entity : values())
            try {
                a(entity.getNMSClass(), entity.getName(), entity.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }

        BiomeBase[] biomes;
        try {
            biomes = (BiomeBase[]) getPrivateStatic(BiomeBase.class, "biomes");
        } catch (Exception e) {
            return;
        }
        for (BiomeBase biomeBase : biomes) {
            if (biomeBase == null)
                break;

            for (String field : new String[]{"at", "au", "av", "aw"})
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<BiomeMeta> mobList = (List<BiomeMeta>) list.get(biomeBase);

                    for (BiomeMeta meta : mobList)
                        for (PetEntityType entity : values())
                            if (entity.getCustomClass().equals(meta.b))
                                meta.b = entity.getNMSClass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @SuppressWarnings("rawtypes")
    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void a(Class paramClass, String paramString, int paramInt) {
        try {
            ((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map) getPrivateStatic(EntityTypes.class, "e")).put(paramInt, paramClass);
            ((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, paramInt);
            ((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString, paramInt);
        } catch (Exception e) {}

    }

}
