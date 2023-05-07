package com.example.pos_system.DAO;

import com.example.pos_system.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierDAOImp implements CashierDAO {
    ObservableList<Product> productList = FXCollections.observableArrayList();

    //MostSelling
    private int[] Most5Selling;
    private String[] NameOfMost5Selling = new String[5];

    public String[] getNameOfMost5Selling() {
        MostSelling();
        if(Most5Selling.length < 5)
            return null;
        return NameOfMost5Selling;
    }

    public int[] getMost5Selling() {
        MostSelling();
        if(Most5Selling.length < 5)
            return null;
        return Most5Selling;
    }


    //getProduct (Search for ID then return(id-ProductName-Price before &After)
    public Product getProduct(int id) {
        if (isExist(id)) {
            Connection connection = DBConnection.getConnection();
            String query = "select * from product WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    return new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("proName"),
                            resultSet.getFloat("priceA"),
                            resultSet.getFloat("priceB"),
                            resultSet.getInt("quantity"),
                            resultSet.getDate("EXpDate"),
                            resultSet.getDate("prodDate"),
                            resultSet.getInt("numOfSales"),
                            resultSet.getString("counteryofprod"),
                            resultSet.getString("category")
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
        String quary = "select * from product where id=?";
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
    public void MostSelling() {
        String query = "SELECT *FROM product ORDER BY numOfSales desc LIMIT 5";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            productList.clear();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("proName"),
                        resultSet.getFloat("priceA"),
                        resultSet.getFloat("priceB"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("EXpDate"),
                        resultSet.getDate("prodDate"),
                        resultSet.getInt("numOfSales"),
                        resultSet.getString("counteryofprod"),
                        resultSet.getString("category")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Most5Selling = new int[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            Most5Selling[i] = productList.get(i).getNumOfSales();
            NameOfMost5Selling[i] = productList.get(i).getProName();
        }
    }

    @Override
    public void updateNumsOfSales_Quantity(Product product, int id) {

        Connection conn = DBConnection.getConnection();
        if (conn == null)
            return;
        String quary = "update product set numOfSales=numOfSales + ?,quantity= quantity - ? where id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(quary)) {
            preparedStatement.setInt(1, product.getCountCart());
            preparedStatement.setInt(2, product.getCountCart());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
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
    public int newId() {
        int id = -1;
        Connection conn = DBConnection.getConnection();
        if (conn == null)
            return id;
        //Set
        String query = "insert into customer (servesEvaluation, cashback,massage ) values (5,0,\"\") ";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
        //get
        String query2 = "SELECT max(id) from customer";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query2)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("max(id)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

}