package com.example.pos_system.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        BufferedReader reader = null;
        Connection connection;
        try {
            reader = new BufferedReader(new FileReader("F:/dblink.txt"));
            connection = DriverManager.getConnection(reader.readLine(), reader.readLine(), reader.readLine());
        } catch (SQLException | IOException e) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                return null;
            }
        }
        return connection;
    }


}
