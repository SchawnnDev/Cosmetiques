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

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    @Getter @Setter
    private String name;

    private List<SubMenu> subMenus;

    public Menu(String name){
        this.name = name;
        this.subMenus = new ArrayList<>();
    }

    public void addSubMenu(SubMenu subMenu){
        subMenus.add(subMenu);
    }

}
