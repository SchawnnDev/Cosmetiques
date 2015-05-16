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

import fr.schawnndev.api.Item;
import fr.schawnndev.api.events.ClickEvent;
import fr.schawnndev.api.interfaces.Click;
import fr.schawnndev.api.utils.ItemDisponibility;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class LCCosmetiques extends JavaPlugin {

    @Getter
    private static LCCosmetiques instance;

    public void onEnable() {
        instance = this;
        new ItemDisponibility();


    }

    public void onDisable() {}

}
