package com.example.pos_system;

import com.example.pos_system.DAO.DBConnection;
import com.example.pos_system.DAO.ProductDAOImp;
import com.example.pos_system.model.CurrentDate;
import com.example.pos_system.model.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class EditProductControllers {

    @FXML
    private TextField EXpDateFild;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField categoryFild;

    @FXML
    private TextField countryFild;

    @FXML
    private TextField nameFild;

    @FXML
    private TextField priceAFild;

    @FXML
    private TextField priceBFild;

    @FXML
    private TextField prodDateFild;

    @FXML
    private TextField quantityFild;

    @FXML
    private TextField slaesFild;

    public static boolean isFlagD() {
        return flagD;
    }

    public static void setFlagD(boolean flagD) {
        EditProductControllers.flagD = flagD;
    }

    public static boolean flagD = false;

    @FXML
    void BackB(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Store.fxml")));
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
    void DeleteB(ActionEvent event) {
        productControllers d = new productControllers();
        ProductDAOImp productDAO = new ProductDAOImp();
        Product product = Product.builder()
                .id(d.getid()).build();
        productDAO.deleteProduct(product);
        setFlagD(true);
        BackB(event);
    }

    @FXML
    void UbdateB() {
        CurrentDate currentDate = new CurrentDate();
        if (nameFild.getText().isEmpty() || priceAFild.getText().isEmpty() || priceBFild.getText().isEmpty() || quantityFild.getText().isEmpty()
                || EXpDateFild.getText().isEmpty() || prodDateFild.getText().isEmpty() || slaesFild.getText().isEmpty() || countryFild.getText().isEmpty()
                || categoryFild.getText().isEmpty()) {
            POS_System.error(pane, "All fields must be filled out", 2, 28, 70);
        } else if (Float.parseFloat(priceAFild.getText()) > Float.parseFloat(priceBFild.getText())) {
            POS_System.error(pane, "Price after discount greater than Price before discount", 2, 19, 70);
        } else if (Date.valueOf(EXpDateFild.getText()).compareTo(currentDate) < 0) {
            POS_System.error(pane, "The Product has been Expired", 2, 28, 70);
        } else if (Date.valueOf(EXpDateFild.getText()).compareTo(Date.valueOf(prodDateFild.getText())) < 0) {
            POS_System.error(pane, "Error in Expired Date or Prod Date", 2, 28, 70);
        } else {
            productControllers d = new productControllers();
            ProductDAOImp productDAO = new ProductDAOImp();
            Product product = Product.builder()
                    .proName(nameFild.getText())
                    .priceA(Float.parseFloat(priceAFild.getText()))
                    .priceB(Float.parseFloat(priceBFild.getText()))
                    .numOfSales(Integer.parseInt(slaesFild.getText()))
                    .counteryofprod(countryFild.getText())
                    .category(categoryFild.getText())
                    .quantity(Integer.parseInt(quantityFild.getText()))
                    .EXpDate(Date.valueOf(EXpDateFild.getText()))
                    .prodDate(Date.valueOf(prodDateFild.getText()))
                    .id(d.getid())
                    .build();
            productDAO.ubdateProduct(product);
            POS_System.information(pane, "Success", 2, 28, 70);
            initialize();
        }
    }

    @FXML
    void initialize() {
        productControllers d = new productControllers();
        String query = "select * from product WHERE id =?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, d.getid());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nameFild.setText(resultSet.getString("proName"));
                priceAFild.setText(Float.toString(resultSet.getFloat("priceA")));
                priceBFild.setText(Float.toString(resultSet.getFloat("priceB")));
                quantityFild.setText(Integer.toString(resultSet.getInt("quantity")));
                EXpDateFild.setText(String.valueOf(resultSet.getDate("EXpDate")));
                prodDateFild.setText(String.valueOf(resultSet.getDate("prodDate")));
                slaesFild.setText(Integer.toString(resultSet.getInt("numOfSales")));
                countryFild.setText(resultSet.getString("counteryofprod"));
                categoryFild.setText(resultSet.getString("category"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void close() {
        Platform.exit();
    }

    @FXML
    void mini(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }
}
