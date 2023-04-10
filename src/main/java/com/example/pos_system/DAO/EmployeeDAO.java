package com.example.pos_system.DAO;

import com.example.pos_system.model.Employees;
import javafx.collections.ObservableList;

import java.util.List;

public interface EmployeeDAO {
    ObservableList<Employees> findAll();

    List<Employees> find(String colum);//ممكن امسحها

    Employees findByUserName(String userName);

    void add(Employees employee);

    void update(Employees employee, String username);

    void deleteByUserName(String userName);

    boolean isExist(String userName);

    boolean duplicateID(String ID);

    boolean checkPassword(String username, String password);

    boolean checkJop(String username, String jop);

}
