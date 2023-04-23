package com.example.pos_system.DAO;

import com.example.pos_system.model.Product;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOTest {

    @org.junit.jupiter.api.Test
    void addNewProduct() {
        CashierDAOImp obj=new CashierDAOImp();
        ProductDAOImp obj1=new ProductDAOImp();
        Product p=new Product(1,"Laptop",900,1000,90,Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),1,"China","Laptop");
        //obj1.addNewProduct(p);
        assertAll(
                () -> assertEquals(true,obj.isExist(1))
        );
    }
    @org.junit.jupiter.api.Test
    void ubdateProduct() {
        CashierDAOImp obj=new CashierDAOImp();
        ProductDAOImp obj1=new ProductDAOImp();
        Product p=new Product(1,"mobile",900,1000,90,Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),1,"China","Laptop");
        obj1.ubdateProduct(p);
    }

    @org.junit.jupiter.api.Test
    void allSales() {
        ProductDAOImp obj1=new ProductDAOImp();
        Product p;
        p=new Product(2,"Laptop",900,1000,90,Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),10,"China","Laptop");
        //obj1.addNewProduct(p);
        assertAll(
                () -> assertEquals(11,obj1.allSales())
        );
    }


    @org.junit.jupiter.api.Test
    void deleteProduct() {
        CashierDAOImp obj=new CashierDAOImp();
        ProductDAOImp obj1=new ProductDAOImp();
        Product p=new Product(1,"Laptop",900,1000,90,Date.valueOf("2026-2-2"),Date.valueOf("2023-1-1"),1,"China","Laptop");
        obj1.deleteProduct(p);
        assertAll(
                () -> assertEquals(obj.isExist(1),false)
        );
    }



    @org.junit.jupiter.api.Test
    void expDate() {

    }


}