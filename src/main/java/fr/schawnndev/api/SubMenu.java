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

import lombok.Getter;
import lombok.Setter;

public class SubMenu {

    @Getter
    @Setter
    private String name;

    @Getter @Setter
    private Menu menu;

    public SubMenu(String name, Menu menu){
        this.name = name;
        this.menu = menu;
    }

}
