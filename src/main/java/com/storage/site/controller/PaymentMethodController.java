package com.storage.site.controller;

import com.storage.site.model.Customer;
import com.storage.site.model.PaymentMethod;
import com.storage.site.service.CustomerService;
import com.storage.site.service.JwtService;
import com.storage.site.service.PaymentMethodService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/paymentMethods")
@AllArgsConstructor
public class PaymentMethodController {

    private final JwtService jwtService;
    private final CustomerService customerService;
    private final PaymentMethodService paymentMethodService;

    @GetMapping("/fetchByCustomerId")
    public List<PaymentMethod> fetchByCustomerId(HttpServletRequest request) {
        int customerId = jwtService.parseCustomerId(request);
        if (customerId != 0) {
            return paymentMethodService.getPaymentMethodsByCustomerId(customerId);
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/addPaymentMethod")
    public ResponseEntity<String> addPaymentMethod(@RequestBody PaymentMethod paymentMethod) throws StripeException {
        com.stripe.model.PaymentMethod stripePaymentMethod = com.stripe.model.PaymentMethod.retrieve(paymentMethod.getStripeId());
        Map<String, Object> params = new HashMap<>();
        Customer stripeCustomer = customerService.getCustomerById(paymentMethod.getCustomerId());
        params.put("customer", stripeCustomer.getStripeId());

        stripePaymentMethod.attach(params);

        paymentMethod.setDateAdded(new Date());
        paymentMethodService.save(paymentMethod);
        return new ResponseEntity<>("Payment method successfully added", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentMethodById(@PathVariable Long id) {
        paymentMethodService.setInactive(id);
        return new ResponseEntity<>("Resource deleted", HttpStatus.OK);
    }
}
