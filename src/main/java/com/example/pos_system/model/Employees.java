package com.example.pos_system.model;

import java.sql.Date;

public class Employees {
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

    public Employees(String id, String name, String jop, String gender, int salary, String address, Date birthDate, String phone, String userName, String password) {
        this.id = id;
        this.name = name;
        this.jop = jop;
        this.gender = gender;
        this.salary = salary;
        this.address = address;
        this.birthDate = birthDate;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJop() {
        return jop;
    }

    public void setJop(String jop) {
        this.jop = jop;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String isGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static EmployeeBuilder builder() {
        return new EmployeeBuilder();
    }


    @Override
    public String toString() {
        return "Employees{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jop='" + jop + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", birthDate='" + birthDate + '\'' +
                ", phone=" + phone +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

