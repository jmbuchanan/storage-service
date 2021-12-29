package com.storage.site.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Subscription {

    private int id;
    private String stripeId;
    private Date startDate;
    private Date endDate;
    private int customerId;
    private int unitId;
    private int paymentMethodId;

    public boolean isActive() {
        //either no end date, or end date is in future
        if (endDate == null) {
            return true;
        }
        return endDate.after(new Date());
    }

    public boolean canBeCancelledImmediately() {
        return startDate.after(new Date());
    }
}
