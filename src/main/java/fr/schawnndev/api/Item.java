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
import fr.schawnndev.sql.SQLManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {

    @Getter
    private String id;

    @Getter @Setter
    private boolean vip;

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

    @Getter @Setter
    private ItemMeta itemMeta;

    public Item(String id, int price, boolean vip, Material material, ItemMeta itemMeta, Click click){
        this.name = "";
        this.material = material;
        this.damage = -1;
        this.data = -1;
        this.price = price;
        this.click = click;
        this.vip = vip;
        this.id = id;
        this.itemMeta = itemMeta == null ? getRawItemStack().getItemMeta() : itemMeta;
    }

    public Item(String id, int price, boolean vip, Material material, short damage, ItemMeta itemMeta, Click click){
        this.name = "";
        this.material = material;
        this.damage = damage;
        this.data = -1;
        this.price = price;
        this.click = click;
        this.vip = vip;
        this.id = id;
        this.itemMeta = itemMeta == null ? getRawItemStack().getItemMeta() : itemMeta;
    }

    public Item(String id, int price, boolean vip, Material material, short damage, byte data, ItemMeta itemMeta, Click click){
        this.name = "";
        this.material = material;
        this.damage = damage;
        this.data = data;
        this.price = price;
        this.click = click;
        this.vip = vip;
        this.id = id;
        this.itemMeta = itemMeta == null ? getRawItemStack().getItemMeta() : itemMeta;
    }

    public void addLore(String lore){
        this.lore.add(lore);
    }

    public void addLore(List<String> lore){
        for(int i = 0; i < lore.size(); i++){
            addLore(lore.get(i));
        }
    }

    public ItemStack getRawItemStack(){
        if(data == -1)
            return new ItemStack(material, 1, damage);
         else if (damage == -1)
            return new ItemStack(material, 1);
         else
            return new ItemStack(material, 1, damage, data);
    }

    public ItemStack build(int count){

        // vars

        ItemStack itemStack;

        // init stack

        if(data == -1){
            itemStack = new ItemStack(material, count, damage);
        } else if (damage == -1){
            itemStack = new ItemStack(material, count);
        } else {
            itemStack = new ItemStack(material, count, damage, data);
        }

        // lore

        List<String> lore = this.lore;

        if(vip){
            lore.add("      ");
            lore.add("§bPrix : §6Réservé aux §eVIP");
        } else {
            if(price != -1) {
                lore.add("      ");
                lore.add("§bPrix : §6" + price + " §bLCCoins");
            }
        }

        // set

        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        // return

        return itemStack;
    }

    public ItemStack build(Player player){

        // vars

        ItemStack itemStack;

        // init stack

        if(data == -1){
            itemStack = new ItemStack(material, 1, damage);
        } else if (damage == -1){
            itemStack = new ItemStack(material, 1);
        } else {
            itemStack = new ItemStack(material, 1, damage, data);
        }

        // lore

        List<String> lore = this.lore;


        if(!SQLManager.hasBuyCosmetic(player, id)) {
            if (vip) {
                lore.add("      ");
                lore.add("§bPrix : §6Réservé aux §eVIP");
            } else {
                if (price != -1) {
                    lore.add("      ");
                    lore.add("§bPrix : §6" + price + " §bLCCoins");
                }
            }
        } else {
            lore.add("     ");
            lore.add("§aVous possedez ce gadget !");
        }

        // set

        itemMeta.setLore(lore);
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        // return

        return itemStack;
    }

}
