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
                () -> assertEquals(true,d.isExist(1)),
                () -> assertEquals(false,d.isExist(4))


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
        Customer s=new Customer(1,5,300,"");
        d.UpdateCashback(s,1);
        assertAll(
                () -> assertEquals(300,d.getCashback(1))

        );

    }

    @Test
    void setFeedback() {
        CustomerDAOImp d=new CustomerDAOImp();
        Customer c=new Customer(1,5,300,"it's good service");
        d.setFeedback("it's good service",1);
        assertAll(
                () -> assertEquals(c.toString(),d.getCustomer(1).toString())

        );
    }

    @Test
    void setrating() {
        CustomerDAOImp d=new CustomerDAOImp();
        Customer c=new Customer(1,1,300,"it's good service");
        d.setrating(1,1);
        assertAll(
                () -> assertEquals(c.toString(),d.getCustomer(1).toString())

        );
    }

    @Test
    void feedbackAndRating() {
        CustomerDAOImp d=new CustomerDAOImp();
        assertAll(
                () -> assertEquals(3,d.getAverageRating())

        );
    }
}