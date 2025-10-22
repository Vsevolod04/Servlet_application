/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sorkin.ssp10.myclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cogog
 */
public class DbConnectionExample {
    
    private Connection conn;

    DbConnectionExample() {
        try {
            //динамическая загрузка драйвера вDriverManager
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.err.println("Driver load failed...");
            this.conn = null;
            return;
        }

        try {
            //allowPublicKeyRetrieval запрос открытого ключа у сервера
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ssp?"
                    + "user=root&password=admin&useSSL=false&allowPublicKeyRetrieval=true");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            this.conn = null;
        }
    }

    Connection getConn() {
        return this.conn;
    }
}
