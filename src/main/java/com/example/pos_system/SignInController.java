package com.example.pos_system;

import java.io.IOException;
import java.util.Objects;
import com.example.pos_system.DAO.EmployeeDAO;
import com.example.pos_system.DAO.EmployeesDAOImp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static com.example.pos_system.POS_System.doHashing;

public class SignInController {
    @FXML
    private AnchorPane pane;
    @FXML
    private PasswordField pf_password;

    @FXML
    private RadioButton rd_cashier;

    @FXML
    private RadioButton rd_manager;

    @FXML
    private RadioButton rd_storekeeper;

    @FXML
    private TextField tx_username;
    private String selectedJop = null;
    private static String userName;

    @FXML
    void selectJop() {
        if (rd_manager.isSelected())
            selectedJop = "Manager";
        if (rd_storekeeper.isSelected())
            selectedJop = "Storekeeper";
        if (rd_cashier.isSelected())
            selectedJop = "Cashier";
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
    void signIn(ActionEvent actionEvent) {
        EmployeeDAO employeeDAO = new EmployeesDAOImp();
        if (tx_username.getText().length() == 0 || pf_password.getText().length() == 0 || selectedJop == null) {
            POS_System.error(pane, "All fields must be filled out", 2, 28, 50);
        } else if (!employeeDAO.isExist(tx_username.getText())) {
            POS_System.error(pane, "Username doesn't exist", 2, 28, 50);
        } else if (!employeeDAO.checkPassword(tx_username.getText(), doHashing(pf_password.getText()))) {
            POS_System.error(pane, "wrong password", 2, 28, 50);
        } else if (!employeeDAO.checkJop(tx_username.getText(), selectedJop)) {
            POS_System.error(pane, "wrong Jop", 2, 28, 50);
        } else {
            if (Objects.equals(selectedJop, "Manager")) {
                userName = tx_username.getText();
                POS_System.changeScene(actionEvent,"ManagerPage");
            } else if (Objects.equals(selectedJop, "Cashier")) {
                userName = tx_username.getText();
                POS_System.changeScene(actionEvent,"Cashier");
            } else if (Objects.equals(selectedJop, "Storekeeper")) {
                userName = tx_username.getText();
                POS_System.changeScene(actionEvent,"Store");
            }
        }
    }

    @FXML
    void back(ActionEvent actionEvent) throws IOException {
        POS_System.changeScene(actionEvent,"StartPage");
    }

    public String getUsername() {
        return userName;
    }
}
