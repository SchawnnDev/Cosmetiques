/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.SubMenu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 15:57.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api;

import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.math.PositionConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SubMenu {

    @Getter
    @Setter
    private String name;

    @Getter @Setter
    private Menu menu;

    @Getter
    private List<Item> items;

    @Getter
    private Inventory inventory;

    private PositionConverter converter;

    private GlassColor glassColor;

    public SubMenu(String name, Menu menu, GlassColor glassColor){
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.menu = menu;
        this.glassColor = glassColor;
        this.converter = new PositionConverter();
    }

    public void open(Player player){
        player.openInventory(inventory);
    }

    public void build(){
        inventory = Bukkit.createInventory(null, converter.convert(6,8), name);

        ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0, glassColor.getData());



    }

}
