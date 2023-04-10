package com.example.pos_system;

import com.example.pos_system.DAO.CustomerDAOImp;
import com.example.pos_system.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class FeedbackAndRatingController {

    @FXML
    private TableView<Customer> FeedbackAndRatingTable;

    @FXML
    private TableColumn<Customer, Integer> ID_column;

    @FXML
    private TableColumn<Customer, String> feedbackColumn;

    @FXML
    private TableColumn<Customer, Integer> ratingColumn;
    @FXML
    private TableView<stars> starsTable;
    @FXML
    private TableColumn<stars, ImageView> starsImageColumn;

    @FXML
    private Label averageRatingB;
    @FXML
    private Label averageRatingW;
    void loadData() {
        ObservableList<stars> list = FXCollections.observableArrayList();
        CustomerDAOImp customer = new CustomerDAOImp();
        for(int i=0;i<customer.FeedbackAndRating().size();i++) {
            list.add(new stars("F:\\My-Github\\POS-Management-System\\POS_SYSTEM\\src\\main\\resources\\com\\example\\pos_system\\star.png"));
        }FeedbackAndRatingTable.setItems(customer.FeedbackAndRating());
        ID_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("massage"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("servesEvaluation"));
        starsTable.setItems(list);
        starsImageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));

    }
    @FXML
    void initialize() {
        loadData();
        CustomerDAOImp customerDAOImp=new CustomerDAOImp();
        if(customerDAOImp.getAverageRating()>3){
            averageRatingB.setVisible(true);
            averageRatingB.setText("The average rating of service is "+customerDAOImp.getAverageRating());
        }
        else {
            averageRatingW.setVisible(true);
            averageRatingW.setText("The average rating of service is "+customerDAOImp.getAverageRating());
        }
    }
}
