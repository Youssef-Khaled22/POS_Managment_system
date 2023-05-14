package com.example.pos_system.DAO;

import com.example.pos_system.model.Employees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EmployeesDAOImp implements EmployeeDAO {
    @Override
    public ObservableList<Employees> findAll() {
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            return null;
        ObservableList<Employees> employees = FXCollections.observableArrayList();
        String query = "select * from employees";
        return getEmployees(conn, employees, query);
    }

    ObservableList<Employees> getEmployees(Connection conn, ObservableList<Employees> employees, String query) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(buildEmployee(resultSet));
            }
        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (NullPointerException | SQLException e) {
                throw new RuntimeException();
            }
        }
        return employees;
    }

    @Override
    public Employees findByUserName(String userName) {
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            return null;
        String query = "select * from employees where userName=?";
        return getEmployee(conn, userName, query);
    }
    Employees getEmployee(Connection conn, String userName, String query) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return buildEmployee(resultSet);
        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (NullPointerException | SQLException e) {
                throw new RuntimeException();
            }
        }
        throw new RuntimeException();
    }

    Employees buildEmployee(ResultSet resultSet) throws SQLException {
        String gender;
        if (resultSet.getBoolean("gender")) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        return Employees.builder()
                .id(resultSet.getString("id"))
                .name(resultSet.getString("name"))
                .jop(resultSet.getString("jop"))
                .gender(gender)
                .salary(resultSet.getInt("salary"))
                .address(resultSet.getString("address"))
                .birthDate(resultSet.getDate("birthDate"))
                .phone(resultSet.getString("phone"))
                .userName(resultSet.getString("userName"))
                .password(resultSet.getString("password")).build();
    }

    @Override
    public void add(Employees employee) {
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            throw new RuntimeException();
        if (!isExist(employee.getUserName())) {
            String query = "insert into employees (id,name,jop,gender,salary,address,birthDate,phone,userName,password) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            addEmployee(conn, employee, query);
        }
    }

    void addEmployee(Connection conn, Employees employee, String query) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            setEmployee(employee, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (NullPointerException | SQLException er) {
                throw new RuntimeException();
            }
        }
    }

    void setEmployee(Employees employee, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, employee.getId());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getJop());
        if (Objects.equals(employee.getGender(), "Male"))
            preparedStatement.setInt(4, 1);
        else
            preparedStatement.setInt(4, 0);
        preparedStatement.setInt(5, employee.getSalary());
        preparedStatement.setString(6, employee.getAddress());
        preparedStatement.setDate(7, employee.getBirthDate());
        preparedStatement.setString(8, employee.getPhone());
        preparedStatement.setString(9, employee.getUserName());
        preparedStatement.setString(10, employee.getPassword());
    }

    @Override
    public void update(Employees employee, String username) {
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            throw new RuntimeException();
        if (isExist(username)) {
            String query = "update employees set id=?,name=?,jop=?,gender=?,salary=?,address=?,birthDate=?,phone=?,userName=?,password=? where userName=?";
            updateEmployee(conn, employee, query, username);
        }
    }

    void updateEmployee(Connection conn, Employees employee, String query, String username) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            setEmployee(employee, preparedStatement);
            preparedStatement.setString(11, username);
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (NullPointerException | SQLException er) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void deleteByUserName(String userName) {
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            throw new RuntimeException();
        String query = "delete from employees where userName=?";
        deleteEmployee(conn, query, userName);
    }

    void deleteEmployee(Connection conn, String query, String userName) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (NullPointerException | SQLException er) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public boolean isExist(String userName) {
        Connection conn = DBConnection.getConnection();
        String query = "select * from employees where userName=?";
        return findEmployee(userName, conn, query);
    }

    boolean findEmployee(String userName_or_ID, Connection conn, String query) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, userName_or_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        } catch (NullPointerException | SQLException e) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (NullPointerException | SQLException e) {
                throw new RuntimeException();
            }
        }
    }
    @Override
    public boolean duplicateID(String ID) {
        Connection conn = DBConnection.getConnection();
        String query = "select * from employees where id = ?";
        return findEmployee(ID, conn, query);
    }

    public boolean checkPassword(String username, String password) {
        Employees employee = findByUserName(username);
        return Objects.equals(employee.getPassword(), password);
    }

    @Override
    public boolean checkJop(String username, String jop) {
        Employees employee = findByUserName(username);
        return Objects.equals(employee.getJop(), jop);
    }
}