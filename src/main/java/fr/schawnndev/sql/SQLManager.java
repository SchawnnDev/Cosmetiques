/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.sql.SQLManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 16/05/15 23:48.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.sql;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    @Getter @Setter
    protected static Connection connection;

    /**
     *
     *  PARTIE ACHAT
     *
     */

    public static boolean hasBuyCosmetic(Player player, String cosmetic){

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM cosmetiques WHERE uuid = '" + player.getUniqueId() + "';");

            resultSet.next();

            if(resultSet.getString("uuid") == null)
                return false;
            else if (resultSet.getString("achats") == null)
                return false;
            else {
                String[] cosmetics = resultSet.getString("achats").split(",");

                for(int i = 0; i < cosmetic.length(); i++)
                    if(cosmetics[i].equalsIgnoreCase(cosmetic))
                        return true;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static void addCosmetic(Player player, String cosmetic){

    }

    public static void removeCosmetic(Player player, String cosmetic){


    }

    public static void addToDataBase(Player player){

    }

    public static boolean isInDataBase(Player player){
        return false;
    }

}
