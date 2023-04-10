package com.example.pos_system.model;

public class Customer {
    private int id;
    private int servesEvaluation;//from 1 t0 5
    private float cashback;
    private String massage;

    public Customer(int id, int servesEvaluation, float cashback, String massage) {
        this.id = id;
        this.servesEvaluation = servesEvaluation;
        this.cashback = cashback;
        this.massage = massage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServesEvaluation() {
        return servesEvaluation;
    }

    public void setServesEvaluation(int servesEvaluation) {
        this.servesEvaluation = servesEvaluation;
    }

    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    @Override
    public String toString() {
        return "customer{" +
                "id=" + id +
                ", servesEvaluation=" + servesEvaluation +
                ", cashback=" + cashback +
                ", massage='" + massage + '\'' +
                '}';
    }
}
