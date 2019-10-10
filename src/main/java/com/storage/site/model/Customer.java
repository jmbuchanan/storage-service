
package com.storage.site.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "customers")
@Table
public @Data class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long customerId;
    private String email;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "second_street_address")
    private String secondStreetAddress;
    private String state;
    private String zip;
    private String country;

}
