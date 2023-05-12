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
        ProductDAOImp obj=new ProductDAOImp();
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
    void DeleteProduct() {
        ProductDAOImp obj=new ProductDAOImp();
        Connection conn= DBConnection.getConnection();
        String query="ELETE FROM product WHERE id=?;";

        Product p=new Product(1,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,"China","Laptop");
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.DeleteProduct(conn,query,p);
            }
        });
        String query2="DELETE FROM product WHERE id=?;";
        ProductDAOImp obj2=new ProductDAOImp();
        Connection conn2= DBConnection.getConnection();
        CashierDAOImp obj_Cashier=new CashierDAOImp();

        obj2.addNewProduct(p);
        obj.DeleteProduct(conn2,query2,p);
        assertEquals(false,obj_Cashier.isExist(1));

    }
    @Test
    void deleteProduct() {
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
        Product p=new Product(1,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,"China","Laptop");
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.deleteProduct(p);
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
        CashierDAOImp obj_Cashier=new CashierDAOImp();
        obj.addNewProduct(p);
        assertEquals(true,obj_Cashier.isExist(1));
        obj.deleteProduct(p);
        assertEquals(false,obj_Cashier.isExist(1));


    }

    @Test
    void ubdateProduct() {
    }

    @Test
    void expDate() {
    }

    @Test
    void allsales(){
        String query="ELECT * FROM product ";
        Connection connection= DBConnection.getConnection();
        ProductDAOImp obj=new ProductDAOImp();
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                obj.allsales(connection,query);
            }
        });
        String query2="SELECT * FROM product ";
        Connection connection2= DBConnection.getConnection();
        ProductDAOImp obj2=new ProductDAOImp();
        //NO PRODUCTS
        assertEquals(0,obj2.allsales(connection2,query2));

        // 1 product
        Product p=new Product(1,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,
                "China","Laptop");
        obj.addNewProduct(p);
        assertEquals(1,obj2.allsales(connection2,query2));
        // 2 product
        Product p2=new Product(2,"DELL",900,1000,90,
                Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),
                1,
                "China","Laptop");
        obj.addNewProduct(p2);
        assertEquals(2,obj2.allsales(connection2,query2));
    }
    @Test
    void allSales() {
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
        assertEquals(-1,obj.allSales());

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
        assertEquals(4,obj.allSales());
    }
}