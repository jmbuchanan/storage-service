package com.storage.site.service;

import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private CustomerService customerService;
    private PaymentMethodService paymentMethodService;
    private UnitService unitService;

    public BookingService(CustomerService customerService,
                          PaymentMethodService paymentMethodService,
                          UnitService unitService) {
        this.customerService = customerService;
        this.paymentMethodService = paymentMethodService;
        this.unitService = unitService;
    }


}
