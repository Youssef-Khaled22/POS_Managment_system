package com.example.pos_system;

import com.example.pos_system.DAO.CustomerDAOImp;
import com.example.pos_system.model.CurrentDate;
import com.example.pos_system.model.Customer;
import com.example.pos_system.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.example.pos_system.DAO.CashierDAOImp;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CashierController {
    CashierDAOImp cashierDAO = new CashierDAOImp();
    ObservableList<Product> checkoutList;

    public CashierController() {
        this.checkoutList = FXCollections.observableArrayList();
        this.CashbackFlag = false;
    }

    private float remain;
    @FXML
    private Label RemainingLabel;
    private Stage stage;

    boolean CashbackFlag;

    @FXML
    private AnchorPane cashierPane;

    @FXML
    private TableColumn<Product, String> AfterDiscountColumn;

    @FXML
    private TableColumn<Product, String> COFColumn;

    @FXML
    private TableView<Product> CheckoutTable;

    @FXML
    private TableColumn<Product, String> IDColumn;

    @FXML
    private TableColumn<Product, String> NameColumn;

    @FXML
    private TableColumn<Product, String> PriceColumn;

    @FXML
    private TableColumn<Product, String> QuantityColumn;

    @FXML
    private TextField txCode;

    @FXML
    private TextField txDiscount;

    @FXML
    private TextField txPayed;

    @FXML
    private TextField txTotal;

    @FXML
    private TextField txCardId;

    @FXML
    private TextField txCashback;


    //AddToCart button to check validation of barcode (errors are taken in consideration)
    @FXML
    void AddToCart() {
        CashierDAOImp cashierDAO = new CashierDAOImp();
        CurrentDate currentDate = new CurrentDate();
        if (txCode.getText().equals("")) {

            POS_System.error(cashierPane, "No Barcode!", 2, 28, 60);
        } else {
            int id = Integer.parseInt(txCode.getText());
            Product p = cashierDAO.getProduct(id);

            if (p == null) {

                POS_System.error(cashierPane, "Product barcode isn't exist!", 2, 28, 60);

            } else if (p.getEXpDate().compareTo(currentDate) < 0) {
                POS_System.error(cashierPane, "The Product has been Expired!", 2, 28, 60);
            } else {
                boolean found = false;
                for (int i = 0; i < checkoutList.size(); i++) {
                    if (p.getId() == checkoutList.get(i).getId()) { //Adding Same Product
                        if (checkoutList.get(i).getCountCart() < checkoutList.get(i).getQuantity()) { //Check Quantity
                            checkoutList.get(i).setCountCart(checkoutList.get(i).getCountCart() + 1);
                            checkoutList.set(i, checkoutList.get(i));
                        } else {
                            POS_System.error(cashierPane, "The Product Quantity has been Finished!", 2, 28, 60);
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) {   //new Product
                    if (p.getQuantity() > 0) {  //Check Quantity
                        checkoutList.add(p);
                    } else {

                        POS_System.error(cashierPane, "The Product Quantity has been Finished!", 2, 28, 60);
                    }
                }
                CheckoutTable.setItems(checkoutList);
                float sumB = 0, sumA = 0;
                for (Product product : checkoutList) {
                    sumB += product.getPriceB() * product.getCountCart();
                    sumA += product.getPriceA() * product.getCountCart();
                }
                txTotal.setText(Float.toString(sumA));
                txDiscount.setText(Float.toString(sumB - sumA));
            }
        }
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("proName"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("priceA"));
        AfterDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("priceB"));
        QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("countCart"));
        COFColumn.setCellValueFactory(new PropertyValueFactory<>("counteryofprod"));
    }

    @FXML
    void EnterID_Btn() {
        if (txCardId.getText().equals("")) {

            POS_System.error(cashierPane, "No Card ID!", 2, 28, 60);

        } else {
            CustomerDAOImp customerDAO = new CustomerDAOImp();
            int id = Integer.parseInt(txCardId.getText());
            Customer c = customerDAO.getCustomer(id);
            if (c == null) {

                POS_System.error(cashierPane, "User Not found!", 2, 28, 60);

            } else {
                txCashback.setText(Float.toString(c.getCashback()));
                CashbackFlag = true;
            }
        }
    }

    @FXML
    void Complete_Btn(ActionEvent event) throws IOException {

        if (txPayed.getText().equals("")) {

            POS_System.error(cashierPane, "No Payment Amount!", 2, 28, 60);

        } else {
            try {
                if (!CashbackFlag && Float.parseFloat(txPayed.getText()) < Float.parseFloat(txTotal.getText())) {

                    POS_System.error(cashierPane, "Invalid Payment Amount!", 2, 28, 60);

                } else if (CashbackFlag && (Float.parseFloat(txPayed.getText()) + Float.parseFloat(txCashback.getText())) < Float.parseFloat(txTotal.getText())) {

                    POS_System.error(cashierPane, "Invalid Payment Amount!", 2, 28, 60);

                } else if (CashbackFlag && (Float.parseFloat(txPayed.getText()) + Float.parseFloat(txCashback.getText())) > Float.parseFloat(txTotal.getText())) {
                    int id = Integer.parseInt(txCardId.getText());
                    CustomerDAOImp customerDAO = new CustomerDAOImp();
                    Customer c = customerDAO.getCustomer(id);
                    if (c == null) {

                        POS_System.error(cashierPane, "Customer isn't exist!", 2, 28, 60);

                    } else {
                        if (c.getCashback() >= (Float.parseFloat(txTotal.getText()))) {
                            c.setCashback((c.getCashback() - (Float.parseFloat(txTotal.getText()))));
                        } else {
                            c.setCashback((float) (0.02 * (Float.parseFloat(txPayed.getText()) - remain)));
                        }
                        customerDAO.UpdateCashback(c, Integer.parseInt(txCardId.getText()));
                    }
                    for (Product product : checkoutList) {
                        cashierDAO.updateNumsOfSales_Quantity(product, product.getId());
                    }
                    //new page
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Cashier.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.centerOnScreen();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    //new page
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Cashier.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.centerOnScreen();
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (NumberFormatException e) {
                POS_System.error(cashierPane, "Please enter valid payment amount", 2, 28, 60);
            }
        }
    }

    @FXML
    void viewRemaining() {
        try {
            if (txTotal.getText().length() != 0 && txPayed.getText().length() != 0) {
                RemainingLabel.setVisible(false);
                if (!CashbackFlag)
                    remain = -Float.parseFloat(txTotal.getText()) + Float.parseFloat(txPayed.getText());
                else
                    remain = -Float.parseFloat(txTotal.getText()) + Float.parseFloat(txPayed.getText()) + Float.parseFloat(txCashback.getText());
                if (remain >= 0) {
                    RemainingLabel.setText("Remaining = " + remain + " EGP");
                    RemainingLabel.setVisible(true);
                }
            }
        } catch (NumberFormatException e) {
            POS_System.error(cashierPane, "Please enter valid payment amount", 2, 28, 60);
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
    void Logout_button(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignIn.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void AddCustomer_btn() {
        int id = cashierDAO.newId();
        if (id == -1) {
            POS_System.error(cashierPane, "Error adding new Customer please try again later", 2, 28, 60);
        } else {
            txCardId.setText(Integer.toString(id));
        }
    }
}
