package com.example.pos_system.DAO;

import com.example.pos_system.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOTest {

    @Test
    void getCustomer() {
        CustomerDAOImp d=new CustomerDAOImp();
        Customer c=new Customer(1,5,0,"");
        assertAll(
                () -> assertEquals(c.toString(),d.getCustomer(1).toString())

        );
    }

    @Test
    void isExist() {
        CustomerDAOImp d=new CustomerDAOImp();
        assertAll(
                () -> assertEquals(true,d.isExist(1))

        );
    }

    @Test
    void getCashback() {
        CustomerDAOImp d=new CustomerDAOImp();
        assertAll(
                () -> assertEquals(0,d.getCashback(1))

        );
    }

    @Test
    void updateCashback() {
        CustomerDAOImp d=new CustomerDAOImp();
        Customer s=new Customer(1,5,30,"");
    }

    @Test
    void setFeedback() {
    }

    @Test
    void setrating() {
    }

    @Test
    void feedbackAndRating() {
    }
}