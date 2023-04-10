package com.example.pos_system;

import com.example.pos_system.DAO.EmployeesDAOImp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ManagerController {
    @FXML
    private AnchorPane pane;
    @FXML
    private StackPane changeableScene;
    @FXML
    private Label pageTitle;

    @FXML
    private Label welcome_label = new Label();

    @FXML
    private Label managerName = new Label();

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
    void viewStatistics() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Statistics.fxml")));
            changeableScene.getChildren().removeAll();
            changeableScene.getChildren().setAll(fxml);
            pageTitle.setText("Statistics");
        } catch (Exception e) {
            POS_System.error(pane, "There are no enough products", 2, 28, 70);
        }
    }

    @FXML
    void viewFeedback() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FeedbackAndRating.fxml")));
            changeableScene.getChildren().removeAll();
            changeableScene.getChildren().setAll(fxml);
            pageTitle.setText("Feedback & Rating");
        } catch (Exception e) {
            POS_System.error(pane, "There is no rating or feedback", 2, 28, 70);
        }
    }

    @FXML
    void viewEmployees() throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Employees.fxml")));
        changeableScene.getChildren().removeAll();
        changeableScene.getChildren().setAll(fxml);
        pageTitle.setText("Employees");
    }

    @FXML
    void signOut(ActionEvent actionEvent) {
        POS_System.changeScene(actionEvent,"SignIn");
    }

    @FXML
    void initialize() {
        EmployeesDAOImp employee = new EmployeesDAOImp();
        SignInController signIn = new SignInController();
        if (Objects.equals(employee.findByUserName(signIn.getUsername()).getGender(), "Male")) {
            welcome_label.setText("Welcome MR." + employee.findByUserName(signIn.getUsername()).getName());
            managerName.setText("MR." + employee.findByUserName(signIn.getUsername()).getName());
        } else {
            welcome_label.setText("Welcome Ms." + employee.findByUserName(signIn.getUsername()).getName());
            managerName.setText("Ms." + employee.findByUserName(signIn.getUsername()).getName());
        }
    }
}
