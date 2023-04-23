package com.example.pos_system.DAO;


import com.example.pos_system.model.Product;

public interface ProductDAO {
    void addNewProduct(Product product);

    void deleteProduct(Product product);

    void ubdateProduct(Product product);

    void ExpDate();

    int allSales();

}
