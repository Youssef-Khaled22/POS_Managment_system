package com.example.pos_system.DAO;

import com.example.pos_system.model.Product;

public interface CashierDAO {
    Product getProduct(int id);

    boolean isExist(int id);

    void MostSelling();

    void updateNumsOfSales_Quantity(Product product, int id);

    int newId();

}
