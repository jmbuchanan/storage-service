package com.storage.site.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testEmailFromSetterIsLowercase() {
        Customer customer = new Customer();
        customer.setEmail("COOL_KID@GMAIL.BIZ");

        assertEquals(customer.getEmail(), "cool_kid@gmail.biz");
    }

    @Test
    public void testEmailFromConstructorIsLowercase() {
        Customer customer = new Customer(
                1,
                "ImmaStripeCustomerId",
                "COOL_KID@GMAIL.BIZ",
                "pass",
                "phone",
                "first",
                "last",
                "streetAddr",
                "streetAddr2",
                "city",
                Customer.State.GA,
                "zip",
                new Date(),
                false
        );

        assertEquals(customer.getEmail(), "cool_kid@gmail.biz");
    }

    @Test
    public void testEmptyCustomerIdIsZero() {
        Customer customer = new Customer();
        assertEquals(customer.getId(), 0);
    }

    @Test
    public void testEmptyCustomerToString() {
        Customer customer = new Customer();
        assertEquals(customer.toString(), "Empty Customer Object");
    }

}
