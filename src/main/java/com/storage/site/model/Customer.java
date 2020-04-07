package com.storage.site.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String secondStreetAddress;
    private String state;
    private String zip;
    private String country;
    private boolean isAdmin;

    public Customer() {
    }

    public Customer(long id, String email, String password, String phoneNumber, String firstName, String lastName,
                    String streetAddress, String secondStreetAddress, String state, String zip, String country,
                    boolean isAdmin) {
        this.id = id;
        this.email = email.toLowerCase();
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.secondStreetAddress = secondStreetAddress;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.isAdmin = isAdmin;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    @Override
    public String toString() {
        if (id == 0L) {
            return "Empty Customer Object";
        } else {
            return firstName + " " + lastName;
        }
    }
}
