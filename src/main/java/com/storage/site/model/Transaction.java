package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    private int id;
    private Type type;
    private Date requestDate;
    private Date executionDate;
    private int customerId;
    private int unitId;
    private int paymentMethodId;

    public enum Type {
        BOOK,
        CANCEL;

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
