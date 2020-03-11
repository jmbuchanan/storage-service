package com.storage.site.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "transactions")
@Table
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transactionId;

}
