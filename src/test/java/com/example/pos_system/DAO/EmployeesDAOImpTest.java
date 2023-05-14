package com.example.pos_system.DAO;

import com.example.pos_system.POS_System;
import com.example.pos_system.model.Employees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

class EmployeesDAOImpTest {

    void setUp() {
        try (PrintWriter writer = new PrintWriter("F:/dblink.txt")) {
            writer.println("no connection");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void tearDown() {
        try (PrintWriter writer = new PrintWriter("F:/dblink.txt")) {
            writer.println("jdbc:mysql://localhost:3306/pos_msystem");
            writer.println("root");
            writer.println("958262");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll() {
        setUp();
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertNull(obj.findAll());
        tearDown();
        EmployeesDAOImp obj2 = new EmployeesDAOImp();
        assertEquals("[Employees{id=12345678900005, name='ahmed', jop='Cashier', gender='Male', salary=10000, birthDate='1999-01-01', phone=099649778585, userName='ahmed', password='202cb962ac59075b964b07152d234b70'}, " +
                        "Employees{id=12345678900000, name='mohammed', jop='Storekeeper', gender='Male', salary=30000, birthDate='2001-01-01', phone=09668866, userName='mohammed', password='202cb962ac59075b964b07152d234b70'}, " +
                        "Employees{id=12345678000059, name='salwan', jop='Manager', gender='Female', salary=50000, birthDate='2000-02-02', phone=072986764, userName='salwan', password='202cb962ac59075b964b07152d234b70'}, " +
                        "Employees{id=30108764574690, name='Youssef Khaled', jop='Manager', gender='Male', salary=35000, birthDate='2001-08-22', phone=8677587978, userName='youssef', password='c76a85d0581a6ddc1e547468c710ac7c'}, " +
                        "Employees{id=00000123456789, name='yusuf', jop='Cashier', gender='Male', salary=50000, birthDate='2000-02-02', phone=072986764, userName='yusuf', password='202cb962ac59075b964b07152d234b70'}]"
                ,obj2.findAll().toString());
    }

    @Test
    void getEmployees() {
        setUp();
        EmployeesDAOImp obj = new EmployeesDAOImp();
        Connection conn = DBConnection.getConnection();
        ObservableList<Employees> employees= FXCollections.observableArrayList();
        String query = "select * from employees";
        assertNull(obj.getEmployees(conn,employees,query));
        tearDown();
        conn = DBConnection.getConnection();
        assertEquals("[Employees{id=12345678900005, name='ahmed', jop='Cashier', gender='Male', salary=10000, birthDate='1999-01-01', phone=099649778585, userName='ahmed', password='202cb962ac59075b964b07152d234b70'}, " +
                        "Employees{id=12345678900000, name='mohammed', jop='Storekeeper', gender='Male', salary=30000, birthDate='2001-01-01', phone=09668866, userName='mohammed', password='202cb962ac59075b964b07152d234b70'}, " +
                        "Employees{id=12345678000059, name='salwan', jop='Manager', gender='Female', salary=50000, birthDate='2000-02-02', phone=072986764, userName='salwan', password='202cb962ac59075b964b07152d234b70'}, " +
                        "Employees{id=30108764574690, name='Youssef Khaled', jop='Manager', gender='Male', salary=35000, birthDate='2001-08-22', phone=8677587978, userName='youssef', password='c76a85d0581a6ddc1e547468c710ac7c'}, " +
                        "Employees{id=00000123456789, name='yusuf', jop='Cashier', gender='Male', salary=50000, birthDate='2000-02-02', phone=072986764, userName='yusuf', password='202cb962ac59075b964b07152d234b70'}]"
                ,obj.getEmployees(conn,employees,query).toString());
        query = "select * from employee";
        String finalQuery = query;
        Connection conn2 = DBConnection.getConnection();
        assertNull(obj.getEmployees(conn2, employees, finalQuery));
    }

    @Test
    void findByUserName() {
        setUp();
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertNull(obj.findByUserName("ahmed"));
        tearDown();
        EmployeesDAOImp obj2 = new EmployeesDAOImp();
        assertEquals("Employees{id=12345678900005, name='ahmed', jop='Cashier', gender='Male', salary=10000, birthDate='1999-01-01', phone=099649778585, userName='ahmed', password='202cb962ac59075b964b07152d234b70'}",obj2.findByUserName("ahmed").toString());
        assertNull(obj2.findByUserName("3bas"));
    }

    @Test
    void buildEmployee() {
    }

    @Test
    void add() {
        setUp();
        Employees employee1=new Employees("12345678000009","mahmoud","Manager","Male",3000,"jkbgkhghg", Date.valueOf("2000-2-2"),"072986764","mahmoud", POS_System.doHashing("123"));
        Employees employee2=new Employees("12345678000059","salwan","Manager","Female",50000,"jkbgkhghg", Date.valueOf("2000-2-2"),"072986764","salwan", POS_System.doHashing("123"));
        EmployeesDAOImp obj=new EmployeesDAOImp();
        obj.add(employee1);
        assertFalse(obj.isExist("mahmoud"));
        tearDown();
        obj.add(employee1);
        assertTrue(obj.isExist("mahmoud"));
        obj.add(employee2);
        assertTrue(obj.isExist("mahmoud"));
    }

    @Test
    void addEmployee(){
        Connection conn = DBConnection.getConnection();
        String query = "insert into employee (id,name,jop,gender,salary,address,birthDate,phone,userName,password) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Employees employee1=new Employees("12345678000559","hessan","Manager","Male",3000,"jkbgkhghg", Date.valueOf("2000-2-2"),"072986764","hessan", POS_System.doHashing("123"));
        EmployeesDAOImp obj=new EmployeesDAOImp();
        obj.addEmployee(conn,employee1,query);
        assertFalse(obj.isExist("hessan"));
        obj.addEmployee(null,employee1,query);
        assertFalse(obj.isExist("hessan"));
    }
    @Test
    void setEmployee() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        EmployeesDAOImp obj2 = new EmployeesDAOImp();
        Connection conn = DBConnection.getConnection();
        String query = "select * from employee where userName=?";
        assertNull(obj.getEmployee(conn,"youssef",query));
        assertNull(obj.getEmployee(null,"youssef",query));
    }

    @Test
    void update() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        Employees obj2 = new Employees("00000123456789","yusuf","Cashier","Male",50000,"jkbgkhghg", Date.valueOf("2000-2-2"),"072986764","yusuf", POS_System.doHashing("123"));
        obj.update(obj2,"yusuf");
        assertEquals("Employees{id=00000123456789, name='yusuf', jop='Cashier', gender='Male', salary=50000, birthDate='2000-02-02', phone=072986764, userName='yusuf', password='202cb962ac59075b964b07152d234b70'}",obj.findByUserName("yusuf").toString());
    }

    @Test
    void deleteByUserName() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        obj.deleteByUserName("mahmoud");
        assertFalse(obj.isExist("mahmoud"));
    }

    @Test
    void isExist() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertAll(
                ()-> assertTrue(obj.isExist("youssef")),
                ()-> assertTrue(obj.isExist("mohammed"))
        );
    }

    @Test
    void findEmployee() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        Connection conn= DBConnection.getConnection();
        String query="select * from employee where userName = ?";
        assertFalse(obj.findEmployee("3bas",conn,query));
        query = "select * from employees where userName = ?";
        assertFalse(obj.findEmployee("3bas",conn,query));
    }

    @Test
    void duplicateID() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertTrue(obj.duplicateID("30108764574690"));
        assertFalse(obj.duplicateID("12345678900045"));
    }

    @Test
    void checkPassword() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertTrue(obj.checkPassword("youssef", POS_System.doHashing("958262")));
        assertFalse(obj.checkPassword("youssef", POS_System.doHashing("123")));
    }

    @Test
    void checkJop() {
        EmployeesDAOImp obj = new EmployeesDAOImp();
        assertTrue(obj.checkJop("youssef", "Manager"));
        assertFalse(obj.checkJop("youssef", "Cashier"));
    }
}