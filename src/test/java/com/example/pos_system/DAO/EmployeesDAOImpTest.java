package com.example.pos_system.DAO;

import com.example.pos_system.POS_System;
import com.example.pos_system.model.Employees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

class EmployeesDAOImpTest {

    @org.junit.jupiter.api.Test
    void findAll() {
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
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertNull(obj.findAll());
        try {
            writer = new PrintWriter(new File("F:/dblink.txt"));
            writer.println("jdbc:mysql://localhost:3306/pos_msystem");
            writer.println("root");
            writer.println("958262");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            if (writer!=null)
                writer.close();
        }
        EmployeesDAOImp obj2 = new EmployeesDAOImp();
        assertEquals("[Employees{id=12345678900005, name='ahmed', jop='Cashier', gender='Male', salary=10000, birthDate='1999-01-01', phone=099649778585, userName='ahmed', password='202cb962ac59075b964b07152d234b70'}, " +
                "Employees{id=12345678900000, name='mohammed', jop='Storekeeper', gender='Male', salary=30000, birthDate='2001-01-01', phone=09668866, userName='mohammed', password='202cb962ac59075b964b07152d234b70'}, " +
                "Employees{id=30108764574690, name='Youssef Khaled', jop='Manager', gender='Male', salary=35000, birthDate='2001-08-22', phone=8677587978, userName='youssef', password='c76a85d0581a6ddc1e547468c710ac7c'}, " +
                "Employees{id=00000123456789, name='yusuf', jop='Cashier', gender='Male', salary=30000, birthDate='2001-09-01', phone=097986, userName='yusuf', password='202cb962ac59075b964b07152d234b70'}]",obj2.findAll().toString());
    }

    @org.junit.jupiter.api.Test
    void findByUserName() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertEquals("Employees{id=12345678900005, name='ahmed', jop='Cashier', gender='Male', salary=10000, birthDate='1999-01-01', phone=099649778585, userName='ahmed', password='202cb962ac59075b964b07152d234b70'}",obj.findByUserName("ahmed").toString());
        assertEquals(null,obj.findByUserName("3bas"));
    }

    @org.junit.jupiter.api.Test
    void add() {
        Employees obj=new Employees("12345678000009","mahmoud","Manager","Male",3000,"jkbgkhghg", Date.valueOf("2000-2-2"),"072986764","mahmoud", POS_System.doHashing("123"));
        EmployeesDAOImp obj2=new EmployeesDAOImp();
        obj2.add(obj);
        assertEquals(true, obj2.isExist("mahmoud"));
    }

    @org.junit.jupiter.api.Test
    void update() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        Employees obj2 = new Employees("00000123456789","yusuf","Cashier","Male",50000,"jkbgkhghg", Date.valueOf("2000-2-2"),"072986764","yusuf", POS_System.doHashing("123"));
        obj.update(obj2,"mahmoud");
        assertEquals("Employees{id=00000123456789, name='yusuf', jop='Cashier', gender='Male', salary=30000, birthDate='2001-09-01', phone=097986, userName='yusuf', password='202cb962ac59075b964b07152d234b70'}",obj.findByUserName("yusuf").toString());
    }

    @org.junit.jupiter.api.Test
    void deleteByUserName() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        obj.deleteByUserName("mahmoud");
        assertEquals(false,obj.isExist("mahmoud"));
    }

    @org.junit.jupiter.api.Test
    void isExist() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertAll(
                ()->assertEquals(true, obj.isExist("youssef")),
                ()->assertEquals(true, obj.isExist("mohammed"))
        );
    }

    @org.junit.jupiter.api.Test
    void duplicateID() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertEquals(true,obj.duplicateID("30108764574690"));
        assertEquals(false,obj.duplicateID("12345678900045"));
    }

    @org.junit.jupiter.api.Test
    void checkPassword() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertEquals(true,obj.checkPassword("youssef",POS_System.doHashing("958262")));
        assertEquals(false,obj.checkPassword("youssef",POS_System.doHashing("123")));
    }

    @org.junit.jupiter.api.Test
    void checkJop() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertEquals(true,obj.checkJop("youssef","Manager"));
        assertEquals(false,obj.checkJop("youssef","Cashier"));
    }
}