/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.LCCosmetiques) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 15:53.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of Paul.
 *  ******************************************************
 */

package fr.schawnndev;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LCCosmetiques extends JavaPlugin {

    @Getter
    private static LCCosmetiques instance;

    public void onEnable(){
        instance = this;
    }

    public void onDisable() {}

}
