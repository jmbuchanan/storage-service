package com.storage.site.controller;

import com.storage.site.model.PaymentMethod;
import com.storage.site.service.JwtService;
import com.storage.site.service.PaymentMethodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/paymentMethods")
public class PaymentMethodController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/fetchByCustomerId")
    public List<PaymentMethod> fetchByCustomerId(HttpServletRequest request) {

        log.info("fetching cards by customer id");
        int customerId = jwtService.parseCustomerId(request);

        if (customerId != 0) {
            return paymentMethodService.getPaymentMethodsByCustomerId(customerId);
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/addPaymentMethod")
    public ResponseEntity<String> addPaymentMethod(@RequestBody PaymentMethod paymentMethod) {

        paymentMethod.setDateAdded(new Date());

        paymentMethodService.save(paymentMethod);

        return new ResponseEntity<>("Payment method successfully added", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentMethodById(@PathVariable Long id) {
        log.info("DELETE request received for card id " + id);
        if (paymentMethodService.delete(id)) {
            return new ResponseEntity<>("Resource deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Issue deleting resource", HttpStatus.NOT_FOUND);
        }
    }
}
