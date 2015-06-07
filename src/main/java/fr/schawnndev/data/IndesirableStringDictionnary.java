/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.data.IndesirableStringDictionnary) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 07/06/15 18:15.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.data;

import fr.schawnndev.LCCosmetiques;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class IndesirableStringDictionnary {

    private static List<String> indesirableStrings = new ArrayList<>();

    public static boolean isIndesirable(String string){
        if(indesirableStrings.equals(string.toLowerCase()) || indesirableStrings.equals(string) || indesirableStrings.equals(string.toUpperCase()))
            return true;
        return false;
    }

    public static boolean containsIndesirable(String string){
        if(indesirableStrings.contains(string.toLowerCase()) || indesirableStrings.contains(string) || indesirableStrings.contains(string.toUpperCase()))
            return true;
        return false;
    }

    public static void init(){
        File file = new File(LCCosmetiques.getInstance().getDataFolder().getPath() + "/dictionnary.txt");

        try {
            if(!file.exists())
                file.createNewFile();

            indesirableStrings.addAll(Files.readAllLines(file.toPath(), Charset.forName("UTF-8")));
        } catch (IOException e){
            e.printStackTrace();
        }


    }

}
