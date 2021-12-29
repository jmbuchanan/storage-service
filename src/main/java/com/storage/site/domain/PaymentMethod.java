package com.storage.site.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentMethod {

    private int id;
    private String stripeId;
    private String cardBrand;
    private Date dateAdded;
    private String lastFour;
    private int customerId;
    private boolean canBeDeleted;

        @Override
        //example: Visa ending in 4229 belonging to customer 3.
        public String toString() {
            return StringUtils.capitalize(cardBrand) +
                    " ending in " + lastFour +
                    " belonging to customer " + customerId + ".";
        }
}
