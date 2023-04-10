package com.example.pos_system;

import com.example.pos_system.DAO.EmployeeDAO;
import com.example.pos_system.DAO.EmployeesDAOImp;
import com.example.pos_system.model.Employees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class EmployeesController {
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField tx_username;
    @FXML
    private TableView<Employees> EmployeesTable;
    @FXML
    private TableColumn<Employees, String> address_column;
    @FXML
    private TableColumn<Employees, String> birthdate_column;
    @FXML
    private TableColumn<Employees, String> gender_column;
    @FXML
    private TableColumn<Employees, String> id_column;
    @FXML
    private TableColumn<Employees, String> jop_column;
    @FXML
    private TableColumn<Employees, String> name_column;
    @FXML
    private TableColumn<Employees, String> phone_column;
    @FXML
    private TableColumn<Employees, String> salary_column;
    @FXML
    private TableColumn<Employees, String> userName_column;
    private static String userName;

    @FXML
    void addEmployee(ActionEvent actionEvent) {
        POS_System.changeScene(actionEvent,"Registration");
    }

    @FXML
    void editEmployee(ActionEvent actionEvent) {
        if (tx_username.getText().length() == 0) {
            POS_System.error(pane, "Must enter user name", 2, 28, 50);
        } else if (!(new EmployeesDAOImp().isExist(tx_username.getText()))) {
            POS_System.error(pane, "The user name doesn't exist", 2, 28, 50);
        } else {
            userName = tx_username.getText();
            POS_System.changeScene(actionEvent,"Edit_Employee");
        }
    }

    @FXML
    void deleteEmployee() {
        if (tx_username.getText().length() == 0) {
            POS_System.error(pane, "Must enter user name", 2, 28, 50);
        } else if (!(new EmployeesDAOImp().isExist(tx_username.getText()))) {
            POS_System.error(pane, "The user name doesn't exist", 2, 28, 50);
        } else {
            EmployeeDAO employee = new EmployeesDAOImp();
            POS_System.information(pane, employee.findByUserName(tx_username.getText()).getName() + " deleted successfully", 2, 28, 50);
            employee.deleteByUserName(tx_username.getText());
            loadData();
        }
    }

    void loadData() {
        EmployeeDAO employees = new EmployeesDAOImp();
        EmployeesTable.setItems(employees.findAll());
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        jop_column.setCellValueFactory(new PropertyValueFactory<>("jop"));
        gender_column.setCellValueFactory(new PropertyValueFactory<>("gender"));
        salary_column.setCellValueFactory(new PropertyValueFactory<>("salary"));
        address_column.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthdate_column.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userName_column.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }

    public String getUsername() {
        return userName;
    }

    @FXML
    void initialize() {
        loadData();
    }
}
