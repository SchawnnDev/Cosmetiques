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

import fr.schawnndev.CosmetiqueManager.CosmetiqueType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    @Getter @Setter
    protected static Connection connection;

    @Getter @Setter
    protected static Statement statement;


    public static void init(){

        try {
            statement.executeQuery("CREATE TABLE IF NOT EXISTS `LC_COSMETIQUES` (`uuid` char(36) NOT NULL, `achats` varchar(1000) NOT NULL, `current_active_particle` varchar(55) NOT NULL, `current_active_gadget` varchar(55) NOT NULL, `current_active_pet` varchar(55) NOT NULL, `current_active_petname` varchar(50) NOT NULL, PRIMARY KEY (`uuid`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    /**
     *
     *  PARTIE ACHAT
     *
     */

    public static boolean hasBuyCosmetic(Player player, String cosmetic){
        List<String> cosmetics = getCosmetics(player);
        return !cosmetics.contains("aucun") && cosmetics.contains(cosmetic);
    }

    private static String[] getSplittedCosmetics(Player player){
        List<String> cosmetics = getCosmetics(player);

        String[] split = new String[cosmetics.size()];

        for(int i = 0; i < cosmetics.size(); i++)
            split[i] = cosmetics.get(i);

        return split;
    }

    private static String getCosmeticsString(Player player, List<String> cosmetics){
        String cosmetic = "aucun";

        if(cosmetics.isEmpty())
            return cosmetic;

        for(int i = 0; i < cosmetics.size(); i++)
            if(i == 0)
                cosmetic = cosmetics.get(i);
            else
                cosmetic += "," + cosmetics.get(i);

        return cosmetic;
    }

    public static List<String> getCosmetics(Player player){
        List<String> cosmetics = new ArrayList<>();

        if(!isInDataBase(player))
            addToDataBase(player);

        try {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LC_COSMETIQUES WHERE uuid = '" + player.getUniqueId().toString() + "';");

            if(resultSet.next()){

                if (resultSet.getString("uuid") == null) {
                    cosmetics.add("aucun");

                    return cosmetics;
                } else if (resultSet.getString("achats") == null) {
                    cosmetics.add("aucun");

                    return cosmetics;
                } else {
                    String achats = resultSet.getString("achats");

                    if (achats.equalsIgnoreCase("aucun")) {
                        cosmetics.add("aucun");
                        return cosmetics;
                    }

                    if (achats.contains(",")) {
                        String[] split = achats.split(",");

                        for (int i = 0; i < split.length; i++)
                            cosmetics.add(split[i]);

                        return cosmetics;
                    } else {
                         cosmetics.add(achats);
                        return cosmetics;
                    }
                }

            } else {
                return cosmetics;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return cosmetics;
        }
    }

    public static void addCosmetic(Player player, String cosmetic){

        if(!isInDataBase(player))
            addToDataBase(player);

        try {

            if(!hasBuyCosmetic(player, cosmetic)){

                List<String> cosmetics = getCosmetics(player);

                if(!cosmetics.contains(cosmetic)){
                    if(cosmetics.contains("aucun"))
                        cosmetics.remove("aucun");
                    cosmetics.add(cosmetic);
                }

                PreparedStatement preparedStmt = connection.prepareStatement("UPDATE LC_COSMETIQUES SET achats=? WHERE uuid=?");
                preparedStmt.setString(1, getCosmeticsString(player, cosmetics));
                preparedStmt.setString(2, player.getUniqueId().toString());
                preparedStmt.executeUpdate();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void removeCosmetic(Player player, String cosmetic){

        if(!isInDataBase(player))
            addToDataBase(player);

        try {
            if(hasBuyCosmetic(player, cosmetic)){
                List<String> cosmetics = getCosmetics(player);

                if(!cosmetics.isEmpty() && cosmetics.contains(cosmetic)){
                    cosmetics.remove(cosmetic);
                }

                if(cosmetics.size() <= 0){
                    cosmetics.add("aucun");
                }

                PreparedStatement preparedStmt = connection.prepareStatement("UPDATE LC_COSMETIQUES SET achats=? WHERE uuid=?");
                preparedStmt.setString(1, getCosmeticsString(player, cosmetics));
                preparedStmt.setString(2, player.getUniqueId().toString());
                preparedStmt.executeUpdate();

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void addToDataBase(Player player){

        try {
            statement.executeUpdate("INSERT INTO LC_COSMETIQUES (`uuid`, `achats`, `current_active_particle`, `current_active_gadget`, `current_active_pet`, `current_active_petname`) VALUES ('" + player.getUniqueId().toString() + "', '"+ "aucun" +"', '"+ "aucun"+"', '"+ "aucun"+"', '"+ "aucun"+"', '"+ "aucun"+"');");
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static boolean isInDataBase(Player player){

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM LC_COSMETIQUES WHERE uuid = '" + player.getUniqueId().toString() + "';");

            if(resultSet.next())
                return true;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean hasActiveCosmetic(Player player, CosmetiqueType cosmetiqueType){
        return !getActiveCosmetic(player, cosmetiqueType).equalsIgnoreCase("aucun");
    }

    public static String getActiveCosmetic(Player player, CosmetiqueType cosmetiqueType){

        if(!isInDataBase(player))
            addToDataBase(player);

        try {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LC_COSMETIQUES WHERE uuid = '" + player.getUniqueId().toString() + "';");

            if(resultSet.next()){
                return resultSet.getString(cosmetiqueType.getMySQLTable());
            } else {
                return "aucun";
            }


        } catch (SQLException e){
            e.printStackTrace();
        }

        return "aucun";
    }

    public static void setActiveCosmetic(Player player, String activeCosmetic, CosmetiqueType cosmetiqueType){

        if(!isInDataBase(player))
            addToDataBase(player);

        try {
            PreparedStatement preparedStatement;

            if(cosmetiqueType == CosmetiqueType.GADGET){
                preparedStatement = connection.prepareStatement("UPDATE LC_COSMETIQUES SET current_active_gadget=? WHERE uuid=?");
            } else if (cosmetiqueType == CosmetiqueType.PARTICLE){
                preparedStatement = connection.prepareStatement("UPDATE LC_COSMETIQUES SET current_active_particle=? WHERE uuid=?");
            } else {
                preparedStatement = connection.prepareStatement("UPDATE LC_COSMETIQUES SET current_active_pet=? WHERE uuid=?");
            }

            preparedStatement.setString(1, activeCosmetic);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static boolean hasActivePetName(Player player){
        return !getActivePetName(player).equalsIgnoreCase("aucun");
    }

    public static String getActivePetName(Player player){

        if(!isInDataBase(player))
            addToDataBase(player);

        try {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LC_COSMETIQUES WHERE uuid = '" + player.getUniqueId().toString() + "';");

            if(resultSet.next()){
                return resultSet.getString("current_active_petname");
            } else {
                return "aucun";
            }


        } catch (SQLException e){
            e.printStackTrace();
        }

        return "aucun";
    }

    public static void setActivePetName(Player player, String petName){

        if(!isInDataBase(player))
            addToDataBase(player);

        try {
            PreparedStatement preparedStatement;

            preparedStatement = connection.prepareStatement("UPDATE LC_COSMETIQUES SET current_active_petname=? WHERE uuid=?");

            preparedStatement.setString(1, petName);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
