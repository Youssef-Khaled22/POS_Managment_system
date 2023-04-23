package com.example.pos_system.DAO;

import com.example.pos_system.model.Employees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class EmployeesDAOImp implements EmployeeDAO{
    @Override
    public ObservableList<Employees> findAll() {
        Connection conn= DBConnection.getConnection();
        if (conn==null)
            return null;
        ObservableList<Employees> employees= FXCollections.observableArrayList();
        String query = "select * from employees";
        return getEmployees(conn, employees, query);
    }

    private ObservableList<Employees> getEmployees(Connection conn, ObservableList<Employees> employees, String query) {
        try(PreparedStatement preparedStatement=conn.prepareStatement(query)){
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                employees.add(buildEmployee(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            }catch (SQLException er){
                er.printStackTrace();
            }
        }
        return employees;
    }

    @Override
    public Employees findByUserName(String userName) {
        Connection conn= DBConnection.getConnection();
        if (conn==null)
            return null;
        String query="select * from employees where userName=?";
        try (PreparedStatement preparedStatement=conn.prepareStatement(query)){
            preparedStatement.setString(1,userName);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next())
                return buildEmployee(resultSet);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }catch (SQLException er){
                er.printStackTrace();
            }
        }
        return null;
    }

    private Employees buildEmployee(ResultSet resultSet) throws SQLException {
        String gender=null;
        if (resultSet.getBoolean("gender")){
            gender="Male";
        }else{
            gender="Female";
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
        Connection conn= DBConnection.getConnection();
        if (conn==null)
            return;
        if (!isExist(employee.getUserName())) {
            String query = "insert into employees (id,name,jop,gender,salary,address,birthDate,phone,userName,password) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                setEmployee(employee, preparedStatement);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException er) {
                    er.printStackTrace();
                }
            }
        }
        else
            System.out.println("The User name is already exist");
    }

    private void setEmployee(Employees employee, PreparedStatement preparedStatement) throws SQLException {
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
    public void update(Employees employee,String username) {
        Connection conn= DBConnection.getConnection();
        if (conn==null)
            return;
        if (isExist(username)){
            String query1="update employees set id=?,name=?,jop=?,gender=?,salary=?,address=?,birthDate=?,phone=?,userName=?,password=? where userName=?";
            try (PreparedStatement preparedStatement=conn.prepareStatement(query1)){
                setEmployee(employee, preparedStatement);
                preparedStatement.setString(11,username);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException er) {
                    er.printStackTrace();
                }
            }
        }
        else
            System.out.println("The username isn't exist");
    }

    @Override
    public void deleteByUserName(String userName) {
        Connection conn= DBConnection.getConnection();
        if (conn==null)
            return;
        String query="delete from employees where userName=?";
        try (PreparedStatement preparedStatement=conn.prepareStatement(query)){
            preparedStatement.setString(1,userName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
    }

    @Override
    public boolean isExist(String userName) {
        Connection conn= DBConnection.getConnection();
        String query="select * from employees where userName=?";
        try (PreparedStatement preparedStatement=conn.prepareStatement(query)){
            preparedStatement.setString(1,userName);
            ResultSet resultSet= preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean duplicateID(String ID) {
        Connection conn= DBConnection.getConnection();
        String query="select * from employees where id=?";
        try (PreparedStatement preparedStatement=conn.prepareStatement(query)){
            preparedStatement.setString(1,ID);
            ResultSet resultSet= preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
        return true;
    }

    public boolean checkPassword(String username,String password){
        Employees employee=findByUserName(username);
        return Objects.equals(employee.getPassword(), password);
    }
    @Override
    public boolean checkJop(String username, String jop) {
        Employees employee=findByUserName(username);
        return Objects.equals(employee.getJop(), jop);
    }
}
