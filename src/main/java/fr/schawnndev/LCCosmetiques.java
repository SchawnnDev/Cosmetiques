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
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class LCCosmetiques extends JavaPlugin {

    @Getter
    private static LCCosmetiques instance;

    public void onEnable() {
        instance = this;

        Item item = new Item("salut", 1, Material.COOKED_RABBIT, new Click() {
            @Override
            public void onClick(ClickEvent e) {

            }
        });
    }

    public void onDisable() {}

}
