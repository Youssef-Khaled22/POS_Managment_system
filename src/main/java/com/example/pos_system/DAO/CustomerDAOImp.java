package com.example.pos_system.DAO;

import com.example.pos_system.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImp implements CustomerDAO {

    private float averageRating;

    public float getAverageRating() {
        FeedbackAndRating();
        return averageRating;
    }

    @Override
    public Customer getCustomer(int id) {
        if (isExist(id)) {
            Connection connection = DBConnection.getConnection();
            String query = "select * from customer WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return new Customer(
                            resultSet.getInt("id"),
                            resultSet.getInt("servesEvaluation"),
                            resultSet.getFloat("cashback"),
                            resultSet.getString("massage")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public boolean isExist(int id) {
        Connection conn = DBConnection.getConnection();
        String quary = "select * from customer where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(quary)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public int getCashback(int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return -1;
        }
        String query = "SELECT cashback FROM customer WHERE id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                return 0;
            }
            while (resultSet.next()) {
                return resultSet.getInt("cashback");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
        return -3;
    }

    @Override
    public void UpdateCashback(Customer customer, int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return;
        }
        String query = "UPDATE customer \n" +
                "SET id=?,servesEvaluation=?, cashback=?, massage=?" +
                "WHERE id=?; ";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setInt(2, customer.getServesEvaluation());
            preparedStatement.setFloat(3, customer.getCashback());
            preparedStatement.setString(4, customer.getMassage());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
    }

    @Override
    public void setFeedback(String message, int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return;
        }
        String query = "UPDATE customer " +
                "SET massage = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
    }

    @Override
    public void setrating(int rating, int id) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            return;
        }
        String query = "UPDATE customer " +
                "SET servesEvaluation = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
    }

    @Override
    public ObservableList FeedbackAndRating() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        int sum = 0, count = 0;
        String query = "SELECT *FROM customer ORDER BY servesEvaluation desc ";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            customerList.clear();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customerList.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getInt("servesEvaluation"),
                        resultSet.getFloat("cashback"),
                        resultSet.getString("massage")
                ));
                sum += customerList.get(count).getServesEvaluation();
                count++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        averageRating = (float) sum / count;
        return customerList;
    }
}