package com.example.pos_system;

import com.example.pos_system.DAO.EmployeesDAOImp;
import com.example.pos_system.model.CurrentDate;
import com.example.pos_system.model.Employees;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Date;
import java.util.Objects;

import static com.example.pos_system.POS_System.doHashing;

public class RegistrationController {

    @FXML
    private AnchorPane pane;

    @FXML
    private ComboBox<String> jop_choiceBox;
    @FXML
    private PasswordField pf_conf_password;
    @FXML
    private PasswordField pf_password;
    @FXML
    private RadioButton rd_female;
    @FXML
    private RadioButton rd_male;
    @FXML
    private TextField tx_address;
    @FXML
    private TextField tx_birthdate;
    @FXML
    private TextField tx_id;
    @FXML
    private TextField tx_name;
    @FXML
    private TextField tx_phone;
    @FXML
    private TextField tx_salary;
    @FXML
    private TextField tx_username;

    private final String[] jobs = {"Manager", "Storekeeper", "Cashier"};
    private String gen;

    @FXML
    void selectGender() {
        if (rd_male.isSelected())
            gen = "Male";
        if (rd_female.isSelected())
            gen = "Female";
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
    void save(ActionEvent actionEvent) {
        CurrentDate currentDate = new CurrentDate();
        if (tx_id.getText().length() == 0 || tx_address.getText().length() == 0 || tx_birthdate.getText().length() == 0 ||
                tx_phone.getText().length() == 0 || tx_salary.getText().length() == 0 || tx_name.getText().length() == 0 ||
                tx_username.getText().length() == 0 || pf_password.getText().length() == 0 || pf_conf_password.getText().length() == 0
                || jop_choiceBox.getSelectionModel().getSelectedItem() == null) {
            POS_System.error(pane, "All fields must be filled out", 2, 28, 70);
        } else if (!Objects.equals(pf_password.getText(), pf_conf_password.getText())) {
            POS_System.error(pane, "Password doesn't match", 2, 28, 70);
        } else if (tx_id.getText().length() != 14) {
            POS_System.error(pane, "Invalid national ID", 2, 28, 70);
        } else if (new EmployeesDAOImp().isExist(tx_username.getText())) {
            POS_System.error(pane, "The user name is already exist", 2, 28, 70);
        } else if (new EmployeesDAOImp().duplicateID(tx_id.getText())) {
            POS_System.error(pane, "Duplicated ID! The ID is already exist", 2, 28, 70);
        } else if (Date.valueOf(tx_birthdate.getText()).compareTo(currentDate) > 0) {
            POS_System.error(pane, "Invalid Birth date", 2, 28, 70);
        } else {
            EmployeesDAOImp employeeDAO = new EmployeesDAOImp();
            Employees employee = Employees.builder()
                    .id(tx_id.getText())
                    .name(tx_name.getText())
                    .jop(jop_choiceBox.getSelectionModel().getSelectedItem())
                    .gender(gen)
                    .salary(Integer.parseInt(tx_salary.getText()))
                    .address(tx_address.getText())
                    .birthDate(Date.valueOf(tx_birthdate.getText()))
                    .phone(tx_phone.getText())
                    .userName(tx_username.getText())
                    .password(doHashing(pf_password.getText())).build();
            employeeDAO.add(employee);
            POS_System.information(pane, tx_name.getText() + " added successfully", 3, 28, 70);//need some modification
            POS_System.changeScene(actionEvent,"Registration");
        }
    }

    @FXML
    void cancel(ActionEvent actionEvent) {
        POS_System.changeScene(actionEvent,"ManagerPage");
    }

    @FXML
    void initialize() {
        jop_choiceBox.getItems().addAll(jobs);
    }

}
