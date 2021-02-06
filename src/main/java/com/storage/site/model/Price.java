package com.storage.site.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Price {

    private int id;
    private String stripeId;
    private int price;

}
