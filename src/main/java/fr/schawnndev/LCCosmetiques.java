/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.LCCosmetiques) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 15:53.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev;

import fr.schawnndev.api.events.AchatEvent;
import fr.schawnndev.data.IndesirableStringDictionnary;
import fr.schawnndev.data.ItemStackManager;
import fr.schawnndev.gadgets.GadgetListener;
import fr.schawnndev.listeners.ServerListener;
import fr.schawnndev.menus.MenuManager;
import fr.schawnndev.pets.PetListener;
import fr.schawnndev.pets.pets.PetEntityType;
import fr.schawnndev.reduction.ReductionManager;
import fr.schawnndev.sql.SQL;
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class LCCosmetiques extends JavaPlugin{

    @Getter
    private static LCCosmetiques instance;
    @Getter
    private static SQL sql;

    public void onEnable() {

        // Instances

        instance = this;

        // Listeners

        new ServerListener();
        new AchatEvent();
        new GadgetListener();
        new PetListener();

        // SQL

        sql = new SQL(getConfig().getString("db.host"), getConfig().getString("db.repo"), getConfig().getString("db.user"), getConfig().getString("db.pass"));
        sql.start();
        SQLManager.init();

        // Items

        new ItemStackManager();

        // Menus

        new MenuManager();

        // Data

        saveDefaultConfig();

        new ItemStackManager();

        // Entities

        PetEntityType.registerEntities();

        // Dictionnary

        IndesirableStringDictionnary.init();

        // Reductions

        ReductionManager.enable();

    }

    public void onDisable() {

        // SQL

        sql.stop();

        // Pets

        PetEntityType.unregisterEntities();

        // Reductions

        ReductionManager.disable();

    }

}
