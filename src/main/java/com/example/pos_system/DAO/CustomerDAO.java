package com.example.pos_system.DAO;

import com.example.pos_system.model.Customer;
import javafx.collections.ObservableList;

public interface CustomerDAO {
    Customer getCustomer(int id);

    public boolean isExist(int id);
    int getCashback(int id);
    void UpdateCashback(Customer customer, int id);
    void setFeedback(String message,int id);
    void setrating(int rating,int id);
    ObservableList FeedbackAndRating();

}
