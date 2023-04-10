package com.example.pos_system;

import com.example.pos_system.DAO.CustomerDAOImp;
import com.example.pos_system.DAO.DBConnection;
import com.example.pos_system.model.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CustomerControllers {
    public static void setId(int id) {
        CustomerControllers.id = id;
    }

    public static int id = 0;

    public static int getId() {
        return id;
    }

    @FXML
    private TextField IDText;

    @FXML
    private TextField IDText1;

    @FXML
    private Label category;

    @FXML
    private Label country;

    @FXML
    private Label expdate;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label proddate;

    @FXML
    private AnchorPane pane;

    @FXML
    void Login(ActionEvent event) {
        if (IDText1.getText().equals("")) {
            POS_System.error(pane, "please enter ID", 2, 28, 70);
        } else {
            CustomerDAOImp customerDAO = new CustomerDAOImp();
            if (customerDAO.isExist(Integer.parseInt(IDText1.getText()))) {
                setId(Integer.parseInt(IDText1.getText()));
                try {

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerEmail.fxml")));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.centerOnScreen();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                POS_System.error(pane, "ID is not Exist", 2, 28, 70);
            }
        }
    }

    @FXML
    void Search() {
        if (IDText.getText().isEmpty()) {
            POS_System.error(pane, "Please enter barcode", 2, 28, 70);
        } else {
            Product p = null;
            int id = Integer.parseInt(IDText.getText());
            Connection con = DBConnection.getConnection();
            String query = "select * from product WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    p = new Product(
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
                if (p == null) {
                    POS_System.error(pane, "not found", 2, 28, 70);
                } else {
                    name.setText(p.getProName());
                    price.setText(String.valueOf(p.getPriceA()));
                    country.setText(p.getCounteryofprod());
                    expdate.setText(String.valueOf(p.getEXpDate()));
                    proddate.setText(String.valueOf(p.getProdDate()));
                    category.setText(p.getCategory());

                    name.setVisible(true);
                    price.setVisible(true);
                    country.setVisible(true);
                    expdate.setVisible(true);
                    proddate.setVisible(true);
                    category.setVisible(true);

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void close() {
        Platform.exit();
    }

    @FXML
    void Back_button(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartPage.fxml")));
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
    void mini(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void initialize() {
        loadDate();
    }

    private void loadDate() {
        name.setVisible(false);
        price.setVisible(false);
        country.setVisible(false);
        expdate.setVisible(false);
        proddate.setVisible(false);
        category.setVisible(false);
    }

}
