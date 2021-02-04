package com.storage.site.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Locale;

@Slf4j
@Getter
@Setter
public class Customer {

    private int id;
    private String stripeId;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String secondStreetAddress;
    private String city;
    private State state;
    private String zip;
    private Date dateJoined;
    private boolean isAdmin;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Customer() {
    }

    public Customer(int id, String stripeId, String email, String password, String phoneNumber, String firstName, String lastName,
                    String streetAddress, String secondStreetAddress, String city, State state, String zip, Date dateJoined, boolean isAdmin
        ) {
        this.id = id;
        this.stripeId = stripeId;
        this.email = email.toLowerCase();
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.secondStreetAddress = secondStreetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.dateJoined = dateJoined;
        this.isAdmin = isAdmin;

    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Empty Customer Object";
        } else {
            return firstName + " " + lastName;
        }
    }

    public enum State {
        AK, AL, AR, AS, AZ, CA, CO, CT, DC, DE,
        FL, GA, GU, HI, IA, ID, IL, IN, KS, KY,
        LA, MA, MD, ME, MI, MN, MO, MP, MS, MT,
        NC, ND, NE, NH, NJ, NM, NV, NY, OH, OK,
        OR, PA, PR, RI, SC, SD, TN, TX, UM, UT,
        VA, VI, VT, WA, WI, WV, WY
    }
}
