/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.sql.SQL) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 16/05/15 23:42.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.sql;

import fr.schawnndev.LCCosmetiques;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    @Getter
    private String hostname;

    @Getter
    private String database;

    @Getter
    private String user;

    @Getter
    private String password;

    private int task_id;

    public SQL(String hostname, String database, String user, String password){
        this.hostname = hostname;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void start() {
        this.task_id = Bukkit.getScheduler().runTaskTimer(LCCosmetiques.getInstance(), new Runnable() {

            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    SQLManager.setConnection(DriverManager.getConnection("jdbc:mysql://" + hostname + ":3306/" + database, user, password));
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 20 * 60 * 30).getTaskId();
    }

    public void stop(){
        Bukkit.getScheduler().cancelTask(task_id);
    }

}
