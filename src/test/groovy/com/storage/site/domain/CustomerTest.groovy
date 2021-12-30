package com.storage.site.domain

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

    def "empty customer has id of 0" () {
        given:
        Customer customer = new Customer()

        expect:
        customer.getId() == 0
    }
}
