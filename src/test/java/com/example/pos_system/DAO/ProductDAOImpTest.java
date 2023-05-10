package com.example.pos_system.DAO;

import com.example.pos_system.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImpTest {

    @Test
    void addproduct() {
        /*ProductDAOImp obj=new ProductDAOImp();
        Connection conn= DBConnection.getConnection();
        String query ="INSSERT INTO product (proName,priceA,priceB,quantity,EXpDate,prodDate,numOfSales,counteryofprod,category)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        Product p=new Product(2,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,"China","Laptop");
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.addproduct(conn,query,p);
            }
        });
        Connection conn1= DBConnection.getConnection();
        String query1 ="INSERT INTO product (proName,priceA,priceB,quantity,EXpDate,prodDate,numOfSales,counteryofprod,category)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        Product p2=new Product(2,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,"China","Laptop");
        CashierDAOImp obj_cashier=new CashierDAOImp();
        try {
            obj.addproduct(conn1,query1,p2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(true,obj_cashier.isExist(2));
         */
        Connection conn= DBConnection.getConnection();
        String query ="INSSERT INTO product (proName,priceA,priceB,quantity,EXpDate,prodDate,numOfSales,counteryofprod,category)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        Product p=new Product(2,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,"China","Laptop");
        ProductDAOImp obj=new ProductDAOImp();
        assertEquals(false,obj.addproduct(conn,query,p));

    }

    @Test
    void addNewProduct() {
        PrintWriter writer=null;
        try {
            writer=new PrintWriter(new File("F:/dblink.txt"));
            writer.println("no connection");
        }catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        finally {
            if(writer!=null)
                writer.close();
        }
        ProductDAOImp obj=new ProductDAOImp();
        CashierDAOImp obj_Cashier=new CashierDAOImp();
        Product p=new Product(1,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,"China","Laptop");

        obj.addNewProduct(p);
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj_Cashier.isExist(1);
            }
        });

        try {
            writer=new PrintWriter(new File("F:/dblink.txt"));
            writer.println("jdbc:mysql://localhost:3306/pos_msystem");
            writer.println("root");
            writer.println("123456789");
        }catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        finally {
            if(writer!=null)
                writer.close();
        }
        obj.addNewProduct(p);
        assertEquals(true,obj_Cashier.isExist(1));

    }

    @Test
    void deleteProduct() {
    }

    @Test
    void ubdateProduct() {
    }

    @Test
    void expDate() {
    }

    @Test
    void allSales() {
    }
}