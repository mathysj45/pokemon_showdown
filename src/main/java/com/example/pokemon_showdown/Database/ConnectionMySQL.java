package com.example.pokemon_showdown.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/showdown";
    private static final String USER = "root";
    private static final String PASS = "demopma";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
