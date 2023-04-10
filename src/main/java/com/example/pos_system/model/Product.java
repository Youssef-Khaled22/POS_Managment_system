package com.example.pos_system.model;

import javafx.scene.chart.PieChart;

import java.sql.Date;

public class Product {
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

    private int countCart;

    public int getCountCart() {
        return countCart;
    }

    public void setCountCart(int countCart) {
        this.countCart = countCart;
    }

    //Constructor
    public Product(int id, String proName, float priceA, float priceB, int quantity, Date EXpDate, Date prodDate, int numOfSales, String counteryofprod, String category) {
        this.id = id;
        this.proName = proName;
        this.priceA = priceA;
        this.priceB = priceB;
        this.quantity = quantity;
        this.EXpDate = EXpDate;
        this.prodDate = prodDate;
        this.numOfSales = numOfSales;
        this.counteryofprod = counteryofprod;
        this.category = category;
        this.countCart = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public float getPriceA() {
        return priceA;
    }

    public void setPriceA(float priceA) {
        this.priceA = priceA;
    }

    public float getPriceB() {
        return priceB;
    }

    public void setPriceB(float priceB) {
        this.priceB = priceB;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getEXpDate() {
        return EXpDate;
    }

    public void setEXpDate(Date EXpDate) {
        this.EXpDate = EXpDate;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public int getNumOfSales() {
        return numOfSales;
    }

    public void setNumOfSales(int numOfSales) {
        this.numOfSales = numOfSales;
    }

    public String getCounteryofprod() {
        return counteryofprod;
    }

    public void setCounteryofprod(String counteryofprod) {
        this.counteryofprod = counteryofprod;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //Builder
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", proName='" + proName + '\'' +
                ", priceA=" + priceA +
                ", priceB=" + priceB +
                ", quantity=" + quantity +
                ", EXpDate=" + EXpDate +
                ", prodDate=" + prodDate +
                ", numOfSales=" + numOfSales +
                ", counteryofprod='" + counteryofprod + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}