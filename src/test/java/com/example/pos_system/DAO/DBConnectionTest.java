package com.example.pos_system.DAO;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void getConnection() {
        PrintWriter writer=null;
        try {
            writer = new PrintWriter(new File("F:/dblink.txt"));
            writer.println("no connection");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            if (writer!=null)
                writer.close();
        }
        Connection connection = DBConnection.getConnection();
        assertNull(connection);

    }
}