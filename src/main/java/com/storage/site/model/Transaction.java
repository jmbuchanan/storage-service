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
    private TYPE type;
    private Date date;
    private BigDecimal amount;
    private short customer_id; // up to 32,767
    private byte unit_id;  // up to 128


    public enum TYPE {
        CHARGE("charge"),
        PAYMENT("payment");

        private String name;

        private TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
