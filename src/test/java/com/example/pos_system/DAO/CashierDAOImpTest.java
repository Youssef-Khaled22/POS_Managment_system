package com.example.pos_system.DAO;

import com.example.pos_system.model.Product;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CashierDAOImpTest {
    CashierDAOImp cashierDAOImp = new CashierDAOImp();

    int i = 20;
    @org.junit.jupiter.api.Test
    void getNameOfMost5Selling() {
        String[] Most5Selling = {"laptop" ,"Milk" ,"Yougert" , "car" , "egg"};
        String[] Most5Selling2 = {""};
        String[] Most5SellingCompare = cashierDAOImp.getNameOfMost5Selling();
        assertAll(
                () -> assertEquals(true, Arrays.equals(Most5Selling, Most5SellingCompare)),
                () -> assertEquals(false, Arrays.equals(Most5Selling2, Most5SellingCompare))
        );
        ProductDAOImp productDAO = new ProductDAOImp();
        productDAO.deleteProduct(new Product(i,"laptop",1500,2000,2,Date.valueOf("2023-07-02"), Date.valueOf("2023-02-02"), 100, "USA", "Electronics"));
        assertAll(
                () -> assertEquals(null, cashierDAOImp.getNameOfMost5Selling())
        );
        productDAO.addNewProduct(new Product(1,"laptop",1500,2000,2,Date.valueOf("2023-07-02"), Date.valueOf("2023-02-02"), 100, "USA", "Electronics"));
    }

    @org.junit.jupiter.api.Test
    void getMost5Selling() {
        int[] Most5Selling = {100, 41, 40, 34, 26};
        int[] Most5Selling2 = {0};
        int[] Most5SellingCompare = cashierDAOImp.getMost5Selling();
        assertAll(
                () -> assertEquals(true, Arrays.equals(Most5Selling, Most5SellingCompare)),
                () -> assertEquals(false, Arrays.equals(Most5Selling2, Most5SellingCompare))
        );
        ProductDAOImp productDAO = new ProductDAOImp();
        productDAO.deleteProduct(new Product(i,"laptop",1500,2000,2,Date.valueOf("2023-07-02"), Date.valueOf("2023-02-02"), 100, "USA", "Electronics"));
        assertAll(
                () -> assertEquals(null, cashierDAOImp.getNameOfMost5Selling())
        );
        productDAO.addNewProduct(new Product(1,"laptop",1500,2000,2,Date.valueOf("2023-07-02"), Date.valueOf("2023-02-02"), 100, "USA", "Electronics"));
    }

    @org.junit.jupiter.api.Test
    void getProduct() {
        //boolean null or return product
        Product p = new Product(2,"egg",5,7,4, Date.valueOf("2022-02-02"),Date.valueOf("2022-02-01"),26,"Egypt","Dairy");
        assertAll(
                () -> assertEquals(null, cashierDAOImp.getProduct(100)),
                () -> assertEquals(p.toString(),cashierDAOImp.getProduct(2).toString())
        );
    }

    @org.junit.jupiter.api.Test
    void isExist() {
        assertAll(
                () -> assertEquals(false, cashierDAOImp.isExist(100)),
                () -> assertEquals(true,  cashierDAOImp.isExist(5))
        );
    }

    @org.junit.jupiter.api.Test
    void mostSelling() {
        getMost5Selling();
        getNameOfMost5Selling();
    }

    @org.junit.jupiter.api.Test
    void updateNumsOfSales_Quantity() {
        //update sales + 1 quantity -1
        Product p = new Product(2,"egg",5,7,3, Date.valueOf("2022-02-02"),Date.valueOf("2022-02-01"),27,"Egypt","Dairy");
        cashierDAOImp.updateNumsOfSales_Quantity(p,2);
        assertAll(
                () -> assertEquals(cashierDAOImp.getProduct(2).toString(),p.toString())
        );
    }

    @org.junit.jupiter.api.Test
    void newId() {
        //customer add 2 id's
        assertAll(
                () -> assertEquals(24,cashierDAOImp.newId()),
                () -> assertEquals(25,cashierDAOImp.newId()),
                () -> assertNotEquals(25,cashierDAOImp.newId())
        );
    }
}

//    @org.junit.jupiter.api.Test
//    void getProductTest2() {
//        CashierDAO cashierDAOMoc = mock(CashierDAO.class);
//
//        // Mock the recursive call to computeFactorial
//        when(cashierDAOMoc.isExist(1)).thenReturn(true);
//        when(cashierDAOMoc.isExist(2)).thenReturn(true);
//        when(cashierDAOMoc.isExist(8)).thenReturn(false);
//
//
////        //Create object
//        CashierDAOImp cashierDAOImp = new CashierDAOImp(cashierDAOMoc);
//
//        Product p = new Product(2,"egg",5,7,4, Date.valueOf("2022-02-02"),Date.valueOf("2022-02-01"),26,"Egypt","Dairy");
//        assertAll(
//                () -> assertEquals(null, cashierDAOImp.getProduct(8)),
//                () -> assertEquals(p.toString(),cashierDAOImp.getProduct(2).toString())
//        );
////        verify(cashierDAOMoc).isExist(2);
//    }