package com.storage.site.model;

import org.junit.Test;

import static org.junit.Assert.*;

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
                1L,
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
                false
        );

        assertEquals(customer.getEmail(), "cool_kid@gmail.biz");
    }

    @Test
    public void testEmptyCustomerIdIsZero() {
        Customer customer = new Customer();
        assertEquals(customer.getId(), 0L);
    }

    @Test
    public void testEmptyCustomerToString() {
        Customer customer = new Customer();
        assertEquals(customer.toString(), "Empty Customer Object");
    }

}
