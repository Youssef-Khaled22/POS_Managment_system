package com.example.pos_system;

import com.example.pos_system.DAO.CustomerDAOImp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Objects;

public class CustomerEmailController {


    @FXML
    private AnchorPane pane;

    @FXML
    private Label IDL;

    @FXML
    private Label cashbackL;

    @FXML
    private TextArea messagetext;

    @FXML
    private Rating rating;

    @FXML
    private Button submit;

    @FXML
    void back(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
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
    void exit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void submitButton(ActionEvent event) {
        CustomerDAOImp customerDAO = new CustomerDAOImp();
        customerDAO.setrating((int) rating.getRating(), CustomerControllers.getId());
        customerDAO.setFeedback(messagetext.getText(), CustomerControllers.getId());
        POS_System.information(pane, "Success", 2, 28, 70);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Done");
//        alert.setContentText("Success ");
//        alert.showAndWait();
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Customer.fxml")));
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
    void initialize() {
        IDL.setText(String.valueOf(CustomerControllers.getId()));
        CustomerDAOImp customerDAO = new CustomerDAOImp();
        int x = customerDAO.getCashback(CustomerControllers.getId());
        cashbackL.setText(String.valueOf(x));
    }

}
