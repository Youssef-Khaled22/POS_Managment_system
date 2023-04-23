package com.example.pos_system.DAO;

import com.example.pos_system.model.Product;

import java.sql.Date;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CashierDAOImpTest {
    CashierDAOImp cashierDAOImp = new CashierDAOImp();

    @org.junit.jupiter.api.Test
    void getNameOfMost5Selling() {
        String[] Most5Selling = {"laptop" ,"Milk" ,"Yougert" , "car" , "egg"};
        String[] Most5SellingCompare = cashierDAOImp.getNameOfMost5Selling();
        assertAll(
                () -> assertEquals(true, Arrays.equals(Most5Selling, Most5SellingCompare))
        );
    }

    @org.junit.jupiter.api.Test
    void getMost5Selling() {
        int[] Most5Selling = {100, 41, 40, 34, 24};
        int[] Most5SellingCompare = cashierDAOImp.getMost5Selling();
        assertAll(
                () -> assertEquals(true, Arrays.equals(Most5Selling, Most5SellingCompare))
        );
    }

    @org.junit.jupiter.api.Test
    void getProduct() {
        //boolean null or return product
        Product p = new Product(2,"egg",5,7,6, Date.valueOf("2022-02-02"),Date.valueOf("2022-02-01"),24,"Egypt","Dairy");
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
        Product p = new Product(2,"egg",5,7,6, Date.valueOf("2022-02-02"),Date.valueOf("2022-02-01"),24,"Egypt","Dairy");
        cashierDAOImp.updateNumsOfSales_Quantity(p,2);
        assertAll(
                () -> assertEquals(cashierDAOImp.getProduct(2).toString(),p.toString())
        );
    }

    @org.junit.jupiter.api.Test
    void newId() {
        //customer add 2 id's
        assertAll(
                () -> assertEquals(22,cashierDAOImp.newId()),
                () -> assertEquals(23,cashierDAOImp.newId())
        );
    }
}