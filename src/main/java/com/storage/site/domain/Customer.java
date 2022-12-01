package com.storage.site.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int id;
    private String stripeId;
    @Email
    private String email;
    @NotBlank(message = "Password is mandatory.")
    private String password;
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Street address is mandatory")
    private String streetAddress;
    private String secondStreetAddress;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotNull(message = "State is mandatory")
    private State state;
    @NotBlank(message = "Zip is mandatory")
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
