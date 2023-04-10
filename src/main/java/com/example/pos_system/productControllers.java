package com.example.pos_system;

import com.example.pos_system.DAO.DBConnection;
import com.example.pos_system.model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class productControllers {
    Connection connection = DBConnection.getConnection();
    ResultSet resultSet = null;
    static int iddd = 0;

    int getid() {
        return iddd;
    }

    void setid(int d) {
        iddd = d;
    }

    @FXML
    private TableView<Product> productTables;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<Product, String> categoryCol;

    @FXML
    private TextField categoryText;

    @FXML
    private TableColumn<Product, String> countryCol;

    @FXML
    private TableColumn<Product, String> expDateCol;

    @FXML
    private TableColumn<Product, String> idCol;

    @FXML
    private TextField idText;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> offerCol;

    @FXML
    private TableColumn<Product, String> priceCol;

    @FXML
    private TableColumn<Product, String> prodDateCol;

    @FXML
    private TableColumn<Product, String> quantityCol;

    @FXML
    private TableColumn<Product, String> salesCol;

    @FXML
    void Search() {
        if (!idText.getText().isEmpty() && !categoryText.getText().isEmpty() || idText.getText().isEmpty() && categoryText.getText().isEmpty()) {
            POS_System.error(pane, "Please choose search by id or category", 2, 28, 70);
        } else if (!idText.getText().isEmpty() && categoryText.getText().isEmpty()) {
            int id = Integer.parseInt(idText.getText());
            Connection con = DBConnection.getConnection();
            String query = "select * from product WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                productList.clear();
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
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
                    productTables.setItems(productList);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("proName"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("priceA"));
            offerCol.setCellValueFactory(new PropertyValueFactory<>("priceB"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            prodDateCol.setCellValueFactory(new PropertyValueFactory<>("EXpDate"));
            expDateCol.setCellValueFactory(new PropertyValueFactory<>("prodDate"));
            salesCol.setCellValueFactory(new PropertyValueFactory<>("numOfSales"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("counteryofprod"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        } else {
            String category = (categoryText.getText());
            Connection con = DBConnection.getConnection();
            String query = "select * from product WHERE category = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                productList.clear();
                preparedStatement.setString(1, category);
                resultSet = preparedStatement.executeQuery();
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
                    productTables.setItems(productList);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("proName"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("priceA"));
            offerCol.setCellValueFactory(new PropertyValueFactory<>("priceB"));
            quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            prodDateCol.setCellValueFactory(new PropertyValueFactory<>("EXpDate"));
            expDateCol.setCellValueFactory(new PropertyValueFactory<>("prodDate"));
            salesCol.setCellValueFactory(new PropertyValueFactory<>("numOfSales"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<>("counteryofprod"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        }

    }

    @FXML
    void exit() {
        Platform.exit();
    }

    @FXML
    void getAddView(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProducts.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    void refreshView() {
        String query = "SELECT * FROM product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            productList.clear();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("proName"),
                        resultSet.getFloat("priceA"),
                        resultSet.getFloat("priceB"),
                        resultSet.getInt("quantity"),
                        resultSet.getDate("prodDate"),
                        resultSet.getDate("EXpDate"),
                        resultSet.getInt("numOfSales"),
                        resultSet.getString("counteryofprod"),
                        resultSet.getString("category")
                ));
                productTables.setItems(productList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        if (EditProductControllers.isFlagD()) {
            POS_System.information(pane, "Success", 2, 28, 70);
            EditProductControllers.setFlagD(false);
        }
        loadDate();
    }

    private void loadDate() {

        refreshView();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("proName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("priceA"));
        offerCol.setCellValueFactory(new PropertyValueFactory<>("priceB"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        prodDateCol.setCellValueFactory(new PropertyValueFactory<>("EXpDate"));
        expDateCol.setCellValueFactory(new PropertyValueFactory<>("prodDate"));
        salesCol.setCellValueFactory(new PropertyValueFactory<>("numOfSales"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("counteryofprod"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    @FXML
    void Logout_button(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Signin.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void EDIIIT(MouseEvent event) {
        if (idText.getText().isEmpty()) {
            POS_System.error(pane, "Please enter ID", 2, 28, 70);
        } else {
            Connection connection = DBConnection.getConnection();
            String query = "SELECT * FROM product WHERE id= ?";
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(idText.getText()));
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    setid(Integer.parseInt(idText.getText()));
                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditProduct.fxml")));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    POS_System.error(pane, "ID not found", 2, 28, 70);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

}