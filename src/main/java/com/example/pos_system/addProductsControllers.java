package com.example.pos_system;

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
import java.sql.Date;
import java.util.Objects;

public class addProductsControllers {

    @FXML
    private TextField EXpDateFild;

    @FXML
    private TextField categoryFild;

    @FXML
    private TextField countryFild;

    @FXML
    private AnchorPane pane;

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

    @FXML
    void back(ActionEvent event) {
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
    void exit() {
        Platform.exit();
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void save() {
        CurrentDate currentDate = new CurrentDate();

        if (nameFild.getText().isEmpty() || priceAFild.getText().isEmpty() || priceBFild.getText().isEmpty() || quantityFild.getText().isEmpty()
                || EXpDateFild.getText().isEmpty() || prodDateFild.getText().isEmpty() || slaesFild.getText().isEmpty() || countryFild.getText().isEmpty()
                || categoryFild.getText().isEmpty()) {
            POS_System.error(pane, "All fields must be filled out", 2, 28, 70);
        } else if (Float.parseFloat(priceAFild.getText()) > Float.parseFloat(priceBFild.getText())) {
            POS_System.error(pane, "Price after discount greater than Price before discount", 2, 20, 70);
        } else if (Date.valueOf(EXpDateFild.getText()).compareTo(currentDate) < 0) {
            POS_System.error(pane, "The Product has been Expired", 2, 28, 70);
        } else if (Date.valueOf(EXpDateFild.getText()).compareTo(Date.valueOf(prodDateFild.getText())) < 0) {
            POS_System.error(pane, "Error in Expired Date or Prod Date ", 2, 28, 70);
        } else {
            ProductDAOImp productDAO = new ProductDAOImp();
            Product product = Product.builder()
                    .proName(nameFild.getText())
                    .priceA(Float.parseFloat(priceAFild.getText()))
                    .priceB(Float.parseFloat(priceBFild.getText()))
                    .quantity(Integer.parseInt(quantityFild.getText()))
                    .EXpDate(Date.valueOf(EXpDateFild.getText()))
                    .prodDate(Date.valueOf(prodDateFild.getText()))
                    .numOfSales(Integer.parseInt(slaesFild.getText()))
                    .counteryofprod(countryFild.getText())
                    .category(categoryFild.getText())
                    .build();
            productDAO.addNewProduct(product);
            POS_System.information(pane, "Success", 2, 28, 70);
            nameFild.setText("");
            priceAFild.setText("");
            priceBFild.setText("");
            quantityFild.setText("");
            EXpDateFild.setText("");
            EXpDateFild.setText("");
            slaesFild.setText("");
            countryFild.setText("");
            categoryFild.setText("");
            prodDateFild.setText("");

        }

    }

}
