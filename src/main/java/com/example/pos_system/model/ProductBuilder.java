package com.example.pos_system.model;

import java.sql.Date;

public class ProductBuilder {

    private int id;
    private String proName;
    private float priceA;
    private float priceB;
    private int quantity;
    private Date EXpDate;
    private Date prodDate;
    private int numOfSales;
    private String counteryofprod;
    private String category;

    public ProductBuilder id(int id) {
        this.id = id;
        return this;

    }

    public ProductBuilder proName(String proName) {
        this.proName = proName;
        return this;

    }

    public ProductBuilder priceA(float priceA) {
        this.priceA = priceA;
        return this;
    }

    public ProductBuilder priceB(float priceB) {
        this.priceB = priceB;
        return this;
    }

    public ProductBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder EXpDate(Date EXpDate) {
        this.EXpDate = EXpDate;
        return this;
    }

    public ProductBuilder prodDate(Date prodDate) {
        this.prodDate = prodDate;
        return this;
    }

    public ProductBuilder numOfSales(int numOfSales) {
        this.numOfSales = numOfSales;
        return this;
    }

    public ProductBuilder counteryofprod(String counteryofprod) {
        this.counteryofprod = counteryofprod;
        return this;
    }

    public ProductBuilder category(String category) {
        this.category = category;
        return this;
    }

    public Product build() {
        return new Product(id, proName, priceA, priceB, quantity, EXpDate, prodDate, numOfSales, counteryofprod, category);
    }
}
