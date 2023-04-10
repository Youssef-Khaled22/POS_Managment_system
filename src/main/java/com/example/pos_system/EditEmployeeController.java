package com.example.pos_system;

import com.example.pos_system.DAO.EmployeeDAO;
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

public class EditEmployeeController {
    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> jop_choiceBox;
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
    private final String[] jobs = {"Manager", "Storekeeper", "Cashier"};
    private static String gen;
    private final EmployeesController signIn = new EmployeesController();
    private final EmployeeDAO employeeDAO = new EmployeesDAOImp();

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
    void save() {
        CurrentDate currentDate = new CurrentDate();
        if (tx_id.getText().length() == 0 || tx_address.getText().length() == 0 || tx_birthdate.getText().length() == 0 ||
                tx_phone.getText().length() == 0 || tx_salary.getText().length() == 0 || tx_name.getText().length() == 0
                || jop_choiceBox.getSelectionModel().getSelectedItem() == null) {
            POS_System.error(pane, "All fields must be filled out", 2, 28, 60);
        } else if (tx_id.getText().length() != 14) {
            POS_System.error(pane, "Invalid national ID", 2, 28, 60);
        } else if (Date.valueOf(tx_birthdate.getText()).compareTo(currentDate) > 0) {
            POS_System.error(pane, "Invalid Birth date", 2, 28, 60);
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
                    .userName(signIn.getUsername())
                    .password(employeeDAO.findByUserName(signIn.getUsername()).getPassword()).build();
            employeeDAO.update(employee, signIn.getUsername());
            POS_System.information(pane, "All changes have been saved", 2, 28, 60);
        }
    }

    @FXML
    void cancel(ActionEvent actionEvent) {
        POS_System.changeScene(actionEvent,"ManagerPage");
    }

    @FXML
    void initialize() {
        jop_choiceBox.getItems().addAll(jobs);
        tx_id.setText(employeeDAO.findByUserName(signIn.getUsername()).getId());
        tx_name.setText(employeeDAO.findByUserName(signIn.getUsername()).getName());
        tx_salary.setText(String.valueOf(employeeDAO.findByUserName(signIn.getUsername()).getSalary()));
        tx_address.setText(employeeDAO.findByUserName(signIn.getUsername()).getAddress());
        tx_birthdate.setText(employeeDAO.findByUserName(signIn.getUsername()).getBirthDate().toString());
        tx_phone.setText(employeeDAO.findByUserName(signIn.getUsername()).getPhone());
        if (Objects.equals(employeeDAO.findByUserName(signIn.getUsername()).getGender(), "Male")) {
            rd_male.setSelected(true);
            gen = "Male";
        } else {
            rd_female.setSelected(true);
            gen = "Female";
        }
        jop_choiceBox.setValue(employeeDAO.findByUserName(signIn.getUsername()).getJop());
    }
}
