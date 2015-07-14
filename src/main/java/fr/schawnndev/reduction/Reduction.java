/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.reduction.Reduction) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/07/15 15:34.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.reduction;

import fr.schawnndev.CosmetiqueManager;
import lombok.Getter;

public class Reduction {

    @Getter
    private int reduction;

    @Getter
    private CosmetiqueManager.Cosmetique cosmetique;

    public Reduction(CosmetiqueManager.Cosmetique cosmetique, int reduction){
        this.reduction = reduction;
        this.cosmetique = cosmetique;
    }

    public int getPriceAfterReduction(){
        return cosmetique.getPrice() - (cosmetique.getPrice() * reduction / 100);
    }

}
