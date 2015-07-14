/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.reduction.ReductionManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 14/07/15 15:47.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.reduction;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ReductionManager {

    @Getter
    private static List<Reduction> reductions = new ArrayList<>();

    public static boolean hasReduction(CosmetiqueManager.Cosmetique cosmetique) {
        if (cosmetique.isVip())
            return false;

        for (Reduction reduction : reductions)
            if (reduction.getCosmetique() == cosmetique)
                return true;

        return false;
    }

    public static Reduction getReduction(CosmetiqueManager.Cosmetique cosmetique) {
        if (!hasReduction(cosmetique))
            return null;

        for (Reduction reduction : reductions)
            if (reduction.getCosmetique() == cosmetique)
                return reduction;

        return null;
    }

    public static void addReduction(Reduction reduction) {
        if (hasReduction(reduction.getCosmetique()))
            return;

        reductions.add(reduction);
    }

    public static void addReduction(CosmetiqueManager.Cosmetique cosmetique, int percentage) {

        if (hasReduction(cosmetique))
            return;

        Reduction reduction = new Reduction(cosmetique, percentage);

        reductions.add(reduction);
    }

    public static void removeReduction(CosmetiqueManager.Cosmetique cosmetique) {
        if (!hasReduction(cosmetique))
            return;

        reductions.remove(getReduction(cosmetique));
    }

    public static Reduction deserialize(String data) {
        return new Reduction(CosmetiqueManager.Cosmetique.getByMySQLName(data.split("#")[0]), Integer.parseInt(data.split("#")[1]));
    }

    public static String serialize(Reduction reduction) {
        return reduction.getCosmetique().getMysqlName() + "#" + reduction.getReduction();
    }

    public static void enable() {

        LCCosmetiques.getInstance().reloadConfig();

        if (LCCosmetiques.getInstance().getConfig().contains("reductions")) {

            List<String> _reductions = (List<String>) LCCosmetiques.getInstance().getConfig().getList("reductions");

            for (String r : _reductions)
                if (r != null && !r.equals("aucune"))
                    addReduction(deserialize(r));

        }

    }

    public static void disable() {

        List<String> _reductions = new ArrayList<>();

        for (Reduction reduction : reductions)
            _reductions.add(serialize(reduction));

        if (_reductions.size() > 0) {

            LCCosmetiques.getInstance().getConfig().set("reductions", _reductions);
            LCCosmetiques.getInstance().saveConfig();

        }
    }


}
