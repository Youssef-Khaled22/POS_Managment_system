package com.example.pos_system.model;

import java.sql.Date;

public class EmployeeBuilder {
    private String id;
    private String name;
    private String jop;
    private String gender;
    private int salary;
    private String address;
    private Date birthDate;
    private String phone;
    private String userName;
    private String password;

    public EmployeeBuilder id(String id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder jop(String jop) {
        this.jop = jop;
        return this;
    }

    public EmployeeBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public EmployeeBuilder salary(int salary) {
        this.salary = salary;
        return this;
    }

    public EmployeeBuilder address(String address) {
        this.address = address;
        return this;
    }

    public EmployeeBuilder birthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public EmployeeBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public EmployeeBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public EmployeeBuilder password(String password) {
        this.password = password;
        return this;
    }

    public Employees build() {
        return new Employees(id, name, jop, gender, salary, address, birthDate, phone, userName, password);
    }
}
