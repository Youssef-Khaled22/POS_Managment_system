package com.example.pos_system.model;

public class CustomerBuilder {
    private int id;
    private int servesEvaluation;
    private float cashback;
    private String message;

    public CustomerBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder servesEvaluation(int servesEvaluation) {
        this.servesEvaluation = servesEvaluation;
        return this;
    }

    public CustomerBuilder cashback(float cashback) {
        this.cashback = cashback;
        return this;
    }

    public CustomerBuilder message(String message) {
        this.message = message;
        return this;
    }

    public Customer build() {
        return new Customer(id, servesEvaluation, cashback, message);
    }
}
