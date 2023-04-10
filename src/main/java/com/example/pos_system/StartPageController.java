package com.example.pos_system;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StartPageController {
    public Button start_button;
    @FXML
    private CheckBox customer_checkbox;

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
    boolean isCustomer() {
        return customer_checkbox.isSelected();
    }

    @FXML
    void nextScene(ActionEvent actionEvent) {
        if (isCustomer())
            POS_System.changeScene(actionEvent,"Customer");
        else
            POS_System.changeScene(actionEvent,"SignIn");
    }
}