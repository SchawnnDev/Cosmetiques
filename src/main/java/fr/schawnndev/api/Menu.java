/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.Menu) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 15:57.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api;

import fr.schawnndev.api.events.ClickEvent;
import fr.schawnndev.api.interfaces.Click;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.math.PositionConverter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    @Getter @Setter
    private String name;

    private List<SubMenu> subMenus;

    private PositionConverter converter;

    @Getter
    private Inventory inventory;

    @Getter
    private GlassColor glassColor;

    public Menu(String name, GlassColor glassColor){
        this.name = name;
        this.glassColor = glassColor;
        this.subMenus = new ArrayList<>();
        this.converter = new PositionConverter();
    }

    public void addSubMenu(SubMenu subMenu){
        subMenus.add(subMenu);
    }

    public void open(Player player){

    }

    public void build(){


        Item glass = new Item("glass", -1, false, Material.STAINED_GLASS_PANE, (short) 0, glassColor.getData(), null, new Click(){
            @Override
            public void onClick(ClickEvent e) {
                e.setCancelled(true);
            }
        });

        glass.setName("§7-");

        ItemStack glassStack = glass.build(1);

        inventory.setItem(converter.convert(1, 3), glassStack);
        inventory.setItem(converter.convert(1, 7), glassStack);
        inventory.setItem(converter.convert(2, 2), glassStack);
        inventory.setItem(converter.convert(2, 3), glassStack);
        inventory.setItem(converter.convert(2, 7), glassStack);
        inventory.setItem(converter.convert(2, 8), glassStack);
        inventory.setItem(converter.convert(3, 2), glassStack);
        inventory.setItem(converter.convert(3, 8), glassStack);
        inventory.setItem(converter.convert(4, 2), glassStack);
        inventory.setItem(converter.convert(4, 8), glassStack);
        inventory.setItem(converter.convert(5, 2), glassStack);
        inventory.setItem(converter.convert(5, 3), glassStack);
        inventory.setItem(converter.convert(5, 7), glassStack);
        inventory.setItem(converter.convert(5, 8), glassStack);
        inventory.setItem(converter.convert(3, 2), glassStack);
        inventory.setItem(converter.convert(3, 8), glassStack);
        inventory.setItem(converter.convert(6, 3), glassStack);
        inventory.setItem(converter.convert(6, 7), glassStack);

    }

}
