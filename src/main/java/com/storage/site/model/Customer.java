package com.storage.site.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class Customer implements UserDetails {

    private long customerId;
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

    private Collection<? extends GrantedAuthority> authorities;

    public Customer(long customerId, String email, String password, String phoneNumber, String firstName, String lastName,
                    String streetAddress, String secondStreetAddress, String state, String zip, String country,
                    boolean isAdmin) {
        this.customerId = customerId;
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
        this.isAdmin = isAdmin;

        this.setAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (this.isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
