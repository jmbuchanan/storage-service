package com.storage.site.model;

import lombok.Data;

@Data
public class Price {

    private final int id;
    private final String stripeId;
    private final int price;

}
