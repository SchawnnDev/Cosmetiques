/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetFireBall) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 01/06/15 21:42.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class GadgetFireBall extends Gadget implements Listener {

    public GadgetFireBall(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.FIREBALL;

    @Getter
    private Map<UUID, Integer> tasks = new HashMap<>();

    public static List<UUID> cooldowns = new ArrayList<>();

    @Override
    public void start(UUID uuid){

        if(Bukkit.getPlayer(uuid) != null){
            Player player = Bukkit.getPlayer(uuid);

            Fireball fireball = player.getWorld().spawn(player.getLocation().add(0d, 4d, 0d), Fireball.class);
            Snowball snowball = player.getWorld().spawn(player.getLocation().add(0d, 2d, 0d), Snowball.class);
            fireball.setMetadata("gadget_fireball", new FixedMetadataValue(LCCosmetiques.getInstance(), "gadget_fireball"));
            fireball.setVelocity(player.getLocation().getDirection().multiply(-1d));
            fireball.setIsIncendiary(false);
            fireball.setPassenger(snowball);
            snowball.setPassenger(player);

        }
    }

    public static void main(String[] args){

        double add = 0;
        double moyenne = 0;
        double total = 0;
        List<Double> notes = new ArrayList<>();

        for(int i = 1; i <=4; i++) {
            double toAdd = i + new Random().nextInt(10);
            total += toAdd;
            notes.add(toAdd);
        }

        while (moyenne != 8.5){
        //    System.out.println("Check: " + moyenne + " with: " + add);

            moyenne = (total + add) / 5;


            if(moyenne != 8.5) {

                add += 0.5;

                if (add >= 20) {
                    System.err.println("Impossible de calculer avec les notes actuelles.");
                    return;
                }
            }
        }

        notes.add(add);

        String message = "Résultat : ";

        for(double note : notes)
            message += "" + note + " ";

        System.out.println(message);

    }


    @EventHandler
    public void onExplode(EntityExplodeEvent e){
        if(e.getEntity() instanceof Fireball){
            if(e.getEntity().hasMetadata("gadget_fireball"))
                if(e.getEntity().getPassenger() != null && e.getEntity().getPassenger() instanceof Snowball)
                    e.getEntity().getPassenger().remove();
        }
    }

}
