package com.example.pos_system.DAO;

import com.example.pos_system.model.CurrentDate;
import com.example.pos_system.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProductDAOImp implements ProductDAO {
    ObservableList<Product> productList = FXCollections.observableArrayList();
    private Date [] Nearest5ExpDate = new Date[5];
    private String[] NameOfNearest5ExpDate = new String[5];
    private int[] daysLeftForExpire = new int[5];
    public String[] getNameOfNearest5ExpDate(){
        ExpDate();
        return NameOfNearest5ExpDate;
    }
    public Date[] getNearest5ExpDate() {
        ExpDate();
        return Nearest5ExpDate;
    }
    public int[] getdaysLeftForExpire(){
        ExpDate();
        CurrentDate date = new CurrentDate();
        Date now= Date.valueOf(date.TodayDate());
        for (int i=0;i<5;i++){
            daysLeftForExpire[i]= (int) ((getNearest5ExpDate()[i].getTime()-now.getTime())/1000/60/60/24);
        }
        return daysLeftForExpire;
    }
    @Override
    public void addNewProduct(Product product) {
        Connection conn= DBConnection.getConnection();
        if (conn==null)
        {return;}
        String query ="INSERT INTO product (proName,priceA,priceB,quantity,EXpDate,prodDate,numOfSales,counteryofprod,category)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement=conn.prepareStatement(query))
        {
            preparedStatement.setString(1, product.getProName());
            preparedStatement.setFloat(2, product.getPriceA());
            preparedStatement.setFloat(3, product.getPriceB());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setDate(5, product.getEXpDate());
            preparedStatement.setDate(6,product.getProdDate());
            preparedStatement.setInt(7,product.getNumOfSales());
            preparedStatement.setString(8, product.getCounteryofprod());
            preparedStatement.setString(9, product.getCategory());
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException er) {
                er.printStackTrace();
            }
        }
    }

    @Override
    public void deleteProduct(Product product) {
        Connection conn= DBConnection.getConnection();
        if (conn==null)
        {return;}
        String query ="DELETE FROM product WHERE id=?;";
        try (PreparedStatement preparedStatement=conn.prepareStatement(query))
        {
            preparedStatement.setInt(1,product.getId());
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void ubdateProduct(Product product){
        Connection conn= DBConnection.getConnection();
        if (conn==null)
        {return;}
        String query="UPDATE product\n" +
                "SET proName = ?,priceA=?,priceB=?,numOfSales=?,counteryofprod= ?,category= ?,quantity=?,prodDate= ?,EXpDate=? " +
                "WHERE id=?; ";
        try(PreparedStatement preparedStatement=conn.prepareStatement(query))
        {
            preparedStatement.setString(1,product.getProName());
            preparedStatement.setFloat(2,product.getPriceA());
            preparedStatement.setFloat(3,product.getPriceB());
            preparedStatement.setInt(4,product.getNumOfSales());
            preparedStatement.setString(5,product.getCounteryofprod());
            preparedStatement.setString(6,product.getCategory());
            preparedStatement.setInt(7,product.getQuantity());
            preparedStatement.setDate(8, product.getProdDate());
            preparedStatement.setDate(9, product.getEXpDate());
            preparedStatement.setInt(10,product.getId());
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
            conn.close();
            }
            catch (SQLException er) {
            er.printStackTrace();
            }
        }
    }

    @Override
    public void ExpDate() {
        String query="SELECT *FROM product";
        Connection connection= DBConnection.getConnection();;
        try(PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            productList.clear();
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next())
            {
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
        for (int i = 0; i < 5; i++) {
            Nearest5ExpDate[i] = productList.get(i).getEXpDate();
            NameOfNearest5ExpDate[i] = productList.get(i).getProName();
        }
    }

    @Override
    public int allSales() {
        int count=0;
        int sum=0;
        String query="SELECT * FROM product ";
        Connection connection= DBConnection.getConnection();;
        try(PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            productList.clear();
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next())
            {
                count++;
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
        for (int i = 0; i < count; i++) {
            sum+=productList.get(i).getNumOfSales();
        }
        return sum;
    }
}
