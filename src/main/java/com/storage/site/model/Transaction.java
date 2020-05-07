package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    private int id;  // up to 2,147,483,648
    private Type type;
    private Date date;
    private BigDecimal amount;
    private short customer_id; // up to 32,767
    private byte unit_id;  // up to 128


    public enum Type {
        CHARGE,
        PAYMENT;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
