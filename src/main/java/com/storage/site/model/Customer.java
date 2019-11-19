package com.storage.site.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "customers")
@Table
public @Data class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(unique = true)
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
    private boolean admin;

    public Customer(){};

    public Customer(String email, String password, String phoneNumber, String firstName, String lastName,
                    String streetAddress, String secondStreetAddress, String state, String zip, String country,
                    boolean admin) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.secondStreetAddress = secondStreetAddress;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSecondStreetAddress() {
        return secondStreetAddress;
    }

    public void setSecondStreetAddress(String secondStreetAddress) {
        this.secondStreetAddress = secondStreetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
