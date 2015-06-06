/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.api.utils.ItemDisponibility) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 16/05/15 19:11.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.api.utils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ItemDisponibility {

    @Getter
    private static List<InventoryPosition> inventoryPositions;

    public ItemDisponibility(){
        inventoryPositions = new ArrayList<>();

        inventoryPositions.add(new InventoryPosition(3, 3));
        inventoryPositions.add(new InventoryPosition(4, 3));
        inventoryPositions.add(new InventoryPosition(5, 3));
        inventoryPositions.add(new InventoryPosition(6, 3));
        inventoryPositions.add(new InventoryPosition(7, 3));
        inventoryPositions.add(new InventoryPosition(3, 4));
        inventoryPositions.add(new InventoryPosition(4, 4));
        inventoryPositions.add(new InventoryPosition(5, 4));
        inventoryPositions.add(new InventoryPosition(6, 4));
        inventoryPositions.add(new InventoryPosition(7, 4));
        inventoryPositions.add(new InventoryPosition(5, 5));


        // 3 - 3 // 4 - 3 // 5 - 3 // 6 - 3 // 7 - 3 // 3 - 4 // 4 - 4 // 5 - 4 // 6 - 4 // 7 - 4 // 5 - 5

    }

    public class InventoryPosition {

        @Getter @Setter
        private int colonne; // y

        @Getter @Setter
        private int ligne; // x

        public InventoryPosition(int colonne, int ligne){
            this.colonne = colonne;
            this.ligne = ligne;
        }

    }

}
