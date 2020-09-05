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

    private int id;
    private Type type;
    private Date date;
    private BigDecimal amount;
    private int customerId;
    private int unitId;


    public enum Type {
        CHARGE,
        PAYMENT;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
