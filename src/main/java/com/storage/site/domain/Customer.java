package com.storage.site.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
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
