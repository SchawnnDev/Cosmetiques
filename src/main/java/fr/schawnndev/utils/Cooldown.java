/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.utils.Cooldown) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 09/06/15 17:59.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.utils;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.GadgetManager;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Cooldown {

    @Getter
    private UUID player;

    @Getter
    private int cooldown;

    @Getter
    private boolean started;

    @Getter
    private CosmetiqueManager.Cosmetique cosmetique;

    public Cooldown(Player player, CosmetiqueManager.Cosmetique cosmetique, int cooldown, boolean forceStart){
        this.player = player.getUniqueId();
        this.cooldown = cooldown;
        this.started = false;
        this.cosmetique = cosmetique;

        if(forceStart)
            start();

    }

    public void start(){

        this.started = true;

        new BukkitRunnable(){

            @Override
            public void run() {

                if(cooldown <= 0){
                    cancel();
                    stop();
                    return;
                }

                cooldown-=1;
            }

        }.runTaskTimer(LCCosmetiques.getInstance(), 0l, 20l);

    }

    public void stop(){
        GadgetManager.removeCooldown(this);
    }

    public String getMessage(){
        return "§7Vous devez attendre §a" + cooldown + " seconde" +(cooldown == 1 ? "" : "s") + " §7avant de réutiliser ce Gadget.";
    }

    public boolean canUse(){
        return started & cooldown > 0;
    }

}
