package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private String city;
    private State state;
    private String zip;
    private boolean isAdmin;

    public Customer() {
    }

    public Customer(long id, String email, String password, String phoneNumber, String firstName, String lastName,
                    String streetAddress, String secondStreetAddress, String city, State state, String zip, boolean isAdmin
        ) {
        this.id = id;
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

    public enum State {
        AK, AL, AR, AS, AZ, CA, CO, CT, DC, DE,
        FL, GA, GU, HI, IA, ID, IL, IN, KS, KY,
        LA, MA, MD, ME, MI, MN, MO, MP, MS, MT,
        NC, ND, NE, NH, NJ, NM, NV, NY, OH, OK,
        OR, PA, PR, RI, SC, SD, TN, TX, UM, UT,
        VA, VI, VT, WA, WI, WV, WY
    }
}
