package com.storage.site.model

import spock.lang.Specification

class CustomerTest extends Specification {

    def "customer email is set to lowercase"() {
        given:
        def customer = new Customer()

        when:
        customer.setEmail("COOL_KID@GMAIL.COM")

        then:
        customer.getEmail() == "cool_kid@gmail.com"
    }

    def "customer email is set to lowercase from constructor" () {
        given:
        def customer = new Customer(
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

        expect:
        customer.getEmail() == "cool_kid@gmail.biz"
    }

    def "empty customer has id of 0" () {
        given:
        Customer customer = new Customer()

        expect:
        customer.getId() == 0
    }
}
