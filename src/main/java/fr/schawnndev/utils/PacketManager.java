/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.utils.PacketManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 19/05/15 13:13.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.utils;

import fr.schawnndev.particules.ParticleType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PacketManager {

    public static String getCraftbukkitClass() {
        return "org.bukkit.craftbukkit."+getServerVersion()+".";
    }

    public static String getNMSClass() {
        return "net.minecraft.server."+getServerVersion()+".";
    }

    public static String getServerVersion() {
        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        return pkg.substring(pkg.lastIndexOf(".") + 1);
    }

    public static Class<?> getNMSClass(String classe){
        try {
            return Class.forName(getNMSClass() + classe);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Object getPacket(String particle, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount, Material... mat) {
        Class<?> packetPlayOutWorldParticles = getNMSClass("PacketPlayOutWorldParticles");
        int[] data = null;
        if (mat.length > 0) {
            data = new int[]{mat[0].getId(), (byte) 0};
        }
        try {
            Constructor<?> packetPlayOutWorldParticlesConstructor = packetPlayOutWorldParticles.getConstructor(getNMSClass("EnumParticle"), boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
            ParticleType particleEffect = ParticleType.getByName(particle);
            return packetPlayOutWorldParticlesConstructor.newInstance(getNMSClass("EnumParticle").getEnumConstants()[particleEffect.getId()], true, x, y, z, xOffset, yOffset, zOffset, speed, amount, (data == null) ? new int[0] : data);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> wrapperToPrimitive(Class<?> clazz) {
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Integer.class) return int.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Short.class) return short.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Void.class) return void.class;
        if (clazz == Character.class) return char.class;
        return clazz;
    }

    public static Class<?>[] toParamTypes(Object... params) {
        Class<?>[] classes = new Class<?>[params.length];
        for(int i = 0; i < params.length; i++)
            classes[i] = wrapperToPrimitive(params[i].getClass());
        return classes;
    }

    public static Object getHandle(Entity e) {
        return callSuperMethod(e, "getHandle");
    }

    public static Object getHandle(World w) {
        return callSuperMethod(w, "getHandle");
    }

    public static Object playerConnection(Player p) {
        return playerConnection(getHandle(p));
    }

    public static Object playerConnection(Object handle) {
        return getField(handle, "playerConnection");
    }

    public static void sendPacket(Player p, Object packet) {
        Object pc = playerConnection(p);
        try {
            pc.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(pc, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Object callSuperMethod(Object object, String method, Object... params) {
        try {
            Class<?> clazz = object.getClass();
            Method m = clazz.getMethod(method, toParamTypes(params));
            m.setAccessible(true);
            return m.invoke(object, params);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getField(Object object, String field) {
        try {
            Class<?> clazz = object.getClass();
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(object);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
