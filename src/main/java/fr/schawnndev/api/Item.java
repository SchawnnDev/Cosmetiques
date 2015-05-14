/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.Item) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/05/15 17:23.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api;

import fr.schawnndev.api.interfaces.Click;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {

    @Getter @Setter
    private List<String> lore;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Material material;

    @Getter @Setter
    private short damage;

    @Getter @Setter
    private byte data;

    @Getter @Setter
    private int price;

    @Getter @Setter
    private Click click;

    public Item(String name, int price, Material material, Click click){
        this.name = name;
        this.material = material;
        this.damage = -1;
        this.data = -1;
        this.price = price;
        this.click = click;
    }

    public Item(String name, int price, Material material, short damage, Click click){
        this.name = name;
        this.material = material;
        this.damage = damage;
        this.data = -1;
        this.price = price;
        this.click = click;
    }

    public Item(String name, int price, Material material, short damage, byte data, Click click){
        this.name = name;
        this.material = material;
        this.damage = damage;
        this.data = data;
        this.price = price;
        this.click = click;
    }

    public void addLore(String lore){
        this.lore.add(lore);
    }

    public void addLore(List<String> lore){
        for(int i = 0; i < lore.size(); i++){
            addLore(lore.get(i));
        }
    }

    public ItemStack build(int count){

        // vars

        ItemStack itemStack;
        ItemMeta itemMeta;

        // init stack

        if(data == -1){
            itemStack = new ItemStack(material, count, damage);
        } else if (damage == -1){
            itemStack = new ItemStack(material, count);
        } else {
            itemStack = new ItemStack(material, count, damage, data);
        }

        // init meta

        itemMeta = itemStack.getItemMeta();

        // lore

        List<String> lore = this.lore;

        if(price != -1) {
            lore.add("      ");
            lore.add("Prix: " + price);
        }

        // set

        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        // return

        return itemStack;
    }

}
