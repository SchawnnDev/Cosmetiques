/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.math.Calculs) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 24/06/15 09:11.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Calculs {

    public static void main(String[] args){}

    /**
     * J'aime les calculs! :b
     * @return String with total
     */

    public String calculateMoyenneWith4Digits(){
        double add = 0;
        double moyenne = 0;
        double total = 0;
        List<Double> notes = new ArrayList<>();

        for(int i = 1; i <=4; i++) {
            double toAdd = i + new Random().nextInt(10);
            total += toAdd;
            notes.add(toAdd);
        }

        while (moyenne != 8.5){
            //    System.out.println("Check: " + moyenne + " with: " + add);

            moyenne = (total + add) / 5;


            if(moyenne != 8.5) {

                add += 0.5;

                if (add >= 20) {
                    System.err.println("Impossible de calculer avec les notes actuelles.");
                    return "impossible";
                }
            }
        }

        notes.add(add);

        String message = "Résultat : ";

        for(double note : notes)
            message += "" + note + " ";

        System.out.println(message);

        return message;
    }

    /**
     * @param notes The notes to calculate
     * @return The moyenne of the notess
     *  C'est très le francais
     */

    public double calculateMoyenne(double... notes){
        double result = 0.0;

        for(int i = 0; i < notes.length; i++)
            result += notes[i];

        return result / notes.length;
    }
}
