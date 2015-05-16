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

import fr.schawnndev.api.events.ClickEvent;
import fr.schawnndev.api.interfaces.Click;
import fr.schawnndev.api.utils.GlassColor;
import fr.schawnndev.api.utils.ItemDisponibility;
import fr.schawnndev.api.utils.ItemDisponibility.InventoryPosition;
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

    @Getter
    private ItemStack itemStack;

    private PositionConverter converter;

    private GlassColor glassColor;

    public SubMenu(String name, Menu menu, GlassColor glassColor, ItemStack itemStack){
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.menu = menu;
        this.glassColor = glassColor;
        this.converter = new PositionConverter();
        this.itemStack = itemStack;
    }

    public void open(Player player){
        build(player);



        player.openInventory(inventory);
    }

    public void build(Player player){
        inventory = Bukkit.createInventory(null, converter.convert(6,8), name);

        inventory.setItem(converter.convert(1, 5), Manager.getHead(player).build(1));

        // Glass

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

        // Page précédente

        Item pagePrecedente = new Item("pagePrecedente", -1, false, Material.ARROW, null, new Click(){
            @Override
            public void onClick(ClickEvent e) {
                menu.open(e.getPlayer());
                e.setCancelled(true);
            }
        });

        pagePrecedente.setName("§7<===");
        pagePrecedente.addLore("§6Page précédente");

        inventory.setItem(converter.convert(6, 2), pagePrecedente.build(1));

        // SubMenu

        Item category = new Item("category", -1, false, itemStack.getType(), itemStack.getDurability(), itemStack.getData().getData(), null, new Click(){
            @Override
            public void onClick(ClickEvent e) {
                e.setCancelled(true);
            }
        });

        category.setName(name);

        inventory.setItem(converter.convert(1, 1), category.build(1));
        inventory.setItem(converter.convert(1, 8), category.build(1));

        // Items

        int[] positions = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

        for(int i = 0; i < items.size(); i++){

            Item item = items.get(i);

            int count = 0;

            while (positions[count] != 1)
                count++;

            InventoryPosition inv = (InventoryPosition) ItemDisponibility.getInventoryPositions().toArray()[count];

            inventory.setItem(converter.convert(inv.getColonne(), inv.getLigne()), item.build(1));

        }
    }

}
